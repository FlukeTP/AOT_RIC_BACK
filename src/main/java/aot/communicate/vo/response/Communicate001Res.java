package aot.communicate.vo.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import aot.communicate.model.RicCommunicateReqDtl;

public class Communicate001Res {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private BigDecimal phoneAmount;
	private String phoneAmountDF;
	private String contractNo;
	private String mobileSerialNo;
	private BigDecimal chargeRates;
	private BigDecimal insuranceRates;
	private BigDecimal totalChargeRates;
	private BigDecimal vat;
	private BigDecimal totalAll;
	private String chargeRatesDF;
	private String insuranceRatesDF;
	private String totalChargeRatesDF;
	private String remark;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String customerBranch;
	private Date requestDate;
	private String requestDateStr;
	private Date endDate;
	private String endDateStr;
	private String invoiceNo;
	private String receiptNo;
	private String paymentType;
	private String paymentTypeTh;
	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNoStr;
	private String roNumber;
	private String roName;
	private String reverseBtn;
	private String cancelBtn;
	private String flagCancel;
	private String flagEndDate;
	private List<RicCommunicateReqDtl> details;
	private String transactionNo;

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

	public BigDecimal getPhoneAmount() {
		return phoneAmount;
	}

	public void setPhoneAmount(BigDecimal phoneAmount) {
		this.phoneAmount = phoneAmount;
	}

	public String getPhoneAmountDF() {
		return phoneAmountDF;
	}

	public void setPhoneAmountDF(String phoneAmountDF) {
		this.phoneAmountDF = phoneAmountDF;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getMobileSerialNo() {
		return mobileSerialNo;
	}

	public void setMobileSerialNo(String mobileSerialNo) {
		this.mobileSerialNo = mobileSerialNo;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getInsuranceRates() {
		return insuranceRates;
	}

	public void setInsuranceRates(BigDecimal insuranceRates) {
		this.insuranceRates = insuranceRates;
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

	public String getChargeRatesDF() {
		return chargeRatesDF;
	}

	public void setChargeRatesDF(String chargeRatesDF) {
		this.chargeRatesDF = chargeRatesDF;
	}

	public String getInsuranceRatesDF() {
		return insuranceRatesDF;
	}

	public void setInsuranceRatesDF(String insuranceRatesDF) {
		this.insuranceRatesDF = insuranceRatesDF;
	}

	public String getTotalChargeRatesDF() {
		return totalChargeRatesDF;
	}

	public void setTotalChargeRatesDF(String totalChargeRatesDF) {
		this.totalChargeRatesDF = totalChargeRatesDF;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getRequestDateStr() {
		return requestDateStr;
	}

	public void setRequestDateStr(String requestDateStr) {
		this.requestDateStr = requestDateStr;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentTypeTh() {
		return paymentTypeTh;
	}

	public void setPaymentTypeTh(String paymentTypeTh) {
		this.paymentTypeTh = paymentTypeTh;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankExplanation() {
		return bankExplanation;
	}

	public void setBankExplanation(String bankExplanation) {
		this.bankExplanation = bankExplanation;
	}

	public String getBankGuaranteeNo() {
		return bankGuaranteeNo;
	}

	public void setBankGuaranteeNo(String bankGuaranteeNo) {
		this.bankGuaranteeNo = bankGuaranteeNo;
	}

	public String getBankExpNoStr() {
		return bankExpNoStr;
	}

	public void setBankExpNoStr(String bankExpNoStr) {
		this.bankExpNoStr = bankExpNoStr;
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

	public String getReverseBtn() {
		return reverseBtn;
	}

	public void setReverseBtn(String reverseBtn) {
		this.reverseBtn = reverseBtn;
	}

	public String getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(String cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public String getFlagCancel() {
		return flagCancel;
	}

	public void setFlagCancel(String flagCancel) {
		this.flagCancel = flagCancel;
	}

	public String getFlagEndDate() {
		return flagEndDate;
	}

	public void setFlagEndDate(String flagEndDate) {
		this.flagEndDate = flagEndDate;
	}

	public List<RicCommunicateReqDtl> getDetails() {
		return details;
	}

	public void setDetails(List<RicCommunicateReqDtl> details) {
		this.details = details;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

}