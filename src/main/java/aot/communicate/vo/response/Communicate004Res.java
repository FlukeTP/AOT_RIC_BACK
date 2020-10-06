package aot.communicate.vo.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import aot.communicate.model.RicCommunicateReqFlightScheduleDtl;

public class Communicate004Res {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String customerBranch;
	private String contractNo;
	private String rentalAreaCode;
	private String rentalAreaName;
	private String remark;
	private String requestDateStr;
	private String endDateStr;
	private String airport;
	private String paymentType;
	private String paymentTypeTh;
	private String bankName;
	private String bankBranch;
	private String bankExplanation;
	private String bankGuaranteeNo;
	private String bankExpNoStr;
	private BigDecimal AmountLg;
	private BigDecimal AmountMonth;
	private String AmountLgDF;
	private String AmountMonthDF;
	private String totalAllDF;
	private String sapStatus;
	private String sapError;
	private String sapJsonRes;
	private String sapJsonReq;
	private String invoiceNo;
	private String receiptNo;
	private String transactionNo;
	private String flagCancel;
	private String reverseBtn;
	private String flagEndDate;
	private String cancelBtn;
	private List<RicCommunicateReqFlightScheduleDtl> serviceCharge = new ArrayList<RicCommunicateReqFlightScheduleDtl>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRequestDateStr() {
		return requestDateStr;
	}

	public void setRequestDateStr(String requestDateStr) {
		this.requestDateStr = requestDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentTypeTh() {
		return paymentTypeTh;
	}

	public void setPaymentTypeTh(String paymentTypeTh) {
		this.paymentTypeTh = paymentTypeTh;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankExplanation() {
		return bankExplanation;
	}

	public void setBankExplanation(String bankExplanation) {
		this.bankExplanation = bankExplanation;
	}

	public String getBankGuaranteeNo() {
		return bankGuaranteeNo;
	}

	public void setBankGuaranteeNo(String bankGuaranteeNo) {
		this.bankGuaranteeNo = bankGuaranteeNo;
	}

	public String getBankExpNoStr() {
		return bankExpNoStr;
	}

	public void setBankExpNoStr(String bankExpNoStr) {
		this.bankExpNoStr = bankExpNoStr;
	}

	public BigDecimal getAmountLg() {
		return AmountLg;
	}

	public void setAmountLg(BigDecimal amountLg) {
		AmountLg = amountLg;
	}

	public BigDecimal getAmountMonth() {
		return AmountMonth;
	}

	public void setAmountMonth(BigDecimal amountMonth) {
		AmountMonth = amountMonth;
	}

	public String getAmountLgDF() {
		return AmountLgDF;
	}

	public void setAmountLgDF(String amountLgDF) {
		AmountLgDF = amountLgDF;
	}

	public String getAmountMonthDF() {
		return AmountMonthDF;
	}

	public void setAmountMonthDF(String amountMonthDF) {
		AmountMonthDF = amountMonthDF;
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

	public String getFlagCancel() {
		return flagCancel;
	}

	public void setFlagCancel(String flagCancel) {
		this.flagCancel = flagCancel;
	}

	public String getReverseBtn() {
		return reverseBtn;
	}

	public void setReverseBtn(String reverseBtn) {
		this.reverseBtn = reverseBtn;
	}

	public String getFlagEndDate() {
		return flagEndDate;
	}

	public void setFlagEndDate(String flagEndDate) {
		this.flagEndDate = flagEndDate;
	}

	public String getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(String cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public List<RicCommunicateReqFlightScheduleDtl> getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(List<RicCommunicateReqFlightScheduleDtl> serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getTotalAllDF() {
		return totalAllDF;
	}

	public void setTotalAllDF(String totalAllDF) {
		this.totalAllDF = totalAllDF;
	}

}