package aot.water.model;

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
@Table(name = "ric_water_req_change")
public class RicWaterReqChange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4795394241103739612L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "water_change_id")
	private Long waterChangeId;

	@Column(name = "req_id")
	private Long reqId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_branch")
	private String customerBranch;
	
	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "old_serial_no")
	private String oldSerialNo;

	@Column(name = "old_charge_rates")
	private BigDecimal oldChargeRates;

	@Column(name = "old_vat")
	private BigDecimal oldVat;

	@Column(name = "old_totalcharge_rates")
	private BigDecimal oldTotalchargeRates;

	@Column(name = "new_serial_no")
	private String newSerialNo;

	@Column(name = "new_charge_rates")
	private BigDecimal newChargeRates;

	@Column(name = "new_vat")
	private BigDecimal newVat;

	@Column(name = "new_totalcharge_rates")
	private BigDecimal newTotalchargeRates;

	@Column(name = "date_change")
	private Date dateChange;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;
 	
 	@Column(name = "invoice_no_reqcash")
	private String invoiceNoReqcash;
	
	@Column(name = "receipt_no_reqcash")
	private String receiptNoReqcash;
	
	@Column(name = "invoice_no_reqlg")
	private String invoiceNoReqlg;
	
	@Column(name = "receipt_no_reqlg")
	private String receiptNoReqlg;
 
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
 
 	@Column(name = "transaction_no_lg")
	private String transactionNoLg;
 
 	@Column(name = "invoice_no_lg")
	private String invoiceNoLg;
	
	@Column(name = "sap_status_lg")
 	private String sapStatusLg;

 	@Column(name = "sap_error_desc_lg")
 	private String sapErrorDescLg;

 	@Column(name = "sap_json_req_lg")
 	private String sapJsonReqLg;

 	@Column(name = "sap_json_res_lg")
 	private String sapJsonResLg;

	@Column(name = "created_date")
	private Date createDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete;

	public Long getWaterChangeId() {
		return waterChangeId;
	}

	public void setWaterChangeId(Long waterChangeId) {
		this.waterChangeId = waterChangeId;
	}

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
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

	public String getOldSerialNo() {
		return oldSerialNo;
	}

	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
	}

	public BigDecimal getOldChargeRates() {
		return oldChargeRates;
	}

	public void setOldChargeRates(BigDecimal oldChargeRates) {
		this.oldChargeRates = oldChargeRates;
	}

	public BigDecimal getOldVat() {
		return oldVat;
	}

	public void setOldVat(BigDecimal oldVat) {
		this.oldVat = oldVat;
	}

	public BigDecimal getOldTotalchargeRates() {
		return oldTotalchargeRates;
	}

	public void setOldTotalchargeRates(BigDecimal oldTotalchargeRates) {
		this.oldTotalchargeRates = oldTotalchargeRates;
	}

	public String getNewSerialNo() {
		return newSerialNo;
	}

	public void setNewSerialNo(String newSerialNo) {
		this.newSerialNo = newSerialNo;
	}

	public BigDecimal getNewChargeRates() {
		return newChargeRates;
	}

	public void setNewChargeRates(BigDecimal newChargeRates) {
		this.newChargeRates = newChargeRates;
	}

	public BigDecimal getNewVat() {
		return newVat;
	}

	public void setNewVat(BigDecimal newVat) {
		this.newVat = newVat;
	}

	public BigDecimal getNewTotalchargeRates() {
		return newTotalchargeRates;
	}

	public void setNewTotalchargeRates(BigDecimal newTotalchargeRates) {
		this.newTotalchargeRates = newTotalchargeRates;
	}

	public Date getDateChange() {
		return dateChange;
	}

	public void setDateChange(Date dateChange) {
		this.dateChange = dateChange;
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

	public String getInvoiceNoReqcash() {
		return invoiceNoReqcash;
	}

	public void setInvoiceNoReqcash(String invoiceNoReqcash) {
		this.invoiceNoReqcash = invoiceNoReqcash;
	}

	public String getReceiptNoReqcash() {
		return receiptNoReqcash;
	}

	public void setReceiptNoReqcash(String receiptNoReqcash) {
		this.receiptNoReqcash = receiptNoReqcash;
	}

	public String getInvoiceNoReqlg() {
		return invoiceNoReqlg;
	}

	public void setInvoiceNoReqlg(String invoiceNoReqlg) {
		this.invoiceNoReqlg = invoiceNoReqlg;
	}

	public String getReceiptNoReqlg() {
		return receiptNoReqlg;
	}

	public void setReceiptNoReqlg(String receiptNoReqlg) {
		this.receiptNoReqlg = receiptNoReqlg;
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

	public String getTransactionNoLg() {
		return transactionNoLg;
	}

	public void setTransactionNoLg(String transactionNoLg) {
		this.transactionNoLg = transactionNoLg;
	}

	public String getInvoiceNoLg() {
		return invoiceNoLg;
	}

	public void setInvoiceNoLg(String invoiceNoLg) {
		this.invoiceNoLg = invoiceNoLg;
	}

	public String getSapStatusLg() {
		return sapStatusLg;
	}

	public void setSapStatusLg(String sapStatusLg) {
		this.sapStatusLg = sapStatusLg;
	}

	public String getSapErrorDescLg() {
		return sapErrorDescLg;
	}

	public void setSapErrorDescLg(String sapErrorDescLg) {
		this.sapErrorDescLg = sapErrorDescLg;
	}

	public String getSapJsonReqLg() {
		return sapJsonReqLg;
	}

	public void setSapJsonReqLg(String sapJsonReqLg) {
		this.sapJsonReqLg = sapJsonReqLg;
	}

	public String getSapJsonResLg() {
		return sapJsonResLg;
	}

	public void setSapJsonResLg(String sapJsonResLg) {
		this.sapJsonResLg = sapJsonResLg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
