package aot.phone.vo.request;

import org.springframework.web.multipart.MultipartFile;

public class Phone001Req {
	private String periodMonth;
	private String concatCode;
	private MultipartFile file;

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getConcatCode() {
		return concatCode;
	}

	public void setConcatCode(String concatCode) {
		this.concatCode = concatCode;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
