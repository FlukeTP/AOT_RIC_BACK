package aot.it.vo.request;

import java.math.BigDecimal;
import java.util.Date;

public class It007Req {

	private String dedicatedInvoiceId;
	private String monthly;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String contractNo;
	private String location;
	private String periodMonth;
	private String contractData;
	private Date requestStartDate;
	private Date requestEndDate;
	private BigDecimal totalAmount;

	public String getDedicatedInvoiceId() {
		return dedicatedInvoiceId;
	}

	public void setDedicatedInvoiceId(String dedicatedInvoiceId) {
		this.dedicatedInvoiceId = dedicatedInvoiceId;
	}

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContractData() {
		return contractData;
	}

	public void setContractData(String contractData) {
		this.contractData = contractData;
	}

	public Date getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public Date getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

}
