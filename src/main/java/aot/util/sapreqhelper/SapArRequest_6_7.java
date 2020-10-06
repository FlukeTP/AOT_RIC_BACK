package aot.util.sapreqhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.communicate.model.RicCommunicateReqHdr;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_7 {

	@Autowired
	private CommonARRequest commonARRequest;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicCommunicateReqHdr entity) {		
		ArRequest arReq = commonARRequest.getOneTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Header header = arReq.getHeader().get(0);
		header.setRefKeyHeader2(SAPConstants.PAYMENT_TYPE.LG);
		
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		/*
		 * _______________________ require LG ____________________________ */
		header.setDocHeaderText(entity.getContractNo());
		item1.setContractNo(entity.getContractNo());
		item1.setTextApplicationObject("DOC_ITEM");
		item1.setTextID("0001");
		item1.setLongText(entity.getBankName().concat("/").concat(entity.getBankBranch()).concat("/").concat(entity.getRemark()));
		item1.setReferenceKey1(ConvertDateUtils.formatDateToString(entity.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
		item1.setText(entity.getBankGuaranteeNo());
		/* ________________________________________________________________ */
		item1.setPostingKey("09");
		item1.setAccount(entity.getEntreprenuerCode());
		item1.setSpGL("H");
		item1.setAmount(NumberUtils.roundUpTwoDigit(entity.getTotalChargeRates().toString()).toString());
		item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0].trim());
		return arReq;
	}
}
