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
import baiwa.module.service.FwOrgService;
import baiwa.module.vo.request.FwOrgReq;
import baiwa.module.vo.response.FwOrgRes;

@Controller
@RequestMapping("api/org")
public class OrgController {

	private static final Logger logger = LoggerFactory.getLogger(OrgController.class);
	
	@Autowired
	private FwOrgService fwOrgService;
	
	@GetMapping("/get_all")
	@ResponseBody
	public ResponseData<List<FwOrgRes>> listdata() {
		ResponseData<List<FwOrgRes>> responseData = new ResponseData<List<FwOrgRes>>();
		try {
			responseData.setData(fwOrgService.getOrgList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("OrgController::listdata ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_by_id")
	@ResponseBody
	public ResponseData<FwOrgRes> findById(@RequestBody FwOrgReq form) {
		ResponseData<FwOrgRes> responseData = new ResponseData<FwOrgRes>();
		try {
			responseData.setData(fwOrgService.findById(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("OrgController::findById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseData<?> saveOrg(@RequestBody FwOrgReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwOrgService.saveOrg(form);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("OrgController::saveOrg ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public ResponseData<?> deleteOrg(@RequestBody FwOrgReq form) {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			fwOrgService.deleteOrg(form);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("OrgController::saveOrg ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
