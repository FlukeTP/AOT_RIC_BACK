package aot.communicate.vo.request;

import java.math.BigDecimal;

public class Communicate004DtlReq {
	private Long id;
	private String connectSignal;
	private String location;
	private String service;
	private BigDecimal amountLg;
	private BigDecimal amountMonth;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConnectSignal() {
		return connectSignal;
	}

	public void setConnectSignal(String connectSignal) {
		this.connectSignal = connectSignal;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public BigDecimal getAmountLg() {
		return amountLg;
	}

	public void setAmountLg(BigDecimal amountLg) {
		this.amountLg = amountLg;
	}

	public BigDecimal getAmountMonth() {
		return amountMonth;
	}

	public void setAmountMonth(BigDecimal amountMonth) {
		this.amountMonth = amountMonth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
