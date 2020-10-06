package aot.heavyeqp.model;

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
@Table(name = "ric_heavy_equipment_revenue")
public class RicHeavyEquipmentRevenue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781102911128085322L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "heavy_equipment_revenue_id")
	private Long heavyEquipmentRevenueId;
	@Column(name = "entreprenuer_service_code")
	private String entreprenuerServiceCode;
	@Column(name = "entreprenuer_service_name")
	private String entreprenuerServiceName;
	@Column(name = "entreprenuer_service_no")
	private String entreprenuerServiceNo;
	@Column(name = "customer_code")
	private String customerCode;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_branch")
	private String customerBranch;
	@Column(name = "contract_no")
	private String contractNo;
	@Column(name = "equipment_type")
	private String equipmentType;
	@Column(name = "license_plate")
	private String licensePlate;
	@Column(name = "number_license_plate")
	private String numberLicensePlate;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "period_time")
	private String periodTime;
	@Column(name = "all_fees")
	private BigDecimal allFees;
	@Column(name = "vat")
	private BigDecimal vat;
	@Column(name = "driver_rates")
	private BigDecimal driverRates;
	@Column(name = "total_money")
	private BigDecimal totalMoney;
	@Column(name = "payment_type")
	private String paymentType;
	@Column(name = "responsible_person")
	private String responsiblePerson;
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
	@Column(name = "transaction_no")
	private String transactionNo;
	@Column(name = "equipment_code")
	private String equipmentCode;
	@Column(name = "invoice_no")
	private String invoiceNo;

	public Long getHeavyEquipmentRevenueId() {
		return heavyEquipmentRevenueId;
	}

	public void setHeavyEquipmentRevenueId(Long heavyEquipmentRevenueId) {
		this.heavyEquipmentRevenueId = heavyEquipmentRevenueId;
	}

	public String getEntreprenuerServiceCode() {
		return entreprenuerServiceCode;
	}

	public void setEntreprenuerServiceCode(String entreprenuerServiceCode) {
		this.entreprenuerServiceCode = entreprenuerServiceCode;
	}

	public String getEntreprenuerServiceName() {
		return entreprenuerServiceName;
	}

	public void setEntreprenuerServiceName(String entreprenuerServiceName) {
		this.entreprenuerServiceName = entreprenuerServiceName;
	}

	public String getEntreprenuerServiceNo() {
		return entreprenuerServiceNo;
	}

	public void setEntreprenuerServiceNo(String entreprenuerServiceNo) {
		this.entreprenuerServiceNo = entreprenuerServiceNo;
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

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getNumberLicensePlate() {
		return numberLicensePlate;
	}

	public void setNumberLicensePlate(String numberLicensePlate) {
		this.numberLicensePlate = numberLicensePlate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(String periodTime) {
		this.periodTime = periodTime;
	}

	public BigDecimal getAllFees() {
		return allFees;
	}

	public void setAllFees(BigDecimal allFees) {
		this.allFees = allFees;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getDriverRates() {
		return driverRates;
	}

	public void setDriverRates(BigDecimal driverRates) {
		this.driverRates = driverRates;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
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

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
