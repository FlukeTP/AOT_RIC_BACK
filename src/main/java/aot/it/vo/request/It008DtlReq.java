package aot.it.vo.request;

import java.math.BigDecimal;

public class It008DtlReq {
	
	private Long id;
	private Long idHeader;
	private String serviceType;
	private BigDecimal chargeRate;
	private Long equipmentAmount;
	private BigDecimal totalAmount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdHeader() {
		return idHeader;
	}
	public void setIdHeader(Long idHeader) {
		this.idHeader = idHeader;
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
	public Long getEquipmentAmount() {
		return equipmentAmount;
	}
	public void setEquipmentAmount(Long equipmentAmount) {
		this.equipmentAmount = equipmentAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
