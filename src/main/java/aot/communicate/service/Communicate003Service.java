package aot.communicate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.communicate.model.RicCommunicateChangeAirlineLogo;
import aot.communicate.repository.Communicate003Dao;
import aot.communicate.repository.jpa.RicCommunicateChangeAirlineLogoRepository;
import aot.communicate.vo.request.Communicate003Req;
import aot.communicate.vo.response.Communicate003Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_4_12;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate003Service {

	private static final Logger logger = LoggerFactory.getLogger(Communicate003Service.class);

	@Autowired
	private RicCommunicateChangeAirlineLogoRepository ricCommuChangeAirlineLogoRepository;

	@Autowired
	private Communicate003Dao communi003Dao;

	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_4_12 sapArRequest_4_12;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(Long id) throws JsonProcessingException {
		RicCommunicateChangeAirlineLogo logo = ricCommuChangeAirlineLogoRepository.findById(id).get();
		
		/* __________________ set request SAP __________________ */
		ArRequest dataSend = sapArRequest_4_12.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
				DoctypeConstants.I8, logo);
		
		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		
		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(logo.getCommuChangeLogoId());
		reqConnection.setTableName("ric_communicate_change_airline_logo");
		reqConnection.setColumnId("commu_change_logo_id");
//		reqConnection.setColumnInvoiceNo("invoice_no");
//		reqConnection.setColumnTransNo("transaction_no");
//		reqConnection.setColumnSapJsonReq("sap_json_req");
//		reqConnection.setColumnSapJsonRes("sap_json_res");
		reqConnection.setColumnSapError("sap_error_desc");
//		reqConnection.setColumnSapStatus("sap_status");
		/* __________________ set connection SAP __________________ */
		return sapARService.setSapConnection(reqConnection);
	}

	public List<Communicate003Res> getListAll(Communicate003Req request) throws Exception {

		logger.info("getListAll");
		List<Communicate003Res> list = new ArrayList<>();
		try {
			list = communi003Dao.findAllList(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public Communicate003Res findById(Communicate003Req request) throws Exception {
		logger.info("Communi003Service::findById");
		Communicate003Res res = new Communicate003Res();
		RicCommunicateChangeAirlineLogo Communicate = ricCommuChangeAirlineLogoRepository
				.findById(Long.valueOf(request.getCommuChangeLogoId())).get();
		res.setCommuChangeLogoId(Communicate.getCommuChangeLogoId());
		res.setCustomerCode(Communicate.getCustomerCode());
		res.setCustomerName(Communicate.getCustomerName());
		res.setContractNo(Communicate.getContractNo());
		res.setCustomerBranch(Communicate.getCustomerBranch());
		res.setRentalAreaName(Communicate.getRentalAreaName());
		res.setBankName(Communicate.getBankName());
		res.setBankBranch(Communicate.getBankBranch());
		res.setBankExplanation(Communicate.getBankExplanation());
		res.setBankExpNo(Communicate.getBankExpNo());
		res.setBankGuaranteeNo(Communicate.getBankGuaranteeNo());
		res.setStartDate(ConvertDateUtils.formatDateToString(Communicate.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		res.setEndDate(ConvertDateUtils.formatDateToString(Communicate.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		res.setServiceType(Communicate.getServiceType());
		res.setChargeRates(Communicate.getChargeRates());
		res.setTotalAmount(Communicate.getTotalAmount());
		res.setChargeRatesDF(NumberUtils.toDecimalFormat(Communicate.getChargeRates(), true));
		res.setTotalAmountDF(NumberUtils.toDecimalFormat(Communicate.getTotalAmount(), true));
		res.setRemark(Communicate.getRemark());
		
		/* _________ check wording payment type _________ */
		res.setPaymentType(Communicate.getPaymentType());
		switch (res.getPaymentType()) {
		case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
			res.setPaymentTypeTH(CommunicateConstants.PAYMENT_TYPE.CASH.DESC_TH);
			break;
		case CommunicateConstants.PAYMENT_TYPE.BILL.DESC_EN:
			res.setPaymentTypeTH(CommunicateConstants.PAYMENT_TYPE.BILL.DESC_TH);
			break;
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Communicate003Req request) throws Exception {
		logger.info("Communi003Service::save");

		RicCommunicateChangeAirlineLogo logo = null;
		if (StringUtils.isNotBlank(request.getCommuChangeLogoId())) {
			logo = ricCommuChangeAirlineLogoRepository.findById(Long.valueOf(request.getCommuChangeLogoId())).get();
			logo.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			logo.setUpdatedDate(new Date());
		} else {
			logo = new RicCommunicateChangeAirlineLogo();
			logo.setSapStatus(RICConstants.STATUS.PENDING);
			logo.setCreatedBy(UserLoginUtils.getCurrentUsername());
			logo.setCreatedDate(new Date());
			logo.setIsDelete(RICConstants.STATUS.NO);
		}
		// set data
		logo.setCustomerCode(request.getCustomerCode());
		logo.setCustomerName(request.getCustomerName());
		logo.setContractNo(request.getContractNo());
		logo.setCustomerBranch(request.getCustomerBranch());
		logo.setRentalAreaName(request.getRentalAreaName());
		logo.setPaymentType(request.getPaymentType());
		logo.setBankName(request.getBankName());
		logo.setBankBranch(request.getBankBranch());
		logo.setBankExplanation(request.getBankExplanation());
		logo.setBankExpNo(request.getBankExpNo());
		logo.setBankGuaranteeNo(request.getBankGuaranteeNo());
		logo.setStartDate(ConvertDateUtils.parseStringToDate(request.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		logo.setEndDate(ConvertDateUtils.parseStringToDate(request.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		logo.setServiceType(request.getServiceType());
		logo.setChargeRates(NumberUtils.toBigDecimal(request.getChargeRates()));
		logo.setTotalAmount(NumberUtils.toBigDecimal(request.getTotalAmount()));
		logo.setRemark(request.getRemark());
		logo.setAirport(UserLoginUtils.getUser().getAirportCode());
		// save data
		logo = ricCommuChangeAirlineLogoRepository.save(logo);
	}
}
