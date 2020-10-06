package aot.communicate.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.communicate.service.Communicate005Service;
import aot.communicate.vo.request.Communicate005Req;
import aot.communicate.vo.response.Communicate005Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communicate005")
public class Communicate005Controller {

	private static final Logger logger = LoggerFactory.getLogger(Communicate005Controller.class);

	@Autowired
	private Communicate005Service communi005Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<T> save(@RequestBody Communicate005Req request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			communi005Service.save(request);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController005:save ", e);
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<Communicate005Res>> findElec(@RequestBody Communicate005Req request) {
		ResponseData<List<Communicate005Res>> responseData = new ResponseData<>();
		try {
			responseData.setData(communi005Service.findData(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController005::list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/send-to-sap")
	@ResponseBody
	public ResponseData<SapResponse> sendToSap(@RequestBody Long id) {
		ResponseData<SapResponse> response = new ResponseData<SapResponse>();
		try {
			SapResponse resSap = communi005Service.sendToSap(id);
			response.setData(resSap);
			response.setMessage(resSap.getMessage());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAP.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
}
