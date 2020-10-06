package aot.water.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.water.model.RicWaterMeter;
import aot.water.service.Water002Service;
import aot.water.vo.request.Water002Req;
import aot.water.vo.request.Water002SaveReq;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water002")
public class Water002Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Water002Controller.class);
	
	@Autowired
	Water002Service water002Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterMeter> list(@RequestBody Water002Req req ) {
		DataTableAjax<RicWaterMeter> response = new DataTableAjax<RicWaterMeter>();
		List<RicWaterMeter> waterMeterList = new ArrayList<RicWaterMeter>();
		try {	
			waterMeterList = water002Service.getallWaterMeter(req);
			response.setData(waterMeterList);
		} catch (Exception e) {
			logger.error("Electric002Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listEditId")
	@ResponseBody
	public ResponseData<RicWaterMeter> listEditId(@RequestBody Water002Req req) {
		ResponseData<RicWaterMeter> responseData = new ResponseData<RicWaterMeter>();
		try {
			responseData.setData(water002Service.listEditId(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric002Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterMeter(@RequestBody Water002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water002Service.saveWaterMeter(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterMeter(@RequestBody Water002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water002Service.editWaterMeter(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water002Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("test")
	@ResponseBody
	public List<String> listtest() {
		List<String> test = Arrays.asList("wat001", "wat002", "wat003");
		return test;
	}
}
