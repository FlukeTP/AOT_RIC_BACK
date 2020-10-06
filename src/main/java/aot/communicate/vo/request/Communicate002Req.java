package aot.communicate.vo.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import aot.communicate.model.RicCommunicateReqDtl;

public class Communicate002Req {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private BigDecimal phoneAmount;
	private String contractNo;
	private String mobileSerialNo;
	private BigDecimal chargeRates;
	private String insuranceRates;
	private BigDecimal totalChargeRates;
	private String remark;
	private String airport;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String customerBranch;
	private Date requestDate;
	private String requestDateStr;
	private Date cancelDate;
	private String cancelDateStr;
	private String flagSyncInfo;
	private List<RicCommunicateReqDtl> details;
	private String isDeleted;

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

	public BigDecimal getPhoneAmount() {
		return phoneAmount;
	}

	public void setPhoneAmount(BigDecimal phoneAmount) {
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

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public String getInsuranceRates() {
		return insuranceRates;
	}

	public void setInsuranceRates(String insuranceRates) {
		this.insuranceRates = insuranceRates;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
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

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelDateStr() {
		return cancelDateStr;
	}

	public void setCancelDateStr(String cancelDateStr) {
		this.cancelDateStr = cancelDateStr;
	}

	public String getFlagSyncInfo() {
		return flagSyncInfo;
	}

	public void setFlagSyncInfo(String flagSyncInfo) {
		this.flagSyncInfo = flagSyncInfo;
	}

	public List<RicCommunicateReqDtl> getDetails() {
		return details;
	}

	public void setDetails(List<RicCommunicateReqDtl> details) {
		this.details = details;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}