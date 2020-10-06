package aot.electric.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import aot.common.vo.request.ElectricBillReq;
import aot.common.vo.response.ElectricBillRes;
import aot.common.vo.response.FileResponse;
import aot.electric.model.RicElectricMeter;
import aot.electric.model.RicElectricReq;
import aot.electric.service.Electric003Service;
import aot.electric.vo.request.Elec003ConfigReq;
import aot.electric.vo.request.Elec003FindMeterReq;
import aot.electric.vo.request.Elec003FindReq;
import aot.electric.vo.request.Elec003SaveVo;
import aot.electric.vo.request.Elec003UploadFileReq;
import aot.electric.vo.request.Electric003SapReq;
import aot.electric.vo.request.Electric006Req;
import aot.electric.vo.response.Elec003DetailRes;
import aot.electric.vo.response.Elec003UploadFileRes;
import aot.electric.vo.response.Electric003CustomerRes;
import aot.electric.vo.response.Electric003MeterRes;
import aot.electric.vo.response.Electric003Res;
import aot.electric.vo.response.Electric007Res;
import aot.util.sap.domain.response.SapResponse;
import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;

@Controller
@RequestMapping("api/electric003")
public class Electric003Controller {
	@Autowired
	private Electric003Service electric003Service;

	private static final Logger logger = LoggerFactory.getLogger(Electric003Controller.class);

