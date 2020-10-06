package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.common.service.CustomerService;
import aot.common.service.OnetimeGenerator;
import aot.common.vo.response.CustomerRes;
import aot.it.model.RicItCUTETrainingRoomUsageReport;
import aot.it.model.RicItOtherCreateInvoice;
import aot.it.repository.It0103Dao;
import aot.it.repository.jpa.RicItCUTETrainingRoomUsageReportRepository;
import aot.it.vo.request.It0103Req;
import aot.it.vo.response.It0103Res;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_4_18 {

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_4_18.class);

	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private It0103Dao it0103Dao;
	
	@Autowired
	private RicItCUTETrainingRoomUsageReportRepository ricItCUTETrainingRoomUsageReportRepository;
	
	@Autowired
	private OnetimeGenerator oneTime;

	@Autowired
	private CustomerService customerService;

	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype) {
		ArRequest returnRequest = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		try {
			RicItCUTETrainingRoomUsageReport CUTETrainingRoom = ricItCUTETrainingRoomUsageReportRepository
					.findById(Long.valueOf(id)).get();

			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
					CUTETrainingRoom.getTransactionNo());
			
			Header header = returnRequest.getHeader().get(0);
			header.setParkDocument(FLAG.X_FLAG);

			// sapCustomerAdrr
			CustomerRes sapAdrr = customerService.getSAPCustomerAddress(CUTETrainingRoom.getEntreprenuerCode(),
					StringUtils.isNotBlank(CUTETrainingRoom.getEntreprenuerBranch())
							? CUTETrainingRoom.getEntreprenuerBranch().substring(0, 5)
							: null);

			// calculate
			BigDecimal totally = new BigDecimal(0);
			BigDecimal totalAmount = new BigDecimal(0);
			BigDecimal vat = new BigDecimal(0);
			BigDecimal vatConst = new BigDecimal(0.07);
			It0103Req request = new It0103Req();
			List<It0103Res> dtlList = it0103Dao.findAll(request);
			for (It0103Res dtl : dtlList) {
				if (CUTETrainingRoom.getRoomType().equals(dtl.getServiceType())) {
					totalAmount = totalAmount.add(dtl.getChargeRate());
					break;
				}
			}
			vat = vat.add(totalAmount.multiply(vatConst));
			totally = totally.add(totalAmount.add(vat));
			// Create Item1
			Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("01"); // require
			item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER)); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(totally).toString()); // require
			item1.setTaxCode("O7");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(null);
			item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
			item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
			item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
			item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
			item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
			item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCountry("TH");
			item1.setWHTType1("A1");
			item1.setWHTCode1("02");
			item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(totalAmount).toString());
//			item1.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item1.setText(SAPConstants.CUTE_TRAINGING_ROOM.TEXT);
			item1.setContractNo(CUTETrainingRoom.getContractNo());
			itemList.add(item1);

			// Create Item2
			Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item2.setPostingKey("50"); // require
			item2.setAccount("4105170004"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).negate().toString()); // require
			item2.setTaxCode("O7");
//			item2.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item2.setText(SAPConstants.CUTE_TRAINGING_ROOM.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			itemList.add(item2);

			// Create Item3
			Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101001"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
			item3.setTaxCode("O7");
//			item3.setAssignment(eleReqChange.getNewSerialNo());
			item3.setText(SAPConstants.CUTE_TRAINGING_ROOM.TEXT);
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString());
			itemList.add(item3);

			returnRequest.getHeader().get(0).setItem(itemList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnRequest;
	}
	
	public ArRequest getARRequestIt004(String busPlace, String comCode, String doctype, RicItOtherCreateInvoice otherCreateInvoice) {
		ArRequest returnRequest = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		try {

			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
					otherCreateInvoice.getTransactionNo());
			
			Header header = returnRequest.getHeader().get(0);
			header.setParkDocument(FLAG.X_FLAG);	
			
			// sapCustomerAdrr
			CustomerRes sapAdrr = customerService.getSAPCustomerAddress(otherCreateInvoice.getEntreprenuerCode(),
					StringUtils.isNotBlank(otherCreateInvoice.getEntreprenuerBranch())
							? otherCreateInvoice.getEntreprenuerBranch().substring(0, 5)
							: null);

			// calculate
			BigDecimal amount = otherCreateInvoice.getChargeRates().multiply(otherCreateInvoice.getTotalAmount());
			BigDecimal vat = amount.multiply(new BigDecimal(0.07));
			BigDecimal totalAmount = amount.add(vat);
			// Create Item1
			Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("01"); // require
			item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER)); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString()); // require
			item1.setTaxCode("O7");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(null);
			item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
			item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
			item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
			item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
			item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
			item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCountry("TH");
			item1.setWHTType1("A1");
			item1.setWHTCode1("02");
			item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(amount).toString());
//			item1.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item1.setText(SAPConstants.IT.TEXT);
			item1.setContractNo(otherCreateInvoice.getContractNo());
			itemList.add(item1);

			// Create Item2
			Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item2.setPostingKey("50"); // require
			item2.setAccount("4105170001"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(amount).negate().toString()); // require
			item2.setTaxCode("O7");
//			item2.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item2.setText(SAPConstants.IT.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			itemList.add(item2);

			// Create Item3
			Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101001"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
			item3.setTaxCode("O7");
//			item3.setAssignment(eleReqChange.getNewSerialNo());
//			item3.setText(SAPConstants.IT.TEXT);
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(amount).toString());
			itemList.add(item3);

			returnRequest.getHeader().get(0).setItem(itemList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnRequest;
	}
	
	public ArRequest getARRequestIt005(String busPlace, String comCode, String doctype, RicItCUTETrainingRoomUsageReport entity) {
		ArRequest returnRequest = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		try {

			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
					entity.getTransactionNo());
			
			Header header = returnRequest.getHeader().get(0);
			header.setParkDocument(FLAG.X_FLAG);	
			
			// sapCustomerAdrr
			CustomerRes sapAdrr = customerService.getSAPCustomerAddress(entity.getEntreprenuerCode(),
					StringUtils.isNotBlank(entity.getEntreprenuerBranch())
							? entity.getEntreprenuerBranch().substring(0, 5)
							: null);

			// calculate
			BigDecimal amount = entity.getTotalChargeRates();
			BigDecimal vat = amount.multiply(new BigDecimal(0.07));
			BigDecimal totalAmount = amount.add(vat);
			// Create Item1
			Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("01"); // require
			item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER)); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString()); // require
			item1.setTaxCode("O7");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(null);
			item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
			item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
			item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
			item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
			item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
			item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getStreet() : "NO DATA");
			item1.setCountry("TH");
			item1.setWHTType1("A1");
			item1.setWHTCode1("02");
			item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(amount).toString());
//			item1.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item1.setText(SAPConstants.IT.TEXT);
			item1.setContractNo(entity.getContractNo());
			itemList.add(item1);

			// Create Item2
			Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item2.setPostingKey("50"); // require
			item2.setAccount("4105170001"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(amount).negate().toString()); // require
			item2.setTaxCode("O7");
//			item2.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item2.setText(SAPConstants.IT.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			itemList.add(item2);

			// Create Item3
			Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101001"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
			item3.setTaxCode("O7");
//			item3.setAssignment(eleReqChange.getNewSerialNo());
//			item3.setText(SAPConstants.IT.TEXT);
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(amount).toString());
			itemList.add(item3);

			returnRequest.getHeader().get(0).setItem(itemList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnRequest;
	}
}
