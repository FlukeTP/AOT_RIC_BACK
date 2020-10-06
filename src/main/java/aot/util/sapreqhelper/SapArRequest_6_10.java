package aot.util.sapreqhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_10 {
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicCommunicateReqFlightScheduleHdr entity) {		
		ArRequest arReq = commonARRequest.getOneTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Header header = arReq.getHeader().get(0);
		header.setRefKeyHeader2(SAPConstants.PAYMENT_TYPE.CASH_EN);
	
		/* item 1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("09");
		item1.setAccount(entity.getEntreprenuerCode());
		item1.setSpGL("H");
		item1.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getTotalVat(entity.getAmountLg())).toString());
//		item1.setTaxCode("O7");
//		item1.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(entity.getTotalChargeRates()).toString());
		item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0]);
		item1.setReferenceKey2("O7/".concat(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(entity.getAmountLg())).toString()));
		item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(entity.getAmountLg()).toString());
//		item1.setText(SAPConstants.COMMUNICATE.TEXT);
		return arReq;
	}
}
