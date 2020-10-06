package aot.garbagedis.vo.response;

import java.math.BigDecimal;

public class Garbagedis002DtlRes {

	private Long garReqDtlId;
	private Long garReqId;
	private String trashType;
	private Long trashWeight;
	private Long trashSize;
	private BigDecimal chargeRates;
	private BigDecimal moneyAmount;
	
	public Long getGarReqDtlId() {
		return garReqDtlId;
	}
	public void setGarReqDtlId(Long garReqDtlId) {
		this.garReqDtlId = garReqDtlId;
	}
	public Long getGarReqId() {
		return garReqId;
	}
	public void setGarReqId(Long garReqId) {
		this.garReqId = garReqId;
	}
	public String getTrashType() {
		return trashType;
	}
	public void setTrashType(String trashType) {
		this.trashType = trashType;
	}
	public Long getTrashWeight() {
		return trashWeight;
	}
	public void setTrashWeight(Long trashWeight) {
		this.trashWeight = trashWeight;
	}
	public Long getTrashSize() {
		return trashSize;
	}
	public void setTrashSize(Long trashSize) {
		this.trashSize = trashSize;
	}
	public BigDecimal getChargeRates() {
		return chargeRates;
	}
	public void setChargeRates(BigDecimal chargeRates) {
		this.chargeRates = chargeRates;
	}
	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
}
