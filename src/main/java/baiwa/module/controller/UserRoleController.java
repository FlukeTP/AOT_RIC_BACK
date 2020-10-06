package baiwa.module.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.module.service.FwUserRoleService;
import baiwa.module.vo.request.FwUserRoleReq;
import baiwa.module.vo.response.FwUserRoleRes;

@Controller
@RequestMapping("api/userrole")
public class UserRoleController {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	
	@Autowired
	private FwUserRoleService fwUserRoleService;
	
	@GetMapping("/get_all")
	@ResponseBody
	public ResponseData<List<FwUserRoleRes>> listdata() {
		ResponseData<List<FwUserRoleRes>> responseData = new ResponseData<List<FwUserRoleRes>>();
		try {
			responseData.setData(fwUserRoleService.getUserRoleList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("UserRoleController::listdata ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_by_id")
	@ResponseBody
	public ResponseData<FwUserRoleRes> findById(@RequestBody FwUserRoleReq form) {
		ResponseData<FwUserRoleRes> responseData = new ResponseData<FwUserRoleRes>();
		try {
			responseData.setData(fwUserRoleService.findById(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("UserRoleController::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<?> saveUserRole(@RequestBody FwUserRoleReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwUserRoleService.saveUserRole(form);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("UserRoleController::saveUserRole ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public ResponseData<?> deleteUserRole(@RequestBody FwUserRoleReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwUserRoleService.deleteUserRole(form);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("UserRoleController::saveUserRole ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
