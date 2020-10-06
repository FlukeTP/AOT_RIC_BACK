package aot.garbagedis.model;

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
@Table(name = "ric_trash_service_info")
public class RicTrashServiceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5563733480110037290L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trash_service_info_id")
	private Long trashServiceInfoId;
	
	@Column(name = "customer_code")
	private String customerCode;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_branch")
	private String customerBranch;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "start_date_date")
	private Date startDateDate;
	
	@Column(name = "end_date_date")
	private Date endDateDate;
	
	@Column(name = "trash_type")
	private String trashType;
	
	@Column(name = "garbage_type")
	private String garbageType;
	
	@Column(name = "trash_normal_kg")
	private Long trashNormalKg;
	
	@Column(name = "trash_normal_bath")
	private Long trashNormalBath;
	
	@Column(name = "number_tanks")
	private Long numberTanks;
	
	@Column(name = "trash_location")
	private String trashLocation;
	
	@Column(name = "total_charge_rates")
	private BigDecimal totalChargeRates;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "airport")
	private String airport;
	
	@Column(name = "transaction_no_cash")
	private String transactionNoCash;
	
	@Column(name = "invoice_no_cash")
	private String invoiceNoCash;
	
	@Column(name = "sap_status_cash")
	private String sapStatusCash;
	
	@Column(name = "sap_error_desc_cash")
	private String sapErrorDescCash;
	
	@Column(name = "sap_json_req_cash")
	private String sapJsonReqCash;
	
	@Column(name = "sap_json_res_cash")
	private String sapJsonResCash;
	
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

	public Long getTrashServiceInfoId() {
		return trashServiceInfoId;
	}

	public void setTrashServiceInfoId(Long trashServiceInfoId) {
		this.trashServiceInfoId = trashServiceInfoId;
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

	public Date getStartDateDate() {
		return startDateDate;
	}

	public void setStartDateDate(Date startDateDate) {
		this.startDateDate = startDateDate;
	}

	public Date getEndDateDate() {
		return endDateDate;
	}

	public void setEndDateDate(Date endDateDate) {
		this.endDateDate = endDateDate;
	}

	public String getTrashType() {
		return trashType;
	}

	public void setTrashType(String trashType) {
		this.trashType = trashType;
	}

	public String getGarbageType() {
		return garbageType;
	}

	public void setGarbageType(String garbageType) {
		this.garbageType = garbageType;
	}

	public Long getTrashNormalKg() {
		return trashNormalKg;
	}

	public void setTrashNormalKg(Long trashNormalKg) {
		this.trashNormalKg = trashNormalKg;
	}

	public Long getTrashNormalBath() {
		return trashNormalBath;
	}

	public void setTrashNormalBath(Long trashNormalBath) {
		this.trashNormalBath = trashNormalBath;
	}

	public Long getNumberTanks() {
		return numberTanks;
	}

	public void setNumberTanks(Long numberTanks) {
		this.numberTanks = numberTanks;
	}

	public String getTrashLocation() {
		return trashLocation;
	}

	public void setTrashLocation(String trashLocation) {
		this.trashLocation = trashLocation;
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

	public String getTransactionNoCash() {
		return transactionNoCash;
	}

	public void setTransactionNoCash(String transactionNoCash) {
		this.transactionNoCash = transactionNoCash;
	}

	public String getInvoiceNoCash() {
		return invoiceNoCash;
	}

	public void setInvoiceNoCash(String invoiceNoCash) {
		this.invoiceNoCash = invoiceNoCash;
	}

	public String getSapStatusCash() {
		return sapStatusCash;
	}

	public void setSapStatusCash(String sapStatusCash) {
		this.sapStatusCash = sapStatusCash;
	}

	public String getSapErrorDescCash() {
		return sapErrorDescCash;
	}

	public void setSapErrorDescCash(String sapErrorDescCash) {
		this.sapErrorDescCash = sapErrorDescCash;
	}

	public String getSapJsonReqCash() {
		return sapJsonReqCash;
	}

	public void setSapJsonReqCash(String sapJsonReqCash) {
		this.sapJsonReqCash = sapJsonReqCash;
	}

	public String getSapJsonResCash() {
		return sapJsonResCash;
	}

	public void setSapJsonResCash(String sapJsonResCash) {
		this.sapJsonResCash = sapJsonResCash;
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
}
