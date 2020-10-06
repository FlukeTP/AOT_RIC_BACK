package aot.water.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.water.model.RicWaterServiceChargeRatesConfig;
import aot.water.service.Water010Service;
import aot.water.vo.request.Water010Req;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water010")
public class Water010Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water010Controller.class);
	@Autowired
	private Water010Service water010Service ;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterServiceChargeRatesConfig> list() {
		DataTableAjax<RicWaterServiceChargeRatesConfig> response = new DataTableAjax<RicWaterServiceChargeRatesConfig>();
		List<RicWaterServiceChargeRatesConfig> chargeRatesConfigs = new ArrayList<RicWaterServiceChargeRatesConfig>();
		try {	
			chargeRatesConfigs = water010Service.list();
			response.setData(chargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water010Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<RicWaterServiceChargeRatesConfig> listdata(@RequestBody Water010Req request) {
		ResponseData<RicWaterServiceChargeRatesConfig> responseData = new ResponseData<RicWaterServiceChargeRatesConfig>();
		try {
			responseData.setData(water010Service.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water010Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterService(@RequestBody Water010Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water010Service.saveWaterService(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water010Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterService(@RequestBody Water010Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water010Service.editWaterService(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water010Controller::editConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
