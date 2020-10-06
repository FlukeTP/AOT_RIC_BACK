package aot.pos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aot.pos.service.Pos004Service;
import aot.pos.vo.request.Pos004CustomerReq;
import aot.pos.vo.request.Pos004UserReq;
import aot.pos.vo.response.Pos004CustomerRes;
import aot.pos.vo.response.Pos004Res;
import aot.pos.vo.response.Pos004UserRes;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/pos004")
public class Pos004Controller {

	private static final Logger logger = LoggerFactory.getLogger(Pos004Controller.class);

	@Autowired
	private Pos004Service pos004Service;

	@PostMapping("/save-customer")
	@ResponseBody
	public ResponseData<String> saveCustomer(@RequestBody Pos004CustomerReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			String msg = pos004Service.saveCustomer(request);
			if (RESPONSE_MESSAGE.SAVE.SUCCESS.equals(msg)) {
				responseData.setData("SUCCESS");
				responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
				responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			} else {
				responseData.setData("DUPLICATE_DATA");
				responseData.setMessage(RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA);
				responseData.setStatus(RESPONSE_STATUS.DUPLICATE_DATA);
			}
		} catch (Exception e) {
			logger.error("Pos004Controller ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id-customer/{id}")
	@ResponseBody
	public ResponseData<Pos004CustomerRes> getByIdCustomer(@PathVariable("id") String idStr) {
		ResponseData<Pos004CustomerRes> responseData = new ResponseData<Pos004CustomerRes>();
		try {
			responseData.setData(pos004Service.getByIdCustomer(idStr));
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos004Controller::getByIdCustomer ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/list")
	@ResponseBody
	public ResponseData<List<Pos004Res>> list() {
		ResponseData<List<Pos004Res>> responseData = new ResponseData<List<Pos004Res>>();

		try {
			responseData.setData(pos004Service.findData());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos004Controller : list ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save-user")
	@ResponseBody
	public ResponseData<String> saveCustomer(@RequestBody Pos004UserReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			String msg = pos004Service.saveUser(request);
			if (RESPONSE_MESSAGE.SAVE.SUCCESS.equals(msg)) {
				responseData.setData("SUCCESS");
				responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
				responseData.setStatus(RESPONSE_STATUS.SUCCESS);
			} else {
				responseData.setData("DUPLICATE_DATA");
				responseData.setMessage(RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA);
				responseData.setStatus(RESPONSE_STATUS.DUPLICATE_DATA);
			}
		} catch (Exception e) {
			logger.error("Pos004Controller ", e);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/list-user/{id}")
	@ResponseBody
	public ResponseData<List<Pos004UserRes>> listUser(@PathVariable("id") String idStr) {
		ResponseData<List<Pos004UserRes>> responseData = new ResponseData<List<Pos004UserRes>>();
		try {
			responseData.setData(pos004Service.listUser(idStr));
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos004Controller::list-user ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-by-id-user/{id}")
	@ResponseBody
	public ResponseData<Pos004UserRes> getByIdUser(@PathVariable("id") String idStr) {
		ResponseData<Pos004UserRes> responseData = new ResponseData<Pos004UserRes>();
		try {
			responseData.setData(pos004Service.getByIdUser(idStr));
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos004Controller::getByIdUser ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@DeleteMapping("/delete-user/{id}")
	@ResponseBody
	public ResponseData<String> deleteUser(@PathVariable("id") String idStr) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			pos004Service.deleteUser(idStr);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos004Controller::delete ", e);
			responseData.setMessage(RESPONSE_MESSAGE.DELETE.FAILED);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
