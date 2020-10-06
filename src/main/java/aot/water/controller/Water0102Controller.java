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

import aot.water.model.RicWaterMaintenanceChargeRatesConfig;
import aot.water.service.Water0102Service;
import aot.water.vo.request.Water0102Req;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water0102")
public class Water0102Controller {
	private static final Logger logger = LoggerFactory.getLogger(Water0102Controller.class);
	@Autowired
	private Water0102Service water0102Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterMaintenanceChargeRatesConfig> list() {
		DataTableAjax<RicWaterMaintenanceChargeRatesConfig> response = new DataTableAjax<RicWaterMaintenanceChargeRatesConfig>();
		List<RicWaterMaintenanceChargeRatesConfig> maintenanceChargeRatesConfigs = new ArrayList<RicWaterMaintenanceChargeRatesConfig>();
		try {	
			maintenanceChargeRatesConfigs = water0102Service.list();
			response.setData(maintenanceChargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water0102Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<RicWaterMaintenanceChargeRatesConfig> listdata(@RequestBody Water0102Req request) {
		ResponseData<RicWaterMaintenanceChargeRatesConfig> responseData = new ResponseData<RicWaterMaintenanceChargeRatesConfig>();
		try {
			responseData.setData(water0102Service.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0102Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterMaintenance(@RequestBody Water0102Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0102Service.saveWaterMaintenance(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0102Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterMaintenance(@RequestBody Water0102Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0102Service.editWaterMaintenance(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0102Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
}
