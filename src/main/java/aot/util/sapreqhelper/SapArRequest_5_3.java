package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.common.service.CustomerService;
import aot.common.service.OnetimeGenerator;
import aot.common.vo.response.CustomerRes;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterReq;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_5_3{
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;
	
	@Autowired
	private OnetimeGenerator onetimeGenerator;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private CustomerService customerService;
	
	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype, String flagPage) {
		ArRequest returnRequest = null;
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;
		RicWaterReq water003Req = null;
		CustomerRes sapAdrr = null;
		
		switch (flagPage) {
		case "WATER003":
			water003Req = ricWaterReqRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, water003Req.getTransactionNoCash());
			
			returnRequest.getHeader().get(0).setParkDocument("X");
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);
			
			// sapCustomerAdrr
			sapAdrr = customerService.getSAPCustomerAddress(water003Req.getCustomerCode(),
					StringUtils.isNotBlank(water003Req.getCustomerBranch())
							? water003Req.getCustomerBranch().substring(0, 5)
							: null);
			
			// Create Item1
			item1.setPostingKey("01");
			item1.setAccount(onetimeGenerator.getGenerate(RICConstants.CATEGORY.WATER));
			item1.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getTotalVat(new BigDecimal(water003Req.getTotalInstallChargeRates()))).toString());
			item1.setTaxCode("O7");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(water003Req.getCustomerBranch().split(":")[0]);
			item1.setTaxNumber3("1234567890123");
			item1.setName1(water003Req.getCustomerName());
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
			item1.setAssignment(water003Req.getMeterSerialNo());
			item1.setText(SAPConstants.WATER.TEXT);
			item1.setContractNo(water003Req.getContractNo());

			// Create Item2
			item2.setPostingKey("50");
			item2.setAccount("4105110001");
			item2.setAmount("-".concat(water003Req.getTotalInstallChargeRates()));
			item2.setTaxCode("O7");
			item2.setAssignment(water003Req.getMeterSerialNo());
			item2.setText(SAPConstants.WATER.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("9.2");

			// Create Item3
			item3.setPostingKey("50");
			item3.setAccount("2450101001");
			item3.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(new BigDecimal(water003Req.getTotalInstallChargeRates())).negate()).toString());
			item3.setTaxCode("O7");
			item3.setTaxBaseAmount(water003Req.getTotalInstallChargeRates());
//			item3.setAssignment(water003Req.getMeterSerialNo());
//			item3.setText(SAPConstants.WATER.TEXT);
			break;
		case "WATER003_OTHER":
			water003Req = ricWaterReqRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, water003Req.getTransactionNoCash());
			
			returnRequest.getHeader().get(0).setParkDocument("X");
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);
			
			// sapCustomerAdrr
			sapAdrr = customerService.getSAPCustomerAddress(water003Req.getCustomerCode(),
					StringUtils.isNotBlank(water003Req.getCustomerBranch())
							? water003Req.getCustomerBranch().substring(0, 5)
							: null);
			
			// Create Item1
			item1.setPostingKey("01");
			item1.setAccount(onetimeGenerator.getGenerate(RICConstants.CATEGORY.WATER));
			item1.setAmount(NumberUtils.roundUpTwoDigit(water003Req.getTotalChargeRateOther()).toString());
			item1.setTaxCode("O7");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(water003Req.getCustomerBranch().split(":")[0]);
			item1.setTaxNumber3("1234567890123");
			item1.setName1(water003Req.getCustomerName());
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
			item1.setAssignment(water003Req.getMeterSerialNo());
			item1.setText(SAPConstants.WATER.TEXT);
			item1.setContractNo(water003Req.getContractNo());

			// Create Item2
			item2.setPostingKey("50");
			item2.setAccount("4105110001");
			item2.setAmount("-".concat(water003Req.getSumChargeRatesOther().toString()));
			item2.setTaxCode("O7");
			item2.setAssignment(water003Req.getMeterSerialNo());
			item2.setText(SAPConstants.WATER.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("9.2");

			// Create Item3
			item3.setPostingKey("50");
			item3.setAccount("2450101001");
			item3.setAmount(NumberUtils.roundUpTwoDigit(water003Req.getSumVatChargeRatesOther()).negate().toString());
			item3.setTaxCode("O7");
			item3.setTaxBaseAmount(water003Req.getTotalInstallChargeRates());
//			item3.setAssignment(water003Req.getMeterSerialNo());
//			item3.setText(SAPConstants.WATER.TEXT);
			break;
		}
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
