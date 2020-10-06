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

import aot.water.model.RicWaterServiceChargeRatesConfig;
import aot.water.model.RicWaterWasteConfig;
import aot.water.repository.jpa.RicWaterWasteConfigRepository;
import aot.water.vo.request.Water0114Req;
import aot.water.vo.response.Water0114Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water0114Service {

	private static final Logger logger = LoggerFactory.getLogger(Water0114Service.class);

	@Autowired
	private RicWaterWasteConfigRepository configRepository;

	public void save(Water0114Req request) {
		logger.info("saveRicWaterWasteConfig", request);

		RicWaterWasteConfig data = null;
		try {
			data = new RicWaterWasteConfig();
			// set data
			data.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setAirport(request.getAirport());
			data.setServiceType(request.getServiceType());
			data.setChargeRates(request.getChargeRates());
			data.setRemark(request.getRemark());
			data.setCreatedBy(UserLoginUtils.getCurrentUsername());
			data.setCreatedDate(new Date());
			data.setIsDelete("N");
			// save data
			configRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public List<Water0114Res> findDataList() {
		logger.info("findRicWaterWasteConfig");

		List<RicWaterWasteConfig> dataList = configRepository.findAllWaterWasteConfig();
		Water0114Res res = null;
		List<Water0114Res> resList = new ArrayList<>();

		for (RicWaterWasteConfig data : dataList) {
			res = new Water0114Res();
			try {
				res.setWasteConfigId(data.getWasteConfigId());
				res.setAirport(data.getAirport());
				res.setModifiedDate(ConvertDateUtils.formatDateToString(data.getModifiedDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setServiceType(data.getServiceType());
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

	public Water0114Res getById(String idStr) {
		logger.info("getRicWaterWasteConfigById");
		RicWaterWasteConfig data = configRepository.findById(Long.valueOf(idStr)).get();
		Water0114Res res = null;
		try {
			res = new Water0114Res();
			res.setWasteConfigId(data.getWasteConfigId());
			res.setAirport(data.getAirport());
			res.setModifiedDate(ConvertDateUtils.formatDateToString(data.getModifiedDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setServiceType(data.getServiceType());
			res.setChargeRates(data.getChargeRates());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

//	public void update(String idStr, Water0114Req request) {
//		logger.info("updateRateChargeConfig");
//		RicWaterWasteConfig data = configRepository.findById(Long.valueOf(idStr)).get();
//		try {
//			data.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			data.setServiceType(request.getServiceType());
//			data.setChargeRates(request.getChargeRates());
//			data.setRemark(request.getRemark());
//			data.setUpdatedBy("setUpdatedBy");
//			data.setUpdatedDate(new Date());
//			configRepository.save(data);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//	}

	public void update(Water0114Req request) {
		logger.info("updateRateChargeConfig", request);
		try {
			RicWaterWasteConfig data = configRepository.findById(request.getWasteConfigId()).get();
			data.setModifiedDate(ConvertDateUtils.parseStringToDate(request.getModifiedDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setServiceType(request.getServiceType());
			data.setChargeRates(request.getChargeRates());
			data.setRemark(request.getRemark());
			data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			data.setUpdatedDate(new Date());
			configRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<Water0114Res> dataExport = new ArrayList<Water0114Res>();
		dataExport = findDataList();

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
		for (Water0114Res data : dataExport) {
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
			cell.setCellValue(data.getModifiedDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getChargeRates().toString());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getUpdatedDate());
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
