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

import aot.water.model.RicWaterInstallationChargeRatesConfig;
import aot.water.service.Water0112Service;
import aot.water.vo.request.Water0112Req;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water0112")
public class Water0112Controller {
	private static final Logger logger = LoggerFactory.getLogger(Water0112Controller.class);
	@Autowired
	private Water0112Service water0112Service;

	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicWaterInstallationChargeRatesConfig> list() {
		DataTableAjax<RicWaterInstallationChargeRatesConfig> response = new DataTableAjax<RicWaterInstallationChargeRatesConfig>();
		List<RicWaterInstallationChargeRatesConfig> installationChargeRatesConfigs = new ArrayList<RicWaterInstallationChargeRatesConfig>();
		try {	
			installationChargeRatesConfigs = water0112Service.list();
			response.setData(installationChargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water0112Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<RicWaterInstallationChargeRatesConfig> listdata(@RequestBody Water0112Req request) {
		ResponseData<RicWaterInstallationChargeRatesConfig> responseData = new ResponseData<RicWaterInstallationChargeRatesConfig>();
		try {
			responseData.setData(water0112Service.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0112Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveWaterInstallation(@RequestBody Water0112Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0112Service.saveWaterInstallation(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0112Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editWaterInstallation(@RequestBody Water0112Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water0112Service.editWaterInstallation(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water0112Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
