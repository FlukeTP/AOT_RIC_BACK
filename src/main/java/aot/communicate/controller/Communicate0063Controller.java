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

import aot.communicate.service.Communicate0063Service;
import aot.communicate.vo.request.Communicate0063Req;
import aot.communicate.vo.response.Communicate0063Res;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/communi0063")
public class Communicate0063Controller {
	private static final Logger logger = LoggerFactory.getLogger(Communicate0063Controller.class);

	@Autowired
	private Communicate0063Service communi0063Service;

	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<Communicate0063Res>> getListAll(@RequestBody Communicate0063Req request) {
		ResponseData<List<Communicate0063Res>> responseData = new ResponseData<List<Communicate0063Res>>();
		try {
			responseData.setData(communi0063Service.getListAll(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0063Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_id")
	@ResponseBody
	public ResponseData<Communicate0063Res> findById(@RequestBody Communicate0063Req request) {
		ResponseData<Communicate0063Res> responseData = new ResponseData<Communicate0063Res>();
		try {
			responseData.setData(communi0063Service.findById(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0063Controller::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<Communicate0063Res> save(@RequestBody Communicate0063Req request) {
		ResponseData<Communicate0063Res> responseData = new ResponseData<Communicate0063Res>();
		try {
			communi0063Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0061Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get-effective-date")
	@ResponseBody
	public ResponseData<Communicate0063Res> getEffectiveDate(@RequestBody Communicate0063Req request) {
		ResponseData<Communicate0063Res> responseData = new ResponseData<Communicate0063Res>();
		try {
			responseData.setData(communi0063Service.getEffectiveDate(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Communi0063Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
