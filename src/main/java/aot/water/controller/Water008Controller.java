package aot.water.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.util.sap.domain.response.SapResponse;
import aot.water.model.RicWaterMeter;
import aot.water.service.Water008Service;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water008Req;
import aot.water.vo.response.Water008Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water008")
public class Water008Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water008Controller.class);

	@Autowired
	private Water008Service water008Service;

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Water008Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(water008Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Water008Res>> getListWaterReqChange(@RequestBody Water008Req request) {
		ResponseData<List<Water008Res>> responseData = new ResponseData<List<Water008Res>>();
		try {
			responseData.setData(water008Service.getListWaterReqChange(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_all_meter")
	@ResponseBody
	public ResponseData<List<RicWaterMeter>> getListWaterMeter(@RequestBody Water003FindMeterReq request) {
		ResponseData<List<RicWaterMeter>> responseData = new ResponseData<List<RicWaterMeter>>();
		try {
			responseData.setData(water008Service.getListWaterMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::get all meter", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Water008Res> findWaterReqChangeById(@RequestBody Water008Req request) {
		ResponseData<Water008Res> responseData = new ResponseData<Water008Res>();
		try {
			responseData.setData(water008Service.findWaterReqChangeById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_meter")
	@ResponseBody
	public ResponseData<Water008Res> findWaterReqChangeByMeter(@RequestBody Water008Req request) {
		ResponseData<Water008Res> responseData = new ResponseData<Water008Res>();
		try {
			responseData.setData(water008Service.findWaterReqChangeByMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Water008Res> saveWaterReqChange(@RequestBody Water008Req request) {
		ResponseData<Water008Res> responseData = new ResponseData<Water008Res>();
		try {
			water008Service.saveWaterReqChange(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_old_and_new_meter")
	@ResponseBody
	public ResponseData<Water008Res> getWaterOleAndNewMeter(@RequestBody Water008Req request) {
		ResponseData<Water008Res> responseData = new ResponseData<Water008Res>();
		try {
			responseData.setData(water008Service.getWaterOleAndNewMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water008Controller::getWaterOleAndNewMeter ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
