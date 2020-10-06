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

import aot.garbagedis.service.Garbagedis002Service;
import aot.garbagedis.vo.request.Garbagedis002HdrReq;
import aot.garbagedis.vo.response.Garbagedis002HdrRes;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/garbagedis002")
public class Garbagedis002Controller {

	private static final Logger logger = LoggerFactory.getLogger(Garbagedis002Controller.class);

	@Autowired
	private Garbagedis002Service garbagedis002Service;
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Garbagedis002HdrReq req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(garbagedis002Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp001Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Garbagedis002HdrRes>> getAll(@RequestBody Garbagedis002HdrReq request) {
		ResponseData<List<Garbagedis002HdrRes>> responseData = new ResponseData<List<Garbagedis002HdrRes>>();
		try {
			responseData.setData(garbagedis002Service.getAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Garbagedis002HdrRes> findById(@RequestBody Garbagedis002HdrReq request) {
		ResponseData<Garbagedis002HdrRes> responseData = new ResponseData<Garbagedis002HdrRes>();
		try {
			responseData.setData(garbagedis002Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis002Controller::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> save(@RequestBody Garbagedis002HdrReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis002Service.save(request);
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
