package aot.electric.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import aot.electric.model.RicElectricMeter;
import aot.electric.model.RicElectricRateChargeConfig;
import aot.electric.repository.jpa.RicElectricRateChargeConfigRepository;
import aot.electric.vo.request.Electric001Req;
import aot.electric.vo.request.Electric002Req;
import aot.electric.vo.request.Electric007Req;
import aot.electric.vo.response.Electric001Res;
import aot.electric.vo.response.Electric007Res;
import aot.util.sap.constant.SAPConstants;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric007Service {
	private static final Logger logger = LoggerFactory.getLogger(Electric007Service.class);

	@Autowired
	private RicElectricRateChargeConfigRepository ricElectricRateChargeConfigRepository;

	public void saveRateChargeConfig(Electric007Req request) {
		logger.info("saveRateChargeConfig", request);

		RicElectricRateChargeConfig chargeConfig = null;
		try {
			chargeConfig = new RicElectricRateChargeConfig();
			// set data
			chargeConfig.setModifiedYear(ConvertDateUtils.parseStringToDate(request.getModifiedYear(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			chargeConfig.setPhase(request.getPhase());
			chargeConfig.setServiceType(request.getServiceType());
			chargeConfig.setRangeAmpere(request.getRangeAmpere());
			chargeConfig.setChargeRates(request.getChargeRates());
			chargeConfig.setRemark(request.getRemark());
			chargeConfig.setCreatedBy(UserLoginUtils.getCurrentUsername());
			chargeConfig.setCreateDate(new Date());
			chargeConfig.setIsDelete("N");
			// save data
			ricElectricRateChargeConfigRepository.save(chargeConfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public List<Electric007Res> findRateChargeConfig() {
		logger.info("findRateChargeConfig");

		List<RicElectricRateChargeConfig> dataList = ricElectricRateChargeConfigRepository.findAllChargeConfig();
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

	public Electric007Res getById(String idStr) {
		logger.info("getRateChargeConfigById");
		RicElectricRateChargeConfig data = ricElectricRateChargeConfigRepository.findById(Long.valueOf(idStr)).get();
		Electric007Res res = null;
		try {
			res = new Electric007Res();
			res.setRateConfigId(data.getRateConfigId());
			res.setModifiedYear(ConvertDateUtils.formatDateToString(data.getModifiedYear(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setPhase(data.getPhase());
			res.setServiceType(data.getServiceType());
			res.setRangeAmpere(data.getRangeAmpere());
			res.setChargeRates(data.getChargeRates());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<Electric007Res> dataExport = new ArrayList<Electric007Res>();
		dataExport = findRateChargeConfig();

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

		  String[] header = {
				    "ลำดับที่", "วันที่มีผล", "จำนวนเฟส", "ประเภทบริการ", "ขนาดการใช้ไฟฟ้า(แอมแปร์)",
				    "อัตราค่าภาระ(บาท)", "วันที่ปรับปรุง", "ปรับปรุงโดย", "หมายเหตุ" };
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
		for (Electric007Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getModifiedYear());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPhase());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRangeAmpere());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRateConfigId());
			cell.setCellStyle(tdCenter);
			
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
		for (int i = 1; i < 8; i++) {
			sheet.setColumnWidth(i, width * 60);
		}
		sheet.setColumnWidth(2, width * 30);
		sheet.setColumnWidth(5, width * 30);

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

//	public void updateRateChargeConfig(String idStr, Electric007Req request) {
//		logger.info("updateRateChargeConfig");
//		RicElectricRateChargeConfig data = ricElectricRateChargeConfigRepository.findById(Long.valueOf(idStr)).get();
//		try {
//			data.setModifiedYear(ConvertDateUtils.parseStringToDate(request.getModifiedYear(),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			data.setPhase(request.getPhase());
//			data.setServiceType(request.getServiceType());
//			data.setRangeAmpere(request.getRangeAmpere());
//			data.setChargeRates(request.getChargeRates());
//			data.setRemark(request.getRemark());
//			data.setUpdatedBy("setUpdatedBy");
//			data.setUpdatedDate(new Date());
//			ricElectricRateChargeConfigRepository.save(data);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//	}
	
	public void updateRateChargeConfig(Electric007Req request) {
		logger.info("updateRateChargeConfig");
		
		try {
			RicElectricRateChargeConfig data = ricElectricRateChargeConfigRepository.findById(request.getRateConfigId()).get();
			data.setModifiedYear(ConvertDateUtils.parseStringToDate(request.getModifiedYear(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setPhase(request.getPhase());
			data.setServiceType(request.getServiceType());
			data.setRangeAmpere(request.getRangeAmpere());
			data.setChargeRates(request.getChargeRates());
			data.setRemark(request.getRemark());
			data.setUpdatedBy("setUpdatedBy");
			data.setUpdatedDate(new Date());
			ricElectricRateChargeConfigRepository.save(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
