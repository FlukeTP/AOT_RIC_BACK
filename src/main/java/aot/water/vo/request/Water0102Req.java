package aot.water.vo.request;

import java.math.BigDecimal;

public class Water0102Req {

	private Long waterMaintenanceConfigId;
	private String airport;
	private String modifiedDate;
	private String waterMeterSize;
	private BigDecimal chargeRates;
	private String remark;

	public Long getWaterMaintenanceConfigId() {
		return waterMaintenanceConfigId;
	}

	public void setWaterMaintenanceConfigId(Long waterMaintenanceConfigId) {
		this.waterMaintenanceConfigId = waterMaintenanceConfigId;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
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

}
