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

import aot.it.service.It004Service;
import aot.it.vo.request.It004Req;
import aot.it.vo.response.It004Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it004")
public class It004Controller {

	private static final Logger logger = LoggerFactory.getLogger(It004Controller.class);

	@Autowired
	private It004Service it004Service;

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody It004Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(it004Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It004Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/sendToSAPCancel")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAPCancel(@RequestBody It004Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(it004Service.sendSapCancel(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It004Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/getList")
	@ResponseBody
	public ResponseData<List<It004Res>> getList(@RequestBody It004Req request) {
		ResponseData<List<It004Res>> responseData = new ResponseData<List<It004Res>>();
		try {
			responseData.setData(it004Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It004Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It004Res> findById(@RequestBody It004Req request) {
		ResponseData<It004Res> responseData = new ResponseData<It004Res>();
		try {
			responseData.setData(it004Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It004Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<It004Res> saveItOther(@RequestBody It004Req request) {
		ResponseData<It004Res> responseData = new ResponseData<It004Res>();
		try {
			it004Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It004Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	
}
