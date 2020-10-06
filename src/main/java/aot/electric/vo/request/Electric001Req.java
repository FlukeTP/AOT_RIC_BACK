package aot.electric.vo.request;

public class Electric001Req {
	private Long id;
	private String entreprenuerName;
	private String meterName;
	private String periodMonth;
	private String serialNoMeter;
	private String contractNo;

	public String getEntreprenuerName() {
		return entreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getSerialNoMeter() {
		return serialNoMeter;
	}

	public void setSerialNoMeter(String serialNoMeter) {
		this.serialNoMeter = serialNoMeter;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}