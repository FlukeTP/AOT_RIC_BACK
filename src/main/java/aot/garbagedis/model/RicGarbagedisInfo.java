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
@Table(name = "ric_garbagedis_info")
public class RicGarbagedisInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4425531776423257775L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gar_info_id")
	private Long garInfoId;
	
	@Column(name = "gar_req_id")
	private Long garReqId;
	
	@Column(name = "months")
	private String months;
	
	@Column(name = "years")
	private String years;
	
	@Column(name = "customer_code")
	private String customerCode;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_branch")
	private String customerBranch;
	
	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "rental_object")
	private String rentalObject;
	
	@Column(name = "trash_location")
	private String trashLocation;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "general_weight")
	private BigDecimal generalWeight;
	
	@Column(name = "hazardous_weight")
	private BigDecimal hazardousWeight;
	
	@Column(name = "infectious_weight")
	private BigDecimal infectiousWeight;
	
	@Column(name = "general_money")
	private BigDecimal generalMoney;
	
	@Column(name = "hazardous_money")
	private BigDecimal hazardousMoney;
	
	@Column(name = "infectious_money")
	private BigDecimal infectiousMoney;
	
	@Column(name = "total_money")
	private BigDecimal totalMoney;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "airport")
	private String airport;
	
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

	public Long getGarInfoId() {
		return garInfoId;
	}

	public void setGarInfoId(Long garInfoId) {
		this.garInfoId = garInfoId;
	}

	public Long getGarReqId() {
		return garReqId;
	}

	public void setGarReqId(Long garReqId) {
		this.garReqId = garReqId;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
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

	public String getRentalObject() {
		return rentalObject;
	}

	public void setRentalObject(String rentalObject) {
		this.rentalObject = rentalObject;
	}

	public String getTrashLocation() {
		return trashLocation;
	}

	public void setTrashLocation(String trashLocation) {
		this.trashLocation = trashLocation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getGeneralWeight() {
		return generalWeight;
	}

	public void setGeneralWeight(BigDecimal generalWeight) {
		this.generalWeight = generalWeight;
	}

	public BigDecimal getHazardousWeight() {
		return hazardousWeight;
	}

	public void setHazardousWeight(BigDecimal hazardousWeight) {
		this.hazardousWeight = hazardousWeight;
	}

	public BigDecimal getInfectiousWeight() {
		return infectiousWeight;
	}

	public void setInfectiousWeight(BigDecimal infectiousWeight) {
		this.infectiousWeight = infectiousWeight;
	}

	public BigDecimal getGeneralMoney() {
		return generalMoney;
	}

	public void setGeneralMoney(BigDecimal generalMoney) {
		this.generalMoney = generalMoney;
	}

	public BigDecimal getHazardousMoney() {
		return hazardousMoney;
	}

	public void setHazardousMoney(BigDecimal hazardousMoney) {
		this.hazardousMoney = hazardousMoney;
	}

	public BigDecimal getInfectiousMoney() {
		return infectiousMoney;
	}

	public void setInfectiousMoney(BigDecimal infectiousMoney) {
		this.infectiousMoney = infectiousMoney;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
}
