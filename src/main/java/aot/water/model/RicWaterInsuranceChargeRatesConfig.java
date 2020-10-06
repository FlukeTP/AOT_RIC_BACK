package aot.water.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RicWaterInsuranceChargeRatesConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428083976103064318L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "water_insurance_config_id")
	private Long waterInsuranceConfigId;
	@Column(name = "airport")
	private String airport;
	@Column(name = "modified_date")
	private Date modifiedDate;
	@Column(name = "water_meter_size")
	private String waterMeterSize;
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
	@Column(name = "is_delete")
	private String isDelete = "N";
	
	public Long getWaterInsuranceConfigId() {
		return waterInsuranceConfigId;
	}
	public void setWaterInsuranceConfigId(Long waterInsuranceConfigId) {
		this.waterInsuranceConfigId = waterInsuranceConfigId;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getWaterMeterSize() {
		return waterMeterSize;
	}
	public void setWaterMeterSize(String waterMeterSize) {
		this.waterMeterSize = waterMeterSize;
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
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
