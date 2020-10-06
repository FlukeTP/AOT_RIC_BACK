package aot.util.ecm.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import aot.util.ecm.domain.ArrayOfNodeInfo;
import aot.util.ecm.domain.CreateRICFolder;
import aot.util.ecm.domain.CreateRICFolderResponse;
import aot.util.ecm.domain.DeleteDoc;
import aot.util.ecm.domain.DeleteDocResponse;
import aot.util.ecm.domain.DownloadDoc;
import aot.util.ecm.domain.DownloadDocResponse;
import aot.util.ecm.domain.GetDocLink;
import aot.util.ecm.domain.GetDocLinkResponse;
import aot.util.ecm.domain.GetDocList;
import aot.util.ecm.domain.GetDocListResponse;
import aot.util.ecm.domain.OTRICSoap;
import aot.util.ecm.domain.UploadDoc;
import aot.util.ecm.domain.UploadDocResponse;

 
@Service
public class ECMService {
    private static final Logger logger = LoggerFactory.getLogger(ECMService.class);
    
    @Autowired
    private OTRICSoap otricSoap;
    
    @Value("${ecm.appkey}")
    String appkey;
    
    public String converDateforECM(Date processDate) {

 	   DateFormat df = new SimpleDateFormat("yyyy/MM"); 
 	   
 	   if(processDate != null) {
 		   return df.format(processDate);
 	   }else {
 		   return df.format(new Date());
 	   }
    }
    
    public CreateRICFolderResponse createFolder(CreateRICFolder req, Date processDate) throws Exception {
    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
    	req.setXPeriod(period);
    	
    	System.out.println(" ######## REQUEST CreateRICFolder ######## ");
    	ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    	String reqLogger = mapper.writeValueAsString(req);
    	System.out.println(reqLogger);
    	
    	CreateRICFolderResponse folderRes  = new CreateRICFolderResponse();
    	String ecmRes = otricSoap.createRICFolder(req.getXappKey()
    													, req.getXAirport()
    													, req.getXCategory()
    													, req.getXPeriod()
    													, req.getXRICNumber());
    	System.out.println(" ######## ecmRes ######## : " + ecmRes );
    	folderRes.setCreateRICFolderResult(ecmRes);
    	return folderRes;
    }
    
    public UploadDocResponse uploadDoc(UploadDoc req , Date processDate) {
    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
    	req.setXPeriod(period);
    	UploadDocResponse uploadRes = new UploadDocResponse();
    	String ecmRes = otricSoap.uploadDoc(req.getXappKey()
			    			, req.getXAirport()
			    			, req.getXCategory()
			    			, req.getXPeriod()
			    			, req.getXRICNumber()
			    			, req.getXdocName()
			    			, req.getXfileName()
			    			, req.getXcontent());
    	uploadRes.setUploadDocResult(ecmRes);
    	return uploadRes;
    }
    
    public DownloadDocResponse downloadDoc(DownloadDoc req ,Date processDate) {
//    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
//    	req.setXPeriod(period);
    	DownloadDocResponse downloadRes = new DownloadDocResponse();
    	byte[] ecmRes = otricSoap.downloadDoc(req.getXappKey()
			    			, req.getXAirport()
			    			, req.getXCategory()
			    			, req.getXPeriod()
			    			, req.getXRICNumber()
			    			, req.getXdocName());
    	downloadRes.setDownloadDocResult(ecmRes);
    	return downloadRes;
    	
    }
    public GetDocLinkResponse getDocLink(GetDocLink req ,Date processDate) {
    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
    	req.setXPeriod(period);
    	
    	GetDocLinkResponse docLikeRes = new GetDocLinkResponse();
    	String ecmRes = otricSoap.getDocLink(req.getXappKey()
			    			, req.getXAirport()
			    			, req.getXCategory()
			    			, req.getXPeriod()
			    			, req.getXRICNumber()
			    			, req.getXdocName());
    	docLikeRes.setGetDocLinkResult(ecmRes);
    	return docLikeRes;
    }
    
    public DeleteDocResponse deleteDoc(DeleteDoc req ,Date processDate) {
    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
    	req.setXPeriod(period);
    	
    	DeleteDocResponse deleteRes = new DeleteDocResponse();
    	String ecmRes = otricSoap.deleteDoc(req.getXappKey()
			    			, req.getXAirport()
			    			, req.getXCategory()
			    			, req.getXPeriod()
			    			, req.getXRICNumber()
			    			, req.getXdocName());
    	deleteRes.setDeleteDocResult(ecmRes);
    	return deleteRes;
    }
    
    public GetDocListResponse getDocList(GetDocList req ,Date processDate) {
    	String period = this.converDateforECM(processDate);
    	req.setXappKey(appkey);
    	req.setXPeriod(period);
    	
    	GetDocListResponse res = new GetDocListResponse();
    	ArrayOfNodeInfo ecmRes = otricSoap.getDocList(req.getXappKey()
    			, req.getXAirport()
    			, req.getXCategory()
    			, req.getXPeriod()
    			, req.getXRICNumber());
    	res.setGetDocListResult(ecmRes);
    	return res;
    }
    
    public static void main(String[] args) throws Exception {
    	CreateRICFolder en = new CreateRICFolder();
    	ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    	String json = mapper.writeValueAsString(en);
    	System.out.println(json);
	}
    
    public String randomName() {
		String dateN = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return dateN+saltStr;

    }
}
