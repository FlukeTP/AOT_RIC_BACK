package aot.pos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_pos_revenue_customer")
public class RicPosRevenueCustomer implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3585708483097886372L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rev_cus_id")
	private Long revCusId;

	@Column(name = "customer_code")
	private String customerCode;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "start_sale_date")
	private Date startSaleDate;

	@Column(name = "end_sale_date")
	private Date endSaleDate;

	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "sent_status")
	private String sentStatus;

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

	@Column(name = "sap_status")
	private String sapStatus;

	@Column(name = "sap_error_desc")
	private String sapErrorDesc;

	@Column(name = "sap_json_req")
	private String sapJsonReq;

	@Column(name = "sap_json_res")
	private String sapJsonRes;

	public Long getRevCusId() {
		return revCusId;
	}

	public void setRevCusId(Long revCusId) {
		this.revCusId = revCusId;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getStartSaleDate() {
		return startSaleDate;
	}

	public void setStartSaleDate(Date startSaleDate) {
		this.startSaleDate = startSaleDate;
	}

	public Date getEndSaleDate() {
		return endSaleDate;
	}

	public void setEndSaleDate(Date endSaleDate) {
		this.endSaleDate = endSaleDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
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
	
}