	@PostMapping("/save")
	@ResponseBody
	public ResponseData<RicElectricReq> save(@RequestBody Elec003SaveVo form) {
		ResponseData<RicElectricReq> responseData = new ResponseData<RicElectricReq>();
		try {
			responseData.setData(electric003Service.save(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/findElec")
	@ResponseBody
	public ResponseData<List<Electric003Res>> findElec(@RequestBody Elec003FindReq form) {
		ResponseData<List<Electric003Res>> responseData = new ResponseData<>();
		try {
			responseData.setData(electric003Service.findElec(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::save ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/get_meter")
	@ResponseBody
	public ResponseData<List<RicElectricMeter>> getListElectricMeter(@RequestBody Elec003FindMeterReq request) {
		ResponseData<List<RicElectricMeter>> responseData = new ResponseData<List<RicElectricMeter>>();
		try {
			responseData.setData(electric003Service.getListElectricMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("electric003Service::get all meter", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/find_meter")
	@ResponseBody
	public ResponseData<Electric003MeterRes> findReqCancelByMeter(@RequestBody Electric006Req request) {
		ResponseData<Electric003MeterRes> responseData = new ResponseData<Electric003MeterRes>();
		try {
			responseData.setData(electric003Service.findReqCancelByMeter(request));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("electric003Service::find ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/getRateChargeConfig")
	@ResponseBody
	public ResponseData<List<Electric007Res>> getRateChargeConfig(@RequestBody Elec003ConfigReq form) {
		logger.info("Get getRateChargeConfig");
		ResponseData<List<Electric007Res>> responseData = new ResponseData<List<Electric007Res>>();

		try {
			responseData.setData(electric003Service.findRateChargeConfig(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("electric003Service::getRateChargeConfig ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/getSAPCustumer/{type}")
	@ResponseBody
	public ResponseData<List<Electric003CustomerRes>> getSAPCustumer(@PathVariable("type") String form) {
		logger.info("Get getRateChargeConfig");
		ResponseData<List<Electric003CustomerRes>> responseData = new ResponseData<List<Electric003CustomerRes>>();

		try {
			responseData.setData(electric003Service.getSAPCustumer(form));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("electric003Service::getSAPCustumer ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/upload")
	@ResponseBody
	public ResponseData<?> uploadData(@ModelAttribute Elec003UploadFileReq req) {
		logger.info("uploadData");

		ResponseData<?> responseData = new ResponseData<>();
		try {
			electric003Service.uploadFile(req);
			// responseData.setData(service.upload(formVo));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseData.setMessage(e.getMessage());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}

		return responseData;
	}

//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(value = {"/downloadRicFile"},
//	                method = RequestMethod.GET)
//	public HttpEntity<byte[]> downloadFile() {
//	 
////		DownloadDoc downloadReq = new DownloadDoc();
////		downloadReq.setXAirport(UserLoginUtils.getUser().getAirportCode());
////		downloadReq.setXCategory(ECMConstant.CATEGORY.ELECTRICITY);
////		downloadReq.setXdocName(docName);
////		downloadReq.setXfileName(filename);
////		downloadReq.setXcontent(byteArray);
////		downloadReq.setXRICNumber(elereq.getTransactionNoCash()); // TODO ricNO
//		
//	    /** assume that below line gives you file content in byte array **/
//	   // byte[] excelContent = getReportContent();
//	    // prepare response
//		electric003Service.downloadFile(req);
//	    HttpHeaders header = new HttpHeaders();
//	    header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//	    header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=my_file.xls");
//	//    header.setContentLength(excelContent.length);
//	    
//
//		
//	  //  return new HttpEntity<byte[]>(excelContent, header);
//	}

	@GetMapping("/downloadRicFile/{id}")
	@ResponseBody
	public void getDownloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
		FileResponse fileres = electric003Service.downloadFile(id);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileres.getFileName()));

		IOUtils.write(fileres.getFileContent(), response.getOutputStream());
	}

	@PostMapping("/get-file-list")
	@ResponseBody
	public ResponseData<List<Elec003UploadFileRes>> getFileList(@RequestBody Elec003UploadFileReq req) {
		logger.info("uploadData");

		ResponseData<List<Elec003UploadFileRes>> responseData = new ResponseData<List<Elec003UploadFileRes>>();
		try {
			responseData.setData(electric003Service.getFileList(req));
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseData.setMessage(e.getMessage());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}

		return responseData;
	}

	@PostMapping("/delete-file")
	@ResponseBody
	public ResponseData<?> deleteFile(@RequestBody Elec003UploadFileReq req) {
		logger.info("deleteFile reqId={}", req.getReqId());

		ResponseData<?> responseData = new ResponseData<>();
		try {
			electric003Service.deleteFile(req);
			responseData.setMessage(RESPONSE_MESSAGE.SAVE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseData.setMessage(e.getMessage());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}

		return responseData;
	}

	@PostMapping("/cal-money-from-unit")
	@ResponseBody
	public ResponseData<ElectricBillRes> calMoneyFromUnit(@RequestBody ElectricBillReq req) {
		ResponseData<ElectricBillRes> responseData = new ResponseData<>();
		try {
			responseData.setData(electric003Service.calMoneyFromUnit(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseData.setMessage(e.getMessage());
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}

		return responseData;
	}

	@GetMapping("/get-detail/{id}")
	@ResponseBody
	public ResponseData<Elec003DetailRes> getDetail(@PathVariable("id") Long id) {
		ResponseData<Elec003DetailRes> responseData = new ResponseData<>();
		try {
			responseData.setData(electric003Service.getDetail(id));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::getDetail ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@GetMapping("/get-flag-delete/{id}")
	@ResponseBody
	public ResponseData<String> findFlagDelete(@PathVariable("id") String reqId) {
		ResponseData<String> responseData = new ResponseData<>();
		try {
			responseData.setData(electric003Service.findFlagDelete(reqId));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("Electric003Controller::flag-delete ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

	@PostMapping("/send-to-sap")
	@ResponseBody
	public ResponseData<List<SapResponse>> sendToSAP(@RequestBody Electric003SapReq req) {
		ResponseData<List<SapResponse>> responseData = new ResponseData<List<SapResponse>>();
		try {
			responseData.setData(electric003Service.sendSap(req));
			responseData.setMessage(RESPONSE_MESSAGE.SUCCESS);
			responseData.setStatus(RESPONSE_STATUS.SUCCESS);
		} catch (Exception e) {
			logger.error("electric003Service::sendToSAP ", e);
			responseData.setMessage(RESPONSE_MESSAGE.ERROR500);
			responseData.setStatus(RESPONSE_STATUS.FAILED);
		}
		return responseData;
	}

}
