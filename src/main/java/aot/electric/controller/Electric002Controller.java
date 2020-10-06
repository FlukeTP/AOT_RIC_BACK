package aot.electric.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import aot.electric.model.RicElectricMeter;
import aot.electric.service.Electric002Service;
import aot.electric.vo.request.Electric002Req;
import aot.electric.vo.request.Electric002SaveReq;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric002")
public class Electric002Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Electric002Controller.class);
	
	@Autowired
	Electric002Service electric002Service;
	
//	@PostMapping("/list")
//	@ResponseBody
//	public Electric002Res list(@RequestBody Electric002Req req ) {
//		return electric002Service.getallElectricMeter(req);
//	}
//	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicElectricMeter> list(@RequestBody Electric002Req req) {
		DataTableAjax<RicElectricMeter> response = new DataTableAjax<RicElectricMeter>();
		List<RicElectricMeter> eleMeterList = new ArrayList<RicElectricMeter>();
		try {	
			eleMeterList = electric002Service.getallElectricMeter(req);
			response.setData(eleMeterList);
		} catch (Exception e) {
			logger.error("Electric002Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/listEditId")
	@ResponseBody
	public ResponseData<RicElectricMeter> listEditId(@RequestBody Electric002Req request) {
		ResponseData<RicElectricMeter> responseData = new ResponseData<RicElectricMeter>();
		try {
			responseData.setData(electric002Service.listEditId(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("UsersController::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveElectricMeter(@RequestBody Electric002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric002Service.saveElectricMeter(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editElectricMeter(@RequestBody Electric002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			electric002Service.editElectricMeter(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric002Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("test")
	@ResponseBody
	public List<String> listtest() {
		List<String> test = Arrays.asList("ele001", "ele002", "ele003");
		return test;
	}
}
