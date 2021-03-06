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
import aot.it.service.It011Service;
import aot.it.vo.request.It001Req;
import aot.it.vo.request.It011Req;
import aot.it.vo.response.It001Res;
import aot.it.vo.response.It011Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/it011")
public class It011Controller {

	private static final Logger logger = LoggerFactory.getLogger(It011Controller.class);

	@Autowired
	private It011Service it011Service;
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody List<Long> req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(it011Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It011Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<It011Res>> getAll(@RequestBody It011Req request) {
		ResponseData<List<It011Res>> responseData = new ResponseData<List<It011Res>>();
		try {
			responseData.setData(it011Service.getAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It011Controller::getAll ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<It001Res> findById(@RequestBody It001Req request) {
		ResponseData<It001Res> responseData = new ResponseData<It001Res>();
		try {
			responseData.setData(it011Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It011Controller::findById ", e);
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
			Integer countData = it011Service.syncData(periodMonth);
			responseData.setMessage(RESPONSE_MESSAGE.SYNC_DATA.SUCCESS + " (" + countData + ")");
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It011Controller::syncData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> updateData(@RequestBody It001Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			it011Service.updateData(request);
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("It011Controller::updateData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
