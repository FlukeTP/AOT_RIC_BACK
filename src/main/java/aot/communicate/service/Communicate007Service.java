package aot.communicate.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.DoctypeConstants;
import aot.communicate.model.RicCommunicateInfo;
import aot.communicate.model.RicCommunicateReqHdr;
import aot.communicate.repository.Communicate007Dao;
import aot.communicate.repository.CommunicateChargeRatesConfigDao;
import aot.communicate.repository.jpa.RicCommunicateInfoRepository;
import aot.communicate.vo.request.Communicate007Req;
import aot.communicate.vo.response.Communicate007Res;
import aot.communicate.vo.response.CommunicateConfigRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_10;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate007Service {
	private static final Logger logger = LoggerFactory.getLogger(Communicate007Service.class);
	
	@Autowired
	private Communicate007Dao communicate007Dao;
	
	@Autowired
	private RicCommunicateInfoRepository ricCommunicateInfoRepository;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_1_10 sapArRequest_1_10;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private CommunicateChargeRatesConfigDao communicateChargeRatesConfigDao;
	
	public Integer checkBeforeSynData() {
		/* _______ check Data Empty _______ */
		Integer countSearch = ricCommunicateInfoRepository.countByPeriodMonth(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
		if (countSearch == 0) {
			/* update flagSyncInfo 'Y' to 'N' All id */
			communicate007Dao.updateFlagInfo(FLAG.Y_FLAG, FLAG.N_FLAG, null);
		}
		return communicate007Dao.countByFlagInfoBeforeSyncData();
	}
	
	public List<Communicate007Res> search(Communicate007Req request) {
		return communicate007Dao.findByCondition(request);
	}

	public List<SapResponse> sendToSap(List<Long> idx) throws JsonProcessingException {
		List<SapResponse> responseSap = new ArrayList<SapResponse>();

		/* _____________ find all by id _____________ */
		Iterable<RicCommunicateInfo> checkboxList = ricCommunicateInfoRepository.findAllById(idx);

		for (RicCommunicateInfo info : checkboxList) {
			/* _____________ set ARrequest sap _____________ */
			ArRequest dataSend = sapArRequest_1_10.getARRequest(UserLoginUtils.getUser().getAirportCode(),
					SAPConstants.COMCODE, DoctypeConstants.IH, info.getId(), "COMMU007");
			responseSap.add(setResponseSap(dataSend, info));
		}
		return responseSap;
	}
	
	private SapResponse setResponseSap(ArRequest dataSend, RicCommunicateInfo request) throws JsonProcessingException {
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(request.getId());
		reqConnection.setTableName("RIC_COMMUNICATE_INFO");
		reqConnection.setColumnId("id");
//		reqConnection.setColumnInvoiceNo("invoice_no");
//		reqConnection.setColumnTransNo("transaction_no");
//		reqConnection.setColumnSapJsonReq("sap_json_req");
//		reqConnection.setColumnSapJsonRes("sap_json_res");
//		reqConnection.setColumnSapError("sap_error");
//		reqConnection.setColumnSapStatus("sap_status");
		SapResponse sapResponse = sapARService.setSapConnection(reqConnection);
		return sapResponse;
	}
	
	@Transactional
	public Integer syncData(String periodMonth) throws IOException {
		logger.info("syncData Communicate007 Start");
		long start = System.currentTimeMillis();
		/*
		 * data initial from register
		 * */
		List<RicCommunicateInfo> infoList = new ArrayList<RicCommunicateInfo>();
		RicCommunicateInfo info = null;
		List<RicCommunicateReqHdr> ResReq = communicate007Dao.checkCancelDateBeforeSyncData(periodMonth);
		for (RicCommunicateReqHdr initData : ResReq) {
			/* _________ set data register _________ */
			info = setDataRegister(initData);
			info.setPeriodMonth(periodMonth);

			/* __________ find and calculate config __________ */
			BigDecimal chargeRate = BigDecimal.ZERO;
			try {
				CommunicateConfigRes dataRes = communicateChargeRatesConfigDao.handheidTransceiver(new Date());
				chargeRate = dataRes.getChargeRate();
			} catch (Exception e) {
				logger.info("findChargeRatesConfig => data is null");
			}
			
			/* __________ check and calculate charge rate __________ */
			String periodMonthReq = ConvertDateUtils.formatDateToString(initData.getRequestDate(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
			String periodMonthCancel = ConvertDateUtils.formatDateToString(initData.getCancelDate(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
			Calendar calReq = Calendar.getInstance();
			calReq.setTime(initData.getRequestDate());
			int dayReq = calReq.get(Calendar.DATE);
			int days = 0;
			int maxDayOfMonth = 0;
			Calendar calCancel = Calendar.getInstance();
			if (periodMonth.equals(periodMonthReq)) {
				if (periodMonth.equals(periodMonthCancel)) {
					/* __________ request and cancel this period month __________ */
					calCancel.setTime(initData.getCancelDate());
					maxDayOfMonth = calCancel.getActualMaximum(Calendar.DAY_OF_MONTH);
					days = calCancel.get(Calendar.DATE) - dayReq;
				} else {
					/* __________ request this period month __________ */
					maxDayOfMonth = calReq.getActualMaximum(Calendar.DAY_OF_MONTH);
					days = maxDayOfMonth - dayReq;
				}
				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate, info.getPhoneAmount(), avgOfMonth(days, maxDayOfMonth)));
			} else if(periodMonth.equals(periodMonthCancel)) {
				/* __________ cancel this period month __________ */
				calCancel.setTime(initData.getCancelDate());
				maxDayOfMonth = calCancel.getActualMaximum(Calendar.DAY_OF_MONTH);
				days = calCancel.get(Calendar.DATE);
				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate, info.getPhoneAmount(), avgOfMonth(days, maxDayOfMonth)));
			} else {
				info.setTotalChargeRates(NumberUtils.roundUpTwoDigit(chargeRate.multiply(info.getPhoneAmount())));
			}
			
			info.setVat(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(info.getTotalChargeRates())));
			info.setTotalAll(NumberUtils.roundUpTwoDigit(info.getTotalChargeRates().add(info.getVat())));
			
			/* __________ add to list info __________ */
			infoList.add(info);
			
			/* __________ update flag insert __________ */
//			communicate007Dao.updateFlagInfo(FLAG.N_FLAG, FLAG.Y_FLAG, initData.getId());
		}
		ricCommunicateInfoRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData Communicate007 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicCommunicateInfo setDataRegister(RicCommunicateReqHdr initData) {
		RicCommunicateInfo info = new RicCommunicateInfo();
		info.setPhoneAmount(initData.getPhoneAmount());
		info.setTransactionNoReq(initData.getTransactionNo());
		info.setRoNumber(initData.getRoNumber());
		info.setRoName(initData.getRoName());
		info.setEntreprenuerCode(initData.getEntreprenuerCode());
		info.setEntreprenuerName(initData.getEntreprenuerName());
		info.setContractNo(initData.getContractNo());
		info.setCustomerBranch(initData.getCustomerBranch());
		info.setCreatedBy(UserLoginUtils.getCurrentUsername());
		info.setCreatedDate(new Date());
		info.setIsDeleted(FLAG.N_FLAG);
		return info;
	}
	
	private BigDecimal avgOfMonth(int days, int maxDayOfMonth) {
		return new BigDecimal(Double.valueOf(days) / Double.valueOf(maxDayOfMonth));
	}
	
	private BigDecimal calculateTotalChargeRate(BigDecimal chargeRate, BigDecimal phoneAmount, BigDecimal avgOfMonth) {
		return NumberUtils.roundUpTwoDigit(chargeRate.multiply(phoneAmount).multiply(avgOfMonth));
	}

}
