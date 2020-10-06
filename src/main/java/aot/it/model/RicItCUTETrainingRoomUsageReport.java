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
@Table(name = "ric_it_cute_training_room_usage_report")
public class RicItCUTETrainingRoomUsageReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1879959409909827408L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_training_room_usage_id")
	private Long itTrainingRoomUsageId;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;
	
	@Column(name = "entreprenuer_branch")
	private String entreprenuerBranch;
	
	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "rental_area_name")
	private String rentalAreaName;

	@Column(name = "day_amount")
	private String dayAmount;

	@Column(name = "room_type")
	private String roomType;

	@Column(name = "req_start_date")
	private Date reqStartDate;

	@Column(name = "timeperiod")
	private String timeperiod;

	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;

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
	private String bankExpNo;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "invoice_address")
	private String invoiceAddress;

	@Column(name = "receipt_no")
	private String receiptNo;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_deleted")
	private String isDeleted;

	@Column(name = "color_time")
	private String colorTime;
	@Column(name = "transaction_no")
	private String transactionNo;
	
	@Column(name = "sap_status")
	private String sapStatus;
	
	@Column(name = "sap_error")
	private String sapError;
	
	@Column(name = "sap_json_req")
	private String sapJsonReq;
	
	@Column(name = "sap_json_res")
	private String sapJsonRes;

	public Long getItTrainingRoomUsageId() {
		return itTrainingRoomUsageId;
	}

	public void setItTrainingRoomUsageId(Long itTrainingRoomUsageId) {
		this.itTrainingRoomUsageId = itTrainingRoomUsageId;
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

	public String getRentalAreaName() {
		return rentalAreaName;
	}

	public void setRentalAreaName(String rentalAreaName) {
		this.rentalAreaName = rentalAreaName;
	}

	public String getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(String dayAmount) {
		this.dayAmount = dayAmount;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Date getReqStartDate() {
		return reqStartDate;
	}

	public void setReqStartDate(Date reqStartDate) {
		this.reqStartDate = reqStartDate;
	}

	public String getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
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

	public String getBankExpNo() {
		return bankExpNo;
	}

	public void setBankExpNo(String bankExpNo) {
		this.bankExpNo = bankExpNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getColorTime() {
		return colorTime;
	}

	public void setColorTime(String colorTime) {
		this.colorTime = colorTime;
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
}
