package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.communicate.model.RicCommunicateFlightScheduleInfo;
import aot.communicate.model.RicCommunicateInfo;
import aot.communicate.repository.jpa.RicCommunicateFlightScheduleInfoRepository;
import aot.communicate.repository.jpa.RicCommunicateInfoRepository;
import aot.firebrigade.model.RicFirebrigadeManage;
import aot.firebrigade.repository.jpa.RicFirebrigadeManageRepository;
import aot.heavyeqp.model.RicHeavyEquipmentRevenue;
import aot.heavyeqp.repository.jpa.RicHeavyEquipmentRevenueRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_1_10 {

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_1_10.class);

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicFirebrigadeManageRepository ricFirebrigadeManageRepository;

	@Autowired
	private RicCommunicateInfoRepository ricCommunicateInfoRepository;
	
	@Autowired
	private RicCommunicateFlightScheduleInfoRepository ricCommunicateFlightScheduleInfoRepository;
	
	@Autowired
	private RicHeavyEquipmentRevenueRepository ricHeavyEquipmentRevenueRepository;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, Long id, String flagPage) {
		ArRequest returnRequest = null;
		List<Item> itemList = new ArrayList<Item>();
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;

		switch (flagPage) {
		case "FIRE001":
			RicFirebrigadeManage firebrigade = ricFirebrigadeManageRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
					firebrigade.getTransactionNo());
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);

			BigDecimal amount = new BigDecimal(0);
			BigDecimal vat = firebrigade.getVat();
			BigDecimal totalAmount = firebrigade.getTotalAmount();
			if (SAPConstants.UNIT.TIME_TH.equals(firebrigade.getUnit())) {
				amount = firebrigade.getChargeRates();
			} else {
				amount = firebrigade.getChargeRates().multiply(new BigDecimal(firebrigade.getPersonAmount()));
			}
			// Create Item1
			item1.setPostingKey("01"); // require
			item1.setAccount(firebrigade.getCustomerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString()); // require
			item1.setTaxCode("DS");
			item1.setPaymentTerm("Z001");
			item1.setCustomerBranch(firebrigade.getCustomerBranch().split(":")[0]);
			// item1.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item1.setText(SAPConstants.FIREBRIGADE.TEXT);
			item1.setWHTType1("R1");
			item1.setWHTCode1("02");
			item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(amount).toString());

			// Create Item2
			item2.setPostingKey("50"); // require
			item2.setAccount("4105230002"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(amount).negate().toString()); // require
			item2.setTaxCode("DS");
			// item2.setAssignment(garbagedisReqHdr.getEquipmentCode());
			item2.setText(SAPConstants.FIREBRIGADE.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			item2.setContractNo(firebrigade.getContractNo());

			// Create Item3
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101002"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
			item3.setTaxCode("DS");
			// item3.setAssignment(eleReqChange.getNewSerialNo());
			// item3.setText(DEPOSIT_TEXT.DEPOSIT_EQUIPMENT);
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(amount).toString());
			break;
		case "COMMU007":
			RicCommunicateInfo commu007 = ricCommunicateInfoRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, commu007.getTransactionNo());
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);

			// Item1
			item1.setPostingKey("01"); // require
			item1.setAccount(commu007.getEntreprenuerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(commu007.getTotalAll()).toString()); // require
			item1.setTaxCode("DS");
			item1.setCustomerBranch(commu007.getCustomerBranch().split(":")[0]);
			item1.setWHTType1("R1");
			item1.setWHTType2("02");
			item1.setWHTBaseAmount1(commu007.getTotalChargeRates().toString());
			item1.setText(SAPConstants.COMMUNICATE.TEXT);

			// Item2
			item2.setPostingKey("50"); // require
			item2.setAccount("4105090001"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(commu007.getTotalChargeRates()).negate().toString()); // require
			item2.setTaxCode("DS");
			item2.setText(SAPConstants.COMMUNICATE.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			item2.setContractNo(commu007.getContractNo());

			// Item3
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101002"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(commu007.getVat()).negate().toString()); // require
			item3.setTaxCode("DS");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(commu007.getTotalChargeRates()).toString());
			item3.setText(SAPConstants.COMMUNICATE.TEXT);
			break;
		case "COMMU008":
			RicCommunicateFlightScheduleInfo commu008 = ricCommunicateFlightScheduleInfoRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, commu008.getTransactionNo());
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);

			// Item1
			item1.setPostingKey("01"); // require
			item1.setAccount(commu008.getEntreprenuerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(commu008.getTotalAll()).toString()); // require
			item1.setTaxCode("DS");
			item1.setCustomerBranch(commu008.getCustomerBranch().split(":")[0]);
			item1.setWHTType1("R1");
			item1.setWHTType2("02");
			item1.setWHTBaseAmount1(commu008.getTotalChargeRates().toString());
			item1.setText(SAPConstants.COMMUNICATE.TEXT);

			// Item2
			item2.setPostingKey("50"); // require
			item2.setAccount("4105090001"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(commu008.getTotalChargeRates()).negate().toString()); // require
			item2.setTaxCode("DS");
			item2.setText(SAPConstants.COMMUNICATE.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			item2.setContractNo(commu008.getContractNo());

			// Item3
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101002"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(commu008.getVat()).negate().toString()); // require
			item3.setTaxCode("DS");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(commu008.getTotalChargeRates()).toString());
			item3.setText(SAPConstants.COMMUNICATE.TEXT);
			break;
		case "HEAVY001":
			RicHeavyEquipmentRevenue heavy = ricHeavyEquipmentRevenueRepository.findById(id).get();
			returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, heavy.getTransactionNo());
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item2 = returnRequest.getHeader().get(0).getItem().get(1);
			item3 = returnRequest.getHeader().get(0).getItem().get(2);
			
			// Item1
			item1.setPostingKey("01"); // require
			item1.setAccount(heavy.getCustomerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(heavy.getTotalMoney()).toString()); // require
			item1.setTaxCode("DS");
			item1.setCustomerBranch(heavy.getCustomerBranch().split(":")[0]);
			item1.setWHTType1("R1");
			item1.setWHTType2("02");
			item1.setWHTBaseAmount1(heavy.getAllFees().add(heavy.getDriverRates()).toString());
			item1.setText(SAPConstants.EQUIPMENT.TEXT);
			
			// Item2
			item2.setPostingKey("50"); // require
			item2.setAccount("4105090001"); // require
			item2.setAmount(NumberUtils.roundUpTwoDigit(heavy.getAllFees().add(heavy.getDriverRates())).negate().toString()); // require
			item2.setTaxCode("DS");
			item2.setText(SAPConstants.EQUIPMENT.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("27.0");
			item2.setContractNo(heavy.getContractNo());
			
			// Item3
			item3.setPostingKey("50"); // require
			item3.setAccount("2450101002"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(heavy.getVat()).negate().toString()); // require
			item3.setTaxCode("DS");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(heavy.getAllFees().add(heavy.getDriverRates())).toString());
			item3.setText(SAPConstants.EQUIPMENT.TEXT);
			break;
		}

		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
