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

import aot.communicate.service.Communicate0061Service;
import aot.communicate.vo.request.Communicate0061Req;
import aot.communicate.vo.response.Communicate0061Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communi0061")
public class Communicate0061Controller {
	private static final Logger logger = LoggerFactory.getLogger(Communicate0061Controller.class);

    @Autowired
    private Communicate0061Service communi0061Service;
    
    @PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Communicate0061Res>> getListAll(@RequestBody Communicate0061Req request) {
		ResponseData<List<Communicate0061Res>> responseData = new ResponseData<List<Communicate0061Res>>();
		try {
			responseData.setData(communi0061Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0061Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Communicate0061Res> findById(@RequestBody Communicate0061Req request) {
		ResponseData<Communicate0061Res> responseData = new ResponseData<Communicate0061Res>();
		try {
			responseData.setData(communi0061Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0061Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Communicate0061Res> save(@RequestBody Communicate0061Req request) {
		ResponseData<Communicate0061Res> responseData = new ResponseData<Communicate0061Res>();
		try {
			communi0061Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0061Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
