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

import aot.common.constant.RICConstants;
import aot.firebrigade.model.RicFirebrigadeRateChargeConfig;
import aot.firebrigade.model.RicFirebrigadeRateChargeConfigHistory;
import aot.firebrigade.repository.Firebrigade002Dao;
import aot.firebrigade.repository.jpa.RicFirebrigadeRateChargeConfigHistoryRepository;
import aot.firebrigade.repository.jpa.RicFirebrigadeRateChargeConfigRepository;
import aot.firebrigade.vo.request.Firebrigade002Req;
import aot.firebrigade.vo.response.Firebrigade002Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Firebrigade002Service {

	private static final Logger logger = LoggerFactory.getLogger(Firebrigade002Service.class);

	@Autowired
	private RicFirebrigadeRateChargeConfigRepository ricFirebrigadeRateChargeConfigRepository;
	
	@Autowired
	private RicFirebrigadeRateChargeConfigHistoryRepository ricFirebrigadeRateChargeConfigHistoryRepository;

	@Autowired
	private Firebrigade002Dao firebrigade002Dao;
	
	public List<Firebrigade002Res> getListFirebrigadeRateChargeConfig(Firebrigade002Req request) throws Exception {

		logger.info("getListFirebrigadeRateChargeConfig");

		List<Firebrigade002Res> list = new ArrayList<>();
		try {
			list = firebrigade002Dao.findFirebrigadeRateChargeConfig(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}
	
	public List<Firebrigade002Res> getListFirebrigadeRateChargeConfigHis(Firebrigade002Req request) throws Exception {

		logger.info("getListFirebrigadeRateChargeConfig");

		List<RicFirebrigadeRateChargeConfigHistory> list = new ArrayList<>();
		List<Firebrigade002Res> resList = new ArrayList<>();
		try {
			list = ricFirebrigadeRateChargeConfigHistoryRepository.findByRateConfigId(Long.valueOf(request.getRateConfigId()));
			for (RicFirebrigadeRateChargeConfigHistory l : list) {
				Firebrigade002Res res = new Firebrigade002Res();
				res.setCourseName(l.getCourseName());
				res.setChargeRates(l.getChargeRates());
				res.setEffectiveDate(ConvertDateUtils.formatDateToString(l.getEffectiveDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRemark(l.getRemark());
				res.setCreateDate(ConvertDateUtils.formatDateToString(l.getCreateDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setCreatedBy(l.getCreatedBy());
				res.setUnit(l.getUnit());
				resList.add(res);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return resList;
	}
	
	public Firebrigade002Res findById(Firebrigade002Req request) throws Exception {
		logger.info("Firebrigade002Service::findById");
		Firebrigade002Res res = new Firebrigade002Res();
		try {
			RicFirebrigadeRateChargeConfig firebrigade = ricFirebrigadeRateChargeConfigRepository
					.findById(Long.valueOf(request.getRateConfigId())).get();
			res.setRateConfigId(String.valueOf(firebrigade.getRateConfigId()));
			res.setCourseName(firebrigade.getCourseName());
			res.setChargeRates(firebrigade.getChargeRates());
			res.setEffectiveDate(ConvertDateUtils.formatDateToString(firebrigade.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setRemark(firebrigade.getRemark());
			res.setCreateDate(ConvertDateUtils.formatDateToString(firebrigade.getCreateDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setCreatedBy(firebrigade.getCreatedBy());
			res.setUnit(firebrigade.getUnit());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Firebrigade002Req request) throws Exception {
		logger.info("Firebrigade002Service::save");

		RicFirebrigadeRateChargeConfig firebrigade = null;
		RicFirebrigadeRateChargeConfigHistory firebrigadeHis = null;
		try {
			if (StringUtils.isNotBlank(request.getRateConfigId())) {
				firebrigade = ricFirebrigadeRateChargeConfigRepository.findById(Long.valueOf(request.getRateConfigId()))
						.get();
				// set data
				firebrigade.setCourseName(request.getCourseName());
				firebrigade.setChargeRates(new BigDecimal(request.getChargeRates()));
				firebrigade.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				firebrigade.setRemark(request.getRemark());
				firebrigade.setUnit(request.getUnit());
				firebrigade.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				firebrigade.setUpdatedDate(new Date());
			} else {
				firebrigade = new RicFirebrigadeRateChargeConfig();
				// set data
				firebrigade.setCourseName(request.getCourseName());
				firebrigade.setChargeRates(new BigDecimal(request.getChargeRates()));
				firebrigade.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				firebrigade.setRemark(request.getRemark());
				firebrigade.setUnit(request.getUnit());
				firebrigade.setCreatedBy(UserLoginUtils.getCurrentUsername());
				firebrigade.setCreateDate(new Date());
				firebrigade.setIsDelete(RICConstants.STATUS.NO);
			}
			// save data
			firebrigade = ricFirebrigadeRateChargeConfigRepository.save(firebrigade);
			// save to firebrigade History
			firebrigadeHis = new RicFirebrigadeRateChargeConfigHistory();
			firebrigadeHis.setRateConfigId(firebrigade.getRateConfigId());
			firebrigadeHis.setCourseName(firebrigade.getCourseName());
			firebrigadeHis.setChargeRates(firebrigade.getChargeRates());
			firebrigadeHis.setEffectiveDate(firebrigade.getEffectiveDate());
			firebrigadeHis.setRemark(firebrigade.getRemark());
			firebrigadeHis.setUnit(firebrigade.getUnit());
			firebrigadeHis.setCreatedBy(firebrigade.getCreatedBy());
			firebrigadeHis.setCreateDate(firebrigade.getCreateDate());
			firebrigadeHis.setIsDelete(RICConstants.STATUS.NO);
			
			ricFirebrigadeRateChargeConfigHistoryRepository.save(firebrigadeHis);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}
	
	public ByteArrayOutputStream downloadTemplate(String courseName,String effectiveDate) throws Exception {
		Firebrigade002Req form = new Firebrigade002Req();
//		form.setCourseName(courseName);
//		form.setEffectiveDate(effectiveDate);
		List<Firebrigade002Res> dataExport = new ArrayList<Firebrigade002Res>();
		dataExport = getListFirebrigadeRateChargeConfig(form);

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
		
		String[] header = { "ลำดับที่", "วันที่มีผล", "หลักสูตร", "อัตราค่าภาระ", "วันที่ปรับปรุง", "ปรับปรุงโดย", "หมายเหตุ" };

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
		for (Firebrigade002Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEffectiveDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCourseName());
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getChargeRates()));
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCreateDate());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCreatedBy());
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
