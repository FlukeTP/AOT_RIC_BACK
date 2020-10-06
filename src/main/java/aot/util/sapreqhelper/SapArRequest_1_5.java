package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterInfo;
import aot.water.model.RicWaterWasteHeader;
import aot.water.repository.Water009Dao;
import aot.water.vo.response.Water009CalRes;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_5 {

	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private Water009Dao water009Dao;
	
	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicWaterInfo ricWaterInfo) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, ricWaterInfo.getTransactionNo());
		BigDecimal vat = NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue().multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue())));
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		
		/* _____________ set item1 _____________ */
		item1.setPostingKey("01");		//require
		item1.setAccount(ricWaterInfo.getEntreprenuerCode());	//require
		item1.setAmount(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue().add(vat)).toString());		//require
		item1.setTaxCode("DS");
		item1.setCustomerBranch(ricWaterInfo.getCustomerBranch().split(":")[0]);
		item1.setAssignment(ricWaterInfo.getSerialNoMeter());
		item1.setText(SAPConstants.WATER.TEXT);
//		item1.setReferenceKey1(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue());
		item1.setReferenceKey2(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		if ("C".equals(ricWaterInfo.getCustomerType())) {
			item1.setContractNo(ricWaterInfo.getContractNo());
			item2.setAccount("4105110003"); // require
			item2.setPaChargesRate("9.2");
			if(SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
				item2.setPaService("12.2");
			} else {
				item2.setPaService("12.1");
			}
		} else {
			item2.setAccount("4105110004"); // require
			item2.setPaChargesRate("9.2");
			item2.setPaService("12.1");
		}
		
		/* _____________set item2 _____________ */
		item2.setPostingKey("50"); // require
		item2.setAmount("-".concat(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue()).toString())); // require
		item2.setTaxCode("DS");
		item2.setAssignment(ricWaterInfo.getSerialNoMeter());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setTextApplicationObject("DOC_ITEM");
		item2.setTextID("0001");
		if(StringUtils.isNotBlank(ricWaterInfo.getSerialNoMeter())) {
			item2.setLongText(ricWaterInfo.getRoName().concat("/").concat(ricWaterInfo.getSerialNoMeter()).concat(" @ (" + ricWaterInfo.getCurrentMeterValue() + "-" + ricWaterInfo.getBackwardMeterValue() + "=" + ricWaterInfo.getCurrentAmount() + "unit)"));	/* roName /เลข serial มิตเตอร์  (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
		}
		
		/* _____________ set item3 _____________ */
		item3.setPostingKey("50");		//require
		item3.setAccount("2450101002");	//require
		item3.setAmount("-".concat(vat.toString()));			//require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue()).toString());

		return arReq;
	}
	
	public ArRequest getARRequestDynamic(String busPlace, String comCode ,String doctype, List<RicWaterInfo> groupByContractNo) throws Exception {		
		RicWaterInfo get0 = groupByContractNo.get(0);
		ArRequest arReq = commonARRequest.getTemplateMutiItem(busPlace, comCode, doctype, groupByContractNo.size(), get0.getTransactionNo());
		
		/* _________ calculate amount _________ */
		BigDecimal sumAmount = BigDecimal.ZERO;
		for (RicWaterInfo cal : groupByContractNo) {
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
			RicWaterInfo entity = groupByContractNo.get(i-1);
			item.setPostingKey("50"); // require
			item.setAmount("-".concat(NumberUtils.roundUpTwoDigit(entity.getBaseValue()).toString())); // require
			item.setTaxCode("DS");
			item.setAssignment(entity.getSerialNoMeter());
			item.setText(SAPConstants.WATER.TEXT);
			item.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item.setTextApplicationObject("DOC_ITEM");
			item.setTextID("0001");
			item.setLongText(entity.getRoName().concat("/").concat(entity.getSerialNoMeter())
					.concat(" @ (" + entity.getCurrentMeterValue() + "-" + entity.getBackwardMeterValue() + "="
							+ entity.getCurrentAmount()
							+ "unit)")); /* roName /เลข serial มิตเตอร์ (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
			if ("C".equals(entity.getCustomerType())) {
				item.setAccount("4105110003"); // require
				item.setPaChargesRate("9.2");
				if(SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
					item.setPaService("12.2");
				} else {
					item.setPaService("12.1");
				}
			} else {
				item.setAccount("4105110004"); // require
				item.setPaChargesRate("9.2");
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
	
	public ArRequest getARRequestWater009(String busPlace, String comCode, String doctype, RicWaterWasteHeader entity) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Water009CalRes sumAmount = water009Dao.sumAmountDtl(entity.getWasteHeaderId());
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		
		/* _____________ set item1 _____________ */
		item1.setPostingKey("01");		//require
		item1.setAccount(entity.getCustomerCode());	//require
		item1.setAmount(NumberUtils.roundUpTwoDigit(sumAmount.getNetAmount()).toString());		//require
		item1.setTaxCode("DS");
		item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0].trim());
//		item1.setAssignment(entity.getSerialNoMeter());
		item1.setText(SAPConstants.WATER.TEXT);
		item1.setReferenceKey2(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		item1.setContractNo(entity.getContractNo());
		
		/* _____________set item2 _____________ */
		item2.setPostingKey("50"); // require
		item2.setAmount(NumberUtils.roundUpTwoDigit(sumAmount.getAmount().negate()).toString()); // require
		item2.setTaxCode("DS");
//		item2.setAssignment(ricWaterInfo.getSerialNoMeter());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setAccount("4105110003"); // require
		if (SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
			item2.setPaService("12.2");
			item2.setPaChargesRate("9.2");
		} else {
			item2.setPaService("12.1");
			item2.setPaChargesRate("9.1");
		}
//		item2.setTextApplicationObject("DOC_ITEM");
//		item2.setTextID("0001");
//		if(StringUtils.isNotBlank(ricWaterInfo.getSerialNoMeter())) {
//			item2.setLongText(ricWaterInfo.getRoName().concat("/").concat(ricWaterInfo.getSerialNoMeter()).concat(" @ (" + ricWaterInfo.getCurrentMeterValue() + "-" + ricWaterInfo.getBackwardMeterValue() + "=" + ricWaterInfo.getCurrentAmount() + "unit)"));	/* roName /เลข serial มิตเตอร์  (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
//		}
		
		/* _____________ set item3 _____________ */
		item3.setPostingKey("50");		//require
		item3.setAccount("2450101002");	//require
		item3.setAmount(NumberUtils.roundUpTwoDigit(sumAmount.getVat().negate()).toString());			//require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(sumAmount.getAmount()).toString());

		return arReq;
	}

}
