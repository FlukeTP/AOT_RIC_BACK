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

import aot.it.service.It0101Service;
import aot.it.vo.request.It0101Req;
import aot.it.vo.response.It0101Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it0101")
public class It0101Controller {
	private static final Logger logger = LoggerFactory.getLogger(It0101Controller.class);

    @Autowired
    private It0101Service it0101Service;
    
    @PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It0101Res>> getListAll(@RequestBody It0101Req request) {
		ResponseData<List<It0101Res>> responseData = new ResponseData<List<It0101Res>>();
		try {
			responseData.setData(it0101Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0101Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It0101Res> findById(@RequestBody It0101Req request) {
		ResponseData<It0101Res> responseData = new ResponseData<It0101Res>();
		try {
			responseData.setData(it0101Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0101Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<It0101Res> save(@RequestBody It0101Req request) {
		ResponseData<It0101Res> responseData = new ResponseData<It0101Res>();
		try {
			it0101Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It0101Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
