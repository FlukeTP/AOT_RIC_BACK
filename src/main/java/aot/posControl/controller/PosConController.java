package aot.posControl.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import aot.electric.vo.response.Electric001Res;
import aot.posControl.model.RicPosSaleProduct;
import aot.posControl.service.PosConService;
import aot.posControl.service.PosConUploadService;
import aot.posControl.service.PosSaleProductService;
import aot.posControl.service.ReportingRuleService;
import aot.posControl.vo.request.PosConUserDetailRequest;
import aot.posControl.vo.request.PosUploadRequest;
import aot.posControl.vo.response.PosConUserDetailResponse;
import aot.posControl.vo.response.ReportingRuleResponse;
import aot.posControl.vo.response.RicPosSaleProductResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/posc")
public class PosConController {
	
	private static final Logger logger = LoggerFactory.getLogger(PosConController.class);
	
	@Autowired
	private PosConService posConService;
	
	@Autowired
	private PosConUploadService posConUploadService;
	
	@Autowired
	private ReportingRuleService reportingRuleService;
	
	@Autowired
	private PosSaleProductService posSaleProductService;
	
	@PostMapping("/posUserDetail")
	@ResponseBody
	public ResponseData<PosConUserDetailResponse> posUserDetail(@RequestBody PosConUserDetailRequest req) {
		ResponseData<PosConUserDetailResponse> responseData = new ResponseData<PosConUserDetailResponse>();

		try {
			responseData.setData(posConService.getuserDetail(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("PosConController::getuserDetail ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/reportingRule")
	@ResponseBody
	public ResponseData<List<ReportingRuleResponse>> posReportingRule(@RequestBody PosConUserDetailRequest req) {
		ResponseData<List<ReportingRuleResponse>> responseData = new ResponseData<List<ReportingRuleResponse>>();

		try {
			responseData.setData(reportingRuleService.getReportingRuleBYcontract(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("PosConController::getuserDetail ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/uploadExcel")
	@ResponseBody
	public ResponseData<RicPosSaleProductResponse> uploadT1(@ModelAttribute PosUploadRequest req)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ResponseData<RicPosSaleProductResponse> responseData = new ResponseData<RicPosSaleProductResponse>();
		try {
			responseData.setData(posConUploadService.uploadExcel(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("upload: ", e);
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/getSaleProduct")
	@ResponseBody
	public ResponseData<RicPosSaleProductResponse> getSaleProduct(@RequestBody PosUploadRequest req)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ResponseData<RicPosSaleProductResponse> responseData = new ResponseData<RicPosSaleProductResponse>();
		try {
			responseData.setData(posSaleProductService.getSaleProduct(req.getFrequencyNo()));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("upload: ", e);
			responseData.setMessage(ProjectConstant.RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
