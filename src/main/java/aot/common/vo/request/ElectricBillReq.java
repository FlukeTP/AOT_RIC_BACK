package aot.common.vo.request;

import java.math.BigDecimal;

public class ElectricBillReq {
	private String serialNoMeter;
	private String type;
	private BigDecimal currentMeterValue;
	private BigDecimal backwardMeterValue;
	private BigDecimal serviceValue;
	private BigDecimal backwardAmount;
	private BigDecimal currentAmount;

	public String getSerialNoMeter() {
		return serialNoMeter;
	}

	public void setSerialNoMeter(String serialNoMeter) {
		this.serialNoMeter = serialNoMeter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getCurrentMeterValue() {
		return currentMeterValue;
	}

	public void setCurrentMeterValue(BigDecimal currentMeterValue) {
		this.currentMeterValue = currentMeterValue;
	}

	public BigDecimal getBackwardMeterValue() {
		return backwardMeterValue;
	}

	public void setBackwardMeterValue(BigDecimal backwardMeterValue) {
		this.backwardMeterValue = backwardMeterValue;
	}

	public BigDecimal getServiceValue() {
		return serviceValue;
	}

	public void setServiceValue(BigDecimal serviceValue) {
		this.serviceValue = serviceValue;
	}

	public BigDecimal getBackwardAmount() {
		return backwardAmount;
	}

	public void setBackwardAmount(BigDecimal backwardAmount) {
		this.backwardAmount = backwardAmount;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

}