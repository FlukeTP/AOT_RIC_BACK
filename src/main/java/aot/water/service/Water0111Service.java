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

import aot.water.model.RicWaterInsuranceChargeRatesConfig;
import aot.water.model.RicWaterMaintenanceChargeRatesConfig;
import aot.water.repository.jpa.RicWaterInsuranceChargeRatesConfigRepository;
import aot.water.vo.request.Water0111Req;
import aot.water.vo.response.Water0111Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water0111Service {

	@Autowired
	private RicWaterInsuranceChargeRatesConfigRepository ricWaterInsuranceChargeRatesConfigRepository;
	
	public List<RicWaterInsuranceChargeRatesConfig> list() {
		List<RicWaterInsuranceChargeRatesConfig> insuranceChargeRatesConfig = new ArrayList<RicWaterInsuranceChargeRatesConfig>();
		insuranceChargeRatesConfig = ricWaterInsuranceChargeRatesConfigRepository.datalist();
		return insuranceChargeRatesConfig;
	}
	
	public Water0111Res listdata(Water0111Req form) {
		RicWaterInsuranceChargeRatesConfig insuranceChargeRatesConfig = ricWaterInsuranceChargeRatesConfigRepository.findById(form.getWaterInsuranceConfigId()).get();
		Water0111Res ins = new Water0111Res();
		ins.setWaterInsuranceConfigId(insuranceChargeRatesConfig.getWaterInsuranceConfigId());
		ins.setAirport(insuranceChargeRatesConfig.getAirport());
		ins.setModifiedDate(ConvertDateUtils.formatDateToString(insuranceChargeRatesConfig.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY));
		ins.setWaterMeterSize(insuranceChargeRatesConfig.getWaterMeterSize());
		ins.setChargeRates(insuranceChargeRatesConfig.getChargeRates());
		ins.setRemark(insuranceChargeRatesConfig.getRemark());
		return ins;
	}
	
	public void saveWaterInsurance(Water0111Req request) {
		RicWaterInsuranceChargeRatesConfig insuranceChargeRatesConfig = null;
		insuranceChargeRatesConfig = new RicWaterInsuranceChargeRatesConfig();
		// set data
		insuranceChargeRatesConfig.setAirport(request.getAirport());
		insuranceChargeRatesConfig.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN) );
		insuranceChargeRatesConfig.setWaterMeterSize(request.getWaterMeterSize());
		insuranceChargeRatesConfig.setChargeRates(request.getChargeRates());
		insuranceChargeRatesConfig.setRemark(request.getRemark());
		insuranceChargeRatesConfig.setCreatedBy(UserLoginUtils.getCurrentUsername());
		insuranceChargeRatesConfig.setCreatedDate(new Date());
		// save data
		ricWaterInsuranceChargeRatesConfigRepository.save(insuranceChargeRatesConfig);
	}
	
	public void editWaterInsurance(Water0111Req request) {
		RicWaterInsuranceChargeRatesConfig insuranceChargeRatesConfig = null;
		insuranceChargeRatesConfig = new RicWaterInsuranceChargeRatesConfig();
		// set data
		insuranceChargeRatesConfig = ricWaterInsuranceChargeRatesConfigRepository.findById(request.getWaterInsuranceConfigId()).get();
		insuranceChargeRatesConfig.setAirport(request.getAirport());
		insuranceChargeRatesConfig.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN) );
		insuranceChargeRatesConfig.setWaterMeterSize(request.getWaterMeterSize());
		insuranceChargeRatesConfig.setChargeRates(request.getChargeRates());
		insuranceChargeRatesConfig.setRemark(request.getRemark());
		insuranceChargeRatesConfig.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		insuranceChargeRatesConfig.setUpdatedDate(new Date());
		// save data
		ricWaterInsuranceChargeRatesConfigRepository.save(insuranceChargeRatesConfig);
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<RicWaterInsuranceChargeRatesConfig> dataExport = new ArrayList<RicWaterInsuranceChargeRatesConfig>();
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
		for (RicWaterInsuranceChargeRatesConfig data : dataExport) {
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
			cell.setCellValue(data.getWaterMeterSize());
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
