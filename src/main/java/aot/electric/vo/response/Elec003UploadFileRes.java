package aot.electric.vo.response;

public class Elec003UploadFileRes {

	private Long reqFileId;
	private String reqFileName;
	private String reqFileExtension;
	private String createdDate;
	
	public Long getReqFileId() {
		return reqFileId;
	}
	public void setReqFileId(Long reqFileId) {
		this.reqFileId = reqFileId;
	}
	public String getReqFileName() {
		return reqFileName;
	}
	public void setReqFileName(String reqFileName) {
		this.reqFileName = reqFileName;
	}
	public String getReqFileExtension() {
		return reqFileExtension;
	}
	public void setReqFileExtension(String reqFileExtension) {
		this.reqFileExtension = reqFileExtension;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
