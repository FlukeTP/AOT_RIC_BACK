package aot.pos.vo.response;

import java.math.BigDecimal;

public class PosRevenueCustomerPaymentRes {

	private Long cusPayId;
	private String contractNo;
	private String branch;
	private String paymentType;
	private String currency;
	private BigDecimal exchangeRate;
	private BigDecimal amount;
	private BigDecimal amountBaht;
	
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
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmountBaht() {
		return amountBaht;
	}
	public void setAmountBaht(BigDecimal amountBaht) {
		this.amountBaht = amountBaht;
	}
	
}
