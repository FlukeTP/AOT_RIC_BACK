package aot.water.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.util.sap.domain.response.SapResponse;
import aot.water.service.Water007Service;
import aot.water.vo.request.Water007Req;
import aot.water.vo.response.Water007Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water007")
public class Water007Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water007Controller.class);

	@Autowired
	private Water007Service water007Service;
	
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Water007Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(water007Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric005Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Water007Res>> getListReqCancel(@RequestBody Water007Req request) {
		ResponseData<List<Water007Res>> responseData = new ResponseData<List<Water007Res>>();
		try {
			responseData.setData(water007Service.getListWaterReqCancel(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water007Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Water007Res> findReqCancelById(@RequestBody Water007Req request) {
		ResponseData<Water007Res> responseData = new ResponseData<Water007Res>();
		try {
			responseData.setData(water007Service.findWaterReqCancelById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water007Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Water007Res> saveReqCancel(@RequestBody Water007Req request) {
		ResponseData<Water007Res> responseData = new ResponseData<Water007Res>();
		try {
			water007Service.saveWaterReqCancel(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water007Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
