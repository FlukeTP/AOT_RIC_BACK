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
@Table(name = "ric_communicate_info")
public class RicCommunicateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536612410269874104L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "period_month")
	private String periodMonth;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "phone_amount")
	private BigDecimal phoneAmount;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_all")
	private BigDecimal totalAll;

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error")
	private String sapError;

	@Column(name = "sap_json_res")
	private String sapJsonRes;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "invoice_no")
	private String invoiceNo;

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

	@Column(name = "transaction_no_req")
	private String transactionNoReq;

	@Column(name = "ro_number")
	private String roNumber;

	@Column(name = "ro_name")
	private String roName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getTransactionNoReq() {
		return transactionNoReq;
	}

	public void setTransactionNoReq(String transactionNoReq) {
		this.transactionNoReq = transactionNoReq;
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

}