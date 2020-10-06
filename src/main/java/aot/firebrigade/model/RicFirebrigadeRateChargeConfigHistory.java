package aot.firebrigade.model;

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
@Table(name = "ric_firebrigade_rate_charge_config_history")
public class RicFirebrigadeRateChargeConfigHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2607281715685369639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_history_id")
	private Long rateHistoryId;
	
	@Column(name = "rate_config_id")
	private Long rateConfigId;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "charge_rate")
	private BigDecimal chargeRates;

	@Column(name = "effective_date")
	private Date effectiveDate;
	
	@Column(name = "remark")
	private String remark;

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
	
	@Column(name = "unit")
	private String unit;

	public Long getRateHistoryId() {
		return rateHistoryId;
	}

	public void setRateHistoryId(Long rateHistoryId) {
		this.rateHistoryId = rateHistoryId;
	}

	public Long getRateConfigId() {
		return rateConfigId;
	}

	public void setRateConfigId(Long rateConfigId) {
		this.rateConfigId = rateConfigId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
