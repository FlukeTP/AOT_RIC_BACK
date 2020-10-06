package aot.communicate.vo.response;

import java.math.BigDecimal;

public class Communicate0061Res {

	private Long commuTransceiverConfigId;
	private String effectiveDate;
	private String serviceType;
	private String chargeRateName;
	private BigDecimal insuranceFee;
	private BigDecimal chargeRate;
	private String remark;
	private String updatedDate;
	private String updatedBy;
	
	public Long getCommuTransceiverConfigId() {
		return commuTransceiverConfigId;
	}
	public void setCommuTransceiverConfigId(Long commuTransceiverConfigId) {
		this.commuTransceiverConfigId = commuTransceiverConfigId;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getChargeRateName() {
		return chargeRateName;
	}
	public void setChargeRateName(String chargeRateName) {
		this.chargeRateName = chargeRateName;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public BigDecimal getChargeRate() {
		return chargeRate;
	}
	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
