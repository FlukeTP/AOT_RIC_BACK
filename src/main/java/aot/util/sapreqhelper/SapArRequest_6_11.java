package aot.util.sapreqhelper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.it.model.RicItOtherCreateInvoice;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.request.Item;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Service
public class SapArRequest_6_11 {

	@Autowired
	private CommonARRequest commonARRequest;

	public ArRequest getARRequest(String busPlace, String comCode, String doctype, RicItOtherCreateInvoice entity) {		
		ArRequest arReq = commonARRequest.getOneTemplate(busPlace, comCode, doctype, entity.getTransactionNo());
		Header header = arReq.getHeader().get(0);
		header.setDocHeaderText(entity.getContractNo());
		header.setRefKeyHeader2("LG");
		
		BigDecimal amount = entity.getChargeRates().multiply(entity.getTotalAmount());
		BigDecimal vat = amount.multiply(new BigDecimal(0.07));
		BigDecimal totalAmount = amount.add(vat);
		/* item 1 */
		Item item1 = arReq.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("09");
		item1.setAccount(entity.getEntreprenuerCode());
		item1.setSpGL("O");
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString());
		item1.setCustomerBranch(entity.getEntreprenuerBranch().split(":")[0]);
		/*----------------SET NEW LG-------------------*/
		item1.setContractNo(entity.getContractNo());
		header.setDocHeaderText(entity.getContractNo());
		item1.setText(entity.getBankGuaranteeNo());
		item1.setReferenceKey1(ConvertDateUtils.formatDateToString(entity.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
		item1.setTextApplicationObject("DOC_ITEM");
		item1.setLongText(entity.getBankName().concat("/").concat(entity.getBankBranch()).concat("/").concat(entity.getBankExplanation()));
		item1.setTextID("0001");
		/* ________________________________________________________________ */
		return arReq;
	}
}
