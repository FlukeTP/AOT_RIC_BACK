package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.it.model.RicItOtherCreateInvoice;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_11 {

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_1_11.class);

	@Autowired
	private CommonARRequest commonARRequest;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicItOtherCreateInvoice otherCreateInvoice) {
		ArRequest returnRequest = null;
		List<Item> itemList = new ArrayList<Item>();
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;

		returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, otherCreateInvoice.getTransactionNo());
		item1 = returnRequest.getHeader().get(0).getItem().get(0);
		item2 = returnRequest.getHeader().get(0).getItem().get(1);
		item3 = returnRequest.getHeader().get(0).getItem().get(2);

		BigDecimal amount = otherCreateInvoice.getChargeRates().multiply(otherCreateInvoice.getTotalAmount());
		BigDecimal vat = amount.multiply(new BigDecimal(0.07));
		BigDecimal totalAmount = amount.add(vat);
		// Create Item1
		item1.setPostingKey("01"); // require
		item1.setAccount(otherCreateInvoice.getEntreprenuerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString()); // require
		item1.setTaxCode("DS");
		item1.setPaymentTerm("Z001");
		item1.setCustomerBranch(otherCreateInvoice.getEntreprenuerBranch().split(":")[0]);
		// item1.setAssignment(garbagedisReqHdr.getEquipmentCode());
		item1.setText(SAPConstants.IT.TEXT);
		item1.setWHTType1("R1");
		item1.setWHTCode1("02");
		item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(amount).toString());

		// Create Item2
		item2.setPostingKey("50"); // require
		item2.setAccount("4105170002"); // require
		item2.setAmount(NumberUtils.roundUpTwoDigit(amount).negate().toString()); // require
		item2.setTaxCode("DS");
		// item2.setAssignment(garbagedisReqHdr.getEquipmentCode());
		item2.setText(SAPConstants.IT.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaService("12.1");
		item2.setPaChargesRate("27.0");
		item2.setContractNo(otherCreateInvoice.getContractNo());

		// Create Item3
		item3.setPostingKey("50"); // require
		item3.setAccount("2450101002"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
		item3.setTaxCode("DS");
		// item3.setAssignment(eleReqChange.getNewSerialNo());
		// item3.setText(DEPOSIT_TEXT.DEPOSIT_EQUIPMENT);
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(amount).toString());

		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
