package aot.util.sapreqhelper;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.common.service.CustomerService;
import aot.common.service.OnetimeGenerator;
import aot.common.vo.response.CustomerRes;
import aot.communicate.model.RicCommunicateChangeAirlineLogo;
import aot.it.model.RicItOtherCreateInvoice;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_4_12 {

	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private OnetimeGenerator oneTime;
	
	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicCommunicateChangeAirlineLogo entity) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Header header = arReq.getHeader().get(0);
		header.setParkDocument(FLAG.X_FLAG);	
		
		// sapCustomerAdrr
		CustomerRes sapAdrr = customerService.getSAPCustomerAddress(entity.getCustomerCode(),
				StringUtils.isNotBlank(entity.getCustomerBranch())
						? entity.getCustomerBranch().substring(0, 5)
						: null);
		
		/* item 1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("01");
		item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER));
		item1.setAmount(NumberUtils.roundUpTwoDigit(entity.getTotalAmount()).toString());
		item1.setTaxCode("O7");
		item1.setPaymentTerm("Z001");
		item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0]);
		item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
		item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
//		item1.setName1("NO DATA");
		item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
		item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
		item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
		item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");

		// check street4,5 field add City
		if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && sapAdrr.getStreet4().length() > 35) {
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		} else if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && StringUtils.isNotBlank(sapAdrr.getStreet5())) {
			String data = sapAdrr.getStreet4() + " " + sapAdrr.getStreet5();
			if (data.length() > 35) {
				item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
			} else {
				if (StringUtils.isNotBlank(sapAdrr.getCity())) {
					item1.setCity(sapAdrr.getStreet4() + " , " + sapAdrr.getStreet5() + " " + sapAdrr.getCity());
				} else {
					item1.setCity("NO DATA");
				}
			}
		} else {
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		}

		item1.setTaxNumber5(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		item1.setPostalCode(StringUtils.isNotBlank(sapAdrr.getPostCode()) ? sapAdrr.getPostCode() : "NO DATA");
		item1.setCountry(StringUtils.isNotBlank(sapAdrr.getCountry()) ? sapAdrr.getCountry() : "NO DATA");
		item1.setText(SAPConstants.COMMUNICATE.TEXT);
		item1.setContractNo(entity.getContractNo());
		
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("50");
		item2.setAccount("4105990001");
		item2.setAmount(NumberUtils.roundUpTwoDigit(entity.getChargeRates()).toString());
		item2.setTaxCode("O7");
		item2.setText(SAPConstants.COMMUNICATE.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaService("12.1");
		item2.setPaChargesRate("27.0");
		
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("50");
		item3.setAccount("2450101001");
		item3.setAmount(NumberUtils.roundUpTwoDigit(entity.getTotalAmount().subtract(entity.getChargeRates())).toString());
		item3.setTaxCode("O7");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(entity.getTotalAmount().subtract(entity.getChargeRates())).toString());
		item3.setText(SAPConstants.COMMUNICATE.TEXT);
		
		return arReq;
	}
	
	public ArRequest getARRequestIt(String busPlace, String comCode, String doctype, RicItOtherCreateInvoice entity) {		
		ArRequest arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		
		Header header = arReq.getHeader().get(0);
		header.setParkDocument(FLAG.X_FLAG);
		
		// sapCustomerAdrr
		CustomerRes sapAdrr = customerService.getSAPCustomerAddress(entity.getEntreprenuerCode(),
				StringUtils.isNotBlank(entity.getEntreprenuerBranch())
						? entity.getEntreprenuerBranch().substring(0, 5)
						: null);
		
		BigDecimal totalChargeRate = entity.getTotalChargeRates();
		BigDecimal vat = totalChargeRate.multiply(new BigDecimal(0.07));
		BigDecimal totally = totalChargeRate.add(vat);
		/* item 1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("01");
		item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER));
		item1.setAmount(NumberUtils.roundUpTwoDigit(totally).toString());
		item1.setTaxCode("O7");
		item1.setPaymentTerm("Z001");
		item1.setCustomerBranch(entity.getEntreprenuerBranch().split(":")[0]);
		item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
		item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
//		item1.setName1("NO DATA");
		item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
		item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
		item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
		item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");

		// check street4,5 field add City
		if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && sapAdrr.getStreet4().length() > 35) {
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		} else if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && StringUtils.isNotBlank(sapAdrr.getStreet5())) {
			String data = sapAdrr.getStreet4() + " " + sapAdrr.getStreet5();
			if (data.length() > 35) {
				item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
			} else {
				if (StringUtils.isNotBlank(sapAdrr.getCity())) {
					item1.setCity(sapAdrr.getStreet4() + " , " + sapAdrr.getStreet5() + " " + sapAdrr.getCity());
				} else {
					item1.setCity("NO DATA");
				}
			}
		} else {
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		}

		item1.setTaxNumber5(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
		item1.setPostalCode(StringUtils.isNotBlank(sapAdrr.getPostCode()) ? sapAdrr.getPostCode() : "NO DATA");
		item1.setCountry(StringUtils.isNotBlank(sapAdrr.getCountry()) ? sapAdrr.getCountry() : "NO DATA");
		item1.setText(SAPConstants.IT.TEXT);
		item1.setContractNo(entity.getContractNo());
		
		Item item2 = arReq.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("50");
		item2.setAccount("4105990001");
		item2.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).negate().toString());
		item2.setTaxCode("O7");
		item2.setText(SAPConstants.IT.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaService("12.1");
		item2.setPaChargesRate("27.0");
		
		Item item3 = arReq.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("50");
		item3.setAccount("2450101001");
		item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString());
		item3.setTaxCode("O7");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString());
		item3.setText(SAPConstants.IT.TEXT);
		
		return arReq;
	}
	
}
