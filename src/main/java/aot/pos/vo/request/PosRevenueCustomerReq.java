package aot.pos.vo.request;

public class PosRevenueCustomerReq {
	
	private Long revCusId;
	private String customerCode;
	private String customerName;
	private String contractNo;
	private String startSaleDate;
	private String endSaleDate;
	private String fileName;
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
