package aot.water.vo.response;

import java.math.BigDecimal;
import java.util.List;

public class Water009Res {
	private Long wasteHeaderId;
	private Long wasteDetailId;
	private String customerCode;
	private String customerName;
	private String customerBranch;
	private String contractNo;
	private String rentalAreaCode;
	private String rentalAreaName;
	private String paymentType;
	private String serviceType;
	private BigDecimal unit;
	private BigDecimal amount;
	private BigDecimal vat;
	private BigDecimal netAmount;
	private String date;
	private String remark;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String reverseBtn;

	private List<Water009DtlRes> water009DtlRes;
	private String invoiceNo;
	private String receiptNo;
	private String transactionNo;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public BigDecimal getUnit() {
		return unit;
	}
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getReverseBtn() {
		return reverseBtn;
	}
	public void setReverseBtn(String reverseBtn) {
		this.reverseBtn = reverseBtn;
	}
	public List<Water009DtlRes> getWater009DtlRes() {
		return water009DtlRes;
	}
	public void setWater009DtlRes(List<Water009DtlRes> water009DtlRes) {
		this.water009DtlRes = water009DtlRes;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

}