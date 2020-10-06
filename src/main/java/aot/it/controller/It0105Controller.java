package aot.it.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.it.service.It0105Service;
import aot.it.vo.request.It0105Req;
import aot.it.vo.response.It0105Res;
import aot.phone.controller.Phone003Controller;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it0105")
public class It0105Controller {

	private static final Logger logger = LoggerFactory.getLogger(Phone003Controller.class);
	

    @Autowired
    private It0105Service it0105Service;
    
    @PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It0105Res>> getListAll(@RequestBody It0105Req request) {
		ResponseData<List<It0105Res>> responseData = new ResponseData<List<It0105Res>>();
		try {
			responseData.setData(it0105Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0105Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It0105Res> findById(@RequestBody It0105Req request) {
		ResponseData<It0105Res> responseData = new ResponseData<It0105Res>();
		try {
			responseData.setData(it0105Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0105Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<It0105Res> save(@RequestBody It0105Req request) {
		ResponseData<It0105Res> responseData = new ResponseData<It0105Res>();
		try {
			it0105Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0105Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
