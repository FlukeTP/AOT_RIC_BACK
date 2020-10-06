package aot.communicate.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.communicate.repository.Communicate005Dao;
import aot.communicate.repository.jpa.RicCommunicateReqFlightScheduleHdrRepository;
import aot.communicate.vo.request.Communicate005Req;
import aot.communicate.vo.response.Communicate005Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_7_4;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate005Service {

	@Autowired
	private RicCommunicateReqFlightScheduleHdrRepository hdrRepository;

	@Autowired
	private Communicate005Dao communicate005Dao;
	
	@Autowired
	private SapArRequest_7_4 sapArRequest_7_4;
	
	@Autowired
	private SAPARService sapARService;

	@Transactional(rollbackOn = { Exception.class })
	public void save(Communicate005Req request) {
		RicCommunicateReqFlightScheduleHdr data = hdrRepository.findById(request.getId()).get();
		// data

		// set data
//		data.setEntreprenuerCode(request.getEntreprenuerCode());
//		data.setEntreprenuerName(request.getEntreprenuerName());
//		data.setCustomerBranch(request.getCustomerBranch());
//		data.setContractNo(request.getContractNo());
//		data.setRentalAreaCode(request.getRentalAreaCode());
//		data.setRentalAreaName(request.getRentalAreaName());
//		data.setRemark(request.getRemark());
//		data.setRequestDate(ConvertDateUtils.parseStringToDate(request.getRequestDateStr(), ConvertDateUtils.DD_MM_YYYY,
//				ConvertDateUtils.LOCAL_EN));
		data.setAmountLg(NumberUtils.toBigDecimal(request.getAmountLg()));
		data.setCancelDate(ConvertDateUtils.DDMMYYYYToDate(request.getCancelDateStr()));
		data.setEndDate(data.getCancelDate());
//		data.setPaymentType(request.getPaymentType());
//		data.setBankName(request.getBankName());
//		data.setBankBranch(request.getBankBranch());
//		data.setBankExplanation(request.getBankExplanation());
//		data.setBankGuaranteeNo(request.getBankGuaranteeNo());
//		data.setBankExpNo(ConvertDateUtils.parseStringToDate(request.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
//				ConvertDateUtils.LOCAL_EN));
//		data.setTransactionNo(request.getTransactionNo());
//		data.setCreatedBy(UserLoginUtils.getCurrentUsername());
//		data.setCreatedDate(new Date());
//		data.setIsDeleted(FLAG.N_FLAG);
		data.setFlagCancel(CommunicateConstants.FLAG_CANCEL.WAIT);
		// update data data
		hdrRepository.save(data);

		// edit flag isDel
//		this.addFlagYRicCommunicateReqFlightScheduleHdr(request.getTransactionNo());
	}

	public List<Communicate005Res> findData(Communicate005Req request) {
		List<Communicate005Res> dataRes = communicate005Dao.findData(request);
		return dataRes;
	}
	
	public SapResponse sendToSap(Long id) throws Exception {
		/* __________________ set request SAP __________________ */
		ArRequest dataSend = sapArRequest_7_4.getARRequest(UserLoginUtils.getUser().getAirportCode(),
				SAPConstants.COMCODE, DoctypeConstants.IX, id, "COMMUNICATE005");

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		
		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(id);
		reqConnection.setTableName("ric_communicate_req_flight_schedule_hdr");
		reqConnection.setColumnId("id");
		 reqConnection.setColumnInvoiceNo("invoice_no_cancel");
		 reqConnection.setColumnTransNo("transaction_no_cancel");
		 reqConnection.setColumnSapJsonReq("sap_json_req_cancel");
		 reqConnection.setColumnSapJsonRes("sap_json_res_cancel");
		 reqConnection.setColumnSapError("sap_error_cancel");
		 reqConnection.setColumnSapStatus("sap_status_cancel");
		
		/* __________________ set connection SAP __________________ */
		return sapARService.setSapConnection(reqConnection);
	}

}
