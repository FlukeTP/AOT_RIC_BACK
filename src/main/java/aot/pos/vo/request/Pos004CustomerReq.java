package aot.pos.vo.request;

public class Pos004CustomerReq {
	private String posCustomerId;

	private String customerCode;

	private String customerName;

	private String customerBranch;

	private String contractNo;

	private String rentalArea;

	private String remark;

	public String getPosCustomerId() {
		return posCustomerId;
	}

	public void setPosCustomerId(String posCustomerId) {
		this.posCustomerId = posCustomerId;
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

	public String getRentalArea() {
		return rentalArea;
	}

	public void setRentalArea(String rentalArea) {
		this.rentalArea = rentalArea;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
