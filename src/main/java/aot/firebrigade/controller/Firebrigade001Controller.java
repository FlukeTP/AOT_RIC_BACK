package aot.firebrigade.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.firebrigade.service.Firebrigade001Service;
import aot.firebrigade.vo.request.Firebrigade001Req;
import aot.firebrigade.vo.response.Firebrigade001Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/firebrigade001")
public class Firebrigade001Controller {

	private static final Logger logger = LoggerFactory.getLogger(Firebrigade001Controller.class);

	@Autowired
	private Firebrigade001Service firebrigade001Service;

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Firebrigade001Res>> getListFirebrigadeManage(@RequestBody Firebrigade001Req request) {
		ResponseData<List<Firebrigade001Res>> responseData = new ResponseData<List<Firebrigade001Res>>();
		try {
			responseData.setData(firebrigade001Service.getListFirebrigadeManage(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade001Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Firebrigade001Res> findById(@RequestBody Firebrigade001Req request) {
		ResponseData<Firebrigade001Res> responseData = new ResponseData<Firebrigade001Res>();
		try {
			responseData.setData(firebrigade001Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade001Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Firebrigade001Res> saveFirebrigadeManage(@RequestBody Firebrigade001Req request) {
		ResponseData<Firebrigade001Res> responseData = new ResponseData<Firebrigade001Res>();
		try {
			firebrigade001Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade001Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Firebrigade001Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(firebrigade001Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Firebrigade001Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
