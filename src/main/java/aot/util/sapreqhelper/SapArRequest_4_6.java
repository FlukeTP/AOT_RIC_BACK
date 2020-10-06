package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.common.service.OnetimeGenerator;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterReq;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_4_6 {
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private OnetimeGenerator onetimeGenerator;

	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype) {
		RicWaterReq water003Req = ricWaterReqRepository.findById(id).get();
		ArRequest returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
				water003Req.getTransactionNoCash());

		returnRequest.getHeader().get(0).setParkDocument("X");
		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
		Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
		
		BigDecimal vat = NumberUtils.roundUpTwoDigit(
				NumberUtils.roundUpTwoDigit(water003Req.getSumChargeRate()).multiply(NumberUtils
						.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue())));

		// Create Item1
		item1.setPostingKey("01");
		item1.setAccount(onetimeGenerator.getGenerate(RICConstants.CATEGORY.WATER));
		item1.setAmount(NumberUtils.roundUpTwoDigit(water003Req.getSumChargeRate()).add(vat).toString());
		item1.setTaxCode("O7");
		item1.setPaymentTerm("Z001");
		item1.setCustomerBranch(water003Req.getCustomerBranch().split(":")[0]);
		item1.setTaxNumber3("1234567890123");
		item1.setName1(water003Req.getCustomerName());
		item1.setStreet("44/55 Ratchtavee");
		item1.setCity("Ratchatavee");
		item1.setTaxNumber5("Bangkok");
		item1.setPostalCode("10400");
		item1.setCountry("TH");
		item1.setAssignment(water003Req.getMeterSerialNo());
		item1.setText(SAPConstants.WATER.TEXT);
		item1.setContractNo(water003Req.getContractNo());

		// Create Item2
		item2.setPostingKey("50");
		item2.setAccount("4105110003");
		item2.setAmount("-".concat(water003Req.getSumChargeRate().toString()));
		item2.setTaxCode("O7");
		item1.setAssignment(water003Req.getMeterSerialNo());
		item1.setText(SAPConstants.WATER.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaService("12.1");
		item2.setPaChargesRate("9.2");
//		item2.setTextApplicationObject("DOC_ITEM ");
//		item2.setTextID("0001");
//		item2.setLongText(water003Req.getRentalAreaCode().concat("/").concat(water003Req.getMeterSerialNo()).concat("@").concat(str));

		// Create Item3
		item3.setPostingKey("50");
		item3.setAccount("2450101001");
		item3.setAmount("-".concat(vat.toString()));
		item3.setTaxCode("O7");
		item3.setTaxBaseAmount("1000");
		item1.setAssignment(water003Req.getMeterSerialNo());
		item1.setText(SAPConstants.WATER.TEXT);

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
