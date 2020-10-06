package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.phone.model.RicPhoneRateCharge;
import aot.phone.model.RicPhoneReq;
import aot.phone.model.RicPhoneReqCancel;
import aot.phone.repository.jpa.RicPhoneRateChargeRepository;
import aot.phone.repository.jpa.RicPhoneReqCancelRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_7_3 {

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicPhoneReqRepository ricPhoneReqRepository;

	@Autowired
	private RicPhoneReqCancelRepository ricPhoneReqCancelRepository;

	@Autowired
	private RicPhoneRateChargeRepository ricPhoneRateChargeRepository;

	@Autowired
	private SapRicControlDao sapRicControlDao;

	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_7_3.class);

	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) throws Exception {
		ArRequest returnRequest = new ArRequest();

		List<Item> itemList = new ArrayList<Item>();

		RicPhoneReqCancel phoneReqCancel = ricPhoneReqCancelRepository.findById(Long.valueOf(id)).get();
		RicPhoneReq phoneReq = ricPhoneReqRepository.findById(Long.valueOf(phoneReqCancel.getPhoneReqId())).get();

		BigDecimal chageRate = new BigDecimal(0);
		BigDecimal chargeVat = new BigDecimal(0);
		BigDecimal totalChargeRate = new BigDecimal(0);
		List<RicPhoneRateCharge> ricPhoneRateChargeList = ricPhoneRateChargeRepository
				.findByReqId(String.valueOf(phoneReq.getPhoneReqId()));
		for (RicPhoneRateCharge rate : ricPhoneRateChargeList) {
			if (SAPConstants.DEPOSIT_TH.equals(rate.getTypeName())) {
				chageRate = rate.getChargeRates();
				chargeVat = rate.getVat();
				totalChargeRate = rate.getTotalChargeRates().negate();
				break;
			}
		}
		returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
				phoneReqCancel.getTransactionNoLg());
		
		// Create Item1
		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("11"); // require
		item1.setAccount(phoneReqCancel.getCustomerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
		item1.setText(SAPConstants.PHONE.TEXT);
		item1.setContractNo(phoneReqCancel.getContractNo());
		// item1.setInvoiceRef(sapReqLg.getDzdocNo());
		// item1.setFiscalYear(sapReqLg.getDzyear());
		// item1.setLineItem("001");
		List<SapRicControl> sapControls = sapRicControlDao.findByRefkey1(phoneReq.getTransactionNoLg());
		if (sapControls.size() > 0) {
			String receipt = sapControls.get(0).getDzdocNo();
			String dzYear = sapControls.get(0).getDzyear();
			if(receipt != null && dzYear != null) {
				item1.setAssignment(receipt.concat(dzYear)); //receiptYYYY
			} else {
				throw new Exception("dzdocNo and dzyear is not null or empty !! => " + "dzdocNo: " + receipt + ", dzyear: " + dzYear);
			}
		}
		itemList.add(item1);

		// Create Item2
		Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("09"); // require
		item2.setAccount(phoneReqCancel.getCustomerCode()); // require
		item2.setSpGL(SPECIAL_GL.SPGL_5);
		item2.setAmount(NumberUtils.roundUpTwoDigit(chageRate).toString()); // require
		item2.setTaxCode("O7");
		item2.setAssignment(phoneReqCancel.getPhoneNo());
		item2.setText(SAPConstants.PHONE.TEXT);
		item2.setContractNo(phoneReqCancel.getContractNo());
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		itemList.add(item2);

		// Create Item3
		Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("40"); // require
		item3.setAccount("2450101001"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(chargeVat).toString()); // require
		item3.setTaxCode("O7");
		item3.setAssignment(phoneReqCancel.getPhoneNo());
		item3.setText(SAPConstants.PHONE.TEXT);
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(chageRate).toString());
		itemList.add(item3);

		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
