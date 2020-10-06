package aot.electric.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.common.constant.WaterConstants;
import aot.common.service.ElectricBillService;
import aot.common.service.RicNoGenerator;
import aot.common.vo.request.ElectricBillReq;
import aot.common.vo.response.ElectricBillRes;
import aot.common.vo.response.FileResponse;
import aot.electric.model.RicElectricMeter;
import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricRateChargeConfig;
import aot.electric.model.RicElectricReq;
import aot.electric.model.RicElectricReqFile;
import aot.electric.model.SapCustomer;
import aot.electric.repository.Electric003Dao;
import aot.electric.repository.jpa.RicElectricMeterRepository;
import aot.electric.repository.jpa.RicElectricRateChargeConfigRepository;
import aot.electric.repository.jpa.RicElectricRateChargeRepository;
import aot.electric.repository.jpa.RicElectricReqFileRepository;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.electric.repository.jpa.SapCustomerRepository;
import aot.electric.vo.request.Elec003ConfigReq;
import aot.electric.vo.request.Elec003DetailSaveVo;
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
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.ecm.constant.ECMConstant;
import aot.util.ecm.domain.CreateRICFolder;
import aot.util.ecm.domain.CreateRICFolderResponse;
import aot.util.ecm.domain.DownloadDoc;
import aot.util.ecm.domain.DownloadDocResponse;
import aot.util.ecm.domain.UploadDoc;
import aot.util.ecm.domain.UploadDocResponse;
import aot.util.ecm.service.ECMService;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_4_4;
import aot.util.sapreqhelper.SapArRequest_5_1;
import aot.util.sapreqhelper.SapArRequest_6_1;
import aot.util.sapreqhelper.SapArRequest_6_2;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric003Service {

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private RicElectricRateChargeRepository ricElectricRateChargeRepository;

	@Autowired
	private Electric003Dao electric003Dao;

	@Autowired
	private RicElectricRateChargeConfigRepository ricElectricRateChargeConfigRepository;

	@Autowired
	private SapCustomerRepository sapCustomerRepository;

	@Autowired
	private RicElectricReqFileRepository ricElectricReqFileRepository;

	@Autowired
	private RicElectricMeterRepository ricElectricMeterRepository;

	@Autowired
	private ElectricBillService electricBillService;

	@Autowired
	private SysConstantService sysConstantService;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapArRequest_4_4 sapArRequest_4_4;

	@Autowired
	private SapArRequest_5_1 sapArRequest_5_1;

	@Autowired
	private SapArRequest_6_1 sapArRequest_6_1;

	@Autowired
	private SapArRequest_6_2 sapArRequest_6_2;

	@Autowired
	private RicNoGenerator ricNoGenerator;

	@Autowired
	private ECMService ecmService;
	private static final Logger logger = LoggerFactory.getLogger(Electric003Service.class);

	@Transactional(rollbackOn = { Exception.class })
	public RicElectricReq save(Elec003SaveVo form) throws Exception {
		RicElectricReq dataSave = new RicElectricReq();
		dataSave.setRequestStatus(RICConstants.STATUS.YES);
		dataSave.setApproveStatus(RICConstants.STATUS.PENDING);
		dataSave.setSapStatusCash(RICConstants.STATUS.PENDING);
		dataSave.setSapStatusLg(RICConstants.STATUS.PENDING);
		dataSave.setSapStatusPackages(RICConstants.STATUS.PENDING);
		dataSave.setIdCard(form.getIdCard());
		dataSave.setAirport(UserLoginUtils.getUser().getAirportCode());
		dataSave.setCustomerType(form.getCustomerType());
		dataSave.setCustomerCode(form.getCustomerCode());
		dataSave.setCustomerName(form.getCustomerName());
		dataSave.setCustomerBranch(form.getCustomerBranch());

		dataSave.setRequestStartDate(ConvertDateUtils.parseStringToDate(form.getRequestStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataSave.setRequestEndDate(ConvertDateUtils.parseStringToDate(form.getRequestEndDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataSave.setContractNo(form.getContractNo());
		dataSave.setAddressDocument(form.getAddressDocument());
		dataSave.setRequestType(form.getRequestType());
		dataSave.setApplyType(form.getApplyType());
		dataSave.setVoltageType(form.getVoltageType());
		dataSave.setElectricRateType(form.getElectricRateType());
		dataSave.setElectricVoltageType(form.getElectricVoltageType());
		dataSave.setDefaultMeterNo(form.getDefaultMeterNo());
		dataSave.setMeterSerialNo(form.getMeterSerialNo());
		dataSave.setMeterType(form.getMeterType());
		dataSave.setAdhocType(form.getAdhocType());
		dataSave.setAdhocUnit(form.getAdhocUnit());
		dataSave.setAdhocChargeRate(form.getAdhocChargeRate());
		dataSave.setInstallPosition(form.getInstallPosition());
		dataSave.setInstallPositionService(form.getInstallPositionService());
		dataSave.setRentalAreaCode(form.getRentalAreaCode());
		dataSave.setRentalAreaName(form.getRentalAreaName());
		dataSave.setPaymentType(form.getPaymentType());
		dataSave.setRemark(form.getRemark());
		dataSave.setMeterName(form.getMeterName());
		dataSave.setBankName(form.getBankName());
		dataSave.setBankBranch(form.getBankBranch());
		dataSave.setBankExplanation(form.getBankExplanation());
		dataSave.setBankGuaranteeNo(form.getBankGuaranteeNo());
		dataSave.setBankExpNo(ConvertDateUtils.parseStringToDate(form.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		dataSave.setFlagInfo(RICConstants.STATUS.NO);
		dataSave.setCreatedDate(new Date());
		dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setIsDelete(RICConstants.STATUS.NO);
		if(!WaterConstants.REQUEST_TYPE.PACKAGES.equals(form.getRequestType())) {
			dataSave.setSumChargeRates(form.getSumChargeRates() != null ? form.getSumChargeRates() : BigDecimal.ZERO);
			dataSave.setSumVatChargeRates(
					form.getSumVatChargeRates() != null ? form.getSumVatChargeRates() : BigDecimal.ZERO);
			dataSave.setTotalChargeRate(form.getTotalChargeRate() != null ? form.getTotalChargeRate() : BigDecimal.ZERO);
		} else {
			dataSave.setSumChargeRates(NumberUtils.roundUpTwoDigit(form.getAdhocChargeRate()));
			dataSave.setSumVatChargeRates(sysConstantService.getSumVat(dataSave.getSumChargeRates()));
			dataSave.setTotalChargeRate(sysConstantService.getTotalVat(dataSave.getSumChargeRates()));
		}
		
		// add transaction
		String ricNo = ricNoGenerator.getRicNo();
		dataSave.setTransactionNoCash(ricNo);
		dataSave.setTransactionNoLg(ricNoGenerator.getRicNo());
		// this.createFolder(ricNo);
		dataSave = ricElectricReqRepository.save(dataSave);
		
		if(form.getRequestType() != WaterConstants.REQUEST_TYPE.PACKAGES) {
			RicElectricRateCharge dataRateSave = null;
			for (Elec003DetailSaveVo data : form.getServiceCharge()) {
				dataRateSave = new RicElectricRateCharge();
				dataRateSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
				dataRateSave.setEmployeeId(StringUtils.isNotBlank(form.getIdCard()) ? form.getIdCard() : "-");
				dataRateSave.setEmployeeCode(form.getCustomerCode());
				dataRateSave.setEmployeeName(form.getCustomerName());
				dataRateSave.setElectricPhase(data.getElectricPhase());
				dataRateSave.setChargeType(data.getChargeType());
				dataRateSave.setElectricAmpere(data.getElectricAmpere());
				dataRateSave.setChargeRate(data.getChargeRate());
				dataRateSave.setChargeVat(data.getChargeVat());
				dataRateSave.setTotalChargeRate(data.getTotalChargeRate());
				dataRateSave.setReqId(dataSave.getReqId());
				dataRateSave.setIsDelete(FLAG.N_FLAG);
				ricElectricRateChargeRepository.save(dataRateSave);
			}
		}
		return dataSave;
	}

	public List<Electric003Res> findElec(Elec003FindReq req) {
		/* check change success */
		electric003Dao.updateIsDeletedOnChangeSuccess();
		
		/* search */
		List<Electric003Res> dataRes = electric003Dao.findElec(req);
		return dataRes;
	}

	public String findFlagDelete(String reqId) {
		RicElectricReq dataRes = ricElectricReqRepository.findByReqId(Long.valueOf(reqId));
		return dataRes.getIsDelete();
	}

	public List<RicElectricMeter> getListElectricMeter(Elec003FindMeterReq req) {
		List<RicElectricMeter> list = new ArrayList<>();
		list = electric003Dao.getElectricMeterByStatus(req);
		return list;
	}

	public List<Electric007Res> findRateChargeConfig(Elec003ConfigReq req) {
		logger.info("findRateChargeConfig");
		req.setElectricAmpere(req.getElectricAmpere().trim());
		req.setElectricPhase(req.getElectricPhase().trim());
		List<RicElectricRateChargeConfig> dataList = ricElectricRateChargeConfigRepository
				.findByRangeAmpereByPhase(req.getElectricAmpere(), req.getElectricPhase());
		Electric007Res res = null;
		List<Electric007Res> resList = new ArrayList<>();

		for (RicElectricRateChargeConfig data : dataList) {
			res = new Electric007Res();
			try {
				res.setRateConfigId(data.getRateConfigId());
				res.setModifiedYear(ConvertDateUtils.formatDateToString(data.getModifiedYear(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setPhase(data.getPhase());
				res.setServiceType(data.getServiceType());
				res.setRangeAmpere(data.getRangeAmpere());
				res.setChargeRates(data.getChargeRates());
				res.setRemark(data.getRemark());
				res.setUpdatedBy(data.getUpdatedBy());
				res.setUpdatedDate(ConvertDateUtils.formatDateToString(data.getUpdatedDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				resList.add(res);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return resList;
	}

	public List<Electric003CustomerRes> getSAPCustumer(String type) throws Exception {
		List<SapCustomer> dataFind = sapCustomerRepository.findByCustomerType(type);
		List<Electric003CustomerRes> dataRes = new ArrayList<Electric003CustomerRes>();
		Electric003CustomerRes dataSet = null;
		for (SapCustomer entity : dataFind) {
			dataSet = new Electric003CustomerRes();
			dataSet.setCustomerId(entity.getCustomerId());
			dataSet.setCustomerCode(entity.getCustomerCode());
			dataSet.setCustomerName(entity.getCustomerName());
			dataSet.setContactName(entity.getContactName());
			dataSet.setPhoneNo(entity.getPhoneNo());
			dataSet.setAddress(entity.getAddress());
			dataSet.setStatus(entity.getStatus());
			dataSet.setCustomerType(entity.getCustomerType());
			dataRes.add(dataSet);
		}
		return dataRes;
	}

	public String createFolder(String ricNo) throws Exception {
		CreateRICFolder ricFolder = new CreateRICFolder();
		ricFolder.setXCategory(ECMConstant.CATEGORY.ELECTRICITY);
		ricFolder.setXAirport(UserLoginUtils.getUser().getAirportCode());
		ricFolder.setXRICNumber(ricNo);
		System.out.println("ricNo :" + ricNo);
		CreateRICFolderResponse ecmres = ecmService.createFolder(ricFolder, UserLoginUtils.getDateNow());
		return ecmres.getCreateRICFolderResult();
	}

	@Transactional(rollbackOn = { Exception.class })
	public String uploadFile(Elec003UploadFileReq req) throws IOException {
		logger.info("uploadFile filename={}", req.getFile().getOriginalFilename());
		byte[] byteArray = req.getFile().getBytes();
		String docName = FilenameUtils.removeExtension(req.getFile().getOriginalFilename());
		String extension = FilenameUtils.getExtension(req.getFile().getOriginalFilename());

		RicElectricReq elereq = ricElectricReqRepository.findByReqId(Long.parseLong(req.getReqId()));

		String filename = ecmService.randomName();
		UploadDoc uploadReq = new UploadDoc();
		uploadReq.setXAirport(UserLoginUtils.getUser().getAirportCode());
		uploadReq.setXCategory(ECMConstant.CATEGORY.ELECTRICITY);
		uploadReq.setXdocName(docName);
		uploadReq.setXfileName(filename);
		uploadReq.setXcontent(byteArray);
		uploadReq.setXRICNumber(elereq.getTransactionNoCash()); // TODO ricNO
		UploadDocResponse ecmres = ecmService.uploadDoc(uploadReq, UserLoginUtils.getDateNow());

		RicElectricReqFile file = new RicElectricReqFile();
		file.setReqId(Long.valueOf(req.getReqId()));
		file.setReqDocName(docName);
		file.setReqFileName(filename);
		file.setReqFileExtension(extension);
		file.setCreatedBy(UserLoginUtils.getCurrentUsername());
		file.setCreatedDate(UserLoginUtils.getDateNow());
		file.setIsDelete(RICConstants.STATUS.NO);
		file.setPeriod(ecmService.converDateforECM(UserLoginUtils.getDateNow()));
		ricElectricReqFileRepository.save(file);

		return ecmres.getUploadDocResult();
	}

	@Transactional(rollbackOn = { Exception.class })
	public FileResponse downloadFile(Long id) throws IOException {

		RicElectricReqFile fileobj = ricElectricReqFileRepository.findByReqFileId(id);
		RicElectricReq elereq = ricElectricReqRepository.findByReqId(fileobj.getReqId());

		DownloadDoc downloadReq = new DownloadDoc();
		downloadReq.setXAirport(UserLoginUtils.getUser().getAirportCode());
		downloadReq.setXCategory(ECMConstant.CATEGORY.ELECTRICITY);
		downloadReq.setXdocName(fileobj.getReqDocName());
		downloadReq.setXRICNumber(elereq.getTransactionNoCash()); // TODO ricNO
		downloadReq.setXPeriod(fileobj.getPeriod());
		DownloadDocResponse ecmres = ecmService.downloadDoc(downloadReq, UserLoginUtils.getDateNow());
		FileResponse fileRes = new FileResponse();
		fileRes.setFileContent(ecmres.getDownloadDocResult());
		fileRes.setFileName(fileobj.getReqDocName() + "." + fileobj.getReqFileExtension());
		return fileRes;
	}

	public List<Elec003UploadFileRes> getFileList(Elec003UploadFileReq req) {
		logger.info("getFileList reqId={}", req.getReqId());
		List<RicElectricReqFile> fileList = ricElectricReqFileRepository.findByReqId(Long.valueOf(req.getReqId()));
		Elec003UploadFileRes data = null;
		List<Elec003UploadFileRes> dataList = new ArrayList<>();
		for (RicElectricReqFile file : fileList) {
			data = new Elec003UploadFileRes();
			data.setReqFileId(file.getReqFileId());
			data.setReqFileName(file.getReqDocName());
			data.setReqFileExtension(file.getReqFileExtension());
			data.setCreatedDate(ConvertDateUtils.formatDateToString(file.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			dataList.add(data);
		}
		return dataList;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void deleteFile(Elec003UploadFileReq req) {
		RicElectricReqFile file = ricElectricReqFileRepository.findById(Long.valueOf(req.getReqFileId())).get();
		file.setIsDelete("Y");
		ricElectricReqFileRepository.save(file);
	}

	public Electric003MeterRes findReqCancelByMeter(Electric006Req request) throws Exception {
		logger.info("findReqCancelByMeter");
		RicElectricMeter meter = new RicElectricMeter();
		Electric003MeterRes res = null;
		// find data meter
		if (StringUtils.isNotEmpty(request.getNewSerialNo())) {
			meter = ricElectricMeterRepository.findBySerialNo(request.getNewSerialNo());
		}
		// set data for return
		res = new Electric003MeterRes();
		res.setNewSerialNo(meter.getSerialNo());
		res.setNewMeterName(meter.getMeterName());
		res.setNewMeterType(meter.getMeterType());
		res.setNewMeterLocation(meter.getMeterLocation());
		res.setNewFunctionalLocation(meter.getFunctionalLocation());

		return res;
	}

	public ElectricBillRes calMoneyFromUnit(ElectricBillReq req) throws Exception {
//		req.setFtValue(new BigDecimal(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue())); 
		ElectricBillRes dataRes = electricBillService.calculate(req);
		return dataRes;
	}

	public Elec003DetailRes getDetail(Long id) {
		Elec003DetailRes dataRes = new Elec003DetailRes();
		RicElectricReq dataFind = ricElectricReqRepository.findById(id).get();
		dataRes.setRequestStatus(dataFind.getRequestStatus());
		dataRes.setApproveStatus(dataFind.getApproveStatus());
		dataRes.setReqId(id);
		dataRes.setIdCard(dataFind.getIdCard());
		dataRes.setCustomerType(dataFind.getCustomerType());
		dataRes.setCustomerCode(dataFind.getCustomerCode());
		dataRes.setCustomerName(dataFind.getCustomerName());
		dataRes.setCustomerBranch(dataFind.getCustomerBranch());
		dataRes.setContractNo(dataFind.getContractNo());
		dataRes.setRequestStartDate(dataFind.getRequestStartDate());
		dataRes.setRequestEndDate(dataFind.getRequestEndDate());
		dataRes.setRequestEndDateStr(ConvertDateUtils.formatDateToString(dataFind.getRequestEndDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataRes.setRequestStartDateStr(ConvertDateUtils.formatDateToString(dataFind.getRequestStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataRes.setRequestStatus(dataFind.getRequestStatus());
		dataRes.setAirport(dataFind.getAirport());
		dataRes.setAddressDocument(dataFind.getAddressDocument());
		dataRes.setRequestType(dataFind.getRequestType());
		dataRes.setApplyType(dataFind.getApplyType());
		dataRes.setVoltageType(dataFind.getVoltageType());
		dataRes.setElectricRateType(dataFind.getElectricRateType());
		dataRes.setElectricVoltageType(dataFind.getElectricVoltageType());
		dataRes.setDefaultMeterNo(dataFind.getDefaultMeterNo());
		dataRes.setMeterSerialNo(dataFind.getMeterSerialNo());
		dataRes.setMeterType(dataFind.getMeterType());
		dataRes.setAdhocType(dataFind.getAdhocType());
		dataRes.setAdhocUnit(dataFind.getAdhocUnit());
		dataRes.setAdhocChargeRate(dataFind.getAdhocChargeRate());
		dataRes.setInstallPosition(dataFind.getInstallPosition());
		dataRes.setInstallPositionService(dataFind.getInstallPositionService());
		dataRes.setRentalAreaCode(dataFind.getRentalAreaCode());
		dataRes.setRentalAreaName(dataFind.getRentalAreaName());
		dataRes.setPaymentType(dataFind.getPaymentType());
		dataRes.setRemark(dataFind.getRemark());
		dataRes.setCreatedDate(dataFind.getCreatedDate());
		dataRes.setCreatedBy(dataFind.getCreatedBy());
		dataRes.setUpdatedDate(dataFind.getUpdatedDate());
		dataRes.setUpdatedBy(dataFind.getUpdatedBy());
		dataRes.setIsDelete(dataFind.getIsDelete());
		dataRes.setMeterName(dataFind.getMeterName());
		dataRes.setBankName(dataFind.getBankName());
		dataRes.setBankBranch(dataFind.getBankBranch());
		dataRes.setBankExplanation(dataFind.getBankExplanation());
		dataRes.setBankGuaranteeNo(dataFind.getBankGuaranteeNo());
		dataRes.setBankExpNo(dataFind.getBankExpNo());
		dataRes.setBankExpStr(ConvertDateUtils.formatDateToString(dataFind.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));

		List<RicElectricRateCharge> dataFindDetail = ricElectricRateChargeRepository.findByReqId(id);
		dataRes.setRateCharge(dataFindDetail);
		return dataRes;
	}

	public List<SapResponse> sendSap(Electric003SapReq req) throws Exception {
		List<SapResponse> response = new ArrayList<>();
		ArRequest dataSend = new ArRequest();
		SapResponse dataRes = null;
		SapConnectionVo reqConnection = null;
		RicElectricReq electricReq = ricElectricReqRepository.findById(Long.valueOf(req.getReqId())).get();

		if (SAPConstants.REQUEST_TYPE.PACKAGES.equals(electricReq.getRequestType())) {
			// -------------------- PACKAGES -----------------

			/* sapArRequest_4_4 */
			dataSend = sapArRequest_4_4.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					req.getReqId(), DoctypeConstants.I1);
			/* __________________ call SAP __________________ */
			dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(electricReq.getReqId());
			reqConnection.setTableName("ric_electric_req");
			reqConnection.setColumnId("req_id");
			reqConnection.setColumnInvoiceNo("invoice_no_packages");
			reqConnection.setColumnTransNo("transaction_no_packages");
			reqConnection.setColumnSapJsonReq("sap_json_req_packages");
			reqConnection.setColumnSapJsonRes("sap_json_res_packages");
			reqConnection.setColumnSapError("sap_error_desc_packages");
			reqConnection.setColumnSapStatus("sap_status_packages");

			/* __________________ set connection SAP __________________ */
			response.add(sapARService.setSapConnection(reqConnection));
		} else {
			// -------------------- Cash -----------------
			/* sapArRequest_5_1 */
			dataSend = sapArRequest_5_1.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					req.getReqId(), DoctypeConstants.I1);
			/* __________________ call SAP __________________ */
			dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(electricReq.getReqId());
			reqConnection.setTableName("ric_electric_req");
			reqConnection.setColumnId("req_id");
			reqConnection.setColumnInvoiceNo("invoice_no_cash");
			reqConnection.setColumnTransNo("transaction_no_cash");
			reqConnection.setColumnSapJsonReq("sap_json_req_cash");
			reqConnection.setColumnSapJsonRes("sap_json_res_cash");
			reqConnection.setColumnSapError("sap_error_desc_cash");
			reqConnection.setColumnSapStatus("sap_status_cash");

			/* __________________ set connection SAP __________________ */
			response.add(sapARService.setSapConnection(reqConnection));

			// --------- LG
			// ====================PAYMENT_TYPE_BANK_GUARANTEE ============================
			if (SAPConstants.PAYMENT_TYPE.BANK_GUARANTEE.equals(electricReq.getPaymentType())) {
				/* sapArRequest_6_1 */
				dataSend = sapArRequest_6_1.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, Long.valueOf(req.getReqId()), DoctypeConstants.IA);
			} else {
				// ============================= PAYMENT_TYPE_CASH ============================
				/* sapArRequest_6_2 */
				dataSend = sapArRequest_6_2.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, Long.valueOf(req.getReqId()), DoctypeConstants.IA);
			}
			/* __________________ call SAP __________________ */
			dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(electricReq.getReqId());
			reqConnection.setTableName("ric_electric_req");
			reqConnection.setColumnId("req_id");
			reqConnection.setColumnInvoiceNo("invoice_no_lg");
			reqConnection.setColumnTransNo("transaction_no_lg");
			reqConnection.setColumnSapJsonReq("sap_json_req_lg");
			reqConnection.setColumnSapJsonRes("sap_json_res_lg");
			reqConnection.setColumnSapError("sap_error_desc_lg");
			reqConnection.setColumnSapStatus("sap_status_lg");

			/* __________________ set connection SAP __________________ */
			response.add(sapARService.setSapConnection(reqConnection));
		}

		return response;
	}

	public ByteArrayOutputStream downloadTemplate(String customerCode, String customerName, String contractNo,
			String requestStatus, String rentalAreaName, String installPositionService, String customerType)
			throws IOException {
		Elec003FindReq form = new Elec003FindReq();
		form.setCustomerCode(customerCode);
		form.setCustomerName(customerName);
		form.setContracNo(contractNo);
		form.setRequestStatus(requestStatus);
		form.setRentalAreaName(rentalAreaName);
		form.setInstallPositionService(installPositionService);
		form.setCustomerType(customerType);
		List<Electric003Res> dataExport = new ArrayList<Electric003Res>();
		dataExport = findElec(form);

		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle tdStyle = ExcelUtils.createTdCellStyle(workbook);
		CellStyle TopicCenterlite = ExcelUtils.createTopicCenterliteStyle(workbook);
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
		CellStyle TopicRight = ExcelUtils.createTopicRightStyle(workbook);
		CellStyle TopicCenter = ExcelUtils.createTopicCenterStyle(workbook);
		Sheet sheet = workbook.createSheet();
		int rowNum = 0;
		int cellNum = 0;

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setFontName(ExcelUtils.FONT_NAME.TH_SARABUN_PSK);

		CellStyle cellHeaderStyle = ExcelUtils.createThColorStyle(workbook, new XSSFColor(Color.LIGHT_GRAY));
		cellHeaderStyle.setFont(headerFont);

		tdRight.setFont(headerFont);
		tdLeft.setFont(headerFont);

		String[] header = { "ลำดับที่", "วันที่ขอใช้", "วันที่สิ้นสุด", "รหัสผู้ประกอบการ", "ผู้ประกอบการ",
				"เลขที่สัญญา", "Serial No. มิเตอร์", "พื้นที่เช่า (rental object)", "สถานที่ให้บริการ",
				"อัตราค่าภาระ(ไม่รวม Vat)", "อัตราค่าภาระ", "สถานะ", "อัตราค่าภาระ", "", "", "เงินประกัน", "", "" };
		String[] header2 = { "", "", "", "", "", "", "", "", "", "", "", "", "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ",
				"สถานะการส่งข้อมูลเข้าสู่ระบบ SAP", "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ",
				"สถานะการส่งข้อมูลเข้าสู่ระบบ SAP" };

		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		rowNum = 0;
		row = sheet.createRow(rowNum);
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(header[i]);
			cell.setCellStyle(TopicCenter);
			cell.setCellStyle(cellHeaderStyle);
			cellNum++;
		}

		rowNum++;
		row = sheet.createRow(rowNum);
		cellNum = 0;
		for (int i = 0; i < header2.length; i++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(header2[i]);
			cell.setCellStyle(TopicCenter);
			cell.setCellStyle(cellHeaderStyle);
			cellNum++;
		}

		// merge cell
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 14));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 17));

		rowNum++;
		int index = 1;
		for (Electric003Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDateStartReq());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDateEndReq());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerCode());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContracNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterSerialNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRentalAreaName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInstallPositionService());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSumChargeRates());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTotalChargeRate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStatus());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoCash());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDzdocNoCash());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatusCash());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoLg());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDzdocNoLg());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatusLg());
			cell.setCellStyle(tdLeft);

			rowNum++;
			index++;
		}

		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i < 18; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
