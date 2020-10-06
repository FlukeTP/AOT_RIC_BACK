package aot.electric.model;

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
@Table(name = "ric_electric_info")
public class RicElectricInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4542801654365156009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "electric_info_id")
	private Long electricInfoId;

	@Column(name = "id_req")
	private Long idReq;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String EntreprenuerName;

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

	@Column(name = "energy_value")
	private BigDecimal energyValue;

	@Column(name = "service_value")
	private BigDecimal serviceValue;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "period_month")
	private String periodMonth;

	@Column(name = "voltage_type")
	private String voltageType;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "ro_code")
	private String roCode;

	@Column(name = "ro_name")
	private String roName;

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

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "request_type")
	private String requestType;

	public Long getElectricInfoId() {
		return electricInfoId;
	}

	public void setElectricInfoId(Long electricInfoId) {
		this.electricInfoId = electricInfoId;
	}

	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}

	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}

	public String getEntreprenuerName() {
		return EntreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		EntreprenuerName = entreprenuerName;
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

	public BigDecimal getEnergyValue() {
		return energyValue;
	}

	public void setEnergyValue(BigDecimal energyValue) {
		this.energyValue = energyValue;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Long getIdReq() {
		return idReq;
	}

	public void setIdReq(Long idReq) {
		this.idReq = idReq;
	}

}