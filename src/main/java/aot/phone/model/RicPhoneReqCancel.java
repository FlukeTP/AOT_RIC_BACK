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
@Table(name = "ric_phone_req_cancel")
public class RicPhoneReqCancel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1283659982214487588L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phone_cancel_id")
	private Long phoneCancelId;

	@Column(name = "phone_req_id")
	private Long phoneReqId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "date_cancel")
	private Date dateCancel;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;
 	
 	@Column(name = "invoice_no_reqcash")
	private String invoiceNoReqcash;
	
	@Column(name = "receipt_no_reqcash")
	private String receiptNoReqcash;
	
	@Column(name = "invoice_no_reqlg")
	private String invoiceNoReqlg;
	
	@Column(name = "receipt_no_reqlg")
	private String receiptNoReqlg;
 
 	@Column(name = "transaction_no_lg")
	private String transactionNoLg;
 
 	@Column(name = "invoice_no_lg")
	private String invoiceNoLg;
	
	@Column(name = "sap_status_lg")
 	private String sapStatusLg;

 	@Column(name = "sap_error_desc_lg")
 	private String sapErrorDescLg;

 	@Column(name = "sap_json_req_lg")
 	private String sapJsonReqLg;

 	@Column(name = "sap_json_res_lg")
 	private String sapJsonResLg;

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

	public Long getPhoneCancelId() {
		return phoneCancelId;
	}

	public void setPhoneCancelId(Long phoneCancelId) {
		this.phoneCancelId = phoneCancelId;
	}

	public Long getPhoneReqId() {
		return phoneReqId;
	}

	public void setPhoneReqId(Long phoneReqId) {
		this.phoneReqId = phoneReqId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getDateCancel() {
		return dateCancel;
	}

	public void setDateCancel(Date dateCancel) {
		this.dateCancel = dateCancel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getInvoiceNoReqcash() {
		return invoiceNoReqcash;
	}

	public void setInvoiceNoReqcash(String invoiceNoReqcash) {
		this.invoiceNoReqcash = invoiceNoReqcash;
	}

	public String getReceiptNoReqcash() {
		return receiptNoReqcash;
	}

	public void setReceiptNoReqcash(String receiptNoReqcash) {
		this.receiptNoReqcash = receiptNoReqcash;
	}

	public String getInvoiceNoReqlg() {
		return invoiceNoReqlg;
	}

	public void setInvoiceNoReqlg(String invoiceNoReqlg) {
		this.invoiceNoReqlg = invoiceNoReqlg;
	}

	public String getReceiptNoReqlg() {
		return receiptNoReqlg;
	}

	public void setReceiptNoReqlg(String receiptNoReqlg) {
		this.receiptNoReqlg = receiptNoReqlg;
	}

	public String getTransactionNoLg() {
		return transactionNoLg;
	}

	public void setTransactionNoLg(String transactionNoLg) {
		this.transactionNoLg = transactionNoLg;
	}

	public String getInvoiceNoLg() {
		return invoiceNoLg;
	}

	public void setInvoiceNoLg(String invoiceNoLg) {
		this.invoiceNoLg = invoiceNoLg;
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
	
}
