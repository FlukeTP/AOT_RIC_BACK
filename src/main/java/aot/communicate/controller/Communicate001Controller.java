package aot.communicate.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.communicate.service.Communicate001Service;
import aot.communicate.vo.request.Communicate001Req;
import aot.communicate.vo.response.CommunicateConfigRes;
import aot.communicate.vo.response.Communicate001Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communicate001")
public class Communicate001Controller {

	@Autowired
	private Communicate001Service communicate001Service;
	
	@GetMapping("/find-by-id/{id}")
	@ResponseBody
	public ResponseData<Communicate001Res> findByid(@PathVariable Long id) {
		ResponseData<Communicate001Res> response = new ResponseData<Communicate001Res>();
		try {
			 response.setData(communicate001Service.findById(id));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@GetMapping("/find-by-transno/{transNo}")
	@ResponseBody
	public ResponseData<Communicate001Res> findByTransNo(@PathVariable String transNo) {
		ResponseData<Communicate001Res> response = new ResponseData<Communicate001Res>();
		try {
			 response.setData(communicate001Service.findByTransNo(transNo));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<T> save(@RequestBody Communicate001Req request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			// response.setData();
			communicate001Service.save(request);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/search")
	@ResponseBody
	public ResponseData<List<Communicate001Res>> search(@RequestBody Communicate001Req request) {
		ResponseData<List<Communicate001Res>> response = new ResponseData<List<Communicate001Res>>();
		try {
			 response.setData(communicate001Service.search(request));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@GetMapping("/find-chargerates-config/{dateStr}")
	@ResponseBody
	public ResponseData<CommunicateConfigRes> findChargeRatesConfig(@PathVariable String dateStr) {
		ResponseData<CommunicateConfigRes> response = new ResponseData<CommunicateConfigRes>();
		try {
			 response.setData(communicate001Service.findChargeRatesConfig(dateStr));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/send-to-sap")
	@ResponseBody
	public ResponseData<SapResponse> sendToSap(@RequestBody Long id) {
		ResponseData<SapResponse> response = new ResponseData<SapResponse>();
		try {
			SapResponse resSap = communicate001Service.sendToSap(id);
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
