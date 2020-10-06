package aot.cndn.model;

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
@Table(name = "ric_cn_dn")
public class RicCnDn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -211112365786332075L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cn_dn_id")
	private Long cnDnId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "old_invoice_no")
	private String oldInvoiceNo;
	
	@Column(name = "old_receipt_no")
	private String oldReceiptNo;

	@Column(name = "doc_type")
	private String docType;

	@Column(name = "sap_type")
	private String sapType;
	
	@Column(name = "cn_dn")
	private String cnDn;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "gl_account")
	private String glAccount;
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;
 
 	@Column(name = "transaction_no")
	private String transactionNo;
 
 	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "sap_status")
 	private String sapStatus;

 	@Column(name = "sap_error")
 	private String sapError;

 	@Column(name = "sap_json_req")
 	private String sapJsonReq;

 	@Column(name = "sap_json_res")
 	private String sapJsonRes;

	@Column(name = "created_date")
	private Date createDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete;
	
	@Column(name = "request_type")
	private String requestType;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "old_total_amount")
	private BigDecimal oldTotalAmount;
	
	@Column(name = "old_transaction_no")
	private String oldTransactionNo;

	public Long getCnDnId() {
		return cnDnId;
	}

	public void setCnDnId(Long cnDnId) {
		this.cnDnId = cnDnId;
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

	public String getOldInvoiceNo() {
		return oldInvoiceNo;
	}

	public void setOldInvoiceNo(String oldInvoiceNo) {
		this.oldInvoiceNo = oldInvoiceNo;
	}

	public String getOldReceiptNo() {
		return oldReceiptNo;
	}

	public void setOldReceiptNo(String oldReceiptNo) {
		this.oldReceiptNo = oldReceiptNo;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSapType() {
		return sapType;
	}

	public void setSapType(String sapType) {
		this.sapType = sapType;
	}

	public String getCnDn() {
		return cnDn;
	}

	public void setCnDn(String cnDn) {
		this.cnDn = cnDn;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
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

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getSapJsonReq() {
		return sapJsonReq;
	}

	public void setSapJsonReq(String sapJsonReq) {
		this.sapJsonReq = sapJsonReq;
	}

	public String getSapJsonRes() {
		return sapJsonRes;
	}

	public void setSapJsonRes(String sapJsonRes) {
		this.sapJsonRes = sapJsonRes;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getOldTotalAmount() {
		return oldTotalAmount;
	}

	public void setOldTotalAmount(BigDecimal oldTotalAmount) {
		this.oldTotalAmount = oldTotalAmount;
	}

	public String getOldTransactionNo() {
		return oldTransactionNo;
	}

	public void setOldTransactionNo(String oldTransactionNo) {
		this.oldTransactionNo = oldTransactionNo;
	}
	
}
