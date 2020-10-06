package baiwa.module.controller;

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

import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.module.model.SystemConstant;
import baiwa.module.service.SysConstantService;
import baiwa.module.vo.request.SysConstantReq;

@Controller
@RequestMapping("api/constant")
public class SysConstantController {

	private static final Logger logger = LoggerFactory.getLogger(SysConstantController.class);

	@Autowired
	private SysConstantService sysConstantService;

	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<SystemConstant> list(@RequestBody SysConstantReq form) {
		DataTableAjax<SystemConstant> response = new DataTableAjax<SystemConstant>();
		List<SystemConstant> constantsList = new ArrayList<SystemConstant>();
		try {
			constantsList = sysConstantService.list(form);
			response.setData(constantsList);
		} catch (Exception e) {
			logger.error("SysConstantController : ", e);
		}
		return response;
	}

	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<SystemConstant> listdata(@RequestBody SysConstantReq request) {
		ResponseData<SystemConstant> responseData = new ResponseData<SystemConstant>();
		try {
			responseData.setData(sysConstantService.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("SysConstantController::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveConstant(@RequestBody SysConstantReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			sysConstantService.saveConstant(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("SysConstantController::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editConstant(@RequestBody SysConstantReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			sysConstantService.editConstant(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("SysConstantController::editConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-constant/{key}")
	@ResponseBody
	public ResponseData<SystemConstant> getConstantByKey(@PathVariable("key") String key) {
		ResponseData<SystemConstant> responseData = new ResponseData<SystemConstant>();
		try {
			responseData.setData(sysConstantService.getConstantByKey(key));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("SysConstantController::getConstant ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
