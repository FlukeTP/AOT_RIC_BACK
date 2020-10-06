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

import aot.communicate.service.Communicate002Service;
import aot.communicate.vo.request.Communicate002Req;
import aot.communicate.vo.response.Communicate002Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communicate002")
public class Communicate002Controller {
	@Autowired
	private Communicate002Service communicate002Service;
	
	@GetMapping("/find-by-id/{id}")
	@ResponseBody
	public ResponseData<Communicate002Res> findByid(@PathVariable Long id) {
		ResponseData<Communicate002Res> response = new ResponseData<Communicate002Res>();
		try {
			 response.setData(communicate002Service.findById(id));
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public ResponseData<T> update(@RequestBody Communicate002Req request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			communicate002Service.update(request);
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
	public ResponseData<List<Communicate002Res>> search(@RequestBody Communicate002Req request) {
		ResponseData<List<Communicate002Res>> response = new ResponseData<List<Communicate002Res>>();
		try {
			 response.setData(communicate002Service.search(request));
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
			SapResponse resSap = communicate002Service.sendToSap(id);
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
