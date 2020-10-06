package aot.util.sapreqhelper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.communicate.model.RicCommunicateReqHdr;
import aot.communicate.repository.jpa.RicCommunicateReqFlightScheduleHdrRepository;
import aot.communicate.repository.jpa.RicCommunicateReqHdrRepository;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_7_4 {

	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	@Autowired
	private RicCommunicateReqHdrRepository ricCommunicateReqHdrRepository;
	
	@Autowired
	private RicCommunicateReqFlightScheduleHdrRepository ricCommunicateReqFlightScheduleHdrRepository;
	
	@Autowired
	private SysConstantService sysConstantService;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, Long id, String flagPage) throws Exception {
		ArRequest arReq = null;
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;
		List<SapRicControl> sapControls = null;
		
		switch (flagPage) {
		case "COMMUNICATE002":
			RicCommunicateReqHdr entity = ricCommunicateReqHdrRepository.findById(id).get();
			arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNoCancel());
			item1 = arReq.getHeader().get(0).getItem().get(0);
			item2 = arReq.getHeader().get(0).getItem().get(1);
			item3 = arReq.getHeader().get(0).getItem().get(2);
			
			sapControls = sapRicControlDao.findByRefkey1(entity.getTransactionNo());
			if (sapControls.size() > 0) {
				String receipt = sapControls.get(0).getDzdocNo();
				String dzYear = sapControls.get(0).getDzyear();
				if(receipt != null && dzYear != null) {
					item1.setAssignment(receipt.concat(dzYear)); //receiptYYYY
				} else {
					throw new Exception("dzdocNo and dzyear is not null or empty !! => " + "dzdocNo: " + receipt + ", dzyear: " + dzYear);
				}
			}
			// Item1
			item1.setPostingKey("11"); // require
			item1.setAccount(entity.getEntreprenuerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(entity.getTotalAll().negate()).toString()); // require
			item1.setTaxCode("O7");
			item1.setPmtMethod("5");
			item1.setCustomerBranch(entity.getCustomerBranch().split(":")[0]);
			item1.setText(SAPConstants.COMMUNICATE.TEXT);
			item1.setContractNo(entity.getContractNo());
//			if (sapControl.size() > 0) {
//				item1.setInvoiceRef(sapControl.get(0).getDzdocNo());
//			}
//			item1.setFiscalYear(YEAR);
//			item1.setLineItem("001");
			
			// Item2
			item2.setPostingKey("09"); // require
			item2.setAccount("11000000"); // require
			item2.setSpGL("6");
			item2.setAmount(NumberUtils.roundUpTwoDigit(entity.getTotalChargeRates()).toString()); // require
			item2.setTaxCode("O7");
			item2.setText(SAPConstants.COMMUNICATE.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setContractNo(entity.getContractNo());

			// Item3
			item3.setPostingKey("40"); // require
			item3.setAccount("2450101001"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(entity.getVat()).toString()); // require
			item3.setTaxCode("O7");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(entity.getTotalChargeRates()).toString());
			item3.setText(SAPConstants.COMMUNICATE.TEXT);
			break;
		case "COMMUNICATE005":
			RicCommunicateReqFlightScheduleHdr commu005 = ricCommunicateReqFlightScheduleHdrRepository.findById(id).get();
			arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, commu005.getTransactionNoCancel());
			item1 = arReq.getHeader().get(0).getItem().get(0);
			item2 = arReq.getHeader().get(0).getItem().get(1);
			item3 = arReq.getHeader().get(0).getItem().get(2);
			
			sapControls = sapRicControlDao.findByRefkey1(commu005.getTransactionNo());
			if (sapControls.size() > 0) {
				String receipt = sapControls.get(0).getDzdocNo();
				String dzYear = sapControls.get(0).getDzyear();
				if(receipt != null && dzYear != null) {
					item1.setAssignment(receipt.concat(dzYear)); //receiptYYYY
				} else {
					throw new Exception("dzdocNo and dzyear is not null or empty !! => " + "dzdocNo: " + receipt + ", dzyear: " + dzYear);
				}
			}
			
			// Item1
			item1.setPostingKey("11"); // require
			item1.setAccount(commu005.getEntreprenuerCode()); // require
			item1.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getTotalVat(commu005.getAmountLg()).negate()).toString()); // require
			item1.setTaxCode("O7");
			item1.setPmtMethod("5");
			item1.setCustomerBranch(commu005.getCustomerBranch().split(":")[0]);
			item1.setText(SAPConstants.COMMUNICATE.TEXT);
			item1.setContractNo(commu005.getContractNo());
//			if (sapControl.size() > 0) {
//				item1.setInvoiceRef(sapControl.get(0).getDzdocNo());
//			}
//			item1.setFiscalYear(YEAR);
//			item1.setLineItem("001");
			
			// Item2
			item2.setPostingKey("09"); // require
			item2.setAccount("11000000"); // require
			item2.setSpGL("6");
			item2.setAmount(NumberUtils.roundUpTwoDigit(commu005.getAmountLg()).toString()); // require
			item2.setTaxCode("O7");
			item2.setText(SAPConstants.COMMUNICATE.TEXT);
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setContractNo(commu005.getContractNo());

			// Item3
			item3.setPostingKey("40"); // require
			item3.setAccount("2450101001"); // require
			item3.setAmount(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(commu005.getAmountLg())).toString()); // require
			item3.setTaxCode("O7");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(commu005.getAmountLg()).toString());
			item3.setText(SAPConstants.COMMUNICATE.TEXT);
			break;
		}
		return arReq;
	}
}
