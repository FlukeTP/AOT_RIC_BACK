package aot.communicate.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.communicate.service.Communicate0062Service;
import aot.communicate.vo.request.Communicate0062Req;
import aot.communicate.vo.response.Communicate0062Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communi0062")
public class Communicate0062Controller {

	private static final Logger logger = LoggerFactory.getLogger(Communicate0062Controller.class);

    @Autowired
    private Communicate0062Service communi0062Service;
    
    @PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Communicate0062Res>> getListAll(@RequestBody Communicate0062Req request) {
		ResponseData<List<Communicate0062Res>> responseData = new ResponseData<List<Communicate0062Res>>();
		try {
			responseData.setData(communi0062Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0062Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Communicate0062Res> findById(@RequestBody Communicate0062Req request) {
		ResponseData<Communicate0062Res> responseData = new ResponseData<Communicate0062Res>();
		try {
			responseData.setData(communi0062Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0062Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Communicate0062Res> save(@RequestBody Communicate0062Req request) {
		ResponseData<Communicate0062Res> responseData = new ResponseData<Communicate0062Res>();
		try {
			communi0062Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0062Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
