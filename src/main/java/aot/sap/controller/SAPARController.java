package aot.sap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.sap.service.SAPARService;
import aot.util.sap.domain.request.Header;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.util.paramconfig.ParamConfig;

 
@Controller
@RequestMapping("api/sap")
public class SAPARController {
 
	
	
	 @Autowired
	 private ParamConfig paramConfig;
	 
	 @Autowired
	 private SAPARService sapARService;
	
	@PostMapping("/saveSAPAR") 
	@ResponseBody
	public SapResponse  calSAPAR(@RequestBody Header request) {
		SapResponse  responseData = new SapResponse ();
		try { 			
//			responseData =sapARService.callSAPAR(request);	 			
		} catch (Exception e) {
		 e.printStackTrace();
		}
		return responseData;
	}
}