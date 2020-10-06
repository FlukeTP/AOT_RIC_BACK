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
import baiwa.module.service.FwRoleService;
import baiwa.module.vo.request.FwRoleReq;
import baiwa.module.vo.response.FwRoleRes;

@Controller
@RequestMapping("api/role")
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private FwRoleService fwRoleService;
	
	@GetMapping("/get_all")
	@ResponseBody
	public ResponseData<List<FwRoleRes>> listdata() {
		ResponseData<List<FwRoleRes>> responseData = new ResponseData<List<FwRoleRes>>();
		try {
			responseData.setData(fwRoleService.getRoleList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::listdata ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_by_id")
	@ResponseBody
	public ResponseData<FwRoleRes> findById(@RequestBody FwRoleReq form) {
		ResponseData<FwRoleRes> responseData = new ResponseData<FwRoleRes>();
		try {
			responseData.setData(fwRoleService.findById(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<?> saveRole(@RequestBody FwRoleReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwRoleService.saveRole(form);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::saveRole ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public ResponseData<?> deleteRole(@RequestBody FwRoleReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwRoleService.deleteRole(form);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("RoleController::saveRole ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
