package aot.phone.controller;

import java.util.List;

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

import aot.phone.model.RicPhoneReq;
import aot.phone.service.Phone002Service;
import aot.phone.vo.request.Phone002ReceiptReq;
import aot.phone.vo.request.Phone002Req;
import aot.phone.vo.request.Phone002SaveReq;
import aot.phone.vo.response.Phone002PhoneReqRes;
import aot.phone.vo.response.Phone002Res;
import aot.phone.vo.response.Phone002ServiceTypeRes;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

/**
 * Created by imake on 17/07/2019
 */

@Controller
@RequestMapping("api/phone002")
public class Phone002Controller {
	private static final Logger logger = LoggerFactory.getLogger(Phone002Controller.class);

	@Autowired
	private Phone002Service phone002Service;

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<RicPhoneReq> save(@RequestBody Phone002SaveReq form) {
		ResponseData<RicPhoneReq> responseData = new ResponseData<RicPhoneReq>();
		try {
			responseData.setData(phone002Service.save(form));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<Phone002Res>> list(@RequestBody Phone002SaveReq req) {
		ResponseData<List<Phone002Res>> responseData = new ResponseData<List<Phone002Res>>();

		try {
			responseData.setData(phone002Service.findData(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller : list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-service-type/{serviceType}")
	@ResponseBody
	public ResponseData<List<Phone002ServiceTypeRes>> getServiceType(@PathVariable("serviceType") String serviceType) {
		ResponseData<List<Phone002ServiceTypeRes>> responseData = new ResponseData<List<Phone002ServiceTypeRes>>();
		try {
			responseData.setData(phone002Service.getServiceType(serviceType));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller:: getServiceType", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-rate-charge/{phoneType}/{serviceType}")
	@ResponseBody
	public ResponseData<Phone002ServiceTypeRes> getRateCharge(@PathVariable("phoneType") String phoneType,
			@PathVariable("serviceType") String serviceType) {
		ResponseData<Phone002ServiceTypeRes> responseData = new ResponseData<Phone002ServiceTypeRes>();
		try {
			responseData.setData(phone002Service.getRateCharge(phoneType, serviceType));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller:: getRateCharge", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-phone-req")
	@ResponseBody
	public ResponseData<List<Phone002PhoneReqRes>> getPhoneReq() {
		ResponseData<List<Phone002PhoneReqRes>> responseData = new ResponseData<List<Phone002PhoneReqRes>>();
		try {
			responseData.setData(phone002Service.getPhoneReq());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller:: getPhoneReq", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapResponse> sendToSAP(@RequestBody Phone002Req req) {
		ResponseData<SapResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(phone002Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id/{id}")
	@ResponseBody
	public ResponseData<Phone002Res> getById(@PathVariable("id") String idStr) {
		ResponseData<Phone002Res> responseData = new ResponseData<Phone002Res>();
		try {
			responseData.setData(phone002Service.getById(idStr));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller::getById ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save-receipt-no")
	@ResponseBody
	public ResponseData<String> saveReceiptNo(@RequestBody Phone002ReceiptReq form) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			phone002Service.saveReceiptNo(form);
			responseData.setData(RESPONSE_MESSAGE.SUCCESS);
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone002Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
