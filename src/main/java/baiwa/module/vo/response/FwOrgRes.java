package baiwa.module.vo.response;

import java.util.List;

public class FwOrgRes {
	
	private Long orgId;
	private String orgCode;
	private String orgDescription;
	private String createDate;
	private List<FwDepartRes> departDetail;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgDescription() {
		return orgDescription;
	}
	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public List<FwDepartRes> getDepartDetail() {
		return departDetail;
	}
	public void setDepartDetail(List<FwDepartRes> departDetail) {
		this.departDetail = departDetail;
	}
	
}
