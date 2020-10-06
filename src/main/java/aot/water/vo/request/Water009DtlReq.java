package aot.water.vo.request;

import java.math.BigDecimal;

public class Water009DtlReq {
	private Long wasteDetailId;
	private String serviceType;
	private BigDecimal chargeRate;
	private BigDecimal unit;
	private BigDecimal amount;
	private BigDecimal vat;
	private BigDecimal netAmount;

	public Long getWasteDetailId() {
		return wasteDetailId;
	}

	public void setWasteDetailId(Long wasteDetailId) {
		this.wasteDetailId = wasteDetailId;
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

	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

}
