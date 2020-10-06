package aot.common.vo.response;

import java.math.BigDecimal;

public class ElectricBillRes {
	private BigDecimal totalAmount;
	private BigDecimal serviceValue;
	private BigDecimal vat;
	private BigDecimal baseValue;
	private BigDecimal amountMeter;
	private BigDecimal totalChargeRates;
	private BigDecimal calPercent;
	private BigDecimal ftValue;
	private BigDecimal energyValue;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getServiceValue() {
		return serviceValue;
	}
	public void setServiceValue(BigDecimal serviceValue) {
		this.serviceValue = serviceValue;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public BigDecimal getBaseValue() {
		return baseValue;
	}
	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}
	public BigDecimal getAmountMeter() {
		return amountMeter;
	}
	public void setAmountMeter(BigDecimal amountMeter) {
		this.amountMeter = amountMeter;
	}
	public BigDecimal getTotalChargeRates() {
		return totalChargeRates;
	}
	public void setTotalChargeRates(BigDecimal totalChargeRates) {
		this.totalChargeRates = totalChargeRates;
	}
	public BigDecimal getCalPercent() {
		return calPercent;
	}
	public void setCalPercent(BigDecimal calPercent) {
		this.calPercent = calPercent;
	}

	public BigDecimal getFtValue() {
		return ftValue;
	}

	public void setFtValue(BigDecimal ftValue) {
		this.ftValue = ftValue;
	}

	public BigDecimal getEnergyValue() {
		return energyValue;
	}

	public void setEnergyValue(BigDecimal energyValue) {
		this.energyValue = energyValue;
	}

}