package aot.it.service;

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
import aot.it.model.RicItOtherChargeRatesConfig;
import aot.it.repository.It0102Dao;
import aot.it.repository.jpa.RicItOtherChargeRatesConfigRepository;
import aot.it.vo.request.It0102Req;
import aot.it.vo.request.It0102Req;
import aot.it.vo.response.It0102Res;
import aot.it.vo.response.It0102Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It0102Service {

	private static final Logger logger = LoggerFactory.getLogger(It0102Service.class);

	@Autowired
	private RicItOtherChargeRatesConfigRepository ricItOtherChargeRatesConfigRepository;

	@Autowired
	private It0102Dao It0102Dao;

	public List<It0102Res> getListAll(It0102Req request) throws Exception {

		logger.info("getListAll");

		List<It0102Res> list = new ArrayList<>();
		try {
			list = It0102Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public It0102Res findById(It0102Req request) throws Exception {

		logger.info("findById");
		RicItOtherChargeRatesConfig data = null;
		It0102Res res = new It0102Res();
		try {
			data = ricItOtherChargeRatesConfigRepository.findById(Long.valueOf(request.getItOtherConfigId())).get();
			res.setItOtherConfigId(data.getItOtherConfigId());
			res.setEffectiveDate(ConvertDateUtils.formatDateToString(data.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setChargeRateType(data.getChargeRateType());
			res.setServiceType(data.getServiceType());
			res.setChargeRate(data.getChargeRate());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(It0102Req request) throws Exception {
		logger.info("save");

		RicItOtherChargeRatesConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getItOtherConfigId())) {
				data = ricItOtherChargeRatesConfigRepository.findById(Long.valueOf(request.getItOtherConfigId()))
						.get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicItOtherChargeRatesConfig();
				data.setCreatedBy(UserLoginUtils.getCurrentUsername());
				data.setCreatedDate(new Date());
				data.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			data.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setServiceType(request.getServiceType());
			data.setChargeRateType(request.getChargeRateType());
			data.setChargeRate(new BigDecimal(request.getChargeRate()));
			data.setRemark(request.getRemark());
			// save data
			ricItOtherChargeRatesConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}
	
	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It0102Req form = new It0102Req();
		List<It0102Res> dataExport = new ArrayList<It0102Res>();
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

		String[] header = {
			    "ลำดับที่", "วันที่มีผล", "ประเภทบริการ","ประเภทอัตราค่าภาระ", "จำนวนเงิน", "วันที่ปรับปรุง",
			    "ปรับปรุงโดย", "หมายเหตุ"};

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
		for (It0102Res data : dataExport) {
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
			cell.setCellValue(data.getServiceType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getChargeRateType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getChargeRate(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getUpdatedDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getUpdatedBy());
			cell.setCellStyle(tdLeft);

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
