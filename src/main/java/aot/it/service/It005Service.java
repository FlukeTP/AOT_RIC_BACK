package aot.it.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
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

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.it.model.RicItCUTETrainingRoomUsageReport;
import aot.it.repository.It005Dao;
import aot.it.repository.jpa.RicItCUTETrainingRoomUsageReportRepository;
import aot.it.vo.request.It005Req;
import aot.it.vo.response.It005Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_12;
import aot.util.sapreqhelper.SapArRequest_4_18;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It005Service {

	private static final Logger logger = LoggerFactory.getLogger(It005Service.class);

	@Autowired
	private RicItCUTETrainingRoomUsageReportRepository ricItCUTETrainingRoomUsageReportRepository;

	@Autowired
	private It005Dao it005Dao;

	@Autowired
	private SapArRequest_1_12 sapArRequest_1_12;

	@Autowired
	private SapArRequest_4_18 sapArRequest_4_18;

	@Autowired
	private SAPARService sapARService;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(It005Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicItCUTETrainingRoomUsageReport CUTETrainingRoom = ricItCUTETrainingRoomUsageReportRepository
					.findById(Long.valueOf(request.getItTrainingRoomUsageId())).get();
			// set request
			if (SAPConstants.PAYMENT_TYPE.CASH_EN.equals(CUTETrainingRoom.getPaymentType())) {
				dataSend = sapArRequest_4_18.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, Long.valueOf(request.getItTrainingRoomUsageId()), DoctypeConstants.I8);
			} else if (SAPConstants.PAYMENT_TYPE.BILLING_EN.equals(CUTETrainingRoom.getPaymentType())) {
				dataSend = sapArRequest_1_12.getARRequestIt005(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, DoctypeConstants.I8, CUTETrainingRoom);
			}
			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(CUTETrainingRoom.getItTrainingRoomUsageId());
			reqConnection.setTableName("ric_it_cute_training_room_usage_report");
			reqConnection.setColumnId("it_training_room_usage_id");
			// reqConnection.setColumnInvoiceNo("invoice_no");
			// reqConnection.setColumnTransNo("transaction_no");
			// reqConnection.setColumnSapJsonReq("sap_json_req");
			// reqConnection.setColumnSapJsonRes("sap_json_res");
			// reqConnection.setColumnSapError("sap_error");
			// reqConnection.setColumnSapStatus("sap_status");

			/* __________________ set connection SAP __________________ */
			sapResponse = sapARService.setSapConnection(reqConnection);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<It005Res> getListAll(It005Req request) throws Exception {

		logger.info("getListAll");

		List<It005Res> list = new ArrayList<>();
		try {
			list = it005Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public List<It005Res> getListByRoom(It005Req request) throws Exception {

		logger.info("getListAll");

		List<It005Res> list = new ArrayList<>();
		try {
			list = it005Dao.findTimeForCalendar(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public It005Res findById(It005Req request) throws Exception {

		logger.info("findById");

		It005Res res = new It005Res();

		try {
			// update
			if (StringUtils.isNotEmpty(request.getItTrainingRoomUsageId())) {
				RicItCUTETrainingRoomUsageReport req = ricItCUTETrainingRoomUsageReportRepository
						.findById(Long.valueOf(request.getItTrainingRoomUsageId())).get();
				// set data for return
				res.setEntreprenuerCode(req.getEntreprenuerCode());
				res.setEntreprenuerName(req.getEntreprenuerName());
				res.setEntreprenuerBranch(req.getEntreprenuerBranch());
				res.setContractNo(req.getContractNo());
				res.setRentalAreaName(req.getRentalAreaName());
				res.setRoomType(req.getRoomType());
				res.setTimeperiod(req.getTimeperiod());
				res.setTotalChargeRates(req.getTotalChargeRates());
				res.setReqStartDate(ConvertDateUtils.formatDateToString(req.getReqStartDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRemark(req.getRemark());
				res.setAirport(req.getAirport());
				res.setPaymentType(req.getPaymentType());
				res.setBankName(req.getBankName());
				res.setBankBranch(req.getBankBranch());
				res.setBankExplanation(req.getBankExplanation());
				res.setBankGuaranteeNo(req.getBankGuaranteeNo());
				res.setBankExpNo(req.getBankExpNo());
				res.setReceiptNo(req.getReceiptNo());
				res.setInvoiceNo(req.getInvoiceNo());
				res.setInvoiceAddress(req.getInvoiceAddress());
				res.setColorTime(req.getColorTime());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(It005Req request) throws Exception {
		logger.info("save");

		RicItCUTETrainingRoomUsageReport req = null;
		try {
			if (StringUtils.isNotEmpty(request.getItTrainingRoomUsageId())) {
				req = ricItCUTETrainingRoomUsageReportRepository
						.findById(Long.valueOf(request.getItTrainingRoomUsageId())).get();
				// set data
				req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				req.setUpdatedDate(new Date());
			} else {
				req = new RicItCUTETrainingRoomUsageReport();
				// set data
				req.setCreatedBy(UserLoginUtils.getCurrentUsername());
				req.setCreatedDate(new Date());
				req.setSapStatus(RICConstants.STATUS.PENDING);
				req.setIsDeleted(RICConstants.STATUS.NO);
			}
			req.setEntreprenuerCode(request.getEntreprenuerCode());
			req.setEntreprenuerName(request.getEntreprenuerName());
			req.setEntreprenuerBranch(request.getEntreprenuerBranch());
			req.setContractNo(request.getContractNo());
			req.setRentalAreaName(request.getRentalAreaName());
			req.setRoomType(request.getRoomType());
			req.setTimeperiod(request.getTimeperiod());
			req.setTotalChargeRates(request.getTotalChargeRates());
			req.setReqStartDate(ConvertDateUtils.parseStringToDate(request.getReqStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			req.setRemark(request.getRemark());
			req.setAirport(UserLoginUtils.getUser().getAirportCode());
			req.setPaymentType(request.getPaymentType());
			req.setBankName(request.getBankName());
			req.setBankBranch(request.getBankBranch());
			req.setBankExplanation(request.getBankExplanation());
			req.setBankGuaranteeNo(request.getBankGuaranteeNo());
			req.setBankExpNo(request.getBankExpNo());
			req.setReceiptNo(request.getReceiptNo());
			req.setInvoiceNo(request.getInvoiceNo());
			req.setInvoiceAddress(request.getInvoiceAddress());
			req.setColorTime(request.getColorTime());
			// save data
			ricItCUTETrainingRoomUsageReportRepository.save(req);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It005Req form = new It005Req();
		List<It005Res> dataExport = new ArrayList<It005Res>();
		dataExport = getListAll(form);

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

		String[] header = { "ลำดับที่", "รหัสผู้ประกอบการ", "ผู้ประกอบการ", "เลขที่สัญญา", "พื้นที่เช่า", "ห้อง",
				"วันที่ใช้งาน", "ช่วงเวลา", "ประเภทการจ่ายเงิน", "หมายเหตุ" };

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
		for (It005Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerCode());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRentalAreaName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRoomType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReqStartDate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTimeperiod());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPaymentType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRemark());
			cell.setCellStyle(tdCenter);

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
