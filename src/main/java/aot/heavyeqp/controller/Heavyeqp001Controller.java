package aot.heavyeqp.controller;

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

import aot.heavyeqp.service.Heavyeqp001Service;
import aot.heavyeqp.vo.request.Heavyeqp001Req;
import aot.heavyeqp.vo.request.Heavyeqp001SaveReq;
import aot.heavyeqp.vo.response.Heavyeqp001Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/heavyeqp001")
public class Heavyeqp001Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp001Controller.class);
	
	@Autowired
	private Heavyeqp001Service heavyeqp001Service;
	
	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Heavyeqp001Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();
		try {
			responseData.setData(heavyeqp001Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp001Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<Heavyeqp001Res> list(@RequestBody Heavyeqp001Req req ) {
		DataTableAjax<Heavyeqp001Res> response = new DataTableAjax<Heavyeqp001Res>();
		List<Heavyeqp001Res> ricHeavyEquipmentRevenues = new ArrayList<Heavyeqp001Res>();
		try {	
			ricHeavyEquipmentRevenues = heavyeqp001Service.getHeavyEquipment(req);
			response.setData(ricHeavyEquipmentRevenues);
		} catch (Exception e) {
			logger.error("Heavyeqp001Controller : " , e);
		}
		return response;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveHeavyeqpSC(@RequestBody Heavyeqp001SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			heavyeqp001Service.saveHeavyeqpSC(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp001Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
