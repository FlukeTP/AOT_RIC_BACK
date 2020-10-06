package aot.it.vo.request;

import java.math.BigDecimal;

public class It001DtlReq {
	
	private Long id;
	private Long idNetworkCreateInvoice;
	private String serviceType;
	private BigDecimal chargeRate;
	private String calculateInfo;
	private BigDecimal totalAmount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdNetworkCreateInvoice() {
		return idNetworkCreateInvoice;
	}
	public void setIdNetworkCreateInvoice(Long idNetworkCreateInvoice) {
		this.idNetworkCreateInvoice = idNetworkCreateInvoice;
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
	public String getCalculateInfo() {
		return calculateInfo;
	}
	public void setCalculateInfo(String calculateInfo) {
		this.calculateInfo = calculateInfo;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
