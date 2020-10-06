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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.water.model.RicWaterMeter;
import aot.water.model.RicWaterServiceChargeRatesConfig;
import aot.water.repository.jpa.RicWaterServiceConfigRepository;
import aot.water.vo.request.Water002Req;
import aot.water.vo.request.Water010Req;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water010Service {
	
	@Autowired
	private RicWaterServiceConfigRepository ricWaterServiceConfigRepository;
	
	public List<RicWaterServiceChargeRatesConfig> list() {
		List<RicWaterServiceChargeRatesConfig> chargeRatesConfigs = new ArrayList<RicWaterServiceChargeRatesConfig>();
		chargeRatesConfigs = ricWaterServiceConfigRepository.datalist();
		return chargeRatesConfigs;
	}
	
	public RicWaterServiceChargeRatesConfig listdata(Water010Req form) {
		RicWaterServiceChargeRatesConfig chargeRatesConfig = new RicWaterServiceChargeRatesConfig();
		chargeRatesConfig = ricWaterServiceConfigRepository.findById(form.getWaterServiceConfigId()).get();
		return chargeRatesConfig;
	}
	
	public void saveWaterService(Water010Req request) {
		RicWaterServiceChargeRatesConfig chargeRatesConfig = null;
		chargeRatesConfig = new RicWaterServiceChargeRatesConfig();
		// set data
		chargeRatesConfig.setAirport(request.getAirport());
		chargeRatesConfig.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH) );
		chargeRatesConfig.setWaterType(request.getWaterType());
		chargeRatesConfig.setChargeRates(request.getChargeRates());
		chargeRatesConfig.setRemark(request.getRemark());
		chargeRatesConfig.setCreatedBy(UserLoginUtils.getCurrentUsername());
		chargeRatesConfig.setCreatedDate(new Date());
		// save data
		ricWaterServiceConfigRepository.save(chargeRatesConfig);
	}
	
	public void editWaterService(Water010Req request) {
		RicWaterServiceChargeRatesConfig chargeRatesConfig = null;
		chargeRatesConfig = new RicWaterServiceChargeRatesConfig();
		// set data
		chargeRatesConfig = ricWaterServiceConfigRepository.findById(request.getWaterServiceConfigId()).get();
		chargeRatesConfig.setAirport(request.getAirport());
		chargeRatesConfig.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH) );
		chargeRatesConfig.setWaterType(request.getWaterType());
		chargeRatesConfig.setChargeRates(request.getChargeRates());
		chargeRatesConfig.setRemark(request.getRemark());
		chargeRatesConfig.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		chargeRatesConfig.setUpdatedDate(new Date());
		// save data
		ricWaterServiceConfigRepository.save(chargeRatesConfig);
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<RicWaterServiceChargeRatesConfig> dataExport = new ArrayList<RicWaterServiceChargeRatesConfig>();
		dataExport = list();

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
		
		String[] header = { "ลำดับที่", "ท่าอากาศยาน", "วันที่มีผล", "ประเภท", "อัตราค่าภาระ", "วันที่ปรับปรุง", "ปรับปรุงโดย", "หมายเหตุ" };

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
		for (RicWaterServiceChargeRatesConfig data : dataExport) {
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
			cell.setCellValue(ConvertDateUtils.formatDateToString(data.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getWaterType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getChargeRates().toString());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(ConvertDateUtils.formatDateToString(data.getUpdatedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getUpdatedBy());
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
		for(int i = 1 ; i<=7 ; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
