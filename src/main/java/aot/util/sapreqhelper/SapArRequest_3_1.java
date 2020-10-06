package aot.util.sapreqhelper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.cndn.vo.request.SapCnDnReq;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_3_1 {

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_3_1.class);

	@Autowired
	private CommonARRequest commonARRequest;

	public ArRequest getARRequest(SapCnDnReq request) {
		ArRequest returnRequest = null;
		List<Item> itemList = new ArrayList<Item>();
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;

		returnRequest = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(),
				SAPConstants.COMCODE, request.getDoctype(), request.getTransactionNo());
		item1 = returnRequest.getHeader().get(0).getItem().get(0);
		item2 = returnRequest.getHeader().get(0).getItem().get(1);
		item3 = returnRequest.getHeader().get(0).getItem().get(2);

		// Create Item1
		item1.setPostingKey("01"); // require
		item1.setAccount(request.getAccount()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(request.getTotalAmount()).toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
		item1.setCustomerBranch(request.getCustomerBranch().split(":")[0]);
		item1.setAssignment(request.getAssignment());
		item1.setText(request.getText());
		item1.setContractNo(request.getContractNo());
		item1.setInvoiceRef(request.getInvoiceRef());
		item1.setFiscalYear(request.getFiscalYear());
		item1.setLineItem(request.getLineItem());

		// Create Item2
		item2.setPostingKey("50"); // require
		item2.setAccount(request.getGlAccount()); // require
		item2.setAmount(NumberUtils.roundUpTwoDigit(request.getAmount()).negate().toString()); // require
		item2.setTaxCode("O7");
		item2.setPmtMethod("5");
		item2.setAssignment(request.getAssignment());
		item2.setText(request.getText());
		item2.setReferenceKey1(request.getReferenceKey1());
		item2.setReferenceKey2(request.getReferenceKey2());
		item2.setReferenceKey3(request.getReferenceKey3());
		item2.setProfitCenter(request.getProfitCenter());
		item2.setPaService(request.getPaService());
		item2.setPaChargesRate(request.getPaChargesRate());
		item2.setTextApplicationObject(request.getTextApplicationObject());
		item2.setTextID(request.getTextID());
		item2.setLongText(request.getLongText());

		// Create Item3
		item3.setPostingKey("50"); // require
		item3.setAccount("2450101002"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(request.getVat()).negate().toString()); // require
		item3.setTaxCode("O7");
		item3.setPmtMethod("5");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(request.getAmount()).toString());
		item3.setAssignment(request.getAssignment());
		item3.setText(request.getText());

		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
