package baiwa.module.controller;

import java.util.ArrayList;
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

import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.module.model.FwUserDetail;
import baiwa.module.model.FwUsers;
import baiwa.module.service.FwRoleService;
import baiwa.module.service.FwUserRoleService;
import baiwa.module.service.FwUserService;
import baiwa.module.vo.request.FwUserRoleReq;
import baiwa.module.vo.request.UsersReq;
import baiwa.module.vo.response.FwRoleRes;

@Controller
@RequestMapping("api/users")
public class UsersController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private FwUserService fwUserService;

	@Autowired
	private FwRoleService fwRoleService;

	@Autowired
	private FwUserRoleService fwUserRoleService;

	@PostMapping("/list")
	@ResponseBody
	public List<String> list(@RequestBody UsersReq req) {
		return fwUserService.getAlluser(req);
	}

	@PostMapping("/listAll")
	@ResponseBody
	public DataTableAjax<FwUsers> listAll() {
		DataTableAjax<FwUsers> response = new DataTableAjax<FwUsers>();
		List<FwUsers> fwUserList = new ArrayList<FwUsers>();
		try {
			fwUserList = fwUserService.listAll();
			response.setData(fwUserList);
		} catch (Exception e) {
			logger.error("UsersController : ", e);
		}
		return response;
	}

	@PostMapping("/getUser")
	@ResponseBody
	public ResponseData<FwUserDetail> listId(@RequestBody UsersReq request) {
		ResponseData<FwUserDetail> responseData = new ResponseData<FwUserDetail>();
		try {
			responseData.setData(fwUserService.getUser(request));
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
	public ResponseData<String> saveUser(@RequestBody UsersReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			fwUserService.saveUser(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editUser(@RequestBody UsersReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			fwUserService.editUser(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save_user_role")
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

}