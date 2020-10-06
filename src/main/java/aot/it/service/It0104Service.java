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
import aot.it.model.RicItDedicatedCUTEChargeRatesConfig;
import aot.it.repository.It0104Dao;
import aot.it.repository.jpa.RicItDedicatedCUTEChargeRatesConfigRepository;
import aot.it.vo.request.It0104Req;
import aot.it.vo.response.It0104Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It0104Service {

	private static final Logger logger = LoggerFactory.getLogger(It0104Service.class);

	@Autowired
	private RicItDedicatedCUTEChargeRatesConfigRepository ricItDedicatedCUTEChargeRatesConfigRepository;

	@Autowired
	private It0104Dao It0104Dao;

	public List<It0104Res> getListAll(It0104Req request) throws Exception {

		logger.info("getListAll");

		List<It0104Res> list = new ArrayList<>();
		try {
			list = It0104Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public It0104Res findById(It0104Req request) throws Exception {

		logger.info("findById");
		RicItDedicatedCUTEChargeRatesConfig data = null;
		It0104Res res = new It0104Res();
		try {
			data = ricItDedicatedCUTEChargeRatesConfigRepository.findById(Long.valueOf(request.getItDedicatedCUTEConfigId())).get();
			res.setItDedicatedCUTEConfigId(data.getItDedicatedCUTEConfigId());
			res.setEffectiveDate(ConvertDateUtils.formatDateToString(data.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setMonthType(data.getMonthType());
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
	public void save(It0104Req request) throws Exception {
		logger.info("save");

		RicItDedicatedCUTEChargeRatesConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getItDedicatedCUTEConfigId())) {
				data = ricItDedicatedCUTEChargeRatesConfigRepository.findById(Long.valueOf(request.getItDedicatedCUTEConfigId()))
						.get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicItDedicatedCUTEChargeRatesConfig();
				data.setCreatedBy(UserLoginUtils.getCurrentUsername());
				data.setCreatedDate(new Date());
				data.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			data.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setMonthType(request.getMonthType());
			data.setServiceType(request.getServiceType());
			data.setChargeRate(new BigDecimal(request.getChargeRate()));
			data.setRemark(request.getRemark());
			// save data
			ricItDedicatedCUTEChargeRatesConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}
	
	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It0104Req form = new It0104Req();
		List<It0104Res> dataExport = new ArrayList<It0104Res>();
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
			    "ลำดับที่", "วันที่มีผล","สัญญา(เดือน)", "ประเภทบริการ", "จำนวนเงิน", "วันที่ปรับปรุง",
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
		for (It0104Res data : dataExport) {
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
			cell.setCellValue(data.getMonthType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceType());
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
