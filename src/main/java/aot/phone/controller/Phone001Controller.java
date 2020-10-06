package aot.phone.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aot.phone.service.Phone001Service;
import aot.phone.vo.request.Phone001Req;
import aot.phone.vo.response.Phone001Res;
import aot.util.sap.domain.response.SapResponse;
import aot.util.scheduler.service.SyncPhone001Service;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

/**
 * Created by imake on 17/07/2019
 */

@Controller
@RequestMapping("api/phone001")
public class Phone001Controller {

    private static final Logger logger = LoggerFactory.getLogger(Phone001Controller.class);

    @Autowired
    private Phone001Service phone001Service;
    
    @Autowired
    private SyncPhone001Service syncPhone001Service;
    
    @PostMapping("/search")
	@ResponseBody
	public ResponseData<List<Phone001Res>> search(@RequestBody Phone001Req request) {
		ResponseData<List<Phone001Res>> response = new ResponseData<List<Phone001Res>>();
		try {
			response.setData(phone001Service.search(request));
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
    
    @GetMapping("/sync-data")
	@ResponseBody
	public ResponseData<T> getDataByPeriodMonth() {
		ResponseData<T> response = new ResponseData<T>();
		try {
			syncPhone001Service.syncData();
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@GetMapping("/check-sync-data")
	@ResponseBody
	public ResponseData<Integer> checkSyncData() {
		ResponseData<Integer> response = new ResponseData<Integer>();
		try {
			response.setData(phone001Service.checkBeforeSynData());
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/upload-excel")
	@ResponseBody
	public ResponseData<List<Phone001Res>> uploadT1(@ModelAttribute Phone001Req file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ResponseData<List<Phone001Res>> responseData = new ResponseData<List<Phone001Res>>();
		try {
			phone001Service.uploadExcel(file);
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.UPLOAD.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.UPLOAD.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/send-sap")
	@ResponseBody
	public ResponseData<List<SapResponse>> sendSap(@RequestBody List<Long> idx) {
		ResponseData<List<SapResponse>> response = new ResponseData<List<SapResponse>>();
		try {
			response.setData(phone001Service.sendSap(idx));
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
}
