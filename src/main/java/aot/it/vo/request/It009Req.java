package aot.it.vo.request;

import java.math.BigDecimal;

public class It009Req {

	private String itPageReqId;
	private String customerCode;
	private String customerName;
	private String contractNo;
	private String staffType;
	private String publicType;
	private String staffPageNum;
	private String publicPageNum;
	private BigDecimal chargeRates;
	private BigDecimal vat;
	private BigDecimal totalAmount;
	private String status;
	private String requestStartDate;
	private String remark;
	
	public String getItPageReqId() {
		return itPageReqId;
	}
	public void setItPageReqId(String itPageReqId) {
		this.itPageReqId = itPageReqId;
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
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public String getPublicType() {
		return publicType;
	}
	public void setPublicType(String publicType) {
		this.publicType = publicType;
	}
	public String getStaffPageNum() {
		return staffPageNum;
	}
	public void setStaffPageNum(String staffPageNum) {
		this.staffPageNum = staffPageNum;
	}
	public String getPublicPageNum() {
		return publicPageNum;
	}
	public void setPublicPageNum(String publicPageNum) {
		this.publicPageNum = publicPageNum;
	}
	public BigDecimal getChargeRates() {
		return chargeRates;
	}
	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestStartDate() {
		return requestStartDate;
	}
	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
