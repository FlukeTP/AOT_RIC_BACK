package aot.electric.vo.request;

import org.springframework.web.multipart.MultipartFile;

public class Elec003UploadFileReq {
	
	private String reqFileId;
	private String reqId;
	private MultipartFile file;

	public String getReqFileId() {
		return reqFileId;
	}

	public void setReqFileId(String reqFileId) {
		this.reqFileId = reqFileId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
