package aot.electric.vo.response;

import java.math.BigDecimal;

public class Electric008DtlRes {
	private Long chargeMappingId;
	private Long typeConfigId;
	private String voltageType;
	private BigDecimal startRange;
	private BigDecimal endRange;
	private BigDecimal chargeRates;

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

}
