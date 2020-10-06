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
@Table(name = "ric_electric_charge_mapping")
public class RicElectricChargeMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781746225160526607L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "charge_mapping_id")
	private Long chargeMappingId;

	@Column(name = "type_config_id")
	private Long typeConfigId;

	@Column(name = "voltage_type")
	private String voltageType;

	@Column(name = "start_range")
	private BigDecimal startRange;

	@Column(name = "end_range")
	private BigDecimal endRange;

	@Column(name = "charge_rates")
	private BigDecimal chargeRates;

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

	public Long getChargeMappingId() {
		return chargeMappingId;
	}

	public void setChargeMappingId(Long chargeMappingId) {
		this.chargeMappingId = chargeMappingId;
	}

	public Long getTypeConfigId() {
		return typeConfigId;
	}

	public void setTypeConfigId(Long typeConfigId) {
		this.typeConfigId = typeConfigId;
	}

	public String getVoltageType() {
		return voltageType;
	}

	public void setVoltageType(String voltageType) {
		this.voltageType = voltageType;
	}

	public BigDecimal getStartRange() {
		return startRange;
	}

	public void setStartRange(BigDecimal startRange) {
		this.startRange = startRange;
	}

	public BigDecimal getEndRange() {
		return endRange;
	}

	public void setEndRange(BigDecimal endRange) {
		this.endRange = endRange;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
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
