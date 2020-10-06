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
@Table(name = "ric_trash_size")
public class RicTrashSize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5990497263003343647L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trash_size_id")
	private Long trashSizeId;
	@Column(name = "yearly")
	private String yearly;
	@Column(name = "trash_size")
	private BigDecimal trashSize;
	@Column(name = "charge_rates")
	private BigDecimal chargeRates;
	@Column(name = "remark")
	private String remark;
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

	public Long getTrashSizeId() {
		return trashSizeId;
	}

	public void setTrashSizeId(Long trashSizeId) {
		this.trashSizeId = trashSizeId;
	}

	public String getYearly() {
		return yearly;
	}

	public void setYearly(String yearly) {
		this.yearly = yearly;
	}

	public BigDecimal getTrashSize() {
		return trashSize;
	}

	public void setTrashSize(BigDecimal trashSize) {
		this.trashSize = trashSize;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
