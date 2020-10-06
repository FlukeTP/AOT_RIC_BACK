package aot.common.vo.response;

import java.math.BigDecimal;

public class WaterBillRes {
	private BigDecimal totalAmount;
	private BigDecimal serviceValue;
	private BigDecimal vat;
	private BigDecimal baseValue;
	private BigDecimal amountMeter;
	private BigDecimal totalChargeRates;
	private BigDecimal treatmentFee;
	private BigDecimal recycleAmount;

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

	public BigDecimal getTreatmentFee() {
		return treatmentFee;
	}

	public void setTreatmentFee(BigDecimal treatmentFee) {
		this.treatmentFee = treatmentFee;
	}

	public BigDecimal getRecycleAmount() {
		return recycleAmount;
	}

	public void setRecycleAmount(BigDecimal recycleAmount) {
		this.recycleAmount = recycleAmount;
	}

}