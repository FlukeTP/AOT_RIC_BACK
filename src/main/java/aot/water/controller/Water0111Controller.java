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

import aot.water.model.RicWaterInsuranceChargeRatesConfig;
import aot.water.service.Water0111Service;
import aot.water.vo.request.Water0111Req;
import aot.water.vo.response.Water0111Res;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water0111")
public class Water0111Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water0111Controller.class);
	
	@Autowired
	private Water0111Service water0111Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterInsuranceChargeRatesConfig> list() {
		DataTableAjax<RicWaterInsuranceChargeRatesConfig> response = new DataTableAjax<RicWaterInsuranceChargeRatesConfig>();
		List<RicWaterInsuranceChargeRatesConfig> maintenanceChargeRatesConfigs = new ArrayList<RicWaterInsuranceChargeRatesConfig>();
		try {	
			maintenanceChargeRatesConfigs = water0111Service.list();
			response.setData(maintenanceChargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water0111Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<Water0111Res> listdata(@RequestBody Water0111Req request) {
		ResponseData<Water0111Res> responseData = new ResponseData<Water0111Res>();
		try {
			responseData.setData(water0111Service.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0111Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterInsurance(@RequestBody Water0111Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0111Service.saveWaterInsurance(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0111Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterInsurance(@RequestBody Water0111Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0111Service.editWaterInsurance(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0111Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
