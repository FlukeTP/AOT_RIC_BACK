package aot.water.vo.request;

import java.math.BigDecimal;

public class Water0114Req {
	private Long wasteConfigId;
	private String airport;
	private String modifiedDate;
	private String serviceType;
	private BigDecimal chargeRates;
	private String remark;

	public Long getWasteConfigId() {
		return wasteConfigId;
	}

	public void setWasteConfigId(Long wasteConfigId) {
		this.wasteConfigId = wasteConfigId;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
