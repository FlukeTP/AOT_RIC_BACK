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
import aot.common.constant.RICConstants;
import aot.communicate.model.RicCommunicateFlightScheduleInfo;
import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.communicate.repository.Communicate008Dao;
import aot.communicate.repository.CommunicateChargeRatesConfigDao;
import aot.communicate.repository.jpa.RicCommunicateFlightScheduleInfoRepository;
import aot.communicate.vo.request.Communicate008Req;
import aot.communicate.vo.response.CommunicateConfigRes;
import aot.communicate.vo.response.Communicate008Res;
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
public class Communicate008Service {
	private static final Logger logger = LoggerFactory.getLogger(Communicate008Service.class);
	
	@Autowired
	private RicCommunicateFlightScheduleInfoRepository ricCommunicateFlightScheduleInfoRepository;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_1_10 sapArRequest_1_10;
	
	@Autowired
	private CommunicateChargeRatesConfigDao communicateChargeRatesConfigDao;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private Communicate008Dao communicate008Dao;

	public List<Communicate008Res> search(Communicate008Req request) {
		return communicate008Dao.findByCondition(request);
	}

	public List<SapResponse> sendToSap(List<Long> idx) throws JsonProcessingException {
		List<SapResponse> responseSap = new ArrayList<SapResponse>();

		/* _____________ find all by id _____________ */
		Iterable<RicCommunicateFlightScheduleInfo> checkboxList = ricCommunicateFlightScheduleInfoRepository.findAllById(idx);

		for (RicCommunicateFlightScheduleInfo info : checkboxList) {
			/* _____________ set ARrequest sap _____________ */
			ArRequest dataSend = sapArRequest_1_10.getARRequest(UserLoginUtils.getUser().getAirportCode(),
					SAPConstants.COMCODE, DoctypeConstants.IH, info.getId(), "COMMU008");
			responseSap.add(setResponseSap(dataSend, info));
		}
		return responseSap;
	}
	
	private SapResponse setResponseSap(ArRequest dataSend, RicCommunicateFlightScheduleInfo request) throws JsonProcessingException {
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(request.getId());
		reqConnection.setTableName("ric_communicate_flight_schedule_info");
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
		logger.info("syncData Communicate008 Start");
		long start = System.currentTimeMillis();
		
		/*
		 * data initial from register
		 * */
		List<RicCommunicateFlightScheduleInfo> infoList = new ArrayList<RicCommunicateFlightScheduleInfo>();
		RicCommunicateFlightScheduleInfo info = null;
		List<RicCommunicateReqFlightScheduleHdr> ResReq = communicate008Dao.checkCancelDateBeforeSyncData(periodMonth);
		for (RicCommunicateReqFlightScheduleHdr initData : ResReq) {
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
				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate, avgOfMonth(days, maxDayOfMonth)));
			} else if(periodMonth.equals(periodMonthCancel)) {
				/* __________ cancel this period month __________ */
				calCancel.setTime(initData.getCancelDate());
				maxDayOfMonth = calCancel.getActualMaximum(Calendar.DAY_OF_MONTH);
				days = calCancel.get(Calendar.DATE);
				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate,  avgOfMonth(days, maxDayOfMonth)));
			} else {
				info.setTotalChargeRates(NumberUtils.roundUpTwoDigit(chargeRate));
			}
			
			info.setVat(NumberUtils.roundUpTwoDigit(info.getTotalChargeRates().multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue()))));
			info.setTotalAll(NumberUtils.roundUpTwoDigit(info.getTotalChargeRates().add(info.getVat())));
			
			/* __________ add to list info __________ */
			infoList.add(info);
			
			/* __________ update flag insert __________ */
//			communicate008Dao.updateFlagInfo(FLAG.N_FLAG, FLAG.Y_FLAG, initData.getId());
		}
		ricCommunicateFlightScheduleInfoRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData Communicate008 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicCommunicateFlightScheduleInfo setDataRegister(RicCommunicateReqFlightScheduleHdr initData) {
		RicCommunicateFlightScheduleInfo info = new RicCommunicateFlightScheduleInfo();
		info.setTransactionNoReq(initData.getTransactionNo());
		info.setRentalAreaCode(initData.getRentalAreaCode());
		info.setRentalAreaName(initData.getRentalAreaName());
		info.setEntreprenuerCode(initData.getEntreprenuerCode());
		info.setEntreprenuerName(initData.getEntreprenuerName());
		info.setContractNo(initData.getContractNo());
		info.setCustomerBranch(initData.getCustomerBranch());
		info.setRequestDate(initData.getRequestDate());
		info.setEndDate(initData.getEndDate());
		info.setCreatedBy(UserLoginUtils.getCurrentUsername());
		info.setCreatedDate(new Date());
		info.setIsDeleted(FLAG.N_FLAG);
		return info;
	}
	
	private BigDecimal avgOfMonth(int days, int maxDayOfMonth) {
		return new BigDecimal(Double.valueOf(days) / Double.valueOf(maxDayOfMonth));
	}
	
	private BigDecimal calculateTotalChargeRate(BigDecimal chargeRate, BigDecimal avgOfMonth) {
		return NumberUtils.roundUpTwoDigit(chargeRate.multiply(avgOfMonth));
	}

}
