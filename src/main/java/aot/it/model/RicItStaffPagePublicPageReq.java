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
@Table(name = "ric_it_staff_page_public_page_req")
public class RicItStaffPagePublicPageReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2348554115537597002L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_page_req_id")
	private Long itPageReqId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_branch")
	private String customerBranch;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "staff_type")
	private String staffType;

	@Column(name = "public_type")
	private String publicType;

	@Column(name = "staff_page_num")
	private Long staffPageNum;

	@Column(name = "public_page_num")
	private Long publicPageNum;
	
	@Column(name = "charge_rates")
	private BigDecimal chargeRates;

	@Column(name = "vat")
	private BigDecimal vat;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "status")
	private String status;
	
	@Column(name = "request_start_date")
	private Date requestStartDate;

	@Column(name = "request_end_date")
	private Date requestEndDate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;
	
	@Column(name = "transaction_no")
	private String transactionNo;
 
 	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "sap_status")
 	private String sapStatus;

 	@Column(name = "sap_error_desc")
 	private String sapErrorDesc;

 	@Column(name = "sap_json_req")
 	private String sapJsonReq;

 	@Column(name = "sap_json_res")
 	private String sapJsonRes;

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

	public Long getItPageReqId() {
		return itPageReqId;
	}

	public void setItPageReqId(Long itPageReqId) {
		this.itPageReqId = itPageReqId;
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

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public String getPublicType() {
		return publicType;
	}

	public void setPublicType(String publicType) {
		this.publicType = publicType;
	}

	public Long getStaffPageNum() {
		return staffPageNum;
	}

	public void setStaffPageNum(Long staffPageNum) {
		this.staffPageNum = staffPageNum;
	}

	public Long getPublicPageNum() {
		return publicPageNum;
	}

	public void setPublicPageNum(Long publicPageNum) {
		this.publicPageNum = publicPageNum;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
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

	public String getSapErrorDesc() {
		return sapErrorDesc;
	}

	public void setSapErrorDesc(String sapErrorDesc) {
		this.sapErrorDesc = sapErrorDesc;
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
