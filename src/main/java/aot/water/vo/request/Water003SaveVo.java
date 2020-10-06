package aot.water.vo.request;

import java.math.BigDecimal;
import java.util.List;

public class Water003SaveVo {

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
	private String waterType1;
	private String waterType2;
	private String waterType3;

	private String adhocType;
	private String adhocUnit;
	private String personUnit;
	private String meterType;
	private BigDecimal defaultMeterNo;
	private String meterSerialNo;
	private String meterName;

	private String installPosition;
	private String installPositionService;
	private String rentalAreaCode;
	private String rentalAreaName;

	private String paymentType;

	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNo;

	private String remark;

	private String meterSize;
	private String insuranceRates;
	private String vatInsurance;
	private String totalInsuranceChargeRates;
	private String installRates;
	private String vatInstall;
	private String totalInstallChargeRates;
	private String totalChargeRates;

	private BigDecimal sumChargeRatesOther;
	private BigDecimal sumVatChargeRatesOther;
	private BigDecimal totalChargeRateOther;
	private BigDecimal sumChargeRate;

	private List<Water003DetailSaveVo> serviceCharge;

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

	public String getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(String bankExpNo) {
		this.bankExpNo = bankExpNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<Water003DetailSaveVo> getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(List<Water003DetailSaveVo> serviceCharge) {
		this.serviceCharge = serviceCharge;
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
