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
import aot.water.model.RicWaterReq;
import aot.water.model.RicWaterReqChange;
import aot.water.repository.jpa.RicWaterRateChargeRepository;
import aot.water.repository.jpa.RicWaterReqChangeRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_4 {
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;
	
	@Autowired
	private RicWaterRateChargeRepository ricWaterRateChargeRepository;
	
	@Autowired
	private RicWaterReqChangeRepository ricWaterReqChangeRepository;
	
	@Autowired
	private SysConstantService sysConstantService;

	public ArRequest getARRequest(String busPlace, String comCode, Long id, String doctype, String flagPage) {
		ArRequest returnRequest = null;
		Header header = null;
		Item item1 = null;
		
		switch (flagPage) {
		case "WATER003":
			RicWaterReq waterReq = ricWaterReqRepository.findById(id).get();
			returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype, waterReq.getTransactionNoCash());
			header = returnRequest.getHeader().get(0);
			header.setDocHeaderText(waterReq.getContractNo());
			header.setRefKeyHeader2(WaterConstants.CASH);
			
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("09");
			item1.setAccount(waterReq.getCustomerCode());
			item1.setSpGL("I");
			item1.setAmount(NumberUtils.roundUpTwoDigit(waterReq.getTotalInsuranceChargeRates()).toString());
			item1.setCustomerBranch(waterReq.getCustomerBranch().split(":")[0]);
			item1.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_WATER);
			item1.setReferenceKey2("O7/".concat(NumberUtils.roundUpTwoDigit(waterReq.getVatInsurance()).toString()));
			item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(waterReq.getInsuranceRates()).toString());
			item1.setContractNo(waterReq.getContractNo());
			break;

		case "WATER008":
			RicWaterReqChange water008 = ricWaterReqChangeRepository.findById(id).get();
			RicWaterReq water008Req = ricWaterReqRepository.findById(water008.getReqId()).get();
			returnRequest = commonARRequest.getOneTemplate(busPlace, comCode, doctype, null);
			header = returnRequest.getHeader().get(0);
			header.setDocHeaderText(water008.getContractNo());
			header.setRefKeyHeader2(WaterConstants.CASH);
			
//			BigDecimal chageRate = new BigDecimal(0);
//			BigDecimal chargeVat = new BigDecimal(0);
//			BigDecimal totalChargeRate = new BigDecimal(0);
//			List<RicWaterRateCharge> ricWaterRateChargeList = ricWaterRateChargeRepository.findByReqId(water008Req.getReqId());
//			if (null != ricWaterRateChargeList) {
//				for (RicWaterRateCharge rate : ricWaterRateChargeList) {
//					if (SAPConstants.CHARGE_TYPE_LG.equals(rate.getChargeType())) {
//						chageRate = new BigDecimal(rate.getChargeRate());
//						chargeVat = new BigDecimal(rate.getChargeVat());
//						totalChargeRate = new BigDecimal(rate.getTotalChargeRate());
//						break;
//					}
//				}
//			}
			item1 = returnRequest.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("09");
			item1.setAccount(water008.getCustomerCode());
			item1.setSpGL(SPECIAL_GL.SPGL_I);
			item1.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getTotalVat(new BigDecimal(water008Req.getTotalChargeRates()))).toString());
			item1.setCustomerBranch(water008.getCustomerBranch().split(":")[0].trim());
			item1.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_WATER);
			item1.setReferenceKey2("O7/".concat(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(new BigDecimal(water008Req.getTotalChargeRates()))).toString()));
			item1.setReferenceKey3(NumberUtils.roundUpTwoDigit(new BigDecimal(water008Req.getTotalChargeRates())).toString());
			item1.setContractNo(water008.getContractNo());
			break;
		}
		
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(item1);
		returnRequest.getHeader().get(0).setItem(itemList);
		return returnRequest;
	}

}
