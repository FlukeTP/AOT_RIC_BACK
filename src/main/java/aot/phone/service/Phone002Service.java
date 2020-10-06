package aot.phone.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.phone.model.RicPhoneRateCharge;
import aot.phone.model.RicPhoneRateChargeConfig;
import aot.phone.model.RicPhoneReq;
import aot.phone.repository.Phone002Dao;
import aot.phone.repository.jpa.RicPhoneRateChargeConfigRepository;
import aot.phone.repository.jpa.RicPhoneRateChargeRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import aot.phone.vo.request.Phone002DetailReq;
import aot.phone.vo.request.Phone002ReceiptReq;
import aot.phone.vo.request.Phone002Req;
import aot.phone.vo.request.Phone002SaveReq;
import aot.phone.vo.response.Phone002DtlRes;
import aot.phone.vo.response.Phone002PhoneReqRes;
import aot.phone.vo.response.Phone002Res;
import aot.phone.vo.response.Phone002ServiceTypeRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_5_4;
import aot.util.sapreqhelper.SapArRequest_6_6;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

/**
 * Created by imake on 17/07/2019
 */
@Service
public class Phone002Service {
	private static final Logger logger = LoggerFactory.getLogger(Phone002Service.class);

	@Autowired
	private RicPhoneReqRepository ricPhoneReqRepository;

	@Autowired
	private RicPhoneRateChargeConfigRepository ricPhoneRateChargeConfigRepository;

	@Autowired
	private RicPhoneRateChargeRepository ricPhoneRateChargeRepository;

	@Autowired
	private Phone002Dao phone002Dao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapArRequest_5_4 sapArRequest_5_4;

	@Autowired
	private SapArRequest_6_6 sapArRequest_6_6;

	@Transactional(rollbackOn = { Exception.class })
	public RicPhoneReq save(Phone002SaveReq formReq) {
		logger.info("Phone002Service:: save");
		RicPhoneReq dataSave = new RicPhoneReq();
		dataSave.setEntrepreneurCode(formReq.getEntrepreneurCode());
		dataSave.setEntrepreneurName(formReq.getEntrepreneurName());
		dataSave.setBranchCustomer(formReq.getBranchCustomer());
		dataSave.setContractNo(formReq.getContractNo());
		dataSave.setPhoneNo(formReq.getPhoneNo());
		dataSave.setRentalAreaCode(formReq.getRentalAreaCode());
		dataSave.setRentalAreaName(formReq.getRentalAreaName());
		dataSave.setDescription(formReq.getDescription());
		dataSave.setPaymentType(formReq.getPaymentType());
		dataSave.setRequestStartDate(ConvertDateUtils.parseStringToDate(formReq.getRequestStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataSave.setRequestEndDate(ConvertDateUtils.parseStringToDate(formReq.getRequestEndDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataSave.setRemark(formReq.getRemark());
		dataSave.setBankName(formReq.getBankName());
		dataSave.setBankBranch(formReq.getBankBranch());
		dataSave.setBankExplanation(formReq.getBankExplanation());
		dataSave.setBankGuaranteeNo(formReq.getBankGuaranteeNo());
		dataSave.setBankExpNo(ConvertDateUtils.parseStringToDate(formReq.getBankExpNo(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		dataSave.setRequestStatus(RICConstants.STATUS.YES);
		dataSave.setSapStatusCash(RICConstants.STATUS.PENDING);
		dataSave.setSapStatusLg(RICConstants.STATUS.PENDING);
		dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setCreatedDate(new Date());
		dataSave.setIsDelete(RICConstants.STATUS.NO);
		dataSave.setFlagInfo(RICConstants.STATUS.NO);
		ricPhoneReqRepository.save(dataSave);
		// Save detail
		RicPhoneRateCharge detailSave = null;
		for (Phone002DetailReq detail : formReq.getServiceCharge()) {
			detailSave = new RicPhoneRateCharge();
			detailSave.setChargeRates(new BigDecimal(detail.getChargeRates()));
			detailSave.setTypeName(detail.getTypeName());
			detailSave.setServiceTypeName(detail.getServiceTypeName());
			detailSave.setChargeRates(new BigDecimal(detail.getChargeRates()));
			detailSave.setVat(new BigDecimal(detail.getVat()));
			detailSave.setTotalChargeRates(new BigDecimal(detail.getTotalChargeRates()));
			detailSave.setCreateDate(new Date());
			detailSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
			detailSave.setIsDelete(RICConstants.STATUS.NO);
			detailSave.setReqId(String.valueOf(dataSave.getPhoneReqId()));
			ricPhoneRateChargeRepository.save(detailSave);
		}
		return dataSave;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void saveReceiptNo(Phone002ReceiptReq form) {
		RicPhoneReq dataSave = new RicPhoneReq();
		try {
			dataSave = ricPhoneReqRepository.findById(Long.valueOf(form.getPhoneReqId())).get();
			dataSave.setReceiptNoCash(form.getReverseRecCash());
			dataSave.setReceiptNoLg(form.getReverseRecLg());
			ricPhoneReqRepository.save(dataSave);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<Phone002ServiceTypeRes> getServiceType(String serviceType) {
		List<Phone002ServiceTypeRes> dataRes = phone002Dao.getServiceType(serviceType);
		return dataRes;
	}

	public Phone002ServiceTypeRes getRateCharge(String phoneType, String serviceType) {
		RicPhoneRateChargeConfig dataFind = ricPhoneRateChargeConfigRepository.findByPhoneTypeByServiceType(phoneType,
				serviceType);
		Phone002ServiceTypeRes dataRes = new Phone002ServiceTypeRes();
		dataRes.setChargeRate(dataFind.getChargeRate());
		dataRes.setPhoneType(dataFind.getPhoneType());
		dataRes.setServiceType(dataFind.getServiceType());
		return dataRes;
	}

	public List<Phone002PhoneReqRes> getPhoneReq() {
		List<Phone002PhoneReqRes> dataRes = null;
//				phone002Dao.getServiceType(serviceType);
		return dataRes;
	}

	public List<Phone002Res> findData(@RequestBody Phone002SaveReq req) {
		logger.info("findData");

		List<Phone002Res> dataList = new ArrayList<>();

		try {
			dataList = phone002Dao.findData(req);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return dataList;
	}

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(Phone002Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicPhoneReq phoneReq = ricPhoneReqRepository.findById(Long.valueOf(request.getPhoneReqId())).get();
			if (StringUtils.isNotBlank(request.getShowButtonCash())) {
				/* sapArRequest_5_4 */
				dataSend = sapArRequest_5_4.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getPhoneReqId(), DoctypeConstants.IX);

				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(phoneReq.getPhoneReqId());
				reqConnection.setTableName("ric_phone_req");
				reqConnection.setColumnId("phone_req_id");
				reqConnection.setColumnInvoiceNo("invoice_no_cash");
				reqConnection.setColumnTransNo("transaction_no_cash");
				reqConnection.setColumnSapJsonReq("sap_json_req_cash");
				reqConnection.setColumnSapJsonRes("sap_json_res_cash");
				reqConnection.setColumnSapError("sap_error_desc_cash");
				reqConnection.setColumnSapStatus("sap_status_cash");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}

			if (StringUtils.isNotBlank(request.getShowButtonLg())) {
				/* sapArRequest_6_6 */
				dataSend = sapArRequest_6_6.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getPhoneReqId(), DoctypeConstants.IG);

				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(phoneReq.getPhoneReqId());
				reqConnection.setTableName("ric_phone_req");
				reqConnection.setColumnId("phone_req_id");
				reqConnection.setColumnInvoiceNo("invoice_no_lg");
				reqConnection.setColumnTransNo("transaction_no_lg");
				reqConnection.setColumnSapJsonReq("sap_json_req_lg");
				reqConnection.setColumnSapJsonRes("sap_json_res_lg");
				reqConnection.setColumnSapError("sap_error_desc_lg");
				reqConnection.setColumnSapStatus("sap_status_lg");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public Phone002Res getById(String idStr) {
		logger.info("getById id={}", idStr);

		Phone002Res res = null;
		Phone002DtlRes resDtl = null;
		List<Phone002DtlRes> resDtlList = new ArrayList<>();
		try {
			RicPhoneReq header = ricPhoneReqRepository.findById(Long.valueOf(idStr)).get();

			// set header
			res = new Phone002Res();

			res.setEntrepreneurCode(header.getEntrepreneurCode());
			res.setEntrepreneurName(header.getEntrepreneurName());
			res.setBranchCustomer(header.getBranchCustomer());
			res.setContractNo(header.getContractNo());
			res.setPhoneNo(header.getPhoneNo());
			res.setRentalAreaCode(header.getRentalAreaCode());
			res.setRentalAreaName(header.getRentalAreaName());
			res.setDescription(header.getDescription());
			res.setPaymentType(header.getPaymentType());
			res.setRequestStartDate(ConvertDateUtils.formatDateToString(header.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setRequestEndDate(ConvertDateUtils.formatDateToString(header.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setRemark(header.getRemark());
			res.setBankName(header.getBankName());
			res.setBankBranch(header.getBankBranch());
			res.setBankExplanation(header.getBankExplanation());
			res.setBankGuaranteeNo(header.getBankGuaranteeNo());
			res.setBankExpNo(ConvertDateUtils.formatDateToString(header.getBankExpNo(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			// set detail
			List<RicPhoneRateCharge> detailList = ricPhoneRateChargeRepository.findByReqId(idStr);
			if (detailList != null && detailList.size() > 0) {
				for (RicPhoneRateCharge detail : detailList) {
					resDtl = new Phone002DtlRes();

					resDtl.setTypeName(detail.getTypeName());
					resDtl.setServiceTypeName(detail.getServiceTypeName());
					resDtl.setChargeRates(detail.getChargeRates());
					resDtl.setVat(detail.getVat());
					resDtl.setTotalChargeRates(detail.getTotalChargeRates());
					resDtl.setRateChargeId(detail.getRateChargeId());
					resDtlList.add(resDtl);
				}
			}
			res.setPhone002DtlRes(resDtlList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}
	
	public ByteArrayOutputStream downloadTemplate(String entrepreneurName ,String contractNo ,String phoneNo  ,String requestStatus) throws IOException {
		Phone002SaveReq form = new Phone002SaveReq();
		form.setEntrepreneurName(entrepreneurName);;
		form.setContractNo(contractNo);
		form.setPhoneNo(phoneNo);
		form.setRequestStatus(requestStatus);
		List<Phone002Res> dataExport = new ArrayList<Phone002Res>();
		dataExport = findData(form);

		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle tdStyle = ExcelUtils.createTdCellStyle(workbook);
		CellStyle TopicCenterlite = ExcelUtils.createTopicCenterliteStyle(workbook);
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
		CellStyle tdCenter = ExcelUtils.createCenterCellStyle(workbook);
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
		tdCenter.setFont(headerFont);
		
		String[] header = { "ลำดับที่", "รหัสผู้ประกอบการ", "ชื่อผู้ประกอบการ", "เลขที่สัญญา", "เลขหมาย", "อัตราค่าภาระ(บาท)",
		"ภาษี(บาท)", "รวมเงิน(บาท)", "วันที่ขอใช้", "วันที่สิ้นสุด", "สถานะ", "อัตราค่าภาระ", "", "", "เงินประกัน", "", ""};
		
		String[] header2 = {"", "", "", "", "", "", "", "", "", "", "",
		"เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP",
		"เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP" };

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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 13));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 14, 16));

		rowNum++;
		int index = 1;
		for (Phone002Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntrepreneurCode());
			cell.setCellStyle(tdLeft);
			
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntrepreneurName());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPhoneNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getChargeRates()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getVat()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getTotalChargeRates()));
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
		for (int i = 1; i <= 16; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
