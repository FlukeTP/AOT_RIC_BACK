package aot.firebrigade.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.firebrigade.service.Firebrigade002Service;
import aot.firebrigade.vo.request.Firebrigade002Req;
import aot.firebrigade.vo.response.Firebrigade002Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/firebrigade002")
public class Firebrigade002Controller {

	private static final Logger logger = LoggerFactory.getLogger(Firebrigade002Controller.class);

	@Autowired
	private Firebrigade002Service firebrigade002Service;
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Firebrigade002Res>> getListFirebrigadeRateChargeConfig(@RequestBody Firebrigade002Req request) {
		ResponseData<List<Firebrigade002Res>> responseData = new ResponseData<List<Firebrigade002Res>>();
		try {
			responseData.setData(firebrigade002Service.getListFirebrigadeRateChargeConfig(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade002Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_history")
	@ResponseBody
	public ResponseData<List<Firebrigade002Res>> getListFirebrigadeRateChargeConfigHis(@RequestBody Firebrigade002Req request) {
		ResponseData<List<Firebrigade002Res>> responseData = new ResponseData<List<Firebrigade002Res>>();
		try {
			responseData.setData(firebrigade002Service.getListFirebrigadeRateChargeConfigHis(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade002Controller::get history ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Firebrigade002Res> findById(@RequestBody Firebrigade002Req request) {
		ResponseData<Firebrigade002Res> responseData = new ResponseData<Firebrigade002Res>();
		try {
			responseData.setData(firebrigade002Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade002Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Firebrigade002Res> save(@RequestBody Firebrigade002Req request) {
		ResponseData<Firebrigade002Res> responseData = new ResponseData<Firebrigade002Res>();
		try {
			firebrigade002Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
