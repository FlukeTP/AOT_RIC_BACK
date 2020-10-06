package aot.communicate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_communicate_req_hdr")
public class RicCommunicateReqHdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2715248348121930384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "phone_amount")
	private BigDecimal phoneAmount;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "insuranceRates")
	private BigDecimal insuranceRates;

	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;

	@Column(name = "remark")
	private String remark;

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error")
	private String sapError;

	@Column(name = "sap_json_res")
	private String sapJsonRes;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_deleted")
	private String isDeleted;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "cancel_date")
	private Date cancelDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_branch")
	private String bankBranch;

	@Column(name = "bank_explanation")
	private String bankExplanation;

	@Column(name = "bank_guarantee_no")
	private String bankGuaranteeNo;

	@Column(name = "bank_exp_no")
	private Date bankExpNo;

	@Column(name = "flag_cancel")
	private String flagCancel;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "ro_number")
	private String roNumber;

	@Column(name = "ro_name")
	private String roName;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_all")
	private BigDecimal totalAll;

	@Column(name = "transaction_no_cancel")
	private String transactionNoCancel;

	@Column(name = "invoice_no_cancel")
	private String invoiceNoCancel;

	@Column(name = "sap_status_cancel")
	private String sapStatusCancel;

	@Column(name = "sap_error_cancel")
	private String sapErrorCancel;

	@Column(name = "sap_json_res_cancel")
	private String sapJsonResCancel;

	@Column(name = "sap_json_req_cancel")
	private String sapJsonReqCancel;

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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public Date getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(Date bankExpNo) {
		this.bankExpNo = bankExpNo;
	}

	public String getFlagCancel() {
		return flagCancel;
	}

	public void setFlagCancel(String flagCancel) {
		this.flagCancel = flagCancel;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
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

	public String getTransactionNoCancel() {
		return transactionNoCancel;
	}

	public void setTransactionNoCancel(String transactionNoCancel) {
		this.transactionNoCancel = transactionNoCancel;
	}

	public String getInvoiceNoCancel() {
		return invoiceNoCancel;
	}

	public void setInvoiceNoCancel(String invoiceNoCancel) {
		this.invoiceNoCancel = invoiceNoCancel;
	}

	public String getSapStatusCancel() {
		return sapStatusCancel;
	}

	public void setSapStatusCancel(String sapStatusCancel) {
		this.sapStatusCancel = sapStatusCancel;
	}

	public String getSapErrorCancel() {
		return sapErrorCancel;
	}

	public void setSapErrorCancel(String sapErrorCancel) {
		this.sapErrorCancel = sapErrorCancel;
	}

	public String getSapJsonResCancel() {
		return sapJsonResCancel;
	}

	public void setSapJsonResCancel(String sapJsonResCancel) {
		this.sapJsonResCancel = sapJsonResCancel;
	}

	public String getSapJsonReqCancel() {
		return sapJsonReqCancel;
	}

	public void setSapJsonReqCancel(String sapJsonReqCancel) {
		this.sapJsonReqCancel = sapJsonReqCancel;
	}

}