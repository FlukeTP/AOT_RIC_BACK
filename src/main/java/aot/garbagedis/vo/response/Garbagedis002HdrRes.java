package aot.garbagedis.vo.response;

import java.math.BigDecimal;
import java.util.List;

import aot.garbagedis.vo.request.Garbagedis002DtlReq;

public class Garbagedis002HdrRes {

	private Long garReqId;
	private String customerCode;
	private String customerName;
	private String customerBranch;
	private String contractNo;
	private String rentalObject;
	private String serviceType;
	private String trashLocation;
	private String startDate;
	private String endDate;
	private BigDecimal totalChargeRates;
	private BigDecimal totalTrashWeight;
	private BigDecimal totalMoneyAmount;
	private List<Garbagedis002DtlReq> chargeRateDtl;
	private String address;
	private String remark;
	private String airport;
	private Long trashWeight;
	private String transactionNo;
	private String sapStatus;
	private String sapError;
	private String invoiceNo;
	private String receiptNo;
	private String showButton;
	private String generalWeight;
	private String hazardousWeight;
	private String infectiousWeight;
	private String sapJsonReq;
	
	public Long getGarReqId() {
		return garReqId;
	}
	public void setGarReqId(Long garReqId) {
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
	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}
	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}
	public BigDecimal getTotalTrashWeight() {
		return totalTrashWeight;
	}
	public void setTotalTrashWeight(BigDecimal totalTrashWeight) {
		this.totalTrashWeight = totalTrashWeight;
	}
	public BigDecimal getTotalMoneyAmount() {
		return totalMoneyAmount;
	}
	public void setTotalMoneyAmount(BigDecimal totalMoneyAmount) {
		this.totalMoneyAmount = totalMoneyAmount;
	}
	public List<Garbagedis002DtlReq> getChargeRateDtl() {
		return chargeRateDtl;
	}
	public void setChargeRateDtl(List<Garbagedis002DtlReq> chargeRateDtl) {
		this.chargeRateDtl = chargeRateDtl;
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
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public Long getTrashWeight() {
		return trashWeight;
	}
	public void setTrashWeight(Long trashWeight) {
		this.trashWeight = trashWeight;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
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
	public String getShowButton() {
		return showButton;
	}
	public void setShowButton(String showButton) {
		this.showButton = showButton;
	}
	public String getGeneralWeight() {
		return generalWeight;
	}
	public void setGeneralWeight(String generalWeight) {
		this.generalWeight = generalWeight;
	}
	public String getHazardousWeight() {
		return hazardousWeight;
	}
	public void setHazardousWeight(String hazardousWeight) {
		this.hazardousWeight = hazardousWeight;
	}
	public String getInfectiousWeight() {
		return infectiousWeight;
	}
	public void setInfectiousWeight(String infectiousWeight) {
		this.infectiousWeight = infectiousWeight;
	}
	public String getSapJsonReq() {
		return sapJsonReq;
	}
	public void setSapJsonReq(String sapJsonReq) {
		this.sapJsonReq = sapJsonReq;
	}
}
