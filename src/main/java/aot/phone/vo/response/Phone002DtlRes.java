package aot.phone.vo.response;

import java.math.BigDecimal;

public class Phone002DtlRes {
	private Long rateChargeId;
	private String typeName;
	private String serviceTypeName;
	private BigDecimal chargeRates;
	private BigDecimal vat;
	private BigDecimal totalChargeRates;

	public Long getRateChargeId() {
		return rateChargeId;
	}

	public void setRateChargeId(Long rateChargeId) {
		this.rateChargeId = rateChargeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public BigDecimal getChargeRates() {
		return chargeRates;
	}

	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}

	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}

}
