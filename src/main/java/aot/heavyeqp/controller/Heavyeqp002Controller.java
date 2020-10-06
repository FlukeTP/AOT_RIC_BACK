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

import aot.heavyeqp.model.RicManageHeavyEquipment;
import aot.heavyeqp.service.Heavyeqp002Service;
import aot.heavyeqp.vo.request.Heavyeqp002Req;
import aot.heavyeqp.vo.request.Heavyeqp002SaveReq;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/heavyeqp002")
public class Heavyeqp002Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp002Controller.class);
	
	@Autowired
	Heavyeqp002Service heavyeqp002Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicManageHeavyEquipment> list() {
		DataTableAjax<RicManageHeavyEquipment> response = new DataTableAjax<RicManageHeavyEquipment>();
		List<RicManageHeavyEquipment> manageHeavyEquipments = new ArrayList<RicManageHeavyEquipment>();
		try {	
			manageHeavyEquipments = heavyeqp002Service.getallHeavyEquipments();
			response.setData(manageHeavyEquipments);
		} catch (Exception e) {
			logger.error("Heavyeqp002Controller : " , e); 
		}
		return response;
	}
	
	@PostMapping("/listEditId")
	@ResponseBody
	public ResponseData<RicManageHeavyEquipment> listEditId(@RequestBody Heavyeqp002Req request) {
		ResponseData<RicManageHeavyEquipment> responseData = new ResponseData<RicManageHeavyEquipment>();
		try {
			responseData.setData(heavyeqp002Service.listEditId(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp002Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveHeavyeqp(@RequestBody Heavyeqp002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			heavyeqp002Service.saveHeavyeqp(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editHeavyeqp(@RequestBody Heavyeqp002SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			heavyeqp002Service.editHeavyeqp(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp002Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	
}
