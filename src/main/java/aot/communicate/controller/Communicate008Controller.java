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

import aot.communicate.service.Communicate008Service;
import aot.communicate.vo.request.Communicate008Req;
import aot.communicate.vo.response.Communicate008Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communicate008")
public class Communicate008Controller {
	
	@Autowired
	private Communicate008Service communicate008Service;
	
	@GetMapping("/sync-data/{periodMonth}")
	@ResponseBody
	public ResponseData<T> getDataByPeriodMonth(@PathVariable("periodMonth") String periodMonth) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			Integer countData = communicate008Service.syncData(periodMonth);
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.SUCCESS + " (" + countData + ")");
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/search")
	@ResponseBody
	public ResponseData<List<Communicate008Res>> search(@RequestBody Communicate008Req request) {
		ResponseData<List<Communicate008Res>> response = new ResponseData<List<Communicate008Res>>();
		try {
			response.setData(communicate008Service.search(request));
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
	public ResponseData<List<SapResponse>> sendSap(@RequestBody List<Long> idx) {
		ResponseData<List<SapResponse>> response = new ResponseData<List<SapResponse>>();
		try {
			response.setData(communicate008Service.sendToSap(idx));
			response.setMessage(RESPONSE_MESSAGE.SAP.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAP.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
}
