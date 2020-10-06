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

import aot.heavyeqp.model.RicHeavyManageEquipmentType;
import aot.heavyeqp.service.Heavyeqp004Service;
import aot.heavyeqp.vo.request.Heavyeqp004Req;
import aot.heavyeqp.vo.request.Heavyeqp004SaveReq;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/heavyeqp004")
public class Heavyeqp004Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp004Controller.class);
	
	@Autowired
	Heavyeqp004Service heavyeqp004Service;
	
	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<RicHeavyManageEquipmentType> list() {
		DataTableAjax<RicHeavyManageEquipmentType> response = new DataTableAjax<RicHeavyManageEquipmentType>();
		List<RicHeavyManageEquipmentType> heavyManageEquipmentType = new ArrayList<RicHeavyManageEquipmentType>();
		try {	
			heavyManageEquipmentType = heavyeqp004Service.getallHeavyEquipments();
			response.setData(heavyManageEquipmentType);
		} catch (Exception e) {
			logger.error("Heavyeqp004Controller : " , e); 
		}
		return response;
	}
	
	@PostMapping("/listEditId")
	@ResponseBody
	public ResponseData<RicHeavyManageEquipmentType> listEditId(@RequestBody Heavyeqp004Req request) {
		ResponseData<RicHeavyManageEquipmentType> responseData = new ResponseData<RicHeavyManageEquipmentType>();
		try {
			responseData.setData(heavyeqp004Service.listEditId(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp004Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveHeavyeqp(@RequestBody Heavyeqp004SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			heavyeqp004Service.saveHeavyeqp(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp004Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editHeavyeqp(@RequestBody Heavyeqp004SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			heavyeqp004Service.editHeavyeqp(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Heavyeqp004Controller::edit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	
}
