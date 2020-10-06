package aot.util.sapreqhelper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.it.model.RicItOtherCreateInvoice;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_12 {

	@Autowired
	private CommonARRequest commonARRequest;
	
	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicItOtherCreateInvoice entity) {		
		ArRequest arReq = commonARRequest.getOneTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Header header = arReq.getHeader().get(0);
		header.setRefKeyHeader2("CASH");
		
		BigDecimal amount = entity.getChargeRates().multiply(entity.getTotalAmount());
		BigDecimal vat = amount.multiply(new BigDecimal(0.07));
		BigDecimal totalAmount = amount.add(vat);
		/* item 1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("09");
		item1.setAccount(entity.getEntreprenuerCode());
		item1.setSpGL("H");
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString());
		item1.setCustomerBranch(entity.getEntreprenuerBranch().split(":")[0]);
		item1.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_IT);
		item1.setReferenceKey2("O7/".concat(NumberUtils.roundUpTwoDigit(vat).toString()));
		item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(amount).toString());
		
		return arReq;
	}

}
