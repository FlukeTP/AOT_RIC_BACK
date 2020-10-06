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
@Table(name = "ric_garbagedis_req_dtl")
public class RicGarbagedisReqDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7680382921356351752L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gar_req_dtl_id")
	private Long garReqDtlId;
	
	@Column(name = "gar_req_id")
	private Long garReqId;
	
	@Column(name = "trash_type")
	private String trashType;
	
	@Column(name = "trash_weight")
	private Long trashWeight;
	
	@Column(name = "trash_size")
	private Long trashSize;

	@Column(name = "charge_rates")
	private BigDecimal chargeRates;
	
	@Column(name = "money_amount")
	private BigDecimal moneyAmount;
	
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

	public Long getGarReqDtlId() {
		return garReqDtlId;
	}

	public void setGarReqDtlId(Long garReqDtlId) {
		this.garReqDtlId = garReqDtlId;
	}

	public Long getGarReqId() {
		return garReqId;
	}

	public void setGarReqId(Long garReqId) {
		this.garReqId = garReqId;
	}

	public String getTrashType() {
		return trashType;
	}

	public void setTrashType(String trashType) {
		this.trashType = trashType;
	}

	public Long getTrashWeight() {
		return trashWeight;
	}

	public void setTrashWeight(Long trashWeight) {
		this.trashWeight = trashWeight;
	}

	public Long getTrashSize() {
		return trashSize;
	}

	public void setTrashSize(Long trashSize) {
		this.trashSize = trashSize;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
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
