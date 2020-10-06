package aot.it.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.it.service.It008Service;
import aot.it.vo.response.It008Res;
import aot.it.vo.request.It008Req;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it008")
public class It008Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(It008Controller.class);
	
	@Autowired
	private It008Service it008Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<T> save(@RequestBody It008Req request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			it008Service.save(request);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("ItController008:save ", e);
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<It008Res>> findData(@RequestBody It008Req request) {
		ResponseData<List<It008Res>> responseData = new ResponseData<>();
		try {
			responseData.setData(it008Service.findData(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("ItController008::list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get-by-id")
	@ResponseBody
	public ResponseData<It008Res> getById(@RequestBody It008Req request) {
		ResponseData<It008Res> responseData = new ResponseData<>();
		try {
			responseData.setData(it008Service.getById(request.getId()));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("ItController008::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
