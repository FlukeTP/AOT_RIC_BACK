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
@Table(name = "ric_it_network_create_invoice")
public class RicItNetworkCreateInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2190125662803092262L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "network_create_invoice_id")
	private Long itNetworkConfigId;

	@Column(name = "entreprenuer_name")
	private String entreprenuerName;

	@Column(name = "entreprenuer_code")
	private String entreprenuerCode;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "it_location")
	private String itLocation;

	@Column(name = "rental_object_code")
	private String rentalObjectCode;

	@Column(name = "request_start_date")
	private Date requestStartDate;

	@Column(name = "request_end_date")
	private Date requestEndDate;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete = "N";

	@Column(name = "entreprenuer_branch")
	private String entreprenuerBranch;
	
	@Column(name = "transaction_no")
	private String transactionNo;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "sap_status")
	private String sapStatus;
	
	@Column(name = "sap_error")
	private String sapError;
	
	@Column(name = "sap_json_req")
	private String sapJsonReq;
	
	@Column(name = "sap_json_res")
	private String sapJsonRes;

	public Long getItNetworkConfigId() {
		return itNetworkConfigId;
	}

	public void setItNetworkConfigId(Long itNetworkConfigId) {
		this.itNetworkConfigId = itNetworkConfigId;
	}

	public String getEntreprenuerName() {
		return entreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}

	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}

	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	
}
