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

import aot.water.model.RicWaterMaintenanceOtherConfig;
import aot.water.service.Water0113Service;
import aot.water.vo.request.Water0113Req;
import aot.water.vo.response.Water0113Res;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water0113")
public class Water0113Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water0113Controller.class);
	
	@Autowired
	private Water0113Service water0113Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterMaintenanceOtherConfig> list() {
		DataTableAjax<RicWaterMaintenanceOtherConfig> response = new DataTableAjax<RicWaterMaintenanceOtherConfig>();
		List<RicWaterMaintenanceOtherConfig> maintenanceChargeRatesConfigs = new ArrayList<RicWaterMaintenanceOtherConfig>();
		try {	
			maintenanceChargeRatesConfigs = water0113Service.list();
			response.setData(maintenanceChargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water0113Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<Water0113Res> listdata(@RequestBody Water0113Req request) {
		ResponseData<Water0113Res> responseData = new ResponseData<Water0113Res>();
		try {
			responseData.setData(water0113Service.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0113Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterMaintenanceOther(@RequestBody Water0113Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0113Service.saveWaterMaintenanceOther(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0113Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterMaintenanceOther(@RequestBody Water0113Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0113Service.editWaterMaintenanceOther(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0113Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
