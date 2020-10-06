package aot.it.vo.request;

import java.math.BigDecimal;
import java.util.List;

public class It001Req {
	
	private Long networkCreateInvoiceId;
	private String itNetworkServiceId;
	private String itNetworkSeviceId;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String entreprenuerBranch;
	private String contractNo;
	private String itLocation;
	private String rentalObjectCode;
	private String requestStartDate;
	private String requestEndDate;
	private String remark;
	private BigDecimal totalAmount; 
	private List<It001DtlReq> detailChargeRate;
	
	public Long getNetworkCreateInvoiceId() {
		return networkCreateInvoiceId;
	}
	public void setNetworkCreateInvoiceId(Long networkCreateInvoiceId) {
		this.networkCreateInvoiceId = networkCreateInvoiceId;
	}
	public String getItNetworkServiceId() {
		return itNetworkServiceId;
	}
	public void setItNetworkServiceId(String itNetworkServiceId) {
		this.itNetworkServiceId = itNetworkServiceId;
	}
	public String getItNetworkSeviceId() {
		return itNetworkSeviceId;
	}
	public void setItNetworkSeviceId(String itNetworkSeviceId) {
		this.itNetworkSeviceId = itNetworkSeviceId;
	}
	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}
	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}
	public String getEntreprenuerName() {
		return entreprenuerName;
	}
	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}
	public String getEntreprenuerBranch() {
		return entreprenuerBranch;
	}
	public void setEntreprenuerBranch(String entreprenuerBranch) {
		this.entreprenuerBranch = entreprenuerBranch;
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
	public String getRentalObjectCode() {
		return rentalObjectCode;
	}
	public void setRentalObjectCode(String rentalObjectCode) {
		this.rentalObjectCode = rentalObjectCode;
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
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<It001DtlReq> getDetailChargeRate() {
		return detailChargeRate;
	}
	public void setDetailChargeRate(List<It001DtlReq> detailChargeRate) {
		this.detailChargeRate = detailChargeRate;
	}
	
}
