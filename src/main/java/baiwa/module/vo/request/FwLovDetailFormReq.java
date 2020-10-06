package baiwa.module.vo.request;

import java.util.List;

public class FwLovDetailFormReq {
	private String lovKey;
	private FwLovHReq fwLovHReq;
	private List<FwLovDetailReq> fwLovDetailReqs;

	public List<FwLovDetailReq> getFwLovDetailReqs() {
		return fwLovDetailReqs;
	}

	public void setFwLovDetailReqs(List<FwLovDetailReq> fwLovDetailReqs) {
		this.fwLovDetailReqs = fwLovDetailReqs;
	}

	public FwLovHReq getFwLovHReq() {
		return fwLovHReq;
	}

	public void setFwLovHReq(FwLovHReq fwLovHReq) {
		this.fwLovHReq = fwLovHReq;
	}

	public String getLovKey() {
		return lovKey;
	}

	public void setLovKey(String lovKey) {
		this.lovKey = lovKey;
	}

}
