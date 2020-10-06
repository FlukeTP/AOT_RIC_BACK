package aot.util.sap.domain.response;

import aot.util.sap.domain.request.Header;
 
public class SapREResponse {
    private String status; 
    private String message;
    private String messageType; 
    
    private SapREResponseSuccess sapREResponseSuccess;
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
	
	public String getRawJsonStringFromSAP() {
		return rawJsonStringFromSAP;
	}

	public void setRawJsonStringFromSAP(String rawJsonStringFromSAP) {
		this.rawJsonStringFromSAP = rawJsonStringFromSAP;
	}

	public SapREResponseSuccess getSapREResponseSuccess() {
		return sapREResponseSuccess;
	}

	public void setSapREResponseSuccess(SapREResponseSuccess sapREResponseSuccess) {
		this.sapREResponseSuccess = sapREResponseSuccess;
	}

 
    
}
