package aot.common.controller;

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

import aot.common.service.CustomerService;
import aot.common.vo.request.CustomerReq;
import aot.common.vo.response.ContractNoRes;
import aot.common.vo.response.CustomerRes;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/common")
public class CommonController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	CustomerService customerService;

	@PostMapping("/getSAPCustumer")
	@ResponseBody
	public ResponseData<List<CustomerRes>> getCustumer(@RequestBody CustomerReq request) {
		logger.info("Get customer");
		ResponseData<List<CustomerRes>> responseData = new ResponseData<List<CustomerRes>>();

		try {
			responseData.setData(customerService.getSAPCustumer(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommonController::getSAPCustumer ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/getSAPContractNo/{partner}/{branchCode}")
	@ResponseBody
	public ResponseData<List<ContractNoRes>> getContractNo(@PathVariable("partner") String partner, @PathVariable("branchCode") String branchCode) {
		logger.info("Get contractNo");
		ResponseData<List<ContractNoRes>> responseData = new ResponseData<List<ContractNoRes>>();

		try {
			responseData.setData(customerService.getSAPContractNo(partner, branchCode));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommonController::getSAPCustumer ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/getUtilityArea/{contractNo}")
	@ResponseBody
	public ResponseData<List<ContractNoRes>> getAentalAreaByContractNo(@PathVariable("contractNo") String contractNo) {
		logger.info("Get RentalArea");
		ResponseData<List<ContractNoRes>> responseData = new ResponseData<List<ContractNoRes>>();

		try {
			responseData.setData(customerService.getRentalAreaRes(contractNo));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("CommonController::getSAPCustumer ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
