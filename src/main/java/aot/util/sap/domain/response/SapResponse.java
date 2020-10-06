package aot.util.sap.domain.response;

import aot.util.sap.domain.request.Header;
 
public class SapResponse {
    private String status; 
    private String message;
    private String messageType;    
    private  Header  header;
    
    private SAPARResponseSuccess sapARResponseSuccess;
    private SAPARResponseFail sapARResponseFail;
    private String rawJsonStringFromSAP;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public SAPARResponseSuccess getSapARResponseSuccess() {
		return sapARResponseSuccess;
	}

	public void setSapARResponseSuccess(SAPARResponseSuccess sapARResponseSuccess) {
		this.sapARResponseSuccess = sapARResponseSuccess;
	}

	public SAPARResponseFail getSapARResponseFail() {
		return sapARResponseFail;
	}

	public void setSapARResponseFail(SAPARResponseFail sapARResponseFail) {
		this.sapARResponseFail = sapARResponseFail;
	}

	public String getRawJsonStringFromSAP() {
		return rawJsonStringFromSAP;
	}

	public void setRawJsonStringFromSAP(String rawJsonStringFromSAP) {
		this.rawJsonStringFromSAP = rawJsonStringFromSAP;
	}

 
    
}
