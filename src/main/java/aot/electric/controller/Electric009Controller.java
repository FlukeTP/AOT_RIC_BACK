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

import aot.electric.service.Electric009Service;
import aot.electric.vo.request.Electric009Req;
import aot.electric.vo.response.Electric009HistoryRes;
import aot.electric.vo.response.Electric009Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("api/electric009")
public class Electric009Controller {

	public static final String ELECTRIC009 = "electric009";

	private static final Logger logger = LoggerFactory.getLogger(Electric009Controller.class);

	@Autowired
	private Electric009Service electric009Service;

	@PostMapping("/save")
	@ApiOperation(tags = ELECTRIC009, value = "Save system_cal_config")
	@ResponseBody
	public ResponseData<Electric009Res> saveCalConfig(@RequestBody Electric009Req request) {
		ResponseData<Electric009Res> responseData = new ResponseData<Electric009Res>();
		try {
			electric009Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric009Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get")
	@ApiOperation(tags = ELECTRIC009, value = "Get system_cal_config")
	@ResponseBody
	public ResponseData<List<Electric009Res>> getCalConfig() {
		logger.info("Get system_cal_config");
		ResponseData<List<Electric009Res>> responseData = new ResponseData<List<Electric009Res>>();

		try {
			responseData.setData(electric009Service.findCalConfig());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric009Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ApiOperation(tags = ELECTRIC009, value = "Get system_cal_config by Id")
	@ResponseBody
	public ResponseData<Electric009Res> getById(@PathVariable("id") String idStr) {
		ResponseData<Electric009Res> responseData = new ResponseData<Electric009Res>();
		try {
			responseData.setData(electric009Service.getById(idStr));
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric009Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get-history")
	@ResponseBody
	public ResponseData<List<Electric009HistoryRes>> getHistoryByCode(@RequestBody Electric009Req request) {
		logger.info("Get CalHistory by Code: {}", request.getCodeType());
		ResponseData<List<Electric009HistoryRes>> responseData = new ResponseData<List<Electric009HistoryRes>>();

		try {
			responseData.setData(electric009Service.getHistoryByCode(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric009Controller::getHistoryByCode ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
