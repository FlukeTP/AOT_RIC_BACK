package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterReq;
import aot.water.model.RicWaterReqChange;
import aot.water.repository.jpa.RicWaterReqChangeRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_7_2 {

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicWaterReqChangeRepository ricWaterReqChangeRepository;

	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;

	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SapArRequest_7_2.class);

	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) throws Exception {
		ArRequest returnRequest = new ArRequest();

		List<Item> itemList = new ArrayList<Item>();
		RicWaterReqChange waterReqChange = ricWaterReqChangeRepository.findById(Long.valueOf(id)).get();
		RicWaterReq waterReq = ricWaterReqRepository.findById(Long.valueOf(waterReqChange.getReqId())).get();
		// sap lg
//		List<SapRicControl> sapReqLgList = sapRicControlDao.findByRefkey1(waterReq.getTransactionNoLg());
//		SapRicControl sapReqLg = new SapRicControl();
//		if (0 < sapReqLgList.size()) {
//			sapReqLg = sapReqLgList.get(0);
//		}

		returnRequest = commonARRequest.getThreeTemplate(busPlace, comCode, doctype,
				waterReqChange.getTransactionNoCash());

		BigDecimal chageRate = new BigDecimal(0);
		BigDecimal chargeVat = new BigDecimal(0);
		BigDecimal totalChargeRate = new BigDecimal(0);
		if (StringUtils.isNotEmpty(waterReq.getInsuranceRates())) {
			chageRate = new BigDecimal(waterReq.getInsuranceRates());
		}
		if (StringUtils.isNotEmpty(waterReq.getVatInsurance())) {
			chargeVat = new BigDecimal(waterReq.getVatInsurance());
		}
		if (StringUtils.isNotEmpty(waterReq.getTotalInsuranceChargeRates())) {
			totalChargeRate = new BigDecimal(waterReq.getTotalInsuranceChargeRates());
		}

		// Create Item1
		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		List<SapRicControl> sapControls = sapRicControlDao.findByRefkey1(waterReq.getTransactionNoLg());
		if (sapControls.size() > 0) {
			String receipt = sapControls.get(0).getDzdocNo();
			String dzYear = sapControls.get(0).getDzyear();
			if(receipt != null && dzYear != null) {
				item1.setAssignment(receipt.concat(dzYear)); //receiptYYYY
			} else {
				throw new Exception("dzdocNo and dzyear is not null or empty !! => " + "dzdocNo: " + receipt + ", dzyear: " + dzYear);
			}
		}
		item1.setPostingKey("11"); // require
		item1.setAccount(waterReqChange.getCustomerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).negate().toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
//		item1.setAssignment(waterReqChange.getReceiptNoReqlg().concat(sapReqLg.getDzyear()));
		item1.setText(SAPConstants.WATER.TEXT);
		item1.setContractNo(waterReqChange.getContractNo());
//		item1.setInvoiceRef(sapReqLg.getDzdocNo());
//		item1.setFiscalYear(sapReqLg.getDzyear());
//		item1.setLineItem("001");
		itemList.add(item1);

		// Create Item2
		Item item2 = returnRequest.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("09"); // require
		item2.setAccount(waterReqChange.getCustomerCode()); // require
		item2.setSpGL(SPECIAL_GL.SPGL_3);
		item2.setAmount(NumberUtils.roundUpTwoDigit(chageRate).toString()); // require
		item2.setTaxCode("O7");
		item2.setAssignment(waterReqChange.getNewSerialNo());
		item2.setText(SAPConstants.WATER.TEXT);
		item2.setContractNo(waterReqChange.getContractNo());
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		itemList.add(item2);

		// Create Item3
		Item item3 = returnRequest.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("40"); // require
		item3.setAccount("2450101001"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(chargeVat).toString()); // require
		item3.setTaxCode("O7");
		item3.setAssignment(waterReqChange.getNewSerialNo());
		item3.setText(SAPConstants.WATER.TEXT);
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(chageRate).toString());
		itemList.add(item3);

		returnRequest.getHeader().get(0).setItem(itemList);

		return returnRequest;
	}

}
