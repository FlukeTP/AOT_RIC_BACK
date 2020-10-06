package aot.garbagedis.controller;

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

import aot.garbagedis.model.RicTrashSize;
import aot.garbagedis.model.RicTrashSizeServiceFee;
import aot.garbagedis.service.Garbagedis003Service;
import aot.garbagedis.vo.request.Garbagedis003SaveFeeReq;
import aot.garbagedis.vo.request.Garbagedis003SaveReq;
import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/garbagedis003")
public class Garbagedis003Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Garbagedis003Controller.class);
	
	@Autowired
	private Garbagedis003Service garbagedis003Service;
	
	@PostMapping("/list-trash-size")
	@ResponseBody
	public DataTableAjax<RicTrashSize> listTrashSize() {
		DataTableAjax<RicTrashSize> response = new DataTableAjax<RicTrashSize>();
		List<RicTrashSize> trashSizes = new ArrayList<RicTrashSize>();
		try {	
			trashSizes = garbagedis003Service.getlistTrashSize();
			response.setData(trashSizes);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller listTrashSize : " , e); 
		}
		return response;
	}
	
	@PostMapping("/list-trash-fee")
	@ResponseBody
	public DataTableAjax<RicTrashSizeServiceFee> listTrashFee() {
		DataTableAjax<RicTrashSizeServiceFee> response = new DataTableAjax<RicTrashSizeServiceFee>();
		List<RicTrashSizeServiceFee> trashSizeServiceFees = new ArrayList<RicTrashSizeServiceFee>();
		try {	
			trashSizeServiceFees = garbagedis003Service.getlistTrashFee();
			response.setData(trashSizeServiceFees);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller listTrashFee : " , e); 
		}
		return response;
	}
	
	@PostMapping("/save-trash-size")
	@ResponseBody
	public ResponseData<String> saveTrashSize(@RequestBody Garbagedis003SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis003Service.saveTrashSize(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller::saveTrashSize ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save-trash-fee")
	@ResponseBody
	public ResponseData<String> saveTrashFee(@RequestBody Garbagedis003SaveFeeReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis003Service.saveTrashFee(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller::saveTrashFee ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit-trash-size")
	@ResponseBody
	public ResponseData<String> editTrashSize(@RequestBody Garbagedis003SaveReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis003Service.editTrashSize(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller::editTrashSize ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/edit-trash-fee")
	@ResponseBody
	public ResponseData<String> editTrashfee(@RequestBody Garbagedis003SaveFeeReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			garbagedis003Service.editTrashfee(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Garbagedis003Controller::editTrashfee ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
}
