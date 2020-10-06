package aot.water.vo.request;

import java.math.BigDecimal;

public class Water010Req {

	private Long waterServiceConfigId;
	private String airport;
	private String modifiedDate;
	private String waterType;
	private BigDecimal chargeRates;
	private String remark;

	public Long getWaterServiceConfigId() {
		return waterServiceConfigId;
	}

	public void setWaterServiceConfigId(Long waterServiceConfigId) {
		this.waterServiceConfigId = waterServiceConfigId;
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

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
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
