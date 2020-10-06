package aot.firebrigade.model;

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
@Table(name = "ric_firebrigade_manage")
public class RicFirebrigadeManage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486015791133494377L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fire_manage_id")
	private Long fireManageId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "address")
	private String address;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "person_amount")
	private Long personAmount;
	
	@Column(name = "charge_rates")
	private BigDecimal chargeRates;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;

	@Column(name = "customer_branch")
	private String customerBranch;

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

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error_desc")
	private String sapErrorDesc;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "sap_json_res")
	private String sapJsonRes;
	
	@Column(name = "transaction_no")
	private String transactionNo;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "unit")
	private String unit;
	
	public Long getFireManageId() {
		return fireManageId;
	}

	public void setFireManageId(Long fireManageId) {
		this.fireManageId = fireManageId;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getPersonAmount() {
		return personAmount;
	}

	public void setPersonAmount(Long personAmount) {
		this.personAmount = personAmount;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
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

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
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

	public String getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	public String getSapErrorDesc() {
		return sapErrorDesc;
	}

	public void setSapErrorDesc(String sapErrorDesc) {
		this.sapErrorDesc = sapErrorDesc;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
