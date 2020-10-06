package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.phone.model.RicPhoneRateCharge;
import aot.phone.model.RicPhoneReq;
import aot.phone.repository.jpa.RicPhoneRateChargeRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.PAYMENT_TYPE;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_6 implements SapArRequest {

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicPhoneReqRepository ricPhoneReqRepository;

	@Autowired
	private RicPhoneRateChargeRepository ricPhoneRateChargeRepository;

	@Override
	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) {
		List<RicPhoneRateCharge> ricPhoneRateChargeList = ricPhoneRateChargeRepository.findByReqId(id);
		RicPhoneReq phoneReq = ricPhoneReqRepository.findById(Long.valueOf(id)).get();
		ArRequest returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype,
				phoneReq.getTransactionNoCash());

		BigDecimal chargeRate = new BigDecimal(0);
		BigDecimal vat = new BigDecimal(0);
		BigDecimal totalChargeRate = new BigDecimal(0);

		for (RicPhoneRateCharge rate : ricPhoneRateChargeList) {
			if (SAPConstants.DEPOSIT_TH.equals(rate.getTypeName())) {
				chargeRate = rate.getChargeRates();
				vat = rate.getVat();
				totalChargeRate = rate.getTotalChargeRates();
				break;
			}
		}

		// set Header
		String payment = "";
		if (PAYMENT_TYPE.CASH_TH.equals(phoneReq.getPaymentType())) {
			payment = PAYMENT_TYPE.CASH_EN;
		} else {
			payment = PAYMENT_TYPE.LG;
		}
		returnRequest.getHeader().get(0).setRefKeyHeader2(payment);
		returnRequest.getHeader().get(0).setDocHeaderText(phoneReq.getContractNo());

		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		// Create Item1
		item1.setPostingKey("09");
		item1.setAccount(phoneReq.getEntrepreneurCode());
		item1.setSpGL(SPECIAL_GL.SPGL_K);
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString());
		item1.setCustomerBranch(phoneReq.getBranchCustomer().substring(0, 5));
		if (PAYMENT_TYPE.CASH_TH.equals(phoneReq.getPaymentType())) {
			item1.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_TELEPHONE);
			item1.setReferenceKey2("O7/" + NumberUtils.roundUpTwoDigit(vat).toString());
			item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(chargeRate).toString());
		} else {
			item1.setReferenceKey1(ConvertDateUtils.formatDateToString(phoneReq.getRequestEndDate(),
					ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
			item1.setTextApplicationObject("DOC_ITEM");
			item1.setTextID("0001");
			item1.setLongText(phoneReq.getBankName().concat("/").concat(phoneReq.getBankBranch()).concat("/")
					.concat(StringUtils.isNotBlank(phoneReq.getRemark()) ? phoneReq.getRemark() : ""));
			item1.setText(phoneReq.getBankGuaranteeNo());
		}
		item1.setContractNo(phoneReq.getContractNo());

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
