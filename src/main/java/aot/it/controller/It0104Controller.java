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

import aot.it.service.It0104Service;
import aot.it.vo.request.It0104Req;
import aot.it.vo.response.It0104Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it0104")
public class It0104Controller {
	private static final Logger logger = LoggerFactory.getLogger(It0104Controller.class);

    @Autowired
    private It0104Service it0104Service;
    
    @PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It0104Res>> getListAll(@RequestBody It0104Req request) {
		ResponseData<List<It0104Res>> responseData = new ResponseData<List<It0104Res>>();
		try {
			responseData.setData(it0104Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0104Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It0104Res> findById(@RequestBody It0104Req request) {
		ResponseData<It0104Res> responseData = new ResponseData<It0104Res>();
		try {
			responseData.setData(it0104Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0104Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<It0104Res> save(@RequestBody It0104Req request) {
		ResponseData<It0104Res> responseData = new ResponseData<It0104Res>();
		try {
			it0104Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0104Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
