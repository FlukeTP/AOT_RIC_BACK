package aot.water.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.util.sap.domain.response.SapResponse;
import aot.water.service.Water009Service;
import aot.water.vo.request.Water009Req;
import aot.water.vo.response.Water009DtlRes;
import aot.water.vo.response.Water009Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/water009")
public class Water009Controller {

	private static final Logger logger = LoggerFactory.getLogger(Water009Controller.class);

	@Autowired
	private Water009Service water009Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Water009Req> save(@RequestBody Water009Req request) {
		ResponseData<Water009Req> responseData = new ResponseData<Water009Req>();
		try {
			responseData.setData(water009Service.save(request));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water009Controller:save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<Water009Res>> list() {
		ResponseData<List<Water009Res>> responseData = new ResponseData<List<Water009Res>>();

		try {
			responseData.setData(water009Service.findData());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water009Controller : list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Water009Res> getById(@PathVariable("id") String idStr) {
		ResponseData<Water009Res> responseData = new ResponseData<Water009Res>();
		try {
			responseData.setData(water009Service.getById(idStr));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water009Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-dtl-by-id/{id}")
	@ResponseBody
	public ResponseData<List<Water009DtlRes>> getByDtlId(@PathVariable("id") String idStr) {
		ResponseData<List<Water009DtlRes>> responseData = new ResponseData<List<Water009DtlRes>>();
		try {
			responseData.setData(water009Service.findDtlById(idStr));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water009Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

//	@PutMapping("/update/{id}")
//	@ResponseBody
//	public ResponseData<String> update(@PathVariable("id") String idStr, @RequestBody Water009Req request) {
//		ResponseData<String> responseData = new ResponseData<String>();
//		try {
//			water009Service.update(idStr, request);
//			responseData.setData("SUCCESS");
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
//			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
//		} catch (Exception e) {
//			logger.error("Water009Controller::update ", e);
//			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
//			responseData.setStatus(RESPONSE_STATUS.FAILED);
//		}
//		return responseData;
//	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseData<String> update(@RequestBody Water009Req request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water009Service.update(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Water009Controller:update ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("/delete-dtl/{id}")
	@ResponseBody
	public ResponseData<String> delete(@PathVariable("id") String idStr) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			water009Service.deleteDetail(idStr);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::delete ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Long id) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(water009Service.sendSap(id));
			responseData.setMessage(RESPONSE_MESSAGE.SAP.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric006Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAP.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
