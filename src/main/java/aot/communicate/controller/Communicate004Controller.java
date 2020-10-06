package aot.communicate.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
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

import aot.communicate.service.Communicate004Service;
import aot.communicate.vo.request.Communicate004Req;
import aot.communicate.vo.response.Communicate004Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communicate004")
public class Communicate004Controller {

	private static final Logger logger = LoggerFactory.getLogger(Communicate004Controller.class);

	@Autowired
	private Communicate004Service communi004Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<T> save(@RequestBody Communicate004Req request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			communi004Service.save(request);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController004:save ", e);
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<Communicate004Res>> findElec(@RequestBody Communicate004Req request) {
		ResponseData<List<Communicate004Res>> responseData = new ResponseData<>();
		try {
			responseData.setData(communi004Service.findData(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController004::list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Communicate004Res> getById(@PathVariable("id") Long id) {
		ResponseData<Communicate004Res> responseData = new ResponseData<>();
		try {
			responseData.setData(communi004Service.getById(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController004::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/get-by-transaction-no/{id}")
	@ResponseBody
	public ResponseData<Communicate004Res> getByTransactionNo(@PathVariable("id") String id) {
		ResponseData<Communicate004Res> responseData = new ResponseData<>();
		try {
			responseData.setData(communi004Service.getByTransactionNo(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommunicateController004::getById ", e);
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
			SapResponse resSap = communi004Service.sendToSap(id);
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
