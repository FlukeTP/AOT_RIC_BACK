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
@Table(name = "ric_it_network_service_list")
public class RicItNetworkServiceList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2723863941611246557L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_network_service_id")
	private Long itNetworkServiceId;

	@Column(name = "network_create_invoice_id")
	private Long networkCreateInvoiceId;

	// @Column(name = "months")
	// private String months;
	//
	// @Column(name = "years")
	// private String years;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "it_location")
	private String itLocation;

	@Column(name = "rental_object")
	private String rentalObject;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	// @Column(name = "start_date")
	// private Date startDate;
	//
	// @Column(name = "end_date")
	// private Date endDate;

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

	@Column(name = "period_month")
	private String periodMonth;

	public Long getItNetworkServiceId() {
		return itNetworkServiceId;
	}

	public void setItNetworkServiceId(Long itNetworkServiceId) {
		this.itNetworkServiceId = itNetworkServiceId;
	}

	public Long getNetworkCreateInvoiceId() {
		return networkCreateInvoiceId;
	}

	public void setNetworkCreateInvoiceId(Long networkCreateInvoiceId) {
		this.networkCreateInvoiceId = networkCreateInvoiceId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItLocation() {
		return itLocation;
	}

	public void setItLocation(String itLocation) {
		this.itLocation = itLocation;
	}

	public String getRentalObject() {
		return rentalObject;
	}

	public void setRentalObject(String rentalObject) {
		this.rentalObject = rentalObject;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

}