package baiwa.module.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import baiwa.common.bean.DataTableAjax;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.module.model.FwLovDetail;
import baiwa.module.model.FwLovHeader;
import baiwa.module.service.FwLovDetailService;
import baiwa.module.service.FwLovHeaderService;
import baiwa.module.vo.request.FwLovDetailFormReq;
import baiwa.module.vo.request.FwLovHReq;

@Controller
@RequestMapping("api/lov")
public class LovController {

	private static final Logger logger = LoggerFactory.getLogger(LovController.class);

	@Autowired
	private FwLovHeaderService fwLovHeaderService;

	@Autowired
	private FwLovDetailService fwLovDetailService;

	@PostMapping("/list")
	@ResponseBody
	public DataTableAjax<FwLovHeader> list(@RequestBody FwLovHReq form) {
		DataTableAjax<FwLovHeader> response = new DataTableAjax<FwLovHeader>();
		List<FwLovHeader> fwLovList = new ArrayList<FwLovHeader>();
		try {
			fwLovList = fwLovHeaderService.list(form);
			response.setData(fwLovList);
		} catch (Exception e) {
			logger.error("LovController : ", e);
		}
		return response;
	}

	@PostMapping("/listdata")
	@ResponseBody
	public ResponseData<FwLovHeader> listdata(@RequestBody FwLovHReq request) {
		ResponseData<FwLovHeader> responseData = new ResponseData<FwLovHeader>();
		try {
			responseData.setData(fwLovHeaderService.listdata(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("LovController::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<String> saveLovHearder(@RequestBody FwLovHReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			String msg = fwLovHeaderService.saveLovHearder(request);
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
			logger.error("LovController::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/edit")
	@ResponseBody
	public ResponseData<String> editLovHearder(@RequestBody FwLovDetailFormReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			fwLovHeaderService.editLovHearder(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("LovController::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save-data-detail")
	@ResponseBody
	public ResponseData<String> savedata(@RequestBody FwLovDetailFormReq request) {
		ResponseData<String> responseData = new ResponseData<String>();
		try {
			fwLovDetailService.saveLovDetil(request);
			responseData.setData("SUCCESS");
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/list-data-detail")
	@ResponseBody
	public DataTableAjax<FwLovDetail> listDataDetail(@RequestBody FwLovHReq req) {
		DataTableAjax<FwLovDetail> response = new DataTableAjax<FwLovDetail>();
		List<FwLovDetail> fwLovDetailList = new ArrayList<FwLovDetail>();
		try {
			fwLovDetailList = fwLovDetailService.listDataDetail(req);
			response.setData(fwLovDetailList);
		} catch (Exception e) {
			logger.error("Electric002Controller : ", e);
		}
		return response;
	}

}
