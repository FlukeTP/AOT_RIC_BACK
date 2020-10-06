package aot.water.controller;

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

import aot.water.service.Water0114Service;
import aot.water.vo.request.Water0114Req;
import aot.water.vo.response.Water0114Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water0114")
public class Water0114Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water0114Controller.class);

	@Autowired
	private Water0114Service serWater0114Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> save(@RequestBody Water0114Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			serWater0114Service.save(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0114Controller:save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get")
	@ResponseBody
	public ResponseData<List<Water0114Res>> findData() {
		logger.info("Get RicWaterWasteConfig");
		ResponseData<List<Water0114Res>> responseData = new ResponseData<List<Water0114Res>>();

		try {
			responseData.setData(serWater0114Service.findDataList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0114Controller::get ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Water0114Res> getById(@PathVariable("id") String idStr) {
		ResponseData<Water0114Res> responseData = new ResponseData<Water0114Res>();
		try {
			responseData.setData(serWater0114Service.getById(idStr));
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0114Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

//	@PutMapping("/update/{id}")
//	@ResponseBody
//	public ResponseData<String> update(@PathVariable("id") String idStr, @RequestBody Water0114Req request) {
//		ResponseData<String> responseData = new ResponseData<String>();
//		try {
//			serWater0114Service.update(idStr, request);
//			responseData.setData("SUCCESS");
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
//			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
//		} catch (Exception e) {
//			logger.error("Water0114Controller::update ", e);
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
//			responseData.setStatus(RESPONSE_STATUS.FAILED);
//		}
//		return responseData;
//	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> update(@RequestBody Water0114Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			serWater0114Service.update(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0114Controller:update ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
