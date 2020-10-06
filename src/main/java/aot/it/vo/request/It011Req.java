package aot.it.vo.request;

import java.math.BigDecimal;
import java.util.Date;

public class It011Req {

	private String itNetworkServiceId;
	private String monthly;
	private String customerName;
	private String customerCode;
	private String customerBranch;
	private String contractNo;
	private String itLocation;
	private String rentalObject;
	private BigDecimal totalAmount;
	private Date startDate;
	private Date endDate;
	private String remark;
	private String periodMonth;

	public String getItNetworkServiceId() {
		return itNetworkServiceId;
	}

	public void setItNetworkServiceId(String itNetworkServiceId) {
		this.itNetworkServiceId = itNetworkServiceId;
	}

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getItLocation() {
		return itLocation;
	}

	public void setItLocation(String itLocation) {
		this.itLocation = itLocation;
	}

	public String getRentalObject() {
		return rentalObject;
	}

	public void setRentalObject(String rentalObject) {
		this.rentalObject = rentalObject;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}
}
