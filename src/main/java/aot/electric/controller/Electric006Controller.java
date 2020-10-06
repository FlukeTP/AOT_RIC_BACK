package aot.electric.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.electric.model.RicElectricMeter;
import aot.electric.service.Electric006Service;
import aot.electric.vo.request.Elec003FindMeterReq;
import aot.electric.vo.request.Electric006Req;
import aot.electric.vo.response.Electric006Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric006")
public class Electric006Controller {

	private static final Logger logger = LoggerFactory.getLogger(Electric006Controller.class);

	@Autowired
	private Electric006Service electric006Service;

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Electric006Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(electric006Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Electric006Res>> getListReqChange(@RequestBody Electric006Req request) {
		ResponseData<List<Electric006Res>> responseData = new ResponseData<List<Electric006Res>>();
		try {
			responseData.setData(electric006Service.getListReqChange(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_all_meter")
	@ResponseBody
	public ResponseData<List<RicElectricMeter>> getListElectricMeter(@RequestBody Elec003FindMeterReq request) {
		ResponseData<List<RicElectricMeter>> responseData = new ResponseData<List<RicElectricMeter>>();
		try {
			responseData.setData(electric006Service.getListElectricMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::get all meter", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Electric006Res> findReqChangeById(@RequestBody Electric006Req request) {
		ResponseData<Electric006Res> responseData = new ResponseData<Electric006Res>();
		try {
			responseData.setData(electric006Service.findReqChangeById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_meter")
	@ResponseBody
	public ResponseData<Electric006Res> findReqChangeByMeter(@RequestBody Electric006Req request) {
		ResponseData<Electric006Res> responseData = new ResponseData<Electric006Res>();
		try {
			responseData.setData(electric006Service.findReqChangeByMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Electric006Res> saveReqChange(@RequestBody Electric006Req request) {
		ResponseData<Electric006Res> responseData = new ResponseData<Electric006Res>();
		try {
			electric006Service.saveReqChange(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_old_and_new_meter")
	@ResponseBody
	public ResponseData<Electric006Res> getElectricOleAndNewMeter(@RequestBody Electric006Req request) {
		ResponseData<Electric006Res> responseData = new ResponseData<Electric006Res>();
		try {
			responseData.setData(electric006Service.getElectricOleAndNewMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::getElectricOleAndNewMeter ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
