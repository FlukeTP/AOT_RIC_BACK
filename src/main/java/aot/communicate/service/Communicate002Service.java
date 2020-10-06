package aot.communicate.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.communicate.model.RicCommunicateReqHdr;
import aot.communicate.repository.Communicate002Dao;
import aot.communicate.repository.CommunicateChargeRatesConfigDao;
import aot.communicate.repository.jpa.RicCommunicateReqHdrRepository;
import aot.communicate.vo.request.Communicate002Req;
import aot.communicate.vo.response.Communicate002Res;
import aot.communicate.vo.response.CommunicateConfigRes;
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
public class Communicate002Service {
	
	@Autowired
	private RicCommunicateReqHdrRepository ricCommunicateReqHdrRepository;
	
	@Autowired
	private CommunicateChargeRatesConfigDao communicateChargeRatesConfigDao;
	
	@Autowired
	private Communicate002Dao community002Dao;
	
	@Autowired
	private SapArRequest_7_4 sapArRequest_7_4;
	
	@Autowired
	private SAPARService sapARService;

	public Communicate002Res findById(Long id) {
		Communicate002Res response = new Communicate002Res();
		/* header */
		RicCommunicateReqHdr resHdr = ricCommunicateReqHdrRepository.findById(id).get();
		BeanUtils.copyProperties(resHdr, response);
		response.setPhoneAmount(NumberUtils.toDecimalFormat(resHdr.getPhoneAmount(), false));
		response.setInsuranceRates(NumberUtils.toDecimalFormat(resHdr.getInsuranceRates(), true));
		response.setTotalChargeRates(NumberUtils.toDecimalFormat(resHdr.getTotalChargeRates(), true));
		response.setRequestDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getRequestDate()));
		response.setCancelDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getCancelDate()));
		response.setEndDateStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getEndDate()));
		response.setBankExpNoStr(ConvertDateUtils.dateToDDMMYYYY(resHdr.getBankExpNo()));
		CommunicateConfigRes resConfig = communicateChargeRatesConfigDao.handheidTransceiver(resHdr.getRequestDate());
		if (resConfig != null) {
			response.setChargeRates(NumberUtils.toDecimalFormat(resConfig.getChargeRate(), true));
		}
		return response;
	}

	@Transactional
	public void update(Communicate002Req request) {
		RicCommunicateReqHdr resHdr = ricCommunicateReqHdrRepository.findById(request.getId()).get();
		resHdr.setInsuranceRates(NumberUtils.toBigDecimal(request.getInsuranceRates()));
		resHdr.setRemark(request.getRemark());
		resHdr.setCancelDate(ConvertDateUtils.DDMMYYYYToDate(request.getCancelDateStr()));
		resHdr.setEndDate(resHdr.getCancelDate());
		resHdr.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		resHdr.setUpdatedDate(new Date());
		resHdr.setFlagCancel(CommunicateConstants.FLAG_CANCEL.WAIT);
		ricCommunicateReqHdrRepository.save(resHdr);
	}

	public List<Communicate002Res> search(Communicate002Req request) {
		return community002Dao.findByCondition(request);
	}

	public SapResponse sendToSap(Long id) throws Exception {
//		RicCommunicateReqHdr communicateReq = ricCommunicateReqHdrRepository.findById(id).get();

		/* __________________ set request SAP __________________ */
		ArRequest dataSend = sapArRequest_7_4.getARRequest(UserLoginUtils.getUser().getAirportCode(),
				SAPConstants.COMCODE, DoctypeConstants.IX, id, "COMMUNICATE002");

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		
		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(id);
		reqConnection.setTableName("ric_communicate_req_hdr");
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
