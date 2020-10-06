package aot.firebrigade.vo.response;

import java.math.BigDecimal;

public class Firebrigade002Res {

	private String rateConfigId;
	private String courseName;
	private BigDecimal chargeRates;
	private String effectiveDate;
	private String remark;
	private String createDate;
	private String createdBy;
	private String unit;
	
	public String getRateConfigId() {
		return rateConfigId;
	}
	public void setRateConfigId(String rateConfigId) {
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
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
