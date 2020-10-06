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

import aot.it.service.It009Service;
import aot.it.vo.request.It009Req;
import aot.it.vo.response.It009Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it009")
public class It009Controller {

	private static final Logger logger = LoggerFactory.getLogger(It009Controller.class);

	@Autowired
	private It009Service it009Service;

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It009Res>> getListFirebrigadeManage(@RequestBody It009Req request) {
		ResponseData<List<It009Res>> responseData = new ResponseData<List<It009Res>>();
		try {
			responseData.setData(it009Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It009Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It009Res> findById(@RequestBody It009Req request) {
		ResponseData<It009Res> responseData = new ResponseData<It009Res>();
		try {
			responseData.setData(it009Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It009Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<It009Res> saveFirebrigadeManage(@RequestBody It009Req request) {
		ResponseData<It009Res> responseData = new ResponseData<It009Res>();
		try {
			it009Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It009Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody It009Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(it009Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It009Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
