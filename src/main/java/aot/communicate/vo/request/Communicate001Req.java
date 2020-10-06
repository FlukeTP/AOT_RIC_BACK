package aot.communicate.vo.request;

import java.util.Date;
import java.util.List;

public class Communicate001Req {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String phoneAmount;
	private String contractNo;
	private String mobileSerialNo;
	private String chargeRates;
	private String insuranceRates;
	private String totalChargeRates;
	private String remark;
	private String totalChargeAll;
	private String airport;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String customerBranch;
	private Date requestDate;
	private String requestDateStr;
	private String endDateStr;
	private String paymentType;
	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNoStr;
	private String serviceType;
	private String roNumber;
	private String roName;
	private String flagCancel;

	private List<Communicate001DtlReq> mobileSerialNoList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPhoneAmount() {
		return phoneAmount;
	}

	public void setPhoneAmount(String phoneAmount) {
		this.phoneAmount = phoneAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getMobileSerialNo() {
		return mobileSerialNo;
	}

	public void setMobileSerialNo(String mobileSerialNo) {
		this.mobileSerialNo = mobileSerialNo;
	}

	public String getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(String chargeRates) {
		this.chargeRates = chargeRates;
	}

	public String getInsuranceRates() {
		return insuranceRates;
	}

	public void setInsuranceRates(String insuranceRates) {
		this.insuranceRates = insuranceRates;
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

	public String getTotalChargeAll() {
		return totalChargeAll;
	}

	public void setTotalChargeAll(String totalChargeAll) {
		this.totalChargeAll = totalChargeAll;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
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

	public String getCustomerBranch() {
		return customerBranch;
	}

	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestDateStr() {
		return requestDateStr;
	}

	public void setRequestDateStr(String requestDateStr) {
		this.requestDateStr = requestDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
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

	public String getBankExpNoStr() {
		return bankExpNoStr;
	}

	public void setBankExpNoStr(String bankExpNoStr) {
		this.bankExpNoStr = bankExpNoStr;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRoNumber() {
		return roNumber;
	}

	public void setRoNumber(String roNumber) {
		this.roNumber = roNumber;
	}

	public String getRoName() {
		return roName;
	}

	public void setRoName(String roName) {
		this.roName = roName;
	}

	public List<Communicate001DtlReq> getMobileSerialNoList() {
		return mobileSerialNoList;
	}

	public void setMobileSerialNoList(List<Communicate001DtlReq> mobileSerialNoList) {
		this.mobileSerialNoList = mobileSerialNoList;
	}

	public String getFlagCancel() {
		return flagCancel;
	}

	public void setFlagCancel(String flagCancel) {
		this.flagCancel = flagCancel;
	}

}