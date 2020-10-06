package aot.posControl.vo.request;

import org.springframework.web.multipart.MultipartFile;

public class PosUploadRequest {
	private String frequencyNo;
	private MultipartFile fileUpload;
	
	public String getFrequencyNo() {
		return frequencyNo;
	}
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}
	public MultipartFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(MultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	
}
