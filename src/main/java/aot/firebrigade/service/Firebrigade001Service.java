package aot.firebrigade.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.firebrigade.model.RicFirebrigadeManage;
import aot.firebrigade.repository.Firebrigade001Dao;
import aot.firebrigade.repository.jpa.RicFirebrigadeManageRepository;
import aot.firebrigade.vo.request.Firebrigade001Req;
import aot.firebrigade.vo.response.Firebrigade001Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_10;
import aot.util.sapreqhelper.SapArRequest_4_10;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Firebrigade001Service {

	private static final Logger logger = LoggerFactory.getLogger(Firebrigade001Service.class);

	@Autowired
	private RicFirebrigadeManageRepository ricFirebrigadeManageRepository;

	@Autowired
	private Firebrigade001Dao firebrigade001Dao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapArRequest_4_10 sapArRequest_4_10;

	@Autowired
	private SapArRequest_1_10 sapArRequest_1_10;

	public SapResponse sendSap(Firebrigade001Req request) throws Exception {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		RicFirebrigadeManage fire = ricFirebrigadeManageRepository.findById(Long.valueOf(request.getFireManageId()))
				.get();
		/* sapArRequest_X_X */
		if (CommunicateConstants.PAYMENT_TYPE.BILL.DESC_TH.equals(fire.getPaymentType())) {
			dataSend = sapArRequest_1_10.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					DoctypeConstants.IH, Long.valueOf(request.getFireManageId()), "FIRE001");
		} else {
			dataSend = sapArRequest_4_10.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					Long.valueOf(request.getFireManageId()), DoctypeConstants.I8);
		}

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);

		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(fire.getFireManageId());
		reqConnection.setTableName("ric_firebrigade_manage");
		reqConnection.setColumnId("fire_manage_id");
		// reqConnection.setColumnInvoiceNo("invoice_no");
		// reqConnection.setColumnTransNo("transaction_no");
		// reqConnection.setColumnSapJsonReq("sap_json_req");
		// reqConnection.setColumnSapJsonRes("sap_json_res");
		reqConnection.setColumnSapError("sap_error_desc");
		// reqConnection.setColumnSapStatus("sap_status");

		/* __________________ set connection SAP __________________ */
		sapResponse = sapARService.setSapConnection(reqConnection);

		return sapResponse;
	}

	public List<Firebrigade001Res> getListFirebrigadeManage(Firebrigade001Req request) throws Exception {

		logger.info("getListFirebrigadeManage");

		List<Firebrigade001Res> list = new ArrayList<>();
		try {
			list = firebrigade001Dao.findFirebrigadeManage(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public Firebrigade001Res findById(Firebrigade001Req request) throws Exception {
		logger.info("Firebrigade001Service::findById");
		Firebrigade001Res res = new Firebrigade001Res();
		try {
			RicFirebrigadeManage firebrigade = ricFirebrigadeManageRepository
					.findById(Long.valueOf(request.getFireManageId())).get();
			res.setFireManageId(firebrigade.getFireManageId());
			res.setCustomerCode(firebrigade.getCustomerCode());
			res.setCustomerName(firebrigade.getCustomerName());
			res.setCustomerBranch(firebrigade.getCustomerBranch());
			res.setContractNo(firebrigade.getContractNo());
			res.setAddress(firebrigade.getAddress());
			res.setCourseName(firebrigade.getCourseName());
			res.setStartDate(ConvertDateUtils.formatDateToString(firebrigade.getStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setPersonAmount(firebrigade.getPersonAmount());
			res.setChargeRates(firebrigade.getChargeRates());
			res.setVat(firebrigade.getVat());
			res.setTotalAmount(firebrigade.getTotalAmount());
			res.setRemark(firebrigade.getRemark());
			res.setPaymentType(firebrigade.getPaymentType());
			res.setUnit(firebrigade.getUnit());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Firebrigade001Req request) throws Exception {
		logger.info("Firebrigade001Service::save");

		RicFirebrigadeManage firebrigade = null;
		try {
			if (StringUtils.isNotBlank(request.getFireManageId())) {
				firebrigade = ricFirebrigadeManageRepository.findById(Long.valueOf(request.getFireManageId())).get();
				firebrigade.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				firebrigade.setUpdatedDate(new Date());
			} else {
				firebrigade = new RicFirebrigadeManage();
				firebrigade.setSapStatus(RICConstants.STATUS.PENDING);
				firebrigade.setCreatedBy(UserLoginUtils.getCurrentUsername());
				firebrigade.setCreateDate(new Date());
				firebrigade.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			firebrigade.setCustomerCode(request.getCustomerCode());
			firebrigade.setCustomerName(request.getCustomerName());
			firebrigade.setCustomerBranch(request.getCustomerBranch());
			firebrigade.setContractNo(request.getContractNo());
			firebrigade.setAddress(request.getAddress());
			firebrigade.setCourseName(request.getCourseName());
			firebrigade.setStartDate(ConvertDateUtils.parseStringToDate(request.getStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			firebrigade.setPersonAmount(Long.valueOf(request.getPersonAmount()));
			firebrigade.setChargeRates(new BigDecimal(request.getChargeRates()));
			firebrigade.setVat(new BigDecimal(request.getVat()));
			firebrigade.setTotalAmount(new BigDecimal(request.getTotalAmount()));
			firebrigade.setPaymentType(request.getPaymentType());
			firebrigade.setRemark(request.getRemark());
			firebrigade.setAirport(UserLoginUtils.getUser().getAirportCode());
			firebrigade.setUnit(request.getUnit());
			
			firebrigade = ricFirebrigadeManageRepository.save(firebrigade);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}

	public ByteArrayOutputStream downloadTemplate(String customerName, String contractNo, String courseName,
			String startDate) throws Exception {
		Firebrigade001Req form = new Firebrigade001Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setCourseName(courseName);
		form.setStartDate(startDate);
		List<Firebrigade001Res> dataExport = new ArrayList<Firebrigade001Res>();
		dataExport = getListFirebrigadeManage(form);

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

		String[] header = { "ลำดับที่", "หน่วยงาน/ผู้ประกอบการ", "เลขที่สัญญา", "หลักสูตร", "อัตราที่จัดเก็บ",
				"วันที่ฝึกอบรม", "จำนวนคน", "ค่าบริการรวมภาษี", "ประเภทการชำระเงิน", "ใบแจ้งหนี้อัตราค่าภาระ", "ใบเสร็จอัตราค่าภาระ", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP	" };

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
		int index = 1;
		for (Firebrigade001Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
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
			cell.setCellValue(data.getCourseName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getChargeRates(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getStartDate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(new BigDecimal(data.getPersonAmount()), false));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getTotalAmount(), false));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPaymentType());
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatus());
			cell.setCellStyle(tdLeft);

			rowNum++;
			index++;
		}

		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i <= header.length; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
