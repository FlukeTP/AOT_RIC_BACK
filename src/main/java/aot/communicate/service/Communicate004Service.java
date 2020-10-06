package aot.communicate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.communicate.model.RicCommunicateReqFlightScheduleDtl;
import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.communicate.repository.Communicate004Dao;
import aot.communicate.repository.jpa.RicCommunicateReqFlightScheduleDtlRepository;
import aot.communicate.repository.jpa.RicCommunicateReqFlightScheduleHdrRepository;
import aot.communicate.vo.request.Communicate004DtlReq;
import aot.communicate.vo.request.Communicate004Req;
import aot.communicate.vo.response.Communicate004Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_6_10;
import aot.util.sapreqhelper.SapArRequest_6_9;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate004Service {

	@Autowired
	private RicCommunicateReqFlightScheduleHdrRepository hdrRepository;

	@Autowired
	private RicCommunicateReqFlightScheduleDtlRepository dtlRepository;

	@Autowired
	private Communicate004Dao communicate004Dao;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_6_9 sapArRequest_6_9;
	
	@Autowired
	private SapArRequest_6_10 sapArRequest_6_10;

	@Transactional(rollbackOn = { Exception.class })
	public void save(Communicate004Req request) {
		// Header
		RicCommunicateReqFlightScheduleHdr header = null;
		header = new RicCommunicateReqFlightScheduleHdr();

		// set data
		header.setEntreprenuerCode(request.getEntreprenuerCode());
		header.setEntreprenuerName(request.getEntreprenuerName());
		header.setCustomerBranch(request.getCustomerBranch());
		header.setContractNo(request.getContractNo());
		header.setRentalAreaCode(request.getRentalAreaCode());
		header.setRentalAreaName(request.getRentalAreaName());
		header.setRemark(request.getRemark());
		header.setRequestDate(DDMMYYYToDate(request.getRequestDateStr()));
		header.setEndDate(DDMMYYYToDate(request.getEndDateStr()));
		header.setPaymentType(request.getPaymentType());
		header.setBankName(request.getBankName());
		header.setBankBranch(request.getBankBranch());
		header.setBankExplanation(request.getBankExplanation());
		header.setBankGuaranteeNo(request.getBankGuaranteeNo());
		header.setBankExpNo(DDMMYYYToDate(request.getBankExpNo()));

		header.setFlagCancel(CommunicateConstants.FLAG_CANCEL.FALSE);
		header.setAmountLg(NumberUtils.toBigDecimal(request.getAmountLg()));
		header.setAmountMonth(NumberUtils.toBigDecimal(request.getAmountMonth()));

		header.setCreatedBy(UserLoginUtils.getCurrentUsername());
		header.setCreatedDate(new Date());
		header.setIsDeleted(FLAG.N_FLAG);
		// save data Header
		header = hdrRepository.save(header);

		// ==================================================================

		if (request.getServiceCharge() != null && request.getServiceCharge().size() > 0) {
			// DTL
			RicCommunicateReqFlightScheduleDtl detail = null;
			List<RicCommunicateReqFlightScheduleDtl> detailList = new ArrayList<>();

			for (Communicate004DtlReq data : request.getServiceCharge()) {
				detail = new RicCommunicateReqFlightScheduleDtl();
				detail.setIdHdr(header.getId());
				detail.setConnectSignal(data.getConnectSignal());
				detail.setLocation(data.getLocation());
				detail.setService(data.getService());
				detail.setAmountLg(data.getAmountLg() != null ? data.getAmountLg() : BigDecimal.ZERO);
				detail.setAmountMonth(data.getAmountMonth() != null ? data.getAmountMonth() : BigDecimal.ZERO);
				detail.setRemark(data.getRemark());
				detail.setCreatedBy(UserLoginUtils.getCurrentUsername());
				detail.setCreatedDate(new Date());
				detail.setIsDeleted(RICConstants.STATUS.NO);
				detailList.add(detail);
			}
			// save data DTL
			dtlRepository.saveAll(detailList);
		}
	}

	public List<Communicate004Res> findData(Communicate004Req request) {
		List<Communicate004Res> dataRes = communicate004Dao.findData(request);
		return dataRes;
	}

	public Communicate004Res getById(Long id) {
		Communicate004Res data = new Communicate004Res();
		RicCommunicateReqFlightScheduleHdr header = hdrRepository.findById(id).get();

		data.setEntreprenuerCode(header.getEntreprenuerCode());
		data.setEntreprenuerName(header.getEntreprenuerName());
		data.setCustomerBranch(header.getCustomerBranch());
		data.setContractNo(header.getContractNo());
		data.setRentalAreaName(header.getRentalAreaName());
		data.setPaymentType(header.getPaymentType());
		data.setRequestDateStr(dateToDDMMYYY(header.getRequestDate()));
		data.setEndDateStr(dateToDDMMYYY(header.getEndDate()));
		data.setRemark(header.getRemark());
		data.setBankName(header.getBankName());
		data.setBankBranch(header.getBankBranch());
		data.setBankExplanation(header.getBankExplanation());
		data.setBankGuaranteeNo(header.getBankGuaranteeNo());
		data.setBankExpNoStr(dateToDDMMYYY(header.getBankExpNo()));
		data.setAmountLg(header.getAmountLg());
		data.setAmountMonth(header.getAmountMonth());
		data.setAmountLgDF(NumberUtils.toDecimalFormat(header.getAmountLg(), true));
		data.setAmountMonthDF(NumberUtils.toDecimalFormat(header.getAmountMonth(), true));
		data.setTransactionNo(header.getTransactionNo());
		List<RicCommunicateReqFlightScheduleDtl> datalist = dtlRepository.findByIdHdr(id);
		data.setServiceCharge(datalist);
		return data;
	}

	public Communicate004Res getByTransactionNo(String transactionNo) {
		Communicate004Res data = new Communicate004Res();
		RicCommunicateReqFlightScheduleHdr header = hdrRepository.findByTransactionNo(transactionNo);

		data.setEntreprenuerCode(header.getEntreprenuerCode());
		data.setEntreprenuerName(header.getEntreprenuerName());
		data.setCustomerBranch(header.getCustomerBranch());
		data.setContractNo(header.getContractNo());
		data.setRentalAreaName(header.getRentalAreaName());
		data.setPaymentType(header.getPaymentType());
		data.setRequestDateStr(ConvertDateUtils.formatDateToString(header.getRequestDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		data.setEndDateStr(ConvertDateUtils.formatDateToString(header.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		data.setRemark(header.getRemark());
		data.setBankName(header.getBankName());
		data.setBankBranch(header.getBankBranch());
		data.setBankExplanation(header.getBankExplanation());
		data.setBankGuaranteeNo(header.getBankGuaranteeNo());
		data.setBankExpNoStr(ConvertDateUtils.formatDateToString(header.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		data.setAmountLg(header.getAmountLg());
		data.setAmountMonth(header.getAmountMonth());
		data.setTransactionNo(header.getTransactionNo());
		List<RicCommunicateReqFlightScheduleDtl> datalist = dtlRepository.findByIdHdr(header.getId());
		data.setServiceCharge(datalist);
		return data;
	}
	
	public SapResponse sendToSap(Long id) throws Exception {
		RicCommunicateReqFlightScheduleHdr communicateReq = hdrRepository.findById(id).get();

		/* __________________ check line SAP __________________ */
		ArRequest dataSend = null;
		switch (communicateReq.getPaymentType()) {
		case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_EN:
			dataSend = sapArRequest_6_9.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					DoctypeConstants.IH, communicateReq);
			break;
		case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
			dataSend = sapArRequest_6_10.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
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
		reqConnection.setTableName("ric_communicate_req_flight_schedule_hdr");
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

	private String dateToDDMMYYY(Date date) {
		return ConvertDateUtils.formatDateToString(date, ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN);
	}
	
	private Date DDMMYYYToDate(String dateStr) {
		return ConvertDateUtils.parseStringToDate(dateStr, ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN);
	}
}
