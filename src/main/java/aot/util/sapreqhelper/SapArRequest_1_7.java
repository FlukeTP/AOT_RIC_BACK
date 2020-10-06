package aot.util.sapreqhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.phone.model.RicPhoneInfo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_7 {
	@Autowired
	private CommonARRequest commonARRequest;

//	@Autowired
//	private RicPhoneInfoRepository ricPhoneInfoRepository;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicPhoneInfo phone001Req) {
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, phone001Req.getTransactionNo());

		/* set item1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("01"); // require
		item1.setAccount(phone001Req.getEntreprenuerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(phone001Req.getTotalChargeAll()).toString()); // require
		item1.setTaxCode("DS");
		item1.setCustomerBranch(phone001Req.getCustomerBranch());
		item1.setText(SAPConstants.PHONE.TEXT);
		item1.setContractNo(phone001Req.getContractNo());

		/* set item2 */
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("50"); // require
		item2.setAmount("-".concat(NumberUtils.roundUpTwoDigit(phone001Req.getTotalCharge()).toString())); // require
		item2.setTaxCode("DS");
		item2.setText(SAPConstants.PHONE.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaChargesRate("9.3");
		if("C".equals(phone001Req.getCustomerType())) {
			item2.setAccount("4105120020"); // require
		} else {
			item2.setAccount("4105120030"); // require
		}
		if(SAPConstants.BUSPLACE.FREE_ZONE.equals(UserLoginUtils.getUser().getAirportCode())) {
			item2.setPaService("12.2");
		} else {
			item2.setPaService("12.1");
		}
		

		/* set item3 */
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("50"); // require
		item3.setAccount("2450101002"); // require=
		item3.setAmount("-".concat(NumberUtils.roundUpTwoDigit(phone001Req.getVat()).toString())); // require
		item3.setTaxCode("DS");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(phone001Req.getTotalCharge()).toString());
		return arReq;
	}

}
