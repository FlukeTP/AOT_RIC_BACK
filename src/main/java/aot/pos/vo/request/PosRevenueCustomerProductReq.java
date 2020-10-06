package aot.pos.vo.request;

public class PosRevenueCustomerProductReq {

	private Long cusProId;
	private String contractNo;
	private String productType;
	private String includingVatSale;
	private String excludingVatSale;
	private String receiptNum;
	
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
	public String getIncludingVatSale() {
		return includingVatSale;
	}
	public void setIncludingVatSale(String includingVatSale) {
		this.includingVatSale = includingVatSale;
	}
	public String getExcludingVatSale() {
		return excludingVatSale;
	}
	public void setExcludingVatSale(String excludingVatSale) {
		this.excludingVatSale = excludingVatSale;
	}
	public String getReceiptNum() {
		return receiptNum;
	}
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	
}
