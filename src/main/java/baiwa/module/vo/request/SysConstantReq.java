package baiwa.module.vo.request;

public class SysConstantReq {
	private Long constantId;
	private String constantKey;
	private String constantValue;

	public Long getConstantId() {
		return constantId;
	}

	public String getConstantKey() {
		return constantKey;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantId(Long constantId) {
		this.constantId = constantId;
	}

	public void setConstantKey(String constantKey) {
		this.constantKey = constantKey;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}

}
