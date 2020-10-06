package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.WaterConstants;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import aot.water.model.RicWaterRateCharge;
import aot.water.model.RicWaterReq;
import aot.water.model.RicWaterReqChange;
import aot.water.repository.jpa.RicWaterRateChargeRepository;
import aot.water.repository.jpa.RicWaterReqChangeRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_3 {
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;
	
	@Autowired
	private RicWaterReqChangeRepository ricWaterReqChangeRepository;
	
	@Autowired
	private RicWaterRateChargeRepository ricWaterRateChargeRepository;
	
	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype, String flagPage) {
		ArRequest returnRequest = null;
		Header header = null;
		Item item1 = null;
		
		switch (flagPage) {
		case "WATER003":
		RicWaterReq waterReq = ricWaterReqRepository.findById(id).get();
		returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype, waterReq.getTransactionNoLg());
		
		header = returnRequest.getHeader().get(0);
		header.setRefKeyHeader2(WaterConstants.LG);
		header.setDocHeaderText(waterReq.getContractNo());
		
		item1 = returnRequest.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("09");
		item1.setAccount(waterReq.getCustomerCode());
		item1.setSpGL("I");
		item1.setAmount(NumberUtils.roundUpTwoDigit(waterReq.getTotalInstallChargeRates()).toString());
		item1.setCustomerBranch(waterReq.getCustomerBranch().split(":")[0]);
		item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(waterReq.getInsuranceRates()).toString());
		item1.setReferenceKey1(ConvertDateUtils.formatDateToString(waterReq.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
		item1.setContractNo(waterReq.getContractNo());
		item1.setTextApplicationObject("DOC_ITEM");
		item1.setTextID("0001");
		item1.setLongText(waterReq.getBankName().concat("/").concat(waterReq.getBankBranch()).concat("/").concat(waterReq.getRemark()));
		item1.setText(waterReq.getBankGuaranteeNo());
		break;
		
		case "WATER008":
			RicWaterReqChange water008 = ricWaterReqChangeRepository.findById(id).get();
			RicWaterReq water008Req = ricWaterReqRepository.findById(water008.getReqId()).get();
			returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype,
					water008.getTransactionNoLg());
			
			BigDecimal chageRate = new BigDecimal(0);
			BigDecimal chargeVat = new BigDecimal(0);
			BigDecimal totalChargeRate = new BigDecimal(0);
			List<RicWaterRateCharge> ricWaterRateChargeList = ricWaterRateChargeRepository.findByReqId(id);
			if (null != ricWaterRateChargeList) {
				for (RicWaterRateCharge rate : ricWaterRateChargeList) {
					if (SAPConstants.CHARGE_TYPE_LG.equals(rate.getChargeType())) {
						chageRate = new BigDecimal(rate.getChargeRate());
						chargeVat = new BigDecimal(rate.getChargeVat());
						totalChargeRate = new BigDecimal(rate.getTotalChargeRate());
						break;
					}
				}
			}
			
			header = returnRequest.getHeader().get(0);
			header.setRefKeyHeader2(WaterConstants.LG);
			header.setDocHeaderText(water008.getContractNo());
			
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("09");
			item1.setAccount(water008.getCustomerCode());
			item1.setSpGL(SPECIAL_GL.SPGL_I);
			item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString());
			item1.setCustomerBranch(water008.getCustomerBranch().split(":")[0].trim());
			item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(chargeVat).toString());
			item1.setReferenceKey1(ConvertDateUtils.formatDateToString(water008Req.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
			item1.setContractNo(water008.getContractNo());
			item1.setTextApplicationObject("DOC_ITEM");
			item1.setTextID("0001");
			item1.setLongText(water008Req.getBankName().concat("/").concat(water008Req.getBankBranch()).concat("/").concat(water008Req.getRemark()));
			item1.setText(water008Req.getBankGuaranteeNo());
			break;
		}
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}
}
