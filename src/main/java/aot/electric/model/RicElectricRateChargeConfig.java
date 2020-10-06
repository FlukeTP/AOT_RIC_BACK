package aot.electric.model;

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
@Table(name = "ric_electric_rate_charge_config")
public class RicElectricRateChargeConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1495395852907282288L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_config_id")
	private Long rateConfigId;

	@Column(name = "modified_year")
	private Date modifiedYear;

	@Column(name = "phase")
	private Integer phase;

	@Column(name = "service_type")
	private String serviceType;

	@Column(name = "range_ampere")
	private String rangeAmpere;

	@Column(name = "charge_rate")
	private BigDecimal chargeRates;

	@Column(name = "remark")
	private String remark;

	@Column(name = "airport")
	private String airport;

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

	public Long getRateConfigId() {
		return rateConfigId;
	}

	public void setRateConfigId(Long rateConfigId) {
		this.rateConfigId = rateConfigId;
	}

	public Date getModifiedYear() {
		return modifiedYear;
	}

	public void setModifiedYear(Date modifiedYear) {
		this.modifiedYear = modifiedYear;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRangeAmpere() {
		return rangeAmpere;
	}

	public void setRangeAmpere(String rangeAmpere) {
		this.rangeAmpere = rangeAmpere;
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

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
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
