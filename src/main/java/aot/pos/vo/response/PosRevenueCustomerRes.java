package aot.pos.vo.response;

import java.math.BigDecimal;

public class PosRevenueCustomerRes {

	private Long revCusId;
	private String customerCode;
	private String customerName;
	private String contractNo;
	private String startSaleDate;
	private String endSaleDate;
	private BigDecimal includingVatSale;
	private BigDecimal excludingVatSale;
	private String fileName;
	private String sentStatus;
	private String createdDate;
	private String sapStatus;
	private String sapErrorDesc;
	
	public Long getRevCusId() {
		return revCusId;
	}
	public void setRevCusId(Long revCusId) {
		this.revCusId = revCusId;
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
	public String getStartSaleDate() {
		return startSaleDate;
	}
	public void setStartSaleDate(String startSaleDate) {
		this.startSaleDate = startSaleDate;
	}
	public String getEndSaleDate() {
		return endSaleDate;
	}
	public void setEndSaleDate(String endSaleDate) {
		this.endSaleDate = endSaleDate;
	}
	public BigDecimal getIncludingVatSale() {
		return includingVatSale;
	}
	public void setIncludingVatSale(BigDecimal includingVatSale) {
		this.includingVatSale = includingVatSale;
	}
	public BigDecimal getExcludingVatSale() {
		return excludingVatSale;
	}
	public void setExcludingVatSale(BigDecimal excludingVatSale) {
		this.excludingVatSale = excludingVatSale;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSapStatus() {
		return sapStatus;
	}
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	public String getSapErrorDesc() {
		return sapErrorDesc;
	}
	public void setSapErrorDesc(String sapErrorDesc) {
		this.sapErrorDesc = sapErrorDesc;
	}
	
}
