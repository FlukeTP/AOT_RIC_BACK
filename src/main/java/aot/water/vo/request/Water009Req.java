package aot.water.vo.request;

import java.util.List;

public class Water009Req {
	private Long wasteHeaderId;
	private Long wasteDetailId;
	private String customerCode;
	private String customerName;
	private String customerBranch;
	private String contractNo;
	private String rentalAreaCode;
	private String rentalAreaName;
	private String paymentType;
	private String remark;

	private List<Water009DtlReq> water009DtlReq;

	public Long getWasteHeaderId() {
		return wasteHeaderId;
	}

	public void setWasteHeaderId(Long wasteHeaderId) {
		this.wasteHeaderId = wasteHeaderId;
	}

	public Long getWasteDetailId() {
		return wasteDetailId;
	}

	public void setWasteDetailId(Long wasteDetailId) {
		this.wasteDetailId = wasteDetailId;
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

	public List<Water009DtlReq> getWater009DtlReq() {
		return water009DtlReq;
	}

	public void setWater009DtlReq(List<Water009DtlReq> water009DtlReq) {
		this.water009DtlReq = water009DtlReq;
	}

}
