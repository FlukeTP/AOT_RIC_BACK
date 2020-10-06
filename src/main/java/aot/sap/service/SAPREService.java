package aot.sap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aot.common.constant.RICConstants;
import aot.sap.repository.jpa.SapRicControlRepository;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.SapRERequest;
import aot.util.sap.domain.response.SapREResponse;
import aot.util.sap.domain.response.SapREResponseSuccess;
import baiwa.common.util.paramconfig.ParamConfig;

@Service
public class SAPREService {
	
	//test
	@Autowired
	private ParamConfig paramConfig;
	 
	@Autowired
	private SapRicControlRepository sapRicControlRepository;
	 
	@Value("${ws.username}")
	private String username;
	@Value("${ws.password}")
	private String password;
	
	public SapREResponse callSAPRE( SapRERequest request) {
		SapREResponse  responseData = new SapREResponse();		 
		try {
			  
			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);			 
//			String json = mapper.writeValueAsString(request);
//			json = json.replace("\"header\"", "\"Header\"");
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String json = gson.toJson(request);
			
			System.out.println(" ################# JSON From Client Befor Call SAP #####");			
			System.out.println(json);
			 
			
		    final String uri = paramConfig.getParamConfig(RICConstants.SAP_RE_REST_URL);
			System.out.println(" ################# SAP_RE_REST_URL:"+uri);
			
			
		    RestTemplate restTemplate = new RestTemplate(); 
		    HttpHeaders headers = new HttpHeaders();		     
		    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		   // HttpEntity<ArRequest> requestcalSAP = new HttpEntity<>(request, headers); 
		    
	        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password)); 
	 
	        boolean isSuccess =true;
		   
	        String  rawJsonReturnFromSAP = null;
		    try {
		    	System.out.println(" ##  Before Call Case 1 ####");
		    	 rawJsonReturnFromSAP =	restTemplate.postForObject(uri, json, String.class); 
		    	 System.out.println(" ## After Call Case 1  rawJsonReturnFromSAP 1: "+rawJsonReturnFromSAP);
		    }catch(Exception ex) {
		    	 
		    	ex.printStackTrace();
		    	
		    	throw new Exception();
		    }
		    
//	        String  rawJsonReturnFromSAP2 = null;
//		    try {
//		    	System.out.println(" ##  Before Call Case 2 ####");
//		    	rawJsonReturnFromSAP2 =	restTemplate.postForObject(uri, requestcalSAP, String.class); 
//		    	 System.out.println(" ## After Call Case2  rawJsonReturnFromSAP 2: "+rawJsonReturnFromSAP2);
//		    }catch(Exception ex) {
//		    	ex.printStackTrace();
//		    } 
		    ObjectMapper mapper2 = new ObjectMapper();		    
		   
		    SapREResponseSuccess successObj  = null;
//		    SAPARResponseFail failObj = null;

		    try {
		    	System.out.println(" ################# Try to Map Success object :"); 
		    	//successObj= mapper2.readValue(rawJsonReturnFromSAP, SAPARResponseSuccess.class);
		    	successObj = gson.fromJson(rawJsonReturnFromSAP, SapREResponseSuccess.class);
		    } catch (  Exception ex) {  
		    	System.out.println(" ################# Try to Map Fail object :"); 
		    	ex.printStackTrace();
		    	isSuccess = false;
		    } 
		   
		  
		    
		    String jsonresponse = mapper.writeValueAsString(rawJsonReturnFromSAP);
		    System.out.println(" ################# JSON Response From SAP :"+jsonresponse); 		  
		    if(isSuccess) {
		    	responseData.setStatus(SAPConstants.SAP_SUCCESS);
		    	responseData.setSapREResponseSuccess(successObj);
		    	  System.out.println(" ################# Parse Result successObj :"+successObj);
		    }else {
		    	responseData.setStatus(SAPConstants.SAP_FAIL);
		    	 System.out.println(" ################# Parse Result failObj :");
		    }
		    
		    responseData.setRawJsonStringFromSAP(rawJsonReturnFromSAP);
 
		} catch (Exception e) {
			 System.out.println(" #################  Exception in call SAP Network:"); 
			
			responseData.setStatus(SAPConstants.SAP_CONNECTION_FAIL);
			
		 e.printStackTrace();
		}
		return responseData;
	}

}
