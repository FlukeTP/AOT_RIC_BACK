package aot.pos.vo.request;

public class PosRevenueCustomerPaymentReq {

	private Long cusPayId;
	private String contractNo;
	private String branch;
	private String paymentType;
	private String currency;
	private String exchangeRate;
	private String amount;
	private String amountBaht;
	
	public Long getCusPayId() {
		return cusPayId;
	}
	public void setCusPayId(Long cusPayId) {
		this.cusPayId = cusPayId;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmountBaht() {
		return amountBaht;
	}
	public void setAmountBaht(String amountBaht) {
		this.amountBaht = amountBaht;
	}
	
}
