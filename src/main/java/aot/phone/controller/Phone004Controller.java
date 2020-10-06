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

import aot.electric.vo.response.Electric009Res;
import aot.phone.model.RicPhoneRateChargeConfig;
import aot.phone.service.Phone004Service;
import aot.phone.vo.request.Phone004request;
import aot.phone.vo.response.Phone004response;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import io.swagger.annotations.ApiOperation;

/**
 * Created by imake on 17/07/2019
 */

@Controller
@RequestMapping("api/phone004")
public class Phone004Controller {
    private static final Logger logger = LoggerFactory.getLogger(Phone004Controller.class);
    public static final String PHONE004 = "phone 004";
    @Autowired
    private Phone004Service phone004Service;
    
    @PostMapping("/get")
	@ApiOperation(tags = PHONE004, value = "Get phone rate config")
	@ResponseBody
	public ResponseData<List<RicPhoneRateChargeConfig>> getCalConfig() {
		logger.info("Get system_cal_config");
		ResponseData<List<RicPhoneRateChargeConfig>> responseData = new ResponseData<List<RicPhoneRateChargeConfig>>();

		try {
			responseData.setData(phone004Service.getList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone004Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
    
    @GetMapping("/detail/{id}")
	@ApiOperation(tags = PHONE004, value = "Get phone rate config")
	@ResponseBody
	public ResponseData<Phone004response> getDetail(@PathVariable("id") String id) {
		logger.info("Get system_cal_config");
		ResponseData<Phone004response> responseData = new ResponseData<Phone004response>();

		try {
			responseData.setData(phone004Service.getDetail(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone004Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
    
    
    @PostMapping("/save")
    @ApiOperation(tags = PHONE004, value = "Get phone rate config")
	@ResponseBody
	public ResponseData<Phone004response> saveDetail(@RequestBody Phone004request req) {
		logger.info("save phone rate charge");
		ResponseData<Phone004response> responseData = new ResponseData<Phone004response>();
		try {
			responseData.setData(phone004Service.saveReq(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone004Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
