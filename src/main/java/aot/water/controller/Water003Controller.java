package aot.water.controller;

import java.util.ArrayList;
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

import aot.util.sap.domain.response.SapResponseVo;
import aot.water.model.RicWaterMaintenanceOtherConfig;
import aot.water.model.RicWaterMeter;
import aot.water.model.RicWaterReq;
import aot.water.service.Water003Service;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water003Req;
import aot.water.vo.request.Water003SaveVo;
import aot.water.vo.response.Water003ConfigRes;
import aot.water.vo.response.Water003DetailRes;
import aot.water.vo.response.Water003Res;
import aot.water.vo.response.Water003WaterSizeRes;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water003")
public class Water003Controller {
	private static final Logger logger = LoggerFactory.getLogger(Water003Controller.class);

	@Autowired
	private Water003Service water003Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<RicWaterReq> save(@RequestBody Water003SaveVo form) {
		ResponseData<RicWaterReq> responseData = new ResponseData<RicWaterReq>();
		try {
			responseData.setData(water003Service.save(form));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_meter")
	@ResponseBody
	public ResponseData<List<RicWaterMeter>> getWaterMeter(@RequestBody Water003FindMeterReq request) {
		ResponseData<List<RicWaterMeter>> responseData = new ResponseData<List<RicWaterMeter>>();
		try {
			responseData.setData(water003Service.getListWaterMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water003Controller::get all meter", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/findWaterList")
	@ResponseBody
	public ResponseData<List<Water003Res>> findWaterList(@RequestBody Water003Req req) {
		ResponseData<List<Water003Res>> responseData = new ResponseData<>();
		try {
			responseData.setData(water003Service.findWaterList(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get_water_size")
	@ResponseBody
	public ResponseData<List<Water003WaterSizeRes>> getWaterMeterSize() {
		ResponseData<List<Water003WaterSizeRes>> responseData = new ResponseData<List<Water003WaterSizeRes>>();
		try {
			responseData.setData(water003Service.getWaterMeterSize());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water003Controller::getWaterMeterSize", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/getRateConfig")
	@ResponseBody
	public ResponseData<Water003ConfigRes> getRateConfig(@RequestBody String size) {
		ResponseData<Water003ConfigRes> responseData = new ResponseData<Water003ConfigRes>();
		try {
			responseData.setData(water003Service.getRateConfig(size));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water003Controller::getWaterMeterSize", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	// @PostMapping("/getRateChargeConfig")
	// @ResponseBody
	// public ResponseData<List<Electric007Res>> getRateChargeConfig(@RequestBody
	// Elec003ConfigReq form) {
	// logger.info("Get getRateChargeConfig");
	// ResponseData<List<Electric007Res>> responseData = new
	// ResponseData<List<Electric007Res>>();
	// try {
	// responseData.setData(water003Service.findRateChargeConfig(form));
	// responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
	// responseData.setStatus(RESPONSE_STATUS.SUCCESS);
	// } catch (Exception e) {
	// logger.error("Water003Controller::getRateChargeConfig ", e);
	// responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
	// responseData.setStatus(RESPONSE_STATUS.FAILED);
	// }
	// return responseData;
	// }

	@GetMapping("/listOrther")
	@ResponseBody
	public DataTableAjax<RicWaterMaintenanceOtherConfig> listOrther() {
		DataTableAjax<RicWaterMaintenanceOtherConfig> response = new DataTableAjax<RicWaterMaintenanceOtherConfig>();
		List<RicWaterMaintenanceOtherConfig> maintenanceChargeRatesConfigs = new ArrayList<RicWaterMaintenanceOtherConfig>();
		try {
			maintenanceChargeRatesConfigs = water003Service.list();
			response.setData(maintenanceChargeRatesConfigs);
		} catch (Exception e) {
			logger.error("Water003Controller : listOrther", e);
		}
		return response;
	}

	@PostMapping("/send_sap")
	@ResponseBody
	public ResponseData<SapResponseVo> sendSap(@RequestBody Water003Req request) {
		ResponseData<SapResponseVo> responseData = new ResponseData<>();
		try {
			responseData.setData(water003Service.sendSap(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("water003Controller::sendSap ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-detail/{id}")
	@ResponseBody
	public ResponseData<Water003DetailRes> getDetail(@PathVariable("id") Long id) {
		ResponseData<Water003DetailRes> responseData = new ResponseData<>();
		try {
			responseData.setData(water003Service.getDetail(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::getDetail ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
