package baiwa.module.vo.request;

import java.util.List;

public class FwOrgReq {
	
	private String orgId;
	private String orgCode;
	private String orgDescription;
	private List<FwDepartReq> departDetail;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
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
	public List<FwDepartReq> getDepartDetail() {
		return departDetail;
	}
	public void setDepartDetail(List<FwDepartReq> departDetail) {
		this.departDetail = departDetail;
	}
	
}
