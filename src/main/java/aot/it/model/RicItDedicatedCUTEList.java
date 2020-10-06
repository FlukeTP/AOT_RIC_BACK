package aot.it.model;

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
@Table(name = "ric_it_dedicated_cute_list")
public class RicItDedicatedCUTEList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821786299538199718L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "dedicated_invoice_id")
	private Long dedicatedInvoiceId;

	@Column(name = "entreprenuer_Name")
	private String entreprenuerName;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_branch")
	private String entreprenuerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "location")
	private String location;

	@Column(name = "contract_data")
	private String contractData;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error")
	private String sapError;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "sap_json_res")
	private String sapJsonRes;

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

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "period_month")
	private String periodMonth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDedicatedInvoiceId() {
		return dedicatedInvoiceId;
	}

	public void setDedicatedInvoiceId(Long dedicatedInvoiceId) {
		this.dedicatedInvoiceId = dedicatedInvoiceId;
	}

	public String getEntreprenuerName() {
		return entreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}

	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}

	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}

	public String getEntreprenuerBranch() {
		return entreprenuerBranch;
	}

	public void setEntreprenuerBranch(String entreprenuerBranch) {
		this.entreprenuerBranch = entreprenuerBranch;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContractData() {
		return contractData;
	}

	public void setContractData(String contractData) {
		this.contractData = contractData;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

}