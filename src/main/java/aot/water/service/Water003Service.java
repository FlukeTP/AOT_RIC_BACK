package aot.water.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.common.constant.WaterConstants;
import aot.common.service.RicNoGenerator;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sap.domain.response.SapResponseVo;
import aot.util.sapreqhelper.CommonARRequest;
import aot.util.sapreqhelper.SapArRequest_4_6;
import aot.util.sapreqhelper.SapArRequest_5_3;
import aot.util.sapreqhelper.SapArRequest_6_3;
import aot.util.sapreqhelper.SapArRequest_6_4;
import aot.water.model.RicWaterInstallationChargeRatesConfig;
import aot.water.model.RicWaterInsuranceChargeRatesConfig;
import aot.water.model.RicWaterMaintenanceChargeRatesConfig;
import aot.water.model.RicWaterMaintenanceOtherConfig;
import aot.water.model.RicWaterMeter;
import aot.water.model.RicWaterRateCharge;
import aot.water.model.RicWaterReq;
import aot.water.repository.Water003Dao;
import aot.water.repository.jpa.RicWaterInstallationConfigRepository;
import aot.water.repository.jpa.RicWaterInsuranceChargeRatesConfigRepository;
import aot.water.repository.jpa.RicWaterMaintenanceConfigRepository;
import aot.water.repository.jpa.RicWaterMaintenanceOtherConfigRepository;
import aot.water.repository.jpa.RicWaterRateChargeRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import aot.water.vo.request.Water003DetailSaveVo;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water003Req;
import aot.water.vo.request.Water003SaveVo;
import aot.water.vo.response.Water003ConfigRes;
import aot.water.vo.response.Water003DetailRes;
import aot.water.vo.response.Water003Res;
import aot.water.vo.response.Water003WaterSizeRes;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water003Service {
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;

	@Autowired
	private RicWaterRateChargeRepository ricWaterRateChargeRepository;

	@Autowired
	private Water003Dao water003Dao;

	@Autowired
	private RicWaterMaintenanceConfigRepository ricWaterMaintenanceConfigRepository;

	@Autowired
	private RicWaterInstallationConfigRepository ricWaterInstallationConfigRepository;

	@Autowired
	private RicWaterInsuranceChargeRatesConfigRepository ricWaterInsuranceChargeRatesConfigRepository;

	@Autowired
	private RicWaterMaintenanceOtherConfigRepository ricWaterMaintenanceOtherConfigRepository;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicNoGenerator ricNoGenerator;

	@Autowired
	private SapArRequest_4_6 sapArRequest_4_6;

	@Autowired
	private SapArRequest_5_3 sapArRequest_5_3;

	@Autowired
	private SapArRequest_6_3 sapArRequest_6_3;

	@Autowired
	private SapArRequest_6_4 sapArRequest_6_4;

	@Transactional(rollbackOn = { Exception.class })
	public RicWaterReq save(Water003SaveVo form) {
		RicWaterReq dataSave = new RicWaterReq();
		dataSave.setRequestStatus(RICConstants.STATUS.YES);
		dataSave.setApproveStatus(RICConstants.STATUS.PENDING);
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
		dataSave.setWaterType1(form.getWaterType1());
		dataSave.setWaterType2(form.getWaterType2());
		dataSave.setWaterType3(form.getWaterType3());

		dataSave.setDefaultMeterNo(form.getDefaultMeterNo());
		dataSave.setMeterSerialNo(form.getMeterSerialNo());
		dataSave.setMeterType(form.getMeterType());
		dataSave.setAdhocType(form.getAdhocType());
		dataSave.setAdhocUnit(form.getAdhocUnit());
		dataSave.setPersonUnit(form.getPersonUnit());

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

		dataSave.setMeterSize(form.getMeterSize());
		dataSave.setInsuranceRates(form.getInsuranceRates());
		dataSave.setVatInsurance(form.getVatInsurance());
		dataSave.setTotalInsuranceChargeRates(form.getTotalInsuranceChargeRates());

		dataSave.setInstallRates(form.getInstallRates());
		dataSave.setVatInstall(form.getVatInstall());
		dataSave.setTotalInstallChargeRates(form.getTotalInstallChargeRates());
		dataSave.setTotalChargeRates(form.getTotalChargeRates());

		dataSave.setSumChargeRatesOther(
				form.getSumChargeRatesOther() != null ? form.getSumChargeRatesOther() : BigDecimal.ZERO);
		dataSave.setSumVatChargeRatesOther(
				form.getSumVatChargeRatesOther() != null ? form.getSumVatChargeRatesOther() : BigDecimal.ZERO);
		dataSave.setTotalChargeRateOther(
				form.getTotalChargeRateOther() != null ? form.getTotalChargeRateOther() : BigDecimal.ZERO);

		dataSave.setSumChargeRate(form.getSumChargeRate() != null ? form.getSumChargeRate() : BigDecimal.ZERO);

		dataSave.setFlagInfo(RICConstants.STATUS.NO);
		dataSave.setCreatedDate(new Date());
		dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setIsDelete(RICConstants.STATUS.NO);

		dataSave = ricWaterReqRepository.save(dataSave);

		RicWaterRateCharge dataRateSave = null;
		for (Water003DetailSaveVo data : form.getServiceCharge()) {
			dataRateSave = new RicWaterRateCharge();
			dataRateSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
			dataRateSave.setEmployeeId(StringUtils.isNotBlank(form.getIdCard()) ? form.getIdCard() : "-");
			dataRateSave.setEmployeeCode(form.getCustomerCode());
			dataRateSave.setEmployeeName(form.getCustomerName());
			dataRateSave.setWaterPhase(data.getWaterPhase());
			dataRateSave.setChargeType(data.getChargeType());
			dataRateSave.setWaterAmpere(data.getWaterAmpere());
			dataRateSave.setChargeRate(data.getChargeRate());
			dataRateSave.setChargeVat(data.getChargeVat());
			dataRateSave.setTotalChargeRate(data.getTotalChargeRate());
			dataRateSave.setReqId(dataSave.getReqId());
			dataRateSave.setIsDelete("N");
			ricWaterRateChargeRepository.save(dataRateSave);
		}
		return dataSave;
	}

	public Water003DetailRes getDetail(Long id) {
		Water003DetailRes dataRes = new Water003DetailRes();
		RicWaterReq dataFind = ricWaterReqRepository.findById(id).get();
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
		dataRes.setWaterType1(dataFind.getWaterType1());
		dataRes.setWaterType2(dataFind.getWaterType2());
		dataRes.setWaterType3(dataFind.getWaterType3());
		dataRes.setAdhocType(dataFind.getAdhocType());
		dataRes.setAdhocUnit(dataFind.getAdhocUnit());
		dataRes.setPersonUnit(dataFind.getAdhocUnit());
		dataRes.setMeterType(dataFind.getMeterType());
		dataRes.setDefaultMeterNo(dataFind.getDefaultMeterNo());
		dataRes.setMeterSerialNo(dataFind.getMeterSerialNo());
		dataRes.setInstallPosition(dataFind.getInstallPosition());
		dataRes.setInstallPositionService(dataFind.getInstallPositionService());
		dataRes.setRentalAreaCode(dataFind.getRentalAreaCode());
		dataRes.setRentalAreaName(dataFind.getRentalAreaName());
		dataRes.setPaymentType(dataFind.getPaymentType());

		dataRes.setMeterName(dataFind.getMeterName());
		dataRes.setBankName(dataFind.getBankName());
		dataRes.setBankBranch(dataFind.getBankBranch());
		dataRes.setBankExplanation(dataFind.getBankExplanation());
		dataRes.setBankGuaranteeNo(dataFind.getBankGuaranteeNo());
		dataRes.setBankExpNo(dataFind.getBankExpNo());
		dataRes.setBankExpStr(ConvertDateUtils.formatDateToString(dataFind.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		dataRes.setMeterSize(dataFind.getMeterSize());
		dataRes.setInsuranceRates(dataFind.getInsuranceRates());
		dataRes.setVatInsurance(dataFind.getVatInsurance());
		dataRes.setTotalInsuranceChargeRates(dataFind.getTotalInsuranceChargeRates());
		dataRes.setInstallRates(dataFind.getInstallRates());
		dataRes.setVatInstall(dataFind.getVatInstall());
		dataRes.setTotalInstallChargeRates(dataFind.getTotalInstallChargeRates());
		dataRes.setTotalChargeRates(dataFind.getTotalChargeRates());
		dataRes.setSumChargeRate(dataFind.getSumChargeRate());
		dataRes.setRemark(dataFind.getRemark());

		dataRes.setCreatedDate(dataFind.getCreatedDate());
		dataRes.setCreatedBy(dataFind.getCreatedBy());
		dataRes.setUpdatedDate(dataFind.getUpdatedDate());
		dataRes.setUpdatedBy(dataFind.getUpdatedBy());
		dataRes.setIsDelete(dataFind.getIsDelete());

		List<RicWaterRateCharge> dataFindDetail = ricWaterRateChargeRepository.findByReqId(id);
		dataRes.setRateCharge(dataFindDetail);
		return dataRes;
	}

	public List<RicWaterMeter> getListWaterMeter(Water003FindMeterReq req) {
		List<RicWaterMeter> list = new ArrayList<>();
		list = water003Dao.getElectricMeterByStatus(req);
		return list;
	}

	public List<Water003Res> findWaterList(Water003Req req) {
		/* check change success */
		water003Dao.updateIsDeletedOnChangeSuccess();
		
		/* search */
		List<Water003Res> dataRes = water003Dao.findWaterList(req);
		return dataRes;
	}

	public List<Water003WaterSizeRes> getWaterMeterSize() {
		List<RicWaterMaintenanceChargeRatesConfig> dataFindList = ricWaterMaintenanceConfigRepository.datalist();
		List<Water003WaterSizeRes> dataResList = new ArrayList<Water003WaterSizeRes>();
		Water003WaterSizeRes dataSet = null;
		for (RicWaterMaintenanceChargeRatesConfig dataFind : dataFindList) {
			dataSet = new Water003WaterSizeRes();
			dataSet.setWaterMaintenanceConfigId(dataFind.getWaterMaintenanceConfigId().toString());
			dataSet.setAirport(dataFind.getAirport());
			dataSet.setModifiedDate(ConvertDateUtils.formatDateToString(dataFind.getModifiedDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			dataSet.setWaterMeterSize(dataFind.getWaterMeterSize());
			dataSet.setChargeRates(dataFind.getChargeRates().toString());
			dataSet.setRemark(dataFind.getRemark());
			dataResList.add(dataSet);
		}
		return dataResList;
	}

	public Water003ConfigRes getRateConfig(String waterSize) {
		Water003ConfigRes dataRes = new Water003ConfigRes();
		RicWaterInsuranceChargeRatesConfig data = ricWaterInsuranceChargeRatesConfigRepository
				.findByWaterMeterSize(waterSize);
		dataRes.setInsuranceRates(data.getChargeRates().toString());
		RicWaterInstallationChargeRatesConfig data2 = ricWaterInstallationConfigRepository
				.findByWaterMeterSize(waterSize);
		dataRes.setInstallRates(data2.getChargeRates().toString());
		return dataRes;
	}

	public List<RicWaterMaintenanceOtherConfig> list() {
		List<RicWaterMaintenanceOtherConfig> maintenanceOther = new ArrayList<RicWaterMaintenanceOtherConfig>();
		maintenanceOther = ricWaterMaintenanceOtherConfigRepository.datalist();
		return maintenanceOther;
	}

	public SapResponseVo sendSap(Water003Req request) throws JsonProcessingException {
		SapResponseVo dataResSAP = new SapResponseVo();
		ArRequest dataSendCash = null;
		ArRequest dataSendLg = null;
		ArRequest dataSendPackages = null;
		SapResponse dataSendCashRes = null;
		SapResponse dataSendPackagesRes = null;
		SapResponse dataSendLgRes = null;
		RicWaterReq waterReq = ricWaterReqRepository.findById(request.getId()).get();

		/* _________________ check request type _________________ */
		switch (waterReq.getRequestType().trim()) {
		case WaterConstants.REQUEST_TYPE.TEMPORARY:
		case WaterConstants.REQUEST_TYPE.PERMANENT:
			if (WaterConstants.FLAG.X.equals(request.getReverseCashBtn())) {
				dataSendCash = sapArRequest_5_3.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getId(), DoctypeConstants.I1, "WATER003");
				dataSendCashRes = setDataSend(waterReq, dataSendCash, WaterConstants.CASH);
				dataResSAP.setObjSap1(dataSendCashRes);
			}

			/* _________________ check payment type _________________ */
			switch (waterReq.getPaymentType()) {
			case WaterConstants.PAYMENT_TYPE.CASH:
				if (WaterConstants.FLAG.X.equals(request.getReverseLgBtn())) {
					dataSendLg = sapArRequest_6_4.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, request.getId(), DoctypeConstants.IF, "WATER003");
					dataSendLgRes = setDataSend(waterReq, dataSendLg, WaterConstants.LG);
					dataResSAP.setObjSap2(dataSendLgRes);
				}
				break;
			case WaterConstants.PAYMENT_TYPE.BANK_GUARANTEE:
				if (WaterConstants.FLAG.X.equals(request.getReverseLgBtn())) {
					dataSendLg = sapArRequest_6_3.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, request.getId(), DoctypeConstants.IF, "WATER003");
					dataSendLgRes = setDataSend(waterReq, dataSendLg, WaterConstants.LG);
					dataResSAP.setObjSap2(dataSendLgRes);
				}
				break;
			}
			break;
		case WaterConstants.REQUEST_TYPE.PACKAGES:
			if (WaterConstants.FLAG.X.equals(request.getReverseCashBtn())) {
				dataSendPackages = sapArRequest_4_6.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getId(), DoctypeConstants.I6);
				dataSendPackagesRes = setDataSend(waterReq, dataSendPackages, WaterConstants.PACKAGES);
				dataResSAP.setObjSap1(dataSendPackagesRes);
			}
			break;
		case WaterConstants.REQUEST_TYPE.OTHER_TH:
			if (WaterConstants.FLAG.X.equals(request.getReverseCashBtn())) {
				dataSendCash = sapArRequest_5_3.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getId(), DoctypeConstants.I1, "WATER003_OTHER");
				dataSendCashRes = setDataSend(waterReq, dataSendCash, WaterConstants.CASH);
				dataResSAP.setObjSap1(dataSendCashRes);
			}
			break;
		}
		return dataResSAP;
	}

	private SapResponse setDataSend(RicWaterReq waterReq, ArRequest dataSend, String flag)
			throws JsonProcessingException {
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(waterReq.getReqId());
		reqConnection.setTableName("ric_water_req");
		reqConnection.setColumnId("req_id");

		switch (flag) {
		case WaterConstants.CASH:
			reqConnection.setColumnInvoiceNo("invoice_no_cash");
			reqConnection.setColumnTransNo("transaction_no_cash");
			reqConnection.setColumnSapJsonReq("sap_json_req_cash");
			reqConnection.setColumnSapJsonRes("sap_json_res_cash");
			reqConnection.setColumnSapError("sap_error_desc_cash");
			reqConnection.setColumnSapStatus("sap_status_cash");
			break;
		case WaterConstants.LG:
			reqConnection.setColumnInvoiceNo("invoice_no_lg");
			reqConnection.setColumnTransNo("transaction_no_lg");
			reqConnection.setColumnSapJsonReq("sap_json_req_lg");
			reqConnection.setColumnSapJsonRes("sap_json_res_lg");
			reqConnection.setColumnSapError("sap_error_desc_lg");
			reqConnection.setColumnSapStatus("sap_status_lg");
			break;
		case WaterConstants.PACKAGES:
			reqConnection.setColumnInvoiceNo("invoice_no_packages");
			reqConnection.setColumnTransNo("transaction_no_packages");
			reqConnection.setColumnSapJsonReq("sap_json_req_packages");
			reqConnection.setColumnSapJsonRes("sap_json_res_packages");
			reqConnection.setColumnSapError("sap_error_desc_packages");
			reqConnection.setColumnSapStatus("sap_status_packages");
			break;
		}
		return sapARService.setSapConnection(reqConnection);
	}
	
	public ByteArrayOutputStream downloadTemplate(String customerCode,String customerName,String contractNo,String requestStatus,String rentalAreaName,String installPositionService,String customerType) throws IOException {
		Water003Req form = new Water003Req();
		form.setCustomerCode(customerCode);
		form.setCustomerName(customerName);
		form.setContracNo(contractNo);
		form.setRequestStatus(requestStatus);
		form.setRentalAreaName(rentalAreaName);
		form.setInstallPositionService(installPositionService);
		form.setCustomerType(customerType);
		List<Water003Res> dataExport = new ArrayList<Water003Res>();
		dataExport = findWaterList(form);

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

		String[] header = {
			    "ลำดับที่", "รหัสผู้ประกอบการ", "ผู้ประกอบการ", "เลขที่สัญญา", "ชื่อมิเตอร์", "อัตราค่าภาระ",
			    "วันที่ขอใช้", "วันที่สิ้นสุด", "สถานะ", "เงินสด", "", "", "เงินประกัน"};
		String[] header2 = {
			    "", "", "", "", "", "", "", "", "",
			    "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะ",
			    "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะ" };
		
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 11));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 12, 14));

		rowNum++;
		int index = 1;
		for (Water003Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
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
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSumChargeRate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStartDate());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStartDate());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getStatus());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoCash());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptCash());
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
			cell.setCellValue(data.getReceiptLg());
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
		for (int i = 1; i < 15; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
