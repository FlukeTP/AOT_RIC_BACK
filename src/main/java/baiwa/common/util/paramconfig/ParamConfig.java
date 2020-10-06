package baiwa.common.util.paramconfig;

 
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import baiwa.module.model.SystemConstant;
import baiwa.module.service.SysConstantService;
import baiwa.module.vo.request.SysConstantReq;

@Component
public class ParamConfig {
	
	@Autowired
	private SysConstantService sysConstantService;
	
	private HashMap<String,String> paramMap = new HashMap<String,String>();	
	public String getParamConfig(String paramKey) {		  
		  return paramMap.get(paramKey);		
	}
	public HashMap<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	 
	   @PostConstruct
	    public void init(){
	        System.out.println(" ################ in ParamConfig PostConstruct ############ ");
	        List<SystemConstant> lisConstant =sysConstantService.list(new SysConstantReq());
	        
	        for(SystemConstant tmp:lisConstant) {
	        	 System.out.println(tmp.getConstantKey()+":"+tmp.getConstantValue());
	        	 paramMap.put(tmp.getConstantKey(), tmp.getConstantValue());
	        }
	    }
	   
	   
	    public void reload(){
	        System.out.println(" ################ in ParamConfig Reload ############ ");
	        List<SystemConstant> lisConstant =sysConstantService.list(new SysConstantReq());
	        
	        for(SystemConstant tmp:lisConstant) {	        	
	        	 paramMap.put(tmp.getConstantKey(), tmp.getConstantValue());
	        }
	    }

}
