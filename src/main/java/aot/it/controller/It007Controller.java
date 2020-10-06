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

import aot.garbagedis.vo.request.Garbagedis001Req;
import aot.it.service.It007Service;
import aot.it.vo.request.It008Req;
import aot.it.vo.request.It007Req;
import aot.it.vo.response.It008Res;
import aot.it.vo.response.It007Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it007")
public class It007Controller {

	private static final Logger logger = LoggerFactory.getLogger(It007Controller.class);

	@Autowired
	private It007Service it007Service;
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody List<Long> req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(it007Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It007Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It007Res>> getAll(@RequestBody It007Req request) {
		ResponseData<List<It007Res>> responseData = new ResponseData<List<It007Res>>();
		try {
			responseData.setData(it007Service.getAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It007Controller::getAll ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It008Res> findById(@RequestBody It008Req request) {
		ResponseData<It008Res> responseData = new ResponseData<It008Res>();
		try {
			responseData.setData(it007Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It007Controller::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sync_data")
	@ResponseBody
	public ResponseData<String> syncData(@RequestBody String periodMonth) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			Integer countData = it007Service.syncData(periodMonth);
			responseData.setMessage(RESPONSE_MESSAGE.SYNC_DATA.SUCCESS + " (" + countData + ")");
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It007Controller::syncData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> updateData(@RequestBody It008Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			it007Service.updateData(request);
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It007Controller::updateData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
