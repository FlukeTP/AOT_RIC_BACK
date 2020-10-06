package aot.pos.vo.response;

import java.math.BigDecimal;

public class PosRevenueCustomerProductRes {

	private Long cusProId;
	private String contractNo;
	private String productType;
	private BigDecimal includingVatSale;
	private BigDecimal excludingVatSale;
	private Long receiptNum;
	
	public Long getCusProId() {
		return cusProId;
	}
	public void setCusProId(Long cusProId) {
		this.cusProId = cusProId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getIncludingVatSale() {
		return includingVatSale;
	}
	public void setIncludingVatSale(BigDecimal includingVatSale) {
		this.includingVatSale = includingVatSale;
	}
	public BigDecimal getExcludingVatSale() {
		return excludingVatSale;
	}
	public void setExcludingVatSale(BigDecimal excludingVatSale) {
		this.excludingVatSale = excludingVatSale;
	}
	public Long getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(Long receiptNum) {
		this.receiptNum = receiptNum;
	}
	
}
