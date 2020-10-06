package aot.garbagedis.vo.request;

import java.util.List;

public class Garbagedis002HdrReq {

	private String garInfoId;
	private String garReqId;
	private String customerCode;
	private String customerName;
	private String customerBranch;
	private String contractNo;
	private String rentalObject;
	private String serviceType;
	private String trashLocation;
	private String startDate;
	private String endDate;
	private String totalChargeRates;
	private String address;
	private String remark;
	private List<Garbagedis002DtlReq> chargeRateDtl;
	private String totalTrashWeight;
	private String totalMoneyAmount;
	
	public String getGarInfoId() {
		return garInfoId;
	}
	public void setGarInfoId(String garInfoId) {
		this.garInfoId = garInfoId;
	}
	public String getGarReqId() {
		return garReqId;
	}
	public void setGarReqId(String garReqId) {
		this.garReqId = garReqId;
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
	public String getRentalObject() {
		return rentalObject;
	}
	public void setRentalObject(String rentalObject) {
		this.rentalObject = rentalObject;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTrashLocation() {
		return trashLocation;
	}
	public void setTrashLocation(String trashLocation) {
		this.trashLocation = trashLocation;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTotalChargeRates() {
		return totalChargeRates;
	}
	public void setTotalChargeRates(String totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Garbagedis002DtlReq> getChargeRateDtl() {
		return chargeRateDtl;
	}
	public void setChargeRateDtl(List<Garbagedis002DtlReq> chargeRateDtl) {
		this.chargeRateDtl = chargeRateDtl;
	}
	public String getTotalTrashWeight() {
		return totalTrashWeight;
	}
	public void setTotalTrashWeight(String totalTrashWeight) {
		this.totalTrashWeight = totalTrashWeight;
	}
	public String getTotalMoneyAmount() {
		return totalMoneyAmount;
	}
	public void setTotalMoneyAmount(String totalMoneyAmount) {
		this.totalMoneyAmount = totalMoneyAmount;
	}
}
