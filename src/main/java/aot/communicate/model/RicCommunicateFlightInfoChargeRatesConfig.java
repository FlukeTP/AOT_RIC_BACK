package aot.communicate.model;

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
@Table(name = "ric_communicate_flight_info_charge_rates_config")
public class RicCommunicateFlightInfoChargeRatesConfig implements Serializable {

	private static final long serialVersionUID = 3844197721439714176L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "commu_flight_info_config_id")
	private Long commuFlightInfoConfigId;

	@Column(name = "effective_date")
	private Date effectiveDate;

	@Column(name = "service_type")
	private String serviceType;
	
	@Column(name = "charge_rate_Name")
	private String chargeRateName;
	
	@Column(name = "charge_rate")
	private BigDecimal chargeRate;

	@Column(name = "insurance_fee")
	private BigDecimal insuranceFee;

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
	private String isDelete;

	public Long getCommuFlightInfoConfigId() {
		return commuFlightInfoConfigId;
	}

	public void setCommuFlightInfoConfigId(Long commuFlightInfoConfigId) {
		this.commuFlightInfoConfigId = commuFlightInfoConfigId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getChargeRateName() {
		return chargeRateName;
	}

	public void setChargeRateName(String chargeRateName) {
		this.chargeRateName = chargeRateName;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
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
