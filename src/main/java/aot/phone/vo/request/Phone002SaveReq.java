package aot.phone.vo.request;

import java.util.List;

public class Phone002SaveReq {
	private String entrepreneurCode;
	private String entrepreneurName;
	private String branchCustomer;
	private String contractNo;
	private String phoneNo;
	private String description;
	private String paymentType;
	private String requestStartDate;
	private String requestEndDate;
	private String remark;
	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNo;
	private String requestStatus;
	private String rentalAreaCode;
	private String rentalAreaName;
	private List<Phone002DetailReq> serviceCharge;

	public String getEntrepreneurCode() {
		return entrepreneurCode;
	}

	public void setEntrepreneurCode(String entrepreneurCode) {
		this.entrepreneurCode = entrepreneurCode;
	}

	public String getEntrepreneurName() {
		return entrepreneurName;
	}

	public void setEntrepreneurName(String entrepreneurName) {
		this.entrepreneurName = entrepreneurName;
	}

	public String getBranchCustomer() {
		return branchCustomer;
	}

	public void setBranchCustomer(String branchCustomer) {
		this.branchCustomer = branchCustomer;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Phone002DetailReq> getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(List<Phone002DetailReq> serviceCharge) {
		this.serviceCharge = serviceCharge;
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

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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

}
