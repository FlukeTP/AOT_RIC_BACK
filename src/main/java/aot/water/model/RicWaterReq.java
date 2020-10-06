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
@Table(name = "ric_water_req")
public class RicWaterReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 754738802036467148L;

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

	@Column(name = "water_type1")
	private String waterType1;

	@Column(name = "water_type2")
	private String waterType2;

	@Column(name = "water_type3")
	private String waterType3;

	@Column(name = "adhoc_type")
	private String adhocType;
	@Column(name = "adhoc_unit")
	private String adhocUnit;
	@Column(name = "person_unit")
	private String personUnit;

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

	@Column(name = "meter_size")
	private String meterSize;
	@Column(name = "insurance_rates")
	private String insuranceRates;
	@Column(name = "vat_insurance")
	private String vatInsurance;
	@Column(name = "total_insurance_charge_rates")
	private String totalInsuranceChargeRates;
	@Column(name = "install_rates")
	private String installRates;
	@Column(name = "vat_install")
	private String vatInstall;
	@Column(name = "total_install_charge_rates")
	private String totalInstallChargeRates;
	@Column(name = "total_charge_rates")
	private String totalChargeRates;

	@Column(name = "remark")
	private String remark;

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

	@Column(name = "sum_charge_rates_other")
	private BigDecimal sumChargeRatesOther;

	@Column(name = "sum_vat_charge_rates_other")
	private BigDecimal sumVatChargeRatesOther;

	@Column(name = "total_charge_rate_other")
	private BigDecimal totalChargeRateOther;

	@Column(name = "sum_charge_rate")
	private BigDecimal sumChargeRate;

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

	public String getPersonUnit() {
		return personUnit;
	}

	public void setPersonUnit(String personUnit) {
		this.personUnit = personUnit;
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

	public String getMeterSize() {
		return meterSize;
	}

	public void setMeterSize(String meterSize) {
		this.meterSize = meterSize;
	}

	public String getInsuranceRates() {
		return insuranceRates;
	}

	public void setInsuranceRates(String insuranceRates) {
		this.insuranceRates = insuranceRates;
	}

	public String getVatInsurance() {
		return vatInsurance;
	}

	public void setVatInsurance(String vatInsurance) {
		this.vatInsurance = vatInsurance;
	}

	public String getTotalInsuranceChargeRates() {
		return totalInsuranceChargeRates;
	}

	public void setTotalInsuranceChargeRates(String totalInsuranceChargeRates) {
		this.totalInsuranceChargeRates = totalInsuranceChargeRates;
	}

	public String getInstallRates() {
		return installRates;
	}

	public void setInstallRates(String installRates) {
		this.installRates = installRates;
	}

	public String getVatInstall() {
		return vatInstall;
	}

	public void setVatInstall(String vatInstall) {
		this.vatInstall = vatInstall;
	}

	public String getTotalInstallChargeRates() {
		return totalInstallChargeRates;
	}

	public void setTotalInstallChargeRates(String totalInstallChargeRates) {
		this.totalInstallChargeRates = totalInstallChargeRates;
	}

	public String getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(String totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public BigDecimal getSumChargeRatesOther() {
		return sumChargeRatesOther;
	}

	public void setSumChargeRatesOther(BigDecimal sumChargeRatesOther) {
		this.sumChargeRatesOther = sumChargeRatesOther;
	}

	public BigDecimal getSumVatChargeRatesOther() {
		return sumVatChargeRatesOther;
	}

	public void setSumVatChargeRatesOther(BigDecimal sumVatChargeRatesOther) {
		this.sumVatChargeRatesOther = sumVatChargeRatesOther;
	}

	public BigDecimal getTotalChargeRateOther() {
		return totalChargeRateOther;
	}

	public void setTotalChargeRateOther(BigDecimal totalChargeRateOther) {
		this.totalChargeRateOther = totalChargeRateOther;
	}

	public BigDecimal getSumChargeRate() {
		return sumChargeRate;
	}

	public void setSumChargeRate(BigDecimal sumChargeRate) {
		this.sumChargeRate = sumChargeRate;
	}

}