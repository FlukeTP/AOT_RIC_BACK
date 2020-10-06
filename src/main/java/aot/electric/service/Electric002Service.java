package aot.electric.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import aot.common.constant.RICConstants;
import aot.electric.model.RicElectricMeter;
import aot.electric.repository.Electric002Dao;
import aot.electric.repository.jpa.RicElectricMeterRepository;
import aot.electric.vo.request.Electric001Req;
import aot.electric.vo.request.Electric002Req;
import aot.electric.vo.request.Electric002SaveReq;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;


@Service
public class Electric002Service {

	private static final Logger logger = LoggerFactory.getLogger(Electric002Service.class);

	@Autowired
	Electric002Dao electric002Dao;

	@Autowired
	private RicElectricMeterRepository ricElectricMeterRepository;

	public List<RicElectricMeter> getallElectricMeter(Electric002Req form) {
		List<RicElectricMeter> eleMeterList = new ArrayList<RicElectricMeter>();
		eleMeterList = electric002Dao.getallElectricMeter(form);
		return eleMeterList;
	}

	public void saveElectricMeter(Electric002SaveReq request) {
		logger.info("saveElectricMeter", request);
		RicElectricMeter electricMeter = null;
			electricMeter = new RicElectricMeter();
			// set data
			electricMeter.setSerialNo(request.getSerialNo());
			electricMeter.setMeterType(request.getMeterType());
			electricMeter.setMeterName(request.getMeterName());
			electricMeter.setMultipleValue(request.getMultipleValue());
			electricMeter.setMeterLocation(request.getMeterLocation());
			electricMeter.setFunctionalLocation(request.getFunctionalLocation());
			electricMeter.setMeterStatus(request.getMeterStatus());
			electricMeter.setServiceNumber(request.getServiceNumber());
			electricMeter.setRemark(request.getRemark());
			electricMeter.setAirport(request.getAirport());
			electricMeter.setCreateDate(new Date());
			electricMeter.setCreatedBy(UserLoginUtils.getCurrentUsername());
			electricMeter.setIsDelete(RICConstants.STATUS.NO);
			// save data
			ricElectricMeterRepository.save(electricMeter);
	}
	
	public void editElectricMeter(Electric002SaveReq request) {
		logger.info("editElectricMeter", request);
		RicElectricMeter electricMeter = null;
			electricMeter = new RicElectricMeter();
			electricMeter = ricElectricMeterRepository.findById(request.getMeterId()).get();
			// set data
			electricMeter.setSerialNo(request.getSerialNo());
			electricMeter.setMeterType(request.getMeterType());
			electricMeter.setMeterName(request.getMeterName());
			electricMeter.setMultipleValue(request.getMultipleValue());
			electricMeter.setMeterLocation(request.getMeterLocation());
			electricMeter.setFunctionalLocation(request.getFunctionalLocation());
			electricMeter.setMeterStatus(request.getMeterStatus());
			electricMeter.setServiceNumber(request.getServiceNumber());
			electricMeter.setAirport(request.getAirport());
			electricMeter.setRemark(request.getRemark());
			electricMeter.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			electricMeter.setUpdatedDate(new Date());
			// save data
			ricElectricMeterRepository.save(electricMeter);
	}
	
	public void multitable() {
		electric002Dao.nativeSCript();
	}
	
	public RicElectricMeter listEditId(Electric002Req form) {
		RicElectricMeter electricMeter = new RicElectricMeter();	
		electricMeter = ricElectricMeterRepository.findById(form.getMeterId()).get();
		return electricMeter;
	}
	
	public ByteArrayOutputStream downloadTemplate(String serial,String status) throws IOException {
		Electric002Req form = new Electric002Req();
		form.setSerialNo(serial);
		form.setStatus(status);
		List<RicElectricMeter> dataExport = new ArrayList<RicElectricMeter>();
		dataExport = getallElectricMeter(form);

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
				    "ลำดับที่", "สนามบิน", "Serial No. มิเตอร์", "ชื่อมิเตอร์", "ประเภทมิเตอร์", "สถานที่ตั้งมิเตอร์",
				    "Functional Location", "เลขที่ให้บริการ", "สถานะ", "ตัวคูณ", "หมายเหตุ" };
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
		for (RicElectricMeter data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getAirport());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSerialNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterLocation());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getFunctionalLocation());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceNumber());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterStatus());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMultipleValue());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRemark());
			cell.setCellStyle(tdLeft);


			rowNum++;
			index++;
		}

		
		// set width
		int width = 76;
		sheet.setColumnWidth(1, width * 40);
		sheet.setColumnWidth(2, width * 145);
		for (int i = 3; i < 8; i++) {
			sheet.setColumnWidth(i, width * 50);
		}
		sheet.setColumnWidth(5, width * 100);

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
