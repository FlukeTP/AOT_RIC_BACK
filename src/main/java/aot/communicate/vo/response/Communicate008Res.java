package aot.communicate.vo.response;

import java.math.BigDecimal;
import java.util.Date;

public class Communicate008Res {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String contractNo;
	private BigDecimal totalChargeRates;
	private BigDecimal vat;
	private BigDecimal totalAll;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String customerBranch;
	private Date requestDate;
	private String invoiceNo;
	private String receiptNo;
	private String transactionReq;
	private String roNumber;
	private String roName;
	private String requestDateStr;
	private String endDateStr;
	private String reverseBtn;
	private String flagEndDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}

	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}

	public String getEntreprenuerName() {
		return entreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTotalAll() {
		return totalAll;
	}

	public void setTotalAll(BigDecimal totalAll) {
		this.totalAll = totalAll;
	}

	public String getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	public String getSapError() {
		return sapError;
	}

	public void setSapError(String sapError) {
		this.sapError = sapError;
	}

	public String getSapJsonRes() {
		return sapJsonRes;
	}

	public void setSapJsonRes(String sapJsonRes) {
		this.sapJsonRes = sapJsonRes;
	}

	public String getSapJsonReq() {
		return sapJsonReq;
	}

	public void setSapJsonReq(String sapJsonReq) {
		this.sapJsonReq = sapJsonReq;
	}

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getTransactionReq() {
		return transactionReq;
	}

	public void setTransactionReq(String transactionReq) {
		this.transactionReq = transactionReq;
	}

	public String getRoNumber() {
		return roNumber;
	}

	public void setRoNumber(String roNumber) {
		this.roNumber = roNumber;
	}

	public String getRoName() {
		return roName;
	}

	public void setRoName(String roName) {
		this.roName = roName;
	}

	public String getRequestDateStr() {
		return requestDateStr;
	}

	public void setRequestDateStr(String requestDateStr) {
		this.requestDateStr = requestDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getReverseBtn() {
		return reverseBtn;
	}

	public void setReverseBtn(String reverseBtn) {
		this.reverseBtn = reverseBtn;
	}

	public String getFlagEndDate() {
		return flagEndDate;
	}

	public void setFlagEndDate(String flagEndDate) {
		this.flagEndDate = flagEndDate;
	}
}
