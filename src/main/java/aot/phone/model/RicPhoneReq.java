package aot.phone.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_phone_req")
public class RicPhoneReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7590916622150350951L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phone_req_id")
	private Long phoneReqId;

	@Column(name = "entrepreneur_code")
	private String entrepreneurCode;

	@Column(name = "entrepreneur_name")
	private String entrepreneurName;

	@Column(name = "branch_customer")
	private String branchCustomer;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "description")
	private String description;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "request_start_date")
	private Date requestStartDate;

	@Column(name = "request_end_date")
	private Date requestEndDate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete;

	@Column(name = "flag_info")
	private String flagInfo;

	@Column(name = "airport")
	private String airport;

	@Column(name = "invoice_no_cash")
	private String invoiceNoCash;
	@Column(name = "receipt_no_cash")
	private String receiptNoCash;
	@Column(name = "sap_status_cash")
	private String sapStatusCash;
	@Column(name = "sap_error_desc_cash")
	private String sapErrorDescCash;
	@Column(name = "sap_json_req_cash")
	private String sapJsonReqCash;
	@Column(name = "sap_json_res_cash")
	private String sapJsonResCash;
	@Column(name = "transaction_no_cash")
	private String transactionNoCash;

	@Column(name = "invoice_no_lg")
	private String invoiceNoLg;
	@Column(name = "receipt_no_lg")
	private String receiptNoLg;
	@Column(name = "sap_status_lg")
	private String sapStatusLg;
	@Column(name = "sap_error_desc_lg")
	private String sapErrorDescLg;
	@Column(name = "sap_json_req_lg")
	private String sapJsonReqLg;
	@Column(name = "sap_json_res_lg")
	private String sapJsonResLg;
	@Column(name = "transaction_no_lg")
	private String transactionNoLg;
	
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
	@Column(name = "request_status")
	private String requestStatus;

	@Column(name = "rental_area_code")
	private String rentalAreaCode;
	@Column(name = "rental_area_name")
	private String rentalAreaName;

	public Long getPhoneReqId() {
		return phoneReqId;
	}

	public void setPhoneReqId(Long phoneReqId) {
		this.phoneReqId = phoneReqId;
	}

	public String getEntrepreneurCode() {
		return entrepreneurCode;
	}

	public void setEntrepreneurCode(String entrepreneurCode) {
		this.entrepreneurCode = entrepreneurCode;
	}

	public String getEntrepreneurName() {
		return entrepreneurName;
	}

	public void setEntrepreneurName(String entrepreneurName) {
		this.entrepreneurName = entrepreneurName;
	}

	public String getBranchCustomer() {
		return branchCustomer;
	}

	public void setBranchCustomer(String branchCustomer) {
		this.branchCustomer = branchCustomer;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public Date getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getFlagInfo() {
		return flagInfo;
	}

	public void setFlagInfo(String flagInfo) {
		this.flagInfo = flagInfo;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getInvoiceNoCash() {
		return invoiceNoCash;
	}

	public void setInvoiceNoCash(String invoiceNoCash) {
		this.invoiceNoCash = invoiceNoCash;
	}

	public String getReceiptNoCash() {
		return receiptNoCash;
	}

	public void setReceiptNoCash(String receiptNoCash) {
		this.receiptNoCash = receiptNoCash;
	}

	public String getSapStatusCash() {
		return sapStatusCash;
	}

	public void setSapStatusCash(String sapStatusCash) {
		this.sapStatusCash = sapStatusCash;
	}

	public String getSapErrorDescCash() {
		return sapErrorDescCash;
	}

	public void setSapErrorDescCash(String sapErrorDescCash) {
		this.sapErrorDescCash = sapErrorDescCash;
	}

	public String getSapJsonReqCash() {
		return sapJsonReqCash;
	}

	public void setSapJsonReqCash(String sapJsonReqCash) {
		this.sapJsonReqCash = sapJsonReqCash;
	}

	public String getSapJsonResCash() {
		return sapJsonResCash;
	}

	public void setSapJsonResCash(String sapJsonResCash) {
		this.sapJsonResCash = sapJsonResCash;
	}

	public String getTransactionNoCash() {
		return transactionNoCash;
	}

	public void setTransactionNoCash(String transactionNoCash) {
		this.transactionNoCash = transactionNoCash;
	}

	public String getInvoiceNoLg() {
		return invoiceNoLg;
	}

	public void setInvoiceNoLg(String invoiceNoLg) {
		this.invoiceNoLg = invoiceNoLg;
	}

	public String getReceiptNoLg() {
		return receiptNoLg;
	}

	public void setReceiptNoLg(String receiptNoLg) {
		this.receiptNoLg = receiptNoLg;
	}

	public String getSapStatusLg() {
		return sapStatusLg;
	}

	public void setSapStatusLg(String sapStatusLg) {
		this.sapStatusLg = sapStatusLg;
	}

	public String getSapErrorDescLg() {
		return sapErrorDescLg;
	}

	public void setSapErrorDescLg(String sapErrorDescLg) {
		this.sapErrorDescLg = sapErrorDescLg;
	}

	public String getSapJsonReqLg() {
		return sapJsonReqLg;
	}

	public void setSapJsonReqLg(String sapJsonReqLg) {
		this.sapJsonReqLg = sapJsonReqLg;
	}

	public String getSapJsonResLg() {
		return sapJsonResLg;
	}

	public void setSapJsonResLg(String sapJsonResLg) {
		this.sapJsonResLg = sapJsonResLg;
	}

	public String getTransactionNoLg() {
		return transactionNoLg;
	}

	public void setTransactionNoLg(String transactionNoLg) {
		this.transactionNoLg = transactionNoLg;
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

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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

}
