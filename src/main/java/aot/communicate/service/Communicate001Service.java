package aot.communicate.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.communicate.model.RicCommunicateReqDtl;
import aot.communicate.model.RicCommunicateReqHdr;
import aot.communicate.repository.Communicate001Dao;
import aot.communicate.repository.CommunicateChargeRatesConfigDao;
import aot.communicate.repository.jpa.RicCommunicateReqDtlRepository;
import aot.communicate.repository.jpa.RicCommunicateReqHdrRepository;
import aot.communicate.vo.request.Communicate001DtlReq;
import aot.communicate.vo.request.Communicate001Req;
import aot.communicate.vo.response.Communicate001Res;
import aot.communicate.vo.response.CommunicateConfigRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_6_7;
import aot.util.sapreqhelper.SapArRequest_6_8;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate001Service {
	private static final Logger logger = LoggerFactory.getLogger(Communicate001Service.class);
	
	@Autowired
	private Communicate001Dao community001Dao;
	
	@Autowired
	private CommunicateChargeRatesConfigDao communicateChargeRatesConfigDao;
	
	@Autowired
	private RicCommunicateReqHdrRepository ricCommunicateReqHdrRepository;
	
	@Autowired
	private RicCommunicateReqDtlRepository ricCommunicateReqDtlRepository;
	
	@Autowired
	private SapArRequest_6_7 sapArRequest_6_7;
	
	@Autowired
	private SapArRequest_6_8 sapArRequest_6_8;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	public Communicate001Res findById(Long id) {
		Communicate001Res response = new Communicate001Res();
		/* header */
		RicCommunicateReqHdr resHdr = ricCommunicateReqHdrRepository.findById(id).get();
		BeanUtils.copyProperties(resHdr, response);
		response.setPhoneAmountDF(NumberUtils.toDecimalFormat(resHdr.getPhoneAmount(), false));
		response.setInsuranceRatesDF(NumberUtils.toDecimalFormat(resHdr.getInsuranceRates(), true));
		response.setTotalChargeRatesDF(NumberUtils.toDecimalFormat(resHdr.getTotalChargeRates(), true));
		response.setRequestDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getRequestDate()));
		response.setEndDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getEndDate()));
		response.setBankExpNoStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getBankExpNo()));
		CommunicateConfigRes resConfig = communicateChargeRatesConfigDao.handheidTransceiver(resHdr.getRequestDate());
		if (resConfig != null) {
			response.setChargeRatesDF(NumberUtils.toDecimalFormat(resConfig.getChargeRate().multiply(resHdr.getPhoneAmount()), true));
		}
		
		/* detail */
		response.setDetails(ricCommunicateReqDtlRepository.findByIdHdr(resHdr.getId()));
		return response;
	}
	
	public Communicate001Res findByTransNo(String transNo) {
		Communicate001Res response = new Communicate001Res();
		/* header */
		RicCommunicateReqHdr resHdr = ricCommunicateReqHdrRepository.findByTransactionNo(transNo);
		BeanUtils.copyProperties(resHdr, response);
		response.setPhoneAmountDF(NumberUtils.toDecimalFormat(resHdr.getPhoneAmount(), false));
		response.setInsuranceRatesDF(NumberUtils.toDecimalFormat(resHdr.getInsuranceRates(), true));
		response.setTotalChargeRatesDF(NumberUtils.toDecimalFormat(resHdr.getTotalChargeRates(), true));
		response.setRequestDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getRequestDate()));
		response.setEndDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getEndDate()));
		response.setBankExpNoStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getBankExpNo()));
		CommunicateConfigRes resConfig = communicateChargeRatesConfigDao.handheidTransceiver(resHdr.getRequestDate());
		if (resConfig != null) {
			response.setChargeRatesDF(NumberUtils.toDecimalFormat(resConfig.getChargeRate().multiply(resHdr.getPhoneAmount()), true));
		}
		
		/* detail */
		response.setDetails(ricCommunicateReqDtlRepository.findByIdHdr(resHdr.getId()));
		return response;
	}
	
	@Transactional
	public void save(Communicate001Req request) {
		RicCommunicateReqHdr header = new RicCommunicateReqHdr();
		Date currentDate = new Date();
		String user = UserLoginUtils.getCurrentUsername();
		BeanUtils.copyProperties(request, header);
		header.setPhoneAmount(NumberUtils.toBigDecimal(request.getPhoneAmount()));
		header.setInsuranceRates(NumberUtils.toBigDecimal(request.getInsuranceRates()));
//		header.setChargeRates(NumberUtils.toBigDecimal(request.getChargeRates()));
		header.setTotalChargeRates(NumberUtils.toBigDecimal(request.getTotalChargeRates()));
		header.setVat(NumberUtils.roundUpTwoDigit(header.getTotalChargeRates().multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue()))));
		header.setTotalAll(NumberUtils.roundUpTwoDigit(header.getTotalChargeRates().add(header.getVat())));
		header.setRequestDate(ConvertDateUtils.DDMMYYYYToDate(request.getRequestDateStr()));
		header.setEndDate(ConvertDateUtils.DDMMYYYYToDate(request.getEndDateStr()));
		header.setBankExpNo(ConvertDateUtils.DDMMYYYYToDate(request.getBankExpNoStr()));
		header.setCreatedBy(user);
		header.setCreatedDate(currentDate);
		header.setIsDeleted(FLAG.N_FLAG);
		header.setFlagCancel(CommunicateConstants.FLAG_CANCEL.FALSE);
		Long idHdr = ricCommunicateReqHdrRepository.save(header).getId();
		
		RicCommunicateReqDtl detail = null;
		for (Communicate001DtlReq dtl : request.getMobileSerialNoList()) {
			detail = new RicCommunicateReqDtl();
			detail.setIdHdr(idHdr);
			detail.setMobileSerialNo(dtl.getMobileSerialNo());
			detail.setCreatedBy(user);
			detail.setCreatedDate(currentDate);
			detail.setIsDeleted(FLAG.N_FLAG);
			ricCommunicateReqDtlRepository.save(detail);
		}
	}

	public List<Communicate001Res> search(Communicate001Req request) {
		community001Dao.updateFlagCancel();	/* check invoice cancel for update */
		return community001Dao.findByCondition(request);
	}
	
	public CommunicateConfigRes findChargeRatesConfig(String dateStr) {
		CommunicateConfigRes data = null;
		try {
			data = communicateChargeRatesConfigDao.handheidTransceiver(ConvertDateUtils.parseStringToDate(dateStr, ConvertDateUtils.DD_MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		} catch (Exception e) {
			logger.info("findChargeRatesConfig => data is null");
			e.printStackTrace();
		}
		return data;
	}

	public SapResponse sendToSap(Long id) throws Exception {
		RicCommunicateReqHdr communicateReq = ricCommunicateReqHdrRepository.findById(id).get();

		/* __________________ check line SAP __________________ */
		ArRequest dataSend = null;
		switch (communicateReq.getPaymentType()) {
		case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
			dataSend = sapArRequest_6_8.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					DoctypeConstants.IH, communicateReq);
			break;
		case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_EN:
			dataSend = sapArRequest_6_7.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					DoctypeConstants.IH, communicateReq);
			break;
		default:
			throw new Exception("NOT FOUND 'PAYMENT TYPE'!!");
		}
		
		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		
		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(communicateReq.getId());
		reqConnection.setTableName("ric_communicate_req_hdr");
		reqConnection.setColumnId("id");
//		reqConnection.setColumnInvoiceNo("invoice_no");
//		reqConnection.setColumnTransNo("transaction_no");
//		reqConnection.setColumnSapJsonReq("sap_json_req");
//		reqConnection.setColumnSapJsonRes("sap_json_res");
//		reqConnection.setColumnSapError("sap_error");
//		reqConnection.setColumnSapStatus("sap_status");
		/* __________________ set connection SAP __________________ */
		return sapARService.setSapConnection(reqConnection);
	}

}
