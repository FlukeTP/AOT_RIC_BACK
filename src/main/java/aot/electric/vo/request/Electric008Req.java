package aot.electric.vo.request;

import java.math.BigDecimal;
import java.util.List;

public class Electric008Req {
	private Long typeConfigId;
	private String electricType;
	private String rateType;
	private BigDecimal serviceChargeRates;
	private String description;
	private List<Electric008DtlReq> electric008DtlReq;

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

	public BigDecimal getServiceChargeRates() {
		return serviceChargeRates;
	}

	public void setServiceChargeRates(BigDecimal serviceChargeRates) {
		this.serviceChargeRates = serviceChargeRates;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Electric008DtlReq> getElectric008DtlReq() {
		return electric008DtlReq;
	}

	public void setElectric008DtlReq(List<Electric008DtlReq> electric008DtlReq) {
		this.electric008DtlReq = electric008DtlReq;
	}

}
