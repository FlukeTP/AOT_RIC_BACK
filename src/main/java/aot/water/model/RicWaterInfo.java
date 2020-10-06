package aot.water.model;

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
@Table(name = "ric_water_info")
public class RicWaterInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2316406611757967611L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "water_info_id")
	private Long waterInfoId;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "meter_name")
	private String meterName;

	@Column(name = "meter_date")
	private Date meterDate;

	@Column(name = "serial_no_meter")
	private String serialNoMeter;

	@Column(name = "backward_meter_value")
	private BigDecimal backwardMeterValue;

	@Column(name = "current_meter_value")
	private String currentMeterValue;

	@Column(name = "backward_amount")
	private BigDecimal backwardAmount;

	@Column(name = "current_amount")
	private BigDecimal currentAmount;

	@Column(name = "cal_percent")
	private BigDecimal calPercent;

	@Column(name = "base_value")
	private BigDecimal baseValue;

	@Column(name = "ft_value")
	private BigDecimal ftValue;

	@Column(name = "service_value")
	private BigDecimal serviceValue;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "treatment_fee")
	private BigDecimal treatmentFee;

	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "period_month")
	private String periodMonth;

	@Column(name = "voltage_type")
	private String voltageType;

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

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "ro_code")
	private String roCode;

	@Column(name = "ro_name")
	private String roName;

	@Column(name = "water_type")
	private String waterType;

	@Column(name = "recycle_amount")
	private BigDecimal recycleAmount;

	@Column(name = "water_type1")
	private String waterType1;

	@Column(name = "water_type2")
	private String waterType2;

	@Column(name = "water_type3")
	private String waterType3;

	@Column(name = "id_req")
	private Long idReq;

	public Long getWaterInfoId() {
		return waterInfoId;
	}

	public void setWaterInfoId(Long waterInfoId) {
		this.waterInfoId = waterInfoId;
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

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public Date getMeterDate() {
		return meterDate;
	}

	public void setMeterDate(Date meterDate) {
		this.meterDate = meterDate;
	}

	public String getSerialNoMeter() {
		return serialNoMeter;
	}

	public void setSerialNoMeter(String serialNoMeter) {
		this.serialNoMeter = serialNoMeter;
	}

	public BigDecimal getBackwardMeterValue() {
		return backwardMeterValue;
	}

	public void setBackwardMeterValue(BigDecimal backwardMeterValue) {
		this.backwardMeterValue = backwardMeterValue;
	}

	public String getCurrentMeterValue() {
		return currentMeterValue;
	}

	public void setCurrentMeterValue(String currentMeterValue) {
		this.currentMeterValue = currentMeterValue;
	}

	public BigDecimal getBackwardAmount() {
		return backwardAmount;
	}

	public void setBackwardAmount(BigDecimal backwardAmount) {
		this.backwardAmount = backwardAmount;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public BigDecimal getCalPercent() {
		return calPercent;
	}

	public void setCalPercent(BigDecimal calPercent) {
		this.calPercent = calPercent;
	}

	public BigDecimal getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}

	public BigDecimal getFtValue() {
		return ftValue;
	}

	public void setFtValue(BigDecimal ftValue) {
		this.ftValue = ftValue;
	}

	public BigDecimal getServiceValue() {
		return serviceValue;
	}

	public void setServiceValue(BigDecimal serviceValue) {
		this.serviceValue = serviceValue;
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

	public BigDecimal getTreatmentFee() {
		return treatmentFee;
	}

	public void setTreatmentFee(BigDecimal treatmentFee) {
		this.treatmentFee = treatmentFee;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getVoltageType() {
		return voltageType;
	}

	public void setVoltageType(String voltageType) {
		this.voltageType = voltageType;
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

	public String getRoCode() {
		return roCode;
	}

	public void setRoCode(String roCode) {
		this.roCode = roCode;
	}

	public String getRoName() {
		return roName;
	}

	public void setRoName(String roName) {
		this.roName = roName;
	}

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}

	public BigDecimal getRecycleAmount() {
		return recycleAmount;
	}

	public void setRecycleAmount(BigDecimal recycleAmount) {
		this.recycleAmount = recycleAmount;
	}

	public String getWaterType1() {
		return waterType1;
	}

	public void setWaterType1(String waterType1) {
		this.waterType1 = waterType1;
	}

	public String getWaterType2() {
		return waterType2;
	}

	public void setWaterType2(String waterType2) {
		this.waterType2 = waterType2;
	}

	public String getWaterType3() {
		return waterType3;
	}

	public void setWaterType3(String waterType3) {
		this.waterType3 = waterType3;
	}

	public Long getIdReq() {
		return idReq;
	}

	public void setIdReq(Long idReq) {
		this.idReq = idReq;
	}

}