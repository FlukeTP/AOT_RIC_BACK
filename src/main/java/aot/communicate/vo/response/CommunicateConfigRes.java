package aot.communicate.vo.response;

import java.math.BigDecimal;

public class CommunicateConfigRes {
	private Long id;
	private String chargeRateName;
	private String serviceType;
	private BigDecimal chargeRate;
	private String insuranceFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChargeRateName() {
		return chargeRateName;
	}

	public void setChargeRateName(String chargeRateName) {
		this.chargeRateName = chargeRateName;
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

	public String getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

}