package aot.water.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import aot.electric.vo.request.Electric006Req;
import aot.electric.vo.response.Electric009Res;
import aot.water.model.RicWaterMeter;
import aot.water.repository.Water002Dao;
import aot.water.repository.jpa.RicWaterMeterRepository;
import aot.water.vo.request.Water002Req;
import aot.water.vo.request.Water002SaveReq;
import baiwa.util.ExcelUtils;

@Service
public class Water002Service {

	private static final Logger logger = LoggerFactory.getLogger(Water002Service.class);

	@Autowired
	Water002Dao water002Dao;

	@Autowired
	private RicWaterMeterRepository ricWaterMeterRepository;
//	

	public List<RicWaterMeter> getallWaterMeter(Water002Req req) {
		return  water002Dao.getallWaterMeter(req);
		
	}

	public void saveWaterMeter(Water002SaveReq request) {
		logger.info("saveWaterMeter", request);
		RicWaterMeter waterMeter = null;

		waterMeter = new RicWaterMeter();
		// set data
		waterMeter.setAirport(request.getAirport());
		waterMeter.setSerialNo(request.getSerialNo());
		waterMeter.setMeterType(request.getMeterType());
		waterMeter.setMeterName(request.getMeterName());
		waterMeter.setMultipleValue(request.getMultipleValue());
		waterMeter.setMeterLocation(request.getMeterLocation());
		waterMeter.setFunctionalLocation(request.getFunctionalLocation());
		waterMeter.setMeterStatus(request.getMeterStatus());
		waterMeter.setServiceNumber(request.getServiceNumber());
		waterMeter.setRemark(request.getRemark());
		waterMeter.setCreatedBy("Test Kittikun");
		waterMeter.setCreateDate(new Date());
		waterMeter.setIsDelete("N");
		// save data
		ricWaterMeterRepository.save(waterMeter);

	}
	
	public void editWaterMeter(Water002SaveReq request) {
		logger.info("saveWaterMeter", request);
		RicWaterMeter waterMeter = null;

		waterMeter = new RicWaterMeter();
		waterMeter = ricWaterMeterRepository.findById(request.getMeterId()).get();
		// set data
		waterMeter.setAirport(request.getAirport());
		waterMeter.setSerialNo(request.getSerialNo());
		waterMeter.setMeterType(request.getMeterType());
		waterMeter.setMeterName(request.getMeterName());
		waterMeter.setMultipleValue(request.getMultipleValue());
		waterMeter.setMeterLocation(request.getMeterLocation());
		waterMeter.setFunctionalLocation(request.getFunctionalLocation());
		waterMeter.setMeterStatus(request.getMeterStatus());
		waterMeter.setServiceNumber(request.getServiceNumber());
		waterMeter.setRemark(request.getRemark());
		waterMeter.setUpdatedBy("Phattartapong krintavee");
		waterMeter.setUpdatedDate(new Date());
		// save data
		ricWaterMeterRepository.save(waterMeter);

	}
	
	public RicWaterMeter listEditId(Water002Req form) {
		RicWaterMeter waterMeter = new RicWaterMeter();	
		waterMeter = ricWaterMeterRepository.findById(form.getMeterId()).get();
		return waterMeter;
	}

	public void multitable() {
		water002Dao.nativeSCript();
	}
	
	public ByteArrayOutputStream downloadTemplate(String serialNo ,String status ,String meterNp) throws IOException {
		Water002Req form = new Water002Req();
		form.setSerialNo(serialNo);
		form.setStatus(status);
		form.setMeterNp(meterNp);
		List<RicWaterMeter> dataExport = new ArrayList<RicWaterMeter>();
		dataExport = getallWaterMeter(form);

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
		
		String[] header = { "ลำดับที่", "ท่าอากาศยาน", "Serial No. มิเตอร์", "ชื่อมิเตอร์", "ขนาดมิเตอร์", "สถานที่ตั้งมิเตอร์", "สถานะ","หมายเหตุ" };

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
		for (RicWaterMeter data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getAirport());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSerialNo());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterName());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterLocation());
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterStatus());
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
		for(int i = 1 ; i<=6 ; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
