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
@Table(name = "ric_it_cute_training_charge_rates_config")
public class RicItCUTETrainingChargeRatesConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 79953211377425L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "it_cute_config_id")
	private Long itCUTETrainingConfigId;

	@Column(name = "effective_date")
	private Date effectiveDate;

	@Column(name = "service_type")
	private String serviceType;

	@Column(name = "charge_rate")
	private BigDecimal chargeRate;

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
	
	@Column(name = "color_room")
	private String colorRoom;
	
	

	public Long getItCUTETrainingConfigId() {
		return itCUTETrainingConfigId;
	}

	public void setItCUTETrainingConfigId(Long itCUTETrainingConfigId) {
		this.itCUTETrainingConfigId = itCUTETrainingConfigId;
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

	public String getColorRoom() {
		return colorRoom;
	}

	public void setColorRoom(String colorRoom) {
		this.colorRoom = colorRoom;
	}
	
	
}
