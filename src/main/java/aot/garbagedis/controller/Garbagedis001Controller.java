package aot.garbagedis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.garbagedis.service.Garbagedis001Service;
import aot.garbagedis.vo.request.Garbagedis001Req;
import aot.garbagedis.vo.request.Garbagedis002HdrReq;
import aot.garbagedis.vo.response.Garbagedis001Res;
import aot.garbagedis.vo.response.Garbagedis002HdrRes;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/garbagedis001")
public class Garbagedis001Controller {

	private static final Logger logger = LoggerFactory.getLogger(Garbagedis001Controller.class);

	@Autowired
	private Garbagedis001Service garbagedis001Service;
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody List<Long> req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(garbagedis001Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis001Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Garbagedis001Res>> getAll(@RequestBody Garbagedis001Req request) {
		ResponseData<List<Garbagedis001Res>> responseData = new ResponseData<List<Garbagedis001Res>>();
		try {
			responseData.setData(garbagedis001Service.getAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis001Controller::getAll ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Garbagedis002HdrRes> findById(@RequestBody Garbagedis001Req request) {
		ResponseData<Garbagedis002HdrRes> responseData = new ResponseData<Garbagedis002HdrRes>();
		try {
			responseData.setData(garbagedis001Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis001Controller::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sync_data")
	@ResponseBody
	public ResponseData<String> syncData(@RequestBody Garbagedis001Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis001Service.syncData();
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis001Controller::syncData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> updateData(@RequestBody Garbagedis002HdrReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis001Service.updateData(request);
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis001Controller::updateData ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
