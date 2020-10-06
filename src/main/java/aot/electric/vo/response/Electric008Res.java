package aot.electric.vo.response;

import java.math.BigDecimal;
import java.util.List;

public class Electric008Res {
	private Long typeConfigId;
	private String electricType;
	private String rateType;
	private String voltageType;
	private BigDecimal serviceChargeRates;
	private String updatedBy;
	private String updatedDate;
	private String description;
	private List<Electric008DtlRes> electric008DtlRes;

	public Long getTypeConfigId() {
		return typeConfigId;
	}

	public void setTypeConfigId(Long typeConfigId) {
		this.typeConfigId = typeConfigId;
	}

	public String getElectricType() {
		return electricType;
	}

	public void setElectricType(String electricType) {
		this.electricType = electricType;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getVoltageType() {
		return voltageType;
	}

	public void setVoltageType(String voltageType) {
		this.voltageType = voltageType;
	}

	public BigDecimal getServiceChargeRates() {
		return serviceChargeRates;
	}

	public void setServiceChargeRates(BigDecimal serviceChargeRates) {
		this.serviceChargeRates = serviceChargeRates;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Electric008DtlRes> getElectric008DtlRes() {
		return electric008DtlRes;
	}

	public void setElectric008DtlRes(List<Electric008DtlRes> electric008DtlRes) {
		this.electric008DtlRes = electric008DtlRes;
	}

}
