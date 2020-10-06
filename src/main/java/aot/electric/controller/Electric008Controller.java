package aot.electric.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.electric.service.Electric008Service;
import aot.electric.vo.request.Electric008Req;
import aot.electric.vo.response.Electric008Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric008")
public class Electric008Controller {

	private static final Logger logger = LoggerFactory.getLogger(Electric008Controller.class);

	@Autowired
	private Electric008Service electric008Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveRateTypeConfig(@RequestBody Electric008Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric008Service.saveRateChargeConfig(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric008Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get")
	@ResponseBody
	public ResponseData<List<Electric008Res>> geRateTypeConfig() {
		logger.info("Get RicElectricChargeTypeConfig");
		ResponseData<List<Electric008Res>> responseData = new ResponseData<List<Electric008Res>>();

		try {
			responseData.setData(electric008Service.findRateTypeConfig());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric008Controller::get ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Electric008Res> getById(@PathVariable("id") String id) {
		ResponseData<Electric008Res> responseData = new ResponseData<Electric008Res>();
		try {
			responseData.setData(electric008Service.getById(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric009Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

//	@PutMapping("/update/{id}")
//	@ResponseBody
//	public ResponseData<String> update(@PathVariable("id") String idStr, @RequestBody Electric008Req request) {
//		ResponseData<String> responseData = new ResponseData<String>();
//		try {
//			electric008Service.update(idStr, request);
//			responseData.setData("SUCCESS");
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
//			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
//		} catch (Exception e) {
//			logger.error("Water009Controller::update ", e);
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
//			responseData.setStatus(RESPONSE_STATUS.FAILED);
//		}
//		return responseData;
//	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> update(@RequestBody Electric008Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric008Service.update(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric008Controller::update ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("/delete-dtl/{id}")
	@ResponseBody
	public ResponseData<String> delete(@PathVariable("id") String idStr) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric008Service.deleteDetail(idStr);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::delete ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
