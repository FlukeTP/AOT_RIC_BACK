package aot.phone.model;

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
@Table(name = "ric_phone_info")
public class RicPhoneInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9159917749427627259L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "address_code")
	private String addressCode;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "maintenance_charge")
	private BigDecimal maintenanceCharge;

	@Column(name = "service_equipment_charge")
	private BigDecimal serviceEquipmentCharge;

	@Column(name = "internal_line_charge")
	private BigDecimal internalLineCharge;

	@Column(name = "outter_line_charge")
	private BigDecimal outterLineCharge;

	@Column(name = "phone_type")
	private String phoneType;

	@Column(name = "total_charge")
	private BigDecimal totalCharge;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_charge_all")
	private BigDecimal totalChargeAll;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "airport")
	private String airport;

	@Column(name = "period_month")
	private String periodMonth;

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

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "loc_amt")
	private BigDecimal locAmt;
	@Column(name = "loc_svc")
	private BigDecimal locSvc;
	@Column(name = "lng_amt")
	private BigDecimal lngAmt;
	@Column(name = "lng_svc")
	private BigDecimal lngSvc;
	@Column(name = "ovs_amt")
	private BigDecimal ovsAmt;
	@Column(name = "ovs_svc")
	private BigDecimal ovsSvc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public BigDecimal getMaintenanceCharge() {
		return maintenanceCharge;
	}

	public void setMaintenanceCharge(BigDecimal maintenanceCharge) {
		this.maintenanceCharge = maintenanceCharge;
	}

	public BigDecimal getServiceEquipmentCharge() {
		return serviceEquipmentCharge;
	}

	public void setServiceEquipmentCharge(BigDecimal serviceEquipmentCharge) {
		this.serviceEquipmentCharge = serviceEquipmentCharge;
	}

	public BigDecimal getInternalLineCharge() {
		return internalLineCharge;
	}

	public void setInternalLineCharge(BigDecimal internalLineCharge) {
		this.internalLineCharge = internalLineCharge;
	}

	public BigDecimal getOutterLineCharge() {
		return outterLineCharge;
	}

	public void setOutterLineCharge(BigDecimal outterLineCharge) {
		this.outterLineCharge = outterLineCharge;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public BigDecimal getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(BigDecimal totalCharge) {
		this.totalCharge = totalCharge;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTotalChargeAll() {
		return totalChargeAll;
	}

	public void setTotalChargeAll(BigDecimal totalChargeAll) {
		this.totalChargeAll = totalChargeAll;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
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

	public BigDecimal getLocAmt() {
		return locAmt;
	}

	public void setLocAmt(BigDecimal locAmt) {
		this.locAmt = locAmt;
	}

	public BigDecimal getLocSvc() {
		return locSvc;
	}

	public void setLocSvc(BigDecimal locSvc) {
		this.locSvc = locSvc;
	}

	public BigDecimal getLngAmt() {
		return lngAmt;
	}

	public void setLngAmt(BigDecimal lngAmt) {
		this.lngAmt = lngAmt;
	}

	public BigDecimal getLngSvc() {
		return lngSvc;
	}

	public void setLngSvc(BigDecimal lngSvc) {
		this.lngSvc = lngSvc;
	}

	public BigDecimal getOvsAmt() {
		return ovsAmt;
	}

	public void setOvsAmt(BigDecimal ovsAmt) {
		this.ovsAmt = ovsAmt;
	}

	public BigDecimal getOvsSvc() {
		return ovsSvc;
	}

	public void setOvsSvc(BigDecimal ovsSvc) {
		this.ovsSvc = ovsSvc;
	}

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

}