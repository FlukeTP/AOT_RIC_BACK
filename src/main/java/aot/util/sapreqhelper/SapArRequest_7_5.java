package aot.util.sapreqhelper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import aot.it.model.RicItOtherCreateInvoice;
import aot.it.repository.jpa.RicItOtherCreateInvoiceRepository;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SapArRequest_7_5 {
	
	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	@Autowired
	private RicItOtherCreateInvoiceRepository ricItOtherCreateInvoiceRepository;
	

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, Long id) {
		ArRequest arReq = null;
		Item item1 = null;
		Item item2 = null;
		Item item3 = null;
		List<SapRicControl> sapControl = null;
		JsonObject jsonObject = null;
		String YEAR = null;
		
		BigDecimal chageRate = new BigDecimal(0);
		BigDecimal chargeVat = new BigDecimal(0);
		BigDecimal totalChargeRate = new BigDecimal(0);
		
		RicItOtherCreateInvoice entity = ricItOtherCreateInvoiceRepository.findById(Long.valueOf(id)).get();
		arReq = commonARRequest.getThreeTemplate(busPlace, comCode, doctype, entity.getTransactionNoCancel());
		item1 = arReq.getHeader().get(0).getItem().get(0);
		item2 = arReq.getHeader().get(0).getItem().get(1);
		item3 = arReq.getHeader().get(0).getItem().get(2);
		
		totalChargeRate = entity.getTotalChargeRates();
		chageRate = totalChargeRate.divide(BigDecimal.valueOf(1.07));
		chargeVat = (totalChargeRate.subtract(chageRate));
		
		sapControl = sapRicControlDao.findByRefkey1(entity.getTransactionNoCancel());
		jsonObject = new JsonParser().parse(entity.getSapJsonRes()).getAsJsonObject();
		YEAR = jsonObject.get(SAPConstants.YEAR).getAsString();
		
		// Item1
		item1.setPostingKey("11"); // require
		item1.setAccount(entity.getEntreprenuerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
		item1.setCustomerBranch(entity.getEntreprenuerBranch().split(":")[0]);
		item1.setText(SAPConstants.IT.TEXT);
		item1.setContractNo(entity.getContractNo());
//		if (sapControl.size() > 0) {
//			item1.setAssignment(sapControl.get(0).getDzdocNo().concat(YEAR));
//		}
		
		// Item2
		item2.setPostingKey("09"); // require
		item2.setAccount("11000000"); // require
		item2.setSpGL("7");
		item2.setAmount(NumberUtils.roundUpTwoDigit(chageRate).toString()); // require
		item2.setTaxCode("O7");
		item2.setText(SAPConstants.IT.TEXT);
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		item2.setContractNo(entity.getContractNo());

		// Item3
		item3.setPostingKey("40"); // require
		item3.setAccount("2450101001"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(chargeVat).toString()); // require
		item3.setTaxCode("O7");
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(chageRate).toString());
		item3.setText(SAPConstants.COMMUNICATE.TEXT);
		
		return arReq;
	}

}
