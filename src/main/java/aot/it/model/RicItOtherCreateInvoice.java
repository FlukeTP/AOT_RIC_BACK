package aot.it.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_it_other_create_invoice")
public class RicItOtherCreateInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 781957765684004553L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_create_invoice_id")
	private Long itOtherCreateInvoiceId;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "other_type")
	private String otherType;
	
	@Column(name = "rental_object")
	private String rentalObject;
	
	@Column(name = "charge_rates_type")
	private String chargeRatesType;

	@Column(name = "charge_rates")
	private BigDecimal chargeRates;
	
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	
	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;

	@Column(name = "airport")
	private String airport;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "request_start_date")
	private Date requestStartDate;

	@Column(name = "request_end_date")
	private Date requestEndDate;
	
	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_branch")
	private String bankBranch;

	@Column(name = "bank_explanation")
	private String bankExplanation;

	@Column(name = "bank_guarantee_no")
	private String bankGuaranteeNo;

	@Column(name = "bank_exp_no")
	private Date bankExpNo;
	
	@Column(name = "receipt_no")
	private String receiptNo;
 
 	@Column(name = "invoice_no")
	private String invoiceNo;
 	
 	@Column(name = "invoice_no_cancel")
	private String invoiceNoCancel;
	
	@Column(name = "sap_status")
 	private String sapStatus;

 	@Column(name = "sap_error")
 	private String sapError;

 	@Column(name = "sap_json_req")
 	private String sapJsonReq;

 	@Column(name = "sap_json_res")
 	private String sapJsonRes;
 	
 	@Column(name = "sap_status_cancel")
 	private String sapStatusCancel;

 	@Column(name = "sap_error_cancel")
 	private String sapErrorCancel;

 	@Column(name = "sap_json_req_cancel")
 	private String sapJsonReqCancel;

 	@Column(name = "sap_json_res_cancel")
 	private String sapJsonResCancel;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete;

	@Column(name = "entreprenuer_branch")
	private String entreprenuerBranch;
	
	@Column(name = "transaction_no")
	private String transactionNo;
	
	@Column(name = "transaction_no_cancel")
	private String transactionNoCancel;

	public Long getItOtherCreateInvoiceId() {
		return itOtherCreateInvoiceId;
	}

	public void setItOtherCreateInvoiceId(Long itOtherCreateInvoiceId) {
		this.itOtherCreateInvoiceId = itOtherCreateInvoiceId;
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

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public String getRentalObject() {
		return rentalObject;
	}

	public void setRentalObject(String rentalObject) {
		this.rentalObject = rentalObject;
	}

	public String getChargeRatesType() {
		return chargeRatesType;
	}

	public void setChargeRatesType(String chargeRatesType) {
		this.chargeRatesType = chargeRatesType;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getSapJsonReq() {
		return sapJsonReq;
	}

	public void setSapJsonReq(String sapJsonReq) {
		this.sapJsonReq = sapJsonReq;
	}

	public String getSapJsonRes() {
		return sapJsonRes;
	}

	public void setSapJsonRes(String sapJsonRes) {
		this.sapJsonRes = sapJsonRes;
	}

	public String getInvoiceNoCancel() {
		return invoiceNoCancel;
	}

	public void setInvoiceNoCancel(String invoiceNoCancel) {
		this.invoiceNoCancel = invoiceNoCancel;
	}

	public String getSapStatusCancel() {
		return sapStatusCancel;
	}

	public void setSapStatusCancel(String sapStatusCancel) {
		this.sapStatusCancel = sapStatusCancel;
	}

	public String getSapErrorCancel() {
		return sapErrorCancel;
	}

	public void setSapErrorCancel(String sapErrorCancel) {
		this.sapErrorCancel = sapErrorCancel;
	}

	public String getSapJsonReqCancel() {
		return sapJsonReqCancel;
	}

	public void setSapJsonReqCancel(String sapJsonReqCancel) {
		this.sapJsonReqCancel = sapJsonReqCancel;
	}

	public String getSapJsonResCancel() {
		return sapJsonResCancel;
	}

	public void setSapJsonResCancel(String sapJsonResCancel) {
		this.sapJsonResCancel = sapJsonResCancel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public Date getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(Date bankExpNo) {
		this.bankExpNo = bankExpNo;
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

	public String getTransactionNoCancel() {
		return transactionNoCancel;
	}

	public void setTransactionNoCancel(String transactionNoCancel) {
		this.transactionNoCancel = transactionNoCancel;
	}
	
}
