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
import aot.electric.model.RicElectricReq;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_4_4 implements SapArRequest {

	@Autowired
	private SysConstantService sysConstantService;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OnetimeGenerator oneTime;

	@Override
	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) {

		RicElectricReq electricReq = ricElectricReqRepository.findById(Long.valueOf(id)).get();

		ArRequest returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
				electricReq.getTransactionNoPackages());
		
		Header header = returnRequest.getHeader().get(0);
		header.setParkDocument(FLAG.X_FLAG);	

		BigDecimal totalChargeRate = electricReq.getTotalChargeRate() != null
				? NumberUtils.roundUpTwoDigit(electricReq.getTotalChargeRate())
				: NumberUtils.roundUpTwoDigit(0);
		BigDecimal sumRateCharge = electricReq.getSumChargeRates() != null
				? NumberUtils.roundUpTwoDigit(electricReq.getSumChargeRates().multiply(NumberUtils.roundUpTwoDigit(-1)))
				: NumberUtils.roundUpTwoDigit(0);
		BigDecimal sumVat = electricReq.getSumChargeRates() != null
				? NumberUtils
						.roundUpTwoDigit(electricReq.getSumVatChargeRates().multiply(NumberUtils.roundUpTwoDigit(-1)))
				: NumberUtils.roundUpTwoDigit(0);

		// sapCustomerAdrr
		CustomerRes sapAdrr = customerService.getSAPCustomerAddress(electricReq.getCustomerCode(),
				StringUtils.isNotBlank(electricReq.getCustomerBranch())
						? electricReq.getCustomerBranch().substring(0, 5)
						: null);

		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		// Create Item1
		item1.setPostingKey("01");
		item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.ELECTRICITY));
		item1.setSpGL(null);
		item1.setAmount(totalChargeRate.toString());
		item1.setTaxCode("O7");
		item1.setTaxBaseAmount(null);
		item1.setAlternativeRecon(null);
		item1.setPaymentTerm("Z001");
		item1.setPmtMethod(null);
		item1.setCustomerBranch(null);
		item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
		item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
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
		item1.setWHTType1(null);
		item1.setWHTCode1(null);
		item1.setWHTBaseAmount1(null);
		item1.setWHTType2(null);
		item1.setWHTCode2(null);
		item1.setWHTBaseAmount2(null);
		item1.setAssignment(electricReq.getMeterSerialNo());
		item1.setText(SAPConstants.ELECTRIC.TEXT);
		item1.setReferenceKey1(null);
		item1.setReferenceKey2(null);
		item1.setReferenceKey3(null);
		item1.setProfitCenter(null);
		item1.setPaService(null);
		item1.setPaChargesRate(null);
		item1.setContractNo(electricReq.getContractNo());
		item1.setInvoiceRef(null);
		item1.setFiscalYear(null);
		item1.setLineItem(null);
		item1.setTextApplicationObject(null);
		item1.setTextID(null);
		item1.setLongText(null);
		item1.setCalculateTax(null);

		Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
		// Create Item2
		item2.setPostingKey("50");
		item2.setAccount("4105100002");
		item2.setSpGL(null);
		item2.setAmount(sumRateCharge.toString());
		item2.setTaxCode("O7");
		item2.setTaxBaseAmount(null);
		item2.setAlternativeRecon(null);
		item2.setPaymentTerm(null);
		item2.setPmtMethod(null);
		item2.setCustomerBranch(null);
		item2.setTaxNumber3(null);
		item2.setName1(null);
		item2.setName2(null);
		item2.setName3(null);
		item2.setName4(null);
		item2.setStreet(null);
		item2.setCity(null);
		item2.setTaxNumber5(null);
		item2.setPostalCode(null);
		item2.setCountry(null);
		item2.setWHTType1(null);
		item2.setWHTCode1(null);
		item2.setWHTBaseAmount1(null);
		item2.setWHTType2(null);
		item2.setWHTCode2(null);
		item2.setWHTBaseAmount2(null);
		item2.setAssignment(electricReq.getMeterSerialNo());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setReferenceKey1(electricReq.getSumChargeRates() != null
				? NumberUtils.roundUpTwoDigit(electricReq.getSumChargeRates()).toString()
				: "0.00");
		item2.setReferenceKey2(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue());
		item2.setReferenceKey3("Consumption");
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setPaService("12.1");
		item2.setPaChargesRate("9.1");
		item2.setContractNo(null);
		item2.setInvoiceRef(null);
		item2.setFiscalYear(null);
		item2.setLineItem(null);
		item2.setTextApplicationObject("DOC_ITEM");
		item2.setTextID("0001");
		item2.setLongText(electricReq.getRentalAreaCode() + "/" + electricReq.getMeterSerialNo());
		item2.setCalculateTax(null);

		Item item3 = returnRequest.getHeader().get(0).getItem().get(2);

		// Create Item3
		item3.setPostingKey("50");
		item3.setAccount("2450101001");
		item3.setSpGL(null);
		item3.setAmount(sumVat.toString());
		item3.setTaxCode("O7");
		item3.setTaxBaseAmount(electricReq.getSumChargeRates() != null
				? NumberUtils.roundUpTwoDigit(electricReq.getSumChargeRates()).toString()
				: "0.00");
		item3.setAlternativeRecon(null);
		item3.setPaymentTerm(null);
		item3.setPmtMethod(null);
		item3.setCustomerBranch(null);
		item3.setTaxNumber3(null);
		item3.setName1(null);
		item3.setName2(null);
		item3.setName3(null);
		item3.setName4(null);
		item3.setStreet(null);
		item3.setCity(null);
		item3.setTaxNumber5(null);
		item3.setPostalCode(null);
		item3.setCountry(null);
		item3.setWHTType1(null);
		item3.setWHTCode1(null);
		item3.setWHTBaseAmount1(null);
		item3.setWHTType2(null);
		item3.setWHTCode2(null);
		item3.setWHTBaseAmount2(null);
		item3.setAssignment(electricReq.getMeterSerialNo());
		item3.setText(SAPConstants.ELECTRIC.TEXT);
		item3.setReferenceKey1(null);
		item3.setReferenceKey2(null);
		item3.setReferenceKey3(null);
		item3.setProfitCenter(null);
		item3.setPaService(null);
		item3.setPaChargesRate(null);
		item3.setContractNo(null);
		item3.setInvoiceRef(null);
		item3.setFiscalYear(null);
		item3.setLineItem(null);
		item3.setTextApplicationObject(null);
		item3.setTextID(null);
		item3.setLongText(null);
		item3.setCalculateTax(null);

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}

}
