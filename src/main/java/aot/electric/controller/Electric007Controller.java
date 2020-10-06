package aot.electric.controller;

import java.util.List;

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

import aot.electric.service.Electric007Service;
import aot.electric.vo.request.Electric007Req;
import aot.electric.vo.response.Electric007Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric007")
public class Electric007Controller {

	private static final Logger logger = LoggerFactory.getLogger(Electric007Controller.class);

	@Autowired
	private Electric007Service electric007Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveRateChargeConfig(@RequestBody Electric007Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric007Service.saveRateChargeConfig(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric007Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get")
	@ResponseBody
	public ResponseData<List<Electric007Res>> getRateChargeConfig() {
		logger.info("Get ric_electric_rate_charge_config");
		ResponseData<List<Electric007Res>> responseData = new ResponseData<List<Electric007Res>>();

		try {
			responseData.setData(electric007Service.findRateChargeConfig());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric007Controller::get ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Electric007Res> getById(@PathVariable("id") String idStr) {
		ResponseData<Electric007Res> responseData = new ResponseData<Electric007Res>();
		try {
			responseData.setData(electric007Service.getById(idStr));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric007Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

//	@PutMapping("/update/{id}")
//	@ResponseBody
//	public ResponseData<String> update(@PathVariable("id") String idStr, @RequestBody Electric007Req request) {
//		ResponseData<String> responseData = new ResponseData<String>();
//		try {
//			electric007Service.updateRateChargeConfig(idStr, request);
//			responseData.setData("SUCCESS");
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
//			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
//		} catch (Exception e) {
//			logger.error("Electric007Controller::update ", e);
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
//			responseData.setStatus(RESPONSE_STATUS.FAILED);
//		}
//		return responseData;
//	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> update(@RequestBody Electric007Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric007Service.updateRateChargeConfig(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric007Controller::update ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
