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
@Table(name = "ric_communicate_req_flight_schedule_hdr")
public class RicCommunicateReqFlightScheduleHdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3185977292663610379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "rental_area_code")
	private String rentalAreaCode;

	@Column(name = "rental_area_name")
	private String rentalAreaName;

	@Column(name = "remark")
	private String remark;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "end_date")
	private Date endDate;

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

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error")
	private String sapError;

	@Column(name = "sap_json_res")
	private String sapJsonRes;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "amount_lg")
	private BigDecimal amountLg;

	@Column(name = "amount_month")
	private BigDecimal amountMonth;

	@Column(name = "cancel_date")
	private Date cancelDate;

	@Column(name = "sap_status_cancel")
	private String sapStatusCancel;

	@Column(name = "sap_error_cancel")
	private String sapErrorCancel;

	@Column(name = "sap_json_res_cancel")
	private String sapJsonResCancel;

	@Column(name = "sap_json_req_cancel")
	private String sapJsonReqCancel;

	@Column(name = "invoice_no_cancel")
	private String invoiceNoCancel;

	@Column(name = "transaction_no_cancel")
	private String transactionNoCancel;

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

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRentalAreaCode() {
		return rentalAreaCode;
	}

	public void setRentalAreaCode(String rentalAreaCode) {
		this.rentalAreaCode = rentalAreaCode;
	}

	public String getRentalAreaName() {
		return rentalAreaName;
	}

	public void setRentalAreaName(String rentalAreaName) {
		this.rentalAreaName = rentalAreaName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public BigDecimal getAmountLg() {
		return amountLg;
	}

	public void setAmountLg(BigDecimal amountLg) {
		this.amountLg = amountLg;
	}

	public BigDecimal getAmountMonth() {
		return amountMonth;
	}

	public void setAmountMonth(BigDecimal amountMonth) {
		this.amountMonth = amountMonth;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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

	public String getInvoiceNoCancel() {
		return invoiceNoCancel;
	}

	public void setInvoiceNoCancel(String invoiceNoCancel) {
		this.invoiceNoCancel = invoiceNoCancel;
	}

	public String getTransactionNoCancel() {
		return transactionNoCancel;
	}

	public void setTransactionNoCancel(String transactionNoCancel) {
		this.transactionNoCancel = transactionNoCancel;
	}

}