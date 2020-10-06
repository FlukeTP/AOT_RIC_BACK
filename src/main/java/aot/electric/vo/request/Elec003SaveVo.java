package aot.electric.vo.request;

import java.math.BigDecimal;
import java.util.List;

public class Elec003SaveVo {
	private String idCard;
	private String customerType;
	private String customerCode;
	private String customerName;
	private String customerBranch;

	private String requestStartDate;
	private String requestEndDate;
	private String contractNo;

	private String addressDocument;
	private String requestType;
	private String applyType;
	private String voltageType;
	private String electricRateType;
	private String electricVoltageType;
	private BigDecimal defaultMeterNo;
	private String meterSerialNo;
	private String meterType;
	private String adhocType;
	private String adhocUnit;
	private String adhocChargeRate;
	private String installPosition;
	private String installPositionService;
	private String installServiceArea;
	private String rentalAreaCode;
	private String rentalAreaName;
	private String paymentType;
	private String remark;
	private String meterName;

	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNo;

	private BigDecimal sumChargeRates;
	private BigDecimal sumVatChargeRates;
	private BigDecimal totalChargeRate;

	private List<Elec003DetailSaveVo> serviceCharge;

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public String getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
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

	public String getInstallServiceArea() {
		return installServiceArea;
	}

	public void setInstallServiceArea(String installServiceArea) {
		this.installServiceArea = installServiceArea;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
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

	public String getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(String bankExpNo) {
		this.bankExpNo = bankExpNo;
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

	public List<Elec003DetailSaveVo> getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(List<Elec003DetailSaveVo> serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

}
