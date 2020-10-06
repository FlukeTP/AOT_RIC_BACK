package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricReq;
import aot.electric.repository.jpa.RicElectricRateChargeRepository;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_2 {

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private RicElectricRateChargeRepository ricElectricRateChargeRepository;

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_6_2.class);

	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype) {

		ArRequest returnRequest = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		RicElectricReq electricReq = ricElectricReqRepository.findById(Long.valueOf(id)).get();

		returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype, electricReq.getTransactionNoLg());

		// List<RicElectricRateCharge> ricElectricRateChargeList =
		// ricElectricRateChargeRepository.findByReqId(id);
		//
		// BigDecimal amount = new BigDecimal(0);
		// BigDecimal rateCharge = new BigDecimal(0);
		// BigDecimal vat = new BigDecimal(0);
		//
		// for (RicElectricRateCharge ricElectricRateCharge : ricElectricRateChargeList)
		// {
		// if
		// (SAPConstants.CHARGE_TYPE_LG.equals(ricElectricRateCharge.getChargeType())) {
		// rateCharge = new
		// BigDecimal(StringUtils.isNotBlank(ricElectricRateCharge.getChargeRate())
		// ? ricElectricRateCharge.getChargeRate()
		// : "0");
		// vat = new
		// BigDecimal(StringUtils.isNotBlank(ricElectricRateCharge.getChargeVat())
		// ? ricElectricRateCharge.getChargeVat()
		// : "0");
		// amount = new
		// BigDecimal(StringUtils.isNotBlank(ricElectricRateCharge.getTotalChargeRate())
		// ? ricElectricRateCharge.getTotalChargeRate()
		// : "0");
		// break;
		// }
		// }

		// Create add data header
		returnRequest.getHeader().get(0).setRefKeyHeader2(SAPConstants.PAYMENT_TYPE.CASH_EN);

		// Create Item1
		Item item = returnRequest.getHeader().get(0).getItem().get(0);
		item.setPostingKey("09");
		item.setAccount(electricReq.getCustomerCode());
		item.setSpGL("J");
		item.setAmount(NumberUtils.roundUpTwoDigit(electricReq.getTotalChargeRate()).toString()); // require
		item.setCustomerBranch(StringUtils.isNotBlank(electricReq.getCustomerBranch())
				? electricReq.getCustomerBranch().substring(0, 5)
				: null);

		item.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_ELECTRICITY);
		item.setReferenceKey2("O7/" + NumberUtils.roundUpTwoDigit(electricReq.getSumVatChargeRates()));
		item.setReferenceKey3(NumberUtils.roundUpTwoDigit(electricReq.getSumChargeRates()).toString());
		item.setContractNo(electricReq.getContractNo());

		itemList.add(item);

		returnRequest.getHeader().get(0).setItem(itemList);

		return returnRequest;
	}

}
