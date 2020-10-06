package aot.cndn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.cndn.service.CnDn001Service;
import aot.cndn.vo.request.CnDn001Req;
import aot.cndn.vo.response.CnDn001Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/cndn001")
public class CnDn001Controller {

	private static final Logger logger = LoggerFactory.getLogger(CnDn001Controller.class);

	@Autowired
	private CnDn001Service cnDn001Service;

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody CnDn001Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(cnDn001Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CnDn001Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<CnDn001Res>> getAllList(@RequestBody CnDn001Req request) {
		ResponseData<List<CnDn001Res>> responseData = new ResponseData<List<CnDn001Res>>();
		try {
			responseData.setData(cnDn001Service.getAllList(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CnDn001Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<CnDn001Res> findById(@RequestBody CnDn001Req request) {
		ResponseData<CnDn001Res> responseData = new ResponseData<CnDn001Res>();
		try {
			responseData.setData(cnDn001Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CnDn001Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<CnDn001Res> save(@RequestBody CnDn001Req request) {
		ResponseData<CnDn001Res> responseData = new ResponseData<CnDn001Res>();
		try {
			cnDn001Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CnDn001Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
