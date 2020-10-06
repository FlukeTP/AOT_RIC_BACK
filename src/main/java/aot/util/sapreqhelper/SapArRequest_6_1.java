package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricReq;
import aot.electric.repository.jpa.RicElectricRateChargeRepository;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_1 {
	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;
	
	@Autowired
	private RicElectricRateChargeRepository ricElectricRateChargeRepository;
	
	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype) {
		RicElectricReq electricReq = ricElectricReqRepository.findById(Long.valueOf(id)).get();
		
		ArRequest returnRequest = commonARRequest.getOneTemplate(busPlace, comCode , doctype, electricReq.getTransactionNoLg());

		List<RicElectricRateCharge> ricElectricRateChargeList = ricElectricRateChargeRepository.findByReqId(id);
		
		BigDecimal amount = new BigDecimal(0);

		for (RicElectricRateCharge ricElectricRateCharge : ricElectricRateChargeList) {
			if (SAPConstants.CHARGE_TYPE_LG.equals(ricElectricRateCharge.getChargeType())) {
				amount = amount.add(new BigDecimal(ricElectricRateCharge.getTotalChargeRate()));
				break;
			}
		}
		
		// Create add data header
		returnRequest.getHeader().get(0).setRefKeyHeader2(SAPConstants.PAYMENT_TYPE.LG);
		returnRequest.getHeader().get(0).setDocHeaderText(electricReq.getContractNo());
		
		Item item1 = returnRequest.getHeader().get(0).getItem().get(0);
		// Create Item1
		item1.setPostingKey("09");
		item1.setAccount(electricReq.getCustomerCode());
		item1.setSpGL("J");
		item1.setAmount(NumberUtils.roundUpTwoDigit(amount).toString());
		item1.setCustomerBranch(StringUtils.isNotBlank(electricReq.getCustomerBranch())? electricReq.getCustomerBranch().substring(0, 5) : null);
		item1.setReferenceKey1(ConvertDateUtils.formatDateToString(electricReq.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
		item1.setContractNo(electricReq.getContractNo());
		item1.setTextApplicationObject("DOC_ITEM");
		item1.setTextID("0001");
		item1.setLongText(electricReq.getBankName().concat("/").concat(electricReq.getBankBranch()).concat("/").concat(electricReq.getRemark()));
		item1.setText(electricReq.getBankGuaranteeNo());

		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
	
}
