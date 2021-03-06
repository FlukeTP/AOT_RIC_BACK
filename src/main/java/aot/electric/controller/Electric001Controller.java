package aot.electric.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aot.electric.service.Electric001Service;
import aot.electric.vo.request.Electric001Req;
import aot.electric.vo.response.Electric001Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric001")
public class Electric001Controller {
	private static final Logger logger = LoggerFactory.getLogger(Electric001Controller.class);
	
	@Autowired
	private Electric001Service electric001Service;
	
	@PostMapping("/search")
	@ResponseBody
	public ResponseData<List<Electric001Res>> findListOfMonth(@RequestBody Electric001Req request) {
		ResponseData<List<Electric001Res>> response = new ResponseData<List<Electric001Res>>();
		try {
			response.setData(electric001Service.findListOfMonth(request));
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
	public ResponseData<T> save(@RequestBody List<Electric001Res> request) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			electric001Service.save(request);
			response.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@GetMapping("/dropdown/serial-no")
	@ResponseBody
	public ResponseData<List<Electric001Res>> findDropdownSerialNo() {
		ResponseData<List<Electric001Res>> response = new ResponseData<List<Electric001Res>>();
		try {
			response.setData(electric001Service.findDropdownSerialNo());
			response.setMessage(RESPONSE_MESSAGE.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.ERROR500);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
	@PostMapping("/upload-excel")
	@ResponseBody
	public ResponseData<List<Electric001Res>> uploadT1(@ModelAttribute MultipartFile file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ResponseData<List<Electric001Res>> responseData = new ResponseData<List<Electric001Res>>();
		try {
			responseData.setData(electric001Service.uploadExcel(file));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("upload: ", e);
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/sync-data/{periodMonth}")
	@ResponseBody
	public ResponseData<T> getDataByPeriodMonth(@PathVariable("periodMonth") String periodMonth) {
		ResponseData<T> response = new ResponseData<T>();
		try {
			Integer countData = electric001Service.syncData(periodMonth);
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.SUCCESS + " (" + countData + ")");
			response.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(RESPONSE_MESSAGE.SYNC_DATA.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
		}
		return response;
	}
	
//	@GetMapping("/check-sync-data")
//	@ResponseBody
//	public ResponseData<Integer> checkSyncData() {
//		ResponseData<Integer> response = new ResponseData<Integer>();
//		try {
//			response.setData(electric001Service.checkBeforeSynData());
//			response.setStatus(RESPONSE_STATUS.SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			response.setStatus(RESPONSE_STATUS.FAILED);
//		}
//		return response;
//	}

	@PostMapping("/send-sap")
	@ResponseBody
	public ResponseData<List<SapResponse>> sendSap(@RequestBody List<Long> idx) {
		ResponseData<List<SapResponse>> response = new ResponseData<List<SapResponse>>();
		try {
			response.setData(electric001Service.sendSap(idx));
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
