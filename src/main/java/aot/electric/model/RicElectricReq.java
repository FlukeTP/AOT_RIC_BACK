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
@Table(name = "ric_electric_req")
public class RicElectricReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7177904066771670498L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "req_id")
	private Long reqId;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "id_card")
	private String idCard;

	@Column(name = "request_start_date")
	private Date requestStartDate;

	@Column(name = "request_end_date")
	private Date requestEndDate;

	@Column(name = "request_status")
	private String requestStatus;

	@Column(name = "approve_status")
	private String approveStatus;

	@Column(name = "airport")
	private String airport;

	@Column(name = "address_document")
	private String addressDocument;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "apply_type")
	private String applyType;

	@Column(name = "voltage_type")
	private String voltageType;

	@Column(name = "electric_rate_type")
	private String electricRateType;

	@Column(name = "electric_voltage_type")
	private String electricVoltageType;

	@Column(name = "adhoc_type")
	private String adhocType;
	@Column(name = "adhoc_unit")
	private String adhocUnit;
	@Column(name = "adhoc_charge_rate")
	private String adhocChargeRate;

	@Column(name = "meter_type")
	private String meterType;

	@Column(name = "default_meter_no")
	private BigDecimal defaultMeterNo;

	@Column(name = "meter_serial_no")
	private String meterSerialNo;

	@Column(name = "meter_name")
	private String meterName;

	@Column(name = "install_position")
	private String installPosition;
	@Column(name = "install_position_service")
	private String installPositionService;
	@Column(name = "rental_area_code")
	private String rentalAreaCode;
	@Column(name = "rental_area_name")
	private String rentalAreaName;

	@Column(name = "payment_type")
	private String paymentType;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "bank_branch")
	private String bankBranch;
	@Column(name = "bank_explanation")
	private String bankExplanation;
	@Column(name = "bank_guarantee_no")
	private String bankGuaranteeNo;
	@Column(name = "bank_exp_no")
	private Date bankExpNo;

	@Column(name = "remark")
	private String remark;

	@Column(name = "sum_charge_rates")
	private BigDecimal sumChargeRates;

	@Column(name = "sum_vat_charge_rates")
	private BigDecimal sumVatChargeRates;

	@Column(name = "total_charge_rate")
	private BigDecimal totalChargeRate;

	@Column(name = "flag_info")
	private String flagInfo;

	@Column(name = "transaction_no_cash")
	private String transactionNoCash;
	@Column(name = "invoice_no_cash")
	private String invoiceNoCash;
	@Column(name = "sap_status_cash")
	private String sapStatusCash;
	@Column(name = "sap_error_desc_cash")
	private String sapErrorDescCash;
	@Column(name = "sap_json_req_cash")
	private String sapJsonReqCash;
	@Column(name = "sap_json_res_cash")
	private String sapJsonResCash;

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

	@Column(name = "transaction_no_packages")
	private String transactionNoPackages;
	@Column(name = "invoice_no_packages")
	private String invoiceNoPackages;
	@Column(name = "sap_status_packages")
	private String sapStatusPackages;
	@Column(name = "sap_error_desc_packages")
	private String sapErrorDescPackages;
	@Column(name = "sap_json_req_packages")
	private String sapJsonReqPackages;
	@Column(name = "sap_json_res_packages")
	private String sapJsonResPackages;

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

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public Date getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getAddressDocument() {
		return addressDocument;
	}

	public void setAddressDocument(String addressDocument) {
		this.addressDocument = addressDocument;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getVoltageType() {
		return voltageType;
	}

	public void setVoltageType(String voltageType) {
		this.voltageType = voltageType;
	}

	public String getElectricRateType() {
		return electricRateType;
	}

	public void setElectricRateType(String electricRateType) {
		this.electricRateType = electricRateType;
	}

	public String getElectricVoltageType() {
		return electricVoltageType;
	}

	public void setElectricVoltageType(String electricVoltageType) {
		this.electricVoltageType = electricVoltageType;
	}

	public String getAdhocType() {
		return adhocType;
	}

	public void setAdhocType(String adhocType) {
		this.adhocType = adhocType;
	}

	public String getAdhocUnit() {
		return adhocUnit;
	}

	public void setAdhocUnit(String adhocUnit) {
		this.adhocUnit = adhocUnit;
	}

	public String getAdhocChargeRate() {
		return adhocChargeRate;
	}

	public void setAdhocChargeRate(String adhocChargeRate) {
		this.adhocChargeRate = adhocChargeRate;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public BigDecimal getDefaultMeterNo() {
		return defaultMeterNo;
	}

	public void setDefaultMeterNo(BigDecimal defaultMeterNo) {
		this.defaultMeterNo = defaultMeterNo;
	}

	public String getMeterSerialNo() {
		return meterSerialNo;
	}

	public void setMeterSerialNo(String meterSerialNo) {
		this.meterSerialNo = meterSerialNo;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getInstallPosition() {
		return installPosition;
	}

	public void setInstallPosition(String installPosition) {
		this.installPosition = installPosition;
	}

	public String getInstallPositionService() {
		return installPositionService;
	}

	public void setInstallPositionService(String installPositionService) {
		this.installPositionService = installPositionService;
	}

	public String getRentalAreaCode() {
		return rentalAreaCode;
	}

	public void setRentalAreaCode(String rentalAreaCode) {
		this.rentalAreaCode = rentalAreaCode;
	}

	public String getRentalAreaName() {
		return rentalAreaName;
	}

	public void setRentalAreaName(String rentalAreaName) {
		this.rentalAreaName = rentalAreaName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankExplanation() {
		return bankExplanation;
	}

	public void setBankExplanation(String bankExplanation) {
		this.bankExplanation = bankExplanation;
	}

	public String getBankGuaranteeNo() {
		return bankGuaranteeNo;
	}

	public void setBankGuaranteeNo(String bankGuaranteeNo) {
		this.bankGuaranteeNo = bankGuaranteeNo;
	}

	public Date getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(Date bankExpNo) {
		this.bankExpNo = bankExpNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSumChargeRates() {
		return sumChargeRates;
	}

	public void setSumChargeRates(BigDecimal sumChargeRates) {
		this.sumChargeRates = sumChargeRates;
	}

	public BigDecimal getSumVatChargeRates() {
		return sumVatChargeRates;
	}

	public void setSumVatChargeRates(BigDecimal sumVatChargeRates) {
		this.sumVatChargeRates = sumVatChargeRates;
	}

	public BigDecimal getTotalChargeRate() {
		return totalChargeRate;
	}

	public void setTotalChargeRate(BigDecimal totalChargeRate) {
		this.totalChargeRate = totalChargeRate;
	}

	public String getFlagInfo() {
		return flagInfo;
	}

	public void setFlagInfo(String flagInfo) {
		this.flagInfo = flagInfo;
	}

	public String getTransactionNoCash() {
		return transactionNoCash;
	}

	public void setTransactionNoCash(String transactionNoCash) {
		this.transactionNoCash = transactionNoCash;
	}

	public String getInvoiceNoCash() {
		return invoiceNoCash;
	}

	public void setInvoiceNoCash(String invoiceNoCash) {
		this.invoiceNoCash = invoiceNoCash;
	}

	public String getSapStatusCash() {
		return sapStatusCash;
	}

	public void setSapStatusCash(String sapStatusCash) {
		this.sapStatusCash = sapStatusCash;
	}

	public String getSapErrorDescCash() {
		return sapErrorDescCash;
	}

	public void setSapErrorDescCash(String sapErrorDescCash) {
		this.sapErrorDescCash = sapErrorDescCash;
	}

	public String getSapJsonReqCash() {
		return sapJsonReqCash;
	}

	public void setSapJsonReqCash(String sapJsonReqCash) {
		this.sapJsonReqCash = sapJsonReqCash;
	}

	public String getSapJsonResCash() {
		return sapJsonResCash;
	}

	public void setSapJsonResCash(String sapJsonResCash) {
		this.sapJsonResCash = sapJsonResCash;
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

	public String getTransactionNoPackages() {
		return transactionNoPackages;
	}

	public void setTransactionNoPackages(String transactionNoPackages) {
		this.transactionNoPackages = transactionNoPackages;
	}

	public String getInvoiceNoPackages() {
		return invoiceNoPackages;
	}

	public void setInvoiceNoPackages(String invoiceNoPackages) {
		this.invoiceNoPackages = invoiceNoPackages;
	}

	public String getSapStatusPackages() {
		return sapStatusPackages;
	}

	public void setSapStatusPackages(String sapStatusPackages) {
		this.sapStatusPackages = sapStatusPackages;
	}

	public String getSapErrorDescPackages() {
		return sapErrorDescPackages;
	}

	public void setSapErrorDescPackages(String sapErrorDescPackages) {
		this.sapErrorDescPackages = sapErrorDescPackages;
	}

	public String getSapJsonReqPackages() {
		return sapJsonReqPackages;
	}

	public void setSapJsonReqPackages(String sapJsonReqPackages) {
		this.sapJsonReqPackages = sapJsonReqPackages;
	}

	public String getSapJsonResPackages() {
		return sapJsonResPackages;
	}

	public void setSapJsonResPackages(String sapJsonResPackages) {
		this.sapJsonResPackages = sapJsonResPackages;
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
