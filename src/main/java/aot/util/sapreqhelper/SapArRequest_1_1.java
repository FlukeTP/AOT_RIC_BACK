package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.electric.model.RicElectricInfo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_1 {

	@Autowired
	private CommonARRequest commonARRequest;	
	
	@Autowired
	private SysConstantService sysConstantService;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicElectricInfo entity) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		BigDecimal vat = NumberUtils.roundUpTwoDigit(entity.getBaseValue().multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue())));
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		
		/* _____________ set item1 _____________ */
		item1.setPostingKey("01");		//require
		item1.setAccount(entity.getEntreprenuerCode());	//require
		item1.setAmount(NumberUtils.roundUpTwoDigit(entity.getBaseValue().add(vat)).toString());		//require
		item1.setTaxCode("DS");
		item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0]);
		item1.setAssignment(entity.getSerialNoMeter());
		item1.setText(SAPConstants.ELECTRIC.TEXT);
		item1.setReferenceKey1(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue());
		item1.setReferenceKey2(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		item1.setContractNo(entity.getContractNo());
		
			
			
		
		/* _____________set item2 _____________ */
		item2.setPostingKey("50"); // require
		item2.setAccount("4105100003"); // require
		item2.setAmount("-".concat(NumberUtils.roundUpTwoDigit(entity.getBaseValue()).toString())); // require
		item2.setTaxCode("DS");
		item2.setAssignment(entity.getSerialNoMeter());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setTextApplicationObject("DOC_ITEM");
		item2.setTextID("0001");
		item2.setLongText(entity.getRoName().concat("/").concat(entity.getSerialNoMeter()).concat(" (" + entity.getCurrentMeterValue() + "-" + entity.getBackwardMeterValue() + "=" + entity.getCurrentAmount() + "unit)"));	/* roName /เลข serial มิตเตอร์  (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
		item2.setPaChargesRate("9.1");
		if(SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
			item2.setPaService("12.2");
		} else {
			item2.setPaService("12.1");
		}
		
		/* _____________ set item3 _____________ */
		item3.setPostingKey("50");		//require
		item3.setAccount("2450101002");	//require
		item3.setAmount("-".concat(vat.toString()));			//require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(entity.getBaseValue()).toString());

		return arReq;
	}
	
	public ArRequest getARRequestDynamic(String busPlace, String comCode ,String doctype, List<RicElectricInfo> groupByContractNo) throws Exception {		
		RicElectricInfo get0 = groupByContractNo.get(0);
		ArRequest arReq = commonARRequest.getTemplateMutiItem(busPlace, comCode, doctype, groupByContractNo.size(), get0.getTransactionNo());
		
		/* _________ calculate amount _________ */
		BigDecimal sumAmount = BigDecimal.ZERO;
		for (RicElectricInfo cal : groupByContractNo) {
			sumAmount = sumAmount.add(cal.getBaseValue());
		}
		BigDecimal vat = NumberUtils.roundUpTwoDigit(sumAmount.multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue())));
		BigDecimal totalAmount = NumberUtils.roundUpTwoDigit(sumAmount.add(vat));
		
		/* _____________ set item1 _____________ */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("01");		//require
		item1.setAccount(get0.getEntreprenuerCode());	//require
		item1.setAmount(totalAmount.toString());		//require
		item1.setTaxCode("DS");
		item1.setCustomerBranch(get0.getCustomerBranch().split(":")[0]);
		item1.setAssignment(get0.getSerialNoMeter());
		item1.setText(SAPConstants.ELECTRIC.TEXT);
		item1.setReferenceKey1(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue());
		item1.setReferenceKey2(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		if ("C".equals(get0.getCustomerType())) {
			item1.setContractNo(get0.getContractNo());
		}
		
		/* _____________set item2 _____________ */
		List<Item> itemList = arReq.getHeader().get(0).getItem();
		for (int i = 1; i < itemList.size() - 1; i++) {
			Item item = arReq.getHeader().get(0).getItem().get(i);
			RicElectricInfo entity = groupByContractNo.get(i-1);
			item.setPostingKey("50"); // require
			item.setAmount("-".concat(NumberUtils.roundUpTwoDigit(entity.getBaseValue()).toString())); // require
			item.setTaxCode("DS");
			item.setAssignment(entity.getSerialNoMeter());
			item.setText(SAPConstants.ELECTRIC.TEXT);
			item.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item.setTextApplicationObject("DOC_ITEM");
			item.setTextID("0001");
			item.setLongText(entity.getRoName().concat("/").concat(entity.getSerialNoMeter())
					.concat(" (" + entity.getCurrentMeterValue() + "-" + entity.getBackwardMeterValue() + "="
							+ entity.getCurrentAmount()
							+ "unit)")); /* roName /เลข serial มิตเตอร์ (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
			item.setAccount("4105100003"); // require
			item.setPaChargesRate("9.1");
			if (SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
				item.setPaService("12.2");
			} else {
				item.setPaService("12.1");
			}
		}
		
		/* _____________ set item3 _____________ */
		Item item3 = arReq.getHeader().get(0).getItem().get(itemList.size()-1);
		item3.setPostingKey("50"); // require
		item3.setAccount("2450101002"); // require
		item3.setAmount("-".concat(vat.toString())); // require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(sumAmount).toString());
		return arReq;
	}
}
