package aot.electric.vo.response;

import java.math.BigDecimal;

public class Electric007Res {
	private Long rateConfigId;
	private String modifiedYear;
	private Integer phase;
	private String serviceType;
	private String rangeAmpere;
	private BigDecimal chargeRates;
	private String remark;
	private String airport;
	private String updatedBy;
	private String updatedDate;

	public Long getRateConfigId() {
		return rateConfigId;
	}

	public void setRateConfigId(Long rateConfigId) {
		this.rateConfigId = rateConfigId;
	}

	public String getModifiedYear() {
		return modifiedYear;
	}

	public void setModifiedYear(String modifiedYear) {
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}
