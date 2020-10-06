package baiwa.common.util.paramconfig;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

 
@RestController
@RequestMapping("/api/paramconfig")
public class ParamConfigController {

   @Autowired
   private ParamConfig paramConfig;
   
    @GetMapping("/reload")
    public ResponseData<String> reload( ) throws AuthenticationException {
    	System.out.println(" In ParamConfigController.reload:");
    	paramConfig.reload();
    	Map<String,String> paramMap  =paramConfig.getParamMap();    	
        for (Map.Entry<String,String> tmp : paramMap.entrySet())  {           
            System.out.println(tmp.getKey()+":"+tmp.getValue());
        }
       
        ResponseData<String> dataRes = new ResponseData<String>();
        dataRes.setData("success");
        dataRes.setMessage(RESPONSE_MESSAGE.SUCCESS);
        dataRes.setStatus(RESPONSE_STATUS.SUCCESS);
        return dataRes;
    }

}
