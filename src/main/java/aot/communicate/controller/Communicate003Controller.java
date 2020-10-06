package aot.communicate.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.communicate.service.Communicate003Service;
import aot.communicate.vo.request.Communicate003Req;
import aot.communicate.vo.response.Communicate003Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communi003")
public class Communicate003Controller {

	private static final Logger logger = LoggerFactory.getLogger(Communicate003Controller.class);

	@Autowired
	private Communicate003Service communi003Service;

	@PostMapping("/get-all")
	@ResponseBody
	public ResponseData<List<Communicate003Res>> getListAll(@RequestBody Communicate003Req request) {
		ResponseData<List<Communicate003Res>> responseData = new ResponseData<List<Communicate003Res>>();
		try {
			responseData.setData(communi003Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi003Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find-id")
	@ResponseBody
	public ResponseData<Communicate003Res> findById(@RequestBody Communicate003Req request) {
		ResponseData<Communicate003Res> responseData = new ResponseData<Communicate003Res>();
		try {
			responseData.setData(communi003Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi003Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Communicate003Res> save(@RequestBody Communicate003Req request) {
		ResponseData<Communicate003Res> responseData = new ResponseData<Communicate003Res>();
		try {
			communi003Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/send-to-sap")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Long id) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			SapResponse dataRes = communi003Service.sendSap(id);
			responseData.setData(dataRes);
			responseData.setMessage(dataRes.getMessage());
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi003Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAP.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
