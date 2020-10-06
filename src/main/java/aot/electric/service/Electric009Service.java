package aot.electric.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import aot.electric.model.SystemCalConfig;
import aot.electric.model.SystemCalConfigHistory;
import aot.electric.repository.jpa.SystemCalConfigHistoryRepository;
import aot.electric.repository.jpa.SystemCalConfigRepository;
import aot.electric.vo.request.Electric009Req;
import aot.electric.vo.response.Electric008Res;
import aot.electric.vo.response.Electric009HistoryRes;
import aot.electric.vo.response.Electric009Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric009Service {
	private static final Logger logger = LoggerFactory.getLogger(Electric009Service.class);

	@Autowired
	private SystemCalConfigRepository SystemCalConfigRepository;
	
	@Autowired
	private SystemCalConfigHistoryRepository systemCalConfigHistoryRepository;
	
	public void save(Electric009Req request) {
		logger.info("save id: {}", request.getCalConfigId());
		
		SystemCalConfig chargeConfig = null;
		SystemCalConfigHistory history = null;
		if (StringUtils.isNotBlank(request.getCalConfigId())) {
			chargeConfig = SystemCalConfigRepository.findById(Long.valueOf(request.getCalConfigId())).get();
			// set data
			chargeConfig.setValidDate(ConvertDateUtils.parseStringToDate(request.getValidDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			chargeConfig.setValue(request.getValue());
			chargeConfig.setNotificationDate(new Date());
			chargeConfig.setRemark(request.getRemark());
			chargeConfig.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			chargeConfig.setUpdatedDate(new Date());
			// set data for insert to SystemCalConfigHistory
//			history = new SystemCalConfigHistory();
//			history.setHistoryCodeType(request.getCodeType());
//			history.setHistoryBy("Kittikun Kaewsuta");
//			history.setHistoryDate(ConvertDateUtils.parseStringToDate(request.getValidDate(),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			history.setCreatedBy("Kittikun Kaewsuta");
//			history.setCreateDate(new Date());
//			history.setIsDelete("N");
		} else {
			chargeConfig = new SystemCalConfig();
			// set data
			chargeConfig.setValidDate(ConvertDateUtils.parseStringToDate(request.getValidDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			chargeConfig.setValue(request.getValue());
			chargeConfig.setNotificationDate(new Date());
			chargeConfig.setRemark(request.getRemark());
			chargeConfig.setCreatedBy(UserLoginUtils.getCurrentUsername());
			chargeConfig.setCreateDate(new Date());
			chargeConfig.setIsDelete("N");
			// set data for insert to SystemCalConfigHistory
//			history = new SystemCalConfigHistory();
//			history.setHistoryCodeType(request.getCodeType());
////			history.setHistoryBy("Kittikun Kaewsuta");
//			history.setHistoryDate(ConvertDateUtils.parseStringToDate(request.getValidDate(),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			history.setCreatedBy("Kittikun Kaewsuta");
//			history.setCreateDate(new Date());
//			history.setIsDelete("N");
		}
		
		SystemCalConfigRepository.save(chargeConfig);
//		systemCalConfigHistoryRepository.save(history);
	}

	public Electric009Res saveCalConfig(Electric009Req request) throws Exception {
		logger.info("saveCalConfig", request);

		SystemCalConfig chargeConfig = null;
		Electric009Res res = null;
		try {
			chargeConfig = new SystemCalConfig();
			// set data
			chargeConfig.setValidDate(ConvertDateUtils.parseStringToDate(request.getValidDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			chargeConfig.setValue(request.getValue());
			chargeConfig.setNotificationDate(new Date());
			chargeConfig.setRemark(request.getRemark());
			chargeConfig.setCreatedBy("Kittikun Kaewsuta");
			chargeConfig.setCreateDate(new Date());
			chargeConfig.setIsDelete("N");
			// save data
			SystemCalConfigRepository.save(chargeConfig);
			// res
//			res = new Electric009Res();
//			res.setValidDate(request.getValidDate());
//			res.setValue(request.getValue());
//			res.setNotificationDate(ConvertDateUtils.formatDateToString(data.getNotificationDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			res.setRemark(request.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return res;

	}

	public List<Electric009Res> findCalConfig() {
		logger.info("findCalConfig");

		List<SystemCalConfig> dataList = SystemCalConfigRepository.findAllCalConfig();
		Electric009Res res = null;
		List<Electric009Res> resList = new ArrayList<>();

		for (SystemCalConfig data : dataList) {
			res = new Electric009Res();
			try {
				res.setCalConfigId(data.getCalConfigId());
				res.setValidDate(ConvertDateUtils.formatDateToString(data.getValidDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setValue(data.getValue());
				res.setNotificationDate(ConvertDateUtils.formatDateToString(data.getNotificationDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
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
	
	
	public Electric009Res getById(String idStr) throws Exception {
		logger.info("getCalConfigById");
		SystemCalConfig data = SystemCalConfigRepository.findById(Long.valueOf(idStr)).get();
		Electric009Res res = null;
		try {
			res= new Electric009Res();
			res.setCalConfigId(data.getCalConfigId());
			res.setValidDate(ConvertDateUtils.formatDateToString(data.getValidDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setValue(data.getValue());
			res.setNotificationDate(ConvertDateUtils.formatDateToString(data.getNotificationDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}
	
	public List<Electric009HistoryRes> getHistoryByCode(Electric009Req request) {
		List<SystemCalConfigHistory> hisList = systemCalConfigHistoryRepository.findByHistoryCodeType(request.getCodeType());
		List<Electric009HistoryRes> hisResList = new ArrayList<>();;
		Electric009HistoryRes hisRes = null;
		for (SystemCalConfigHistory hisDtl : hisList) {
			hisRes = new Electric009HistoryRes();
			hisRes.setHistoryId(String.valueOf(hisDtl.getHistoryId()));
			hisRes.setHistoryCodeType(hisDtl.getHistoryCodeType());
			hisRes.setHistoryBy(hisDtl.getHistoryBy());
			hisRes.setHistoryDate(ConvertDateUtils.formatDateToString(hisDtl.getHistoryDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			hisResList.add(hisRes);
		}
		return hisResList;
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<Electric009Res> dataExport = new ArrayList<Electric009Res>();
		dataExport = findCalConfig();

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
		
		String[] header = { "ลำดับที่", "วันที่มีผล", "ประเภทบริการ", "อัตราค่าภาระ", "วันที่ปรับปรุง", "ปรับปรุงโดย", "หมายเหตุ" };
		
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
		for (Electric009Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getValidDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getNotificationDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getValue());
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
		for(int i = 1 ; i<=6 ; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
		

}
