package aot.pos.controller;

import java.io.IOException;
import java.util.List;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aot.pos.service.Pos003Service;
import aot.pos.vo.request.Pos003ParseJsonReq;
import aot.pos.vo.request.Pos003Req;
import aot.pos.vo.response.Pos003Res;
import aot.pos.vo.response.PosRevenueCustomerRes;
import aot.util.sap.domain.response.SapREResponse;
import baiwa.common.bean.ResponseData;
import baiwa.common.rest.adapter.LongTypeAdapter;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/pos003")
public class Pos003Controller {

	private static final Logger logger = LoggerFactory.getLogger(Pos003Controller.class);

	private Gson gson = new GsonBuilder().serializeNulls()
			.registerTypeAdapter(Long.class, LongTypeAdapter.getInstance()).create();

	@Autowired
	private Pos003Service pos003Service;

	@PostMapping("/sendToSAP")
	@ResponseBody
	public ResponseData<SapREResponse> sendToSAP(@RequestBody Pos003Req req) {
		ResponseData<SapREResponse> responseData = new ResponseData<>();

		try {
			responseData.setData(pos003Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Phone003Controller::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@PostMapping("/get_all")
	@ResponseBody
	public ResponseData<List<PosRevenueCustomerRes>> getListReqCancel() {
		ResponseData<List<PosRevenueCustomerRes>> responseData = new ResponseData<List<PosRevenueCustomerRes>>();
		try {
			responseData.setData(pos003Service.getRevenueCustomerList());
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos003Controller::get all ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<?> saveReqCancel(@ModelAttribute Pos003Req request) throws IOException {
		ResponseData<?> responseData = new ResponseData<>();
		try {
			// parse json to object and set to request
			Pos003ParseJsonReq formVo = gson.fromJson(request.getJson(), Pos003ParseJsonReq.class);
			request.setHeader(formVo.getHeader());
			request.setProduct(formVo.getProduct());
			request.setPayment(formVo.getPayment());
			
			pos003Service.save(request);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
	
	@GetMapping("/get-value-edit/{id}")
	@ResponseBody
	public ResponseData<Pos003Res> getValueEdit(@PathVariable("id") Long id) {
		ResponseData<Pos003Res> responseData = new ResponseData<Pos003Res>();
		try {
			responseData.setData(pos003Service.getValueEdit(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Pos003Controller::getValueEdit ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}
}
