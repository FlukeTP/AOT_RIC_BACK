package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterInfo;
import aot.water.model.RicWaterReq;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_1_2 {
	@Autowired
	private CommonARRequest commonARRequest;	
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;
	
	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicWaterInfo ricWaterInfo) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, ricWaterInfo.getTransactionNo());
		BigDecimal vat = NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue().multiply(NumberUtils.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue())));
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		
		RicWaterReq reqRes = ricWaterReqRepository.findById(ricWaterInfo.getIdReq()).get();
		
		/* _____________ set item1 _____________ */
		item1.setPostingKey("01");		//require
		item1.setAccount(ricWaterInfo.getEntreprenuerCode());	//require
		item1.setAmount(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue().add(vat)).toString());		//require
		item1.setTaxCode("DS");
//		item1.setCustomerBranch(ricWaterInfo.getCustomerBranch().split(":")[0]);
		item1.setAssignment(ricWaterInfo.getSerialNoMeter());
		item1.setText(SAPConstants.WATER.TEXT);
		item1.setReferenceKey2(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.MM_YYYY_DOT, ConvertDateUtils.LOCAL_EN));
		item1.setContractNo(ricWaterInfo.getContractNo());
		
		/* _____________set item2 _____________ */
		item2.setAccount("4105110004"); // require
		item2.setPostingKey("50"); // require
		item2.setAmount("-".concat(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue()).toString())); // require
		item2.setTaxCode("DS");
		item2.setAssignment(ricWaterInfo.getSerialNoMeter());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setTextApplicationObject("DOC_ITEM");
		item2.setTextID("0001");
		if(StringUtils.isNotBlank(ricWaterInfo.getSerialNoMeter())) {
			item2.setLongText(ricWaterInfo.getRoName().concat("/").concat(reqRes.getInstallPositionService()).concat(" @ (" + NumberUtils.roundUpTwoDigit(ricWaterInfo.getCurrentMeterValue()) + "-" + NumberUtils.roundUpTwoDigit(ricWaterInfo.getBackwardMeterValue()) + "=" + NumberUtils.roundUpTwoDigit(ricWaterInfo.getCurrentAmount()) + "unit)"));	/* roName /เลข serial มิตเตอร์  (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit) */
		}
		item2.setPaChargesRate("9.2");
		item2.setPaService("12.1");
		
		/* _____________ set item3 _____________ */
		item3.setPostingKey("50");		//require
		item3.setAccount("2450101002");	//require
		item3.setAmount("-".concat(vat.toString()));			//require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(ricWaterInfo.getBaseValue()).toString());

		return arReq;
	}

}
