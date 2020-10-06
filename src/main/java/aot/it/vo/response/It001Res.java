package aot.it.vo.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import aot.it.model.RicItNetworkCreateInvoiceMapping;

public class It001Res {
	
	private Long networkCreateInvoiceId;
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
	private String transactionNo;
	private String sapStatus;
	private String sapError;
	private String invoiceNo;
	private String receiptNo;
	private String showButton;
	private List<RicItNetworkCreateInvoiceMapping> detailChargeRate = new ArrayList<RicItNetworkCreateInvoiceMapping>();
	
	public Long getNetworkCreateInvoiceId() {
		return networkCreateInvoiceId;
	}
	public void setNetworkCreateInvoiceId(Long networkCreateInvoiceId) {
		this.networkCreateInvoiceId = networkCreateInvoiceId;
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
	public List<RicItNetworkCreateInvoiceMapping> getDetailChargeRate() {
		return detailChargeRate;
	}
	public void setDetailChargeRate(List<RicItNetworkCreateInvoiceMapping> detailChargeRate) {
		this.detailChargeRate = detailChargeRate;
	}
	public String getEntreprenuerBranch() {
		return entreprenuerBranch;
	}
	public void setEntreprenuerBranch(String entreprenuerBranch) {
		this.entreprenuerBranch = entreprenuerBranch;
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
}
