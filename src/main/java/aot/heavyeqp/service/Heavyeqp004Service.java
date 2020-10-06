package aot.heavyeqp.service;

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

import aot.common.constant.RICConstants;
import aot.heavyeqp.model.RicHeavyManageEquipmentType;
import aot.heavyeqp.repository.Heavyeqp004Dao;
import aot.heavyeqp.repository.jpa.RicHeavyManageEquipmentTypeRepository;
import aot.heavyeqp.vo.request.Heavyeqp004Req;
import aot.heavyeqp.vo.request.Heavyeqp004SaveReq;
import aot.phone.model.RicPhoneRateChargeConfig;
import aot.phone.vo.request.Phone001Req;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Heavyeqp004Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp004Service.class);
	
	@Autowired
	Heavyeqp004Dao heavyeqp004Dao;

	@Autowired
	private RicHeavyManageEquipmentTypeRepository ricHeavyManageEquipmentTypeRepository;
	
	
	public List<RicHeavyManageEquipmentType> getallHeavyEquipments() {
		List<RicHeavyManageEquipmentType> heavyManageEquipmentType = new ArrayList<RicHeavyManageEquipmentType>();
		heavyManageEquipmentType = heavyeqp004Dao.getallHeavyEquipments();
		return heavyManageEquipmentType;
	}
	
	public RicHeavyManageEquipmentType listEditId(Heavyeqp004Req form) {
		RicHeavyManageEquipmentType heavyManageEquipmentType = new RicHeavyManageEquipmentType();	
		heavyManageEquipmentType = ricHeavyManageEquipmentTypeRepository.findById(form.getHeavyManageEquipmentTypeId()).get();
		return heavyManageEquipmentType;
	}
	
	public void saveHeavyeqp(Heavyeqp004SaveReq request) {
		logger.info("saveHeavyeqp", request);
		RicHeavyManageEquipmentType heavyManageEquipmentType = null;
		heavyManageEquipmentType = new RicHeavyManageEquipmentType();
			// set data
		heavyManageEquipmentType.setGlAccount(request.getGlAccount());
		heavyManageEquipmentType.setEquipmentType(request.getEquipmentType());
		heavyManageEquipmentType.setDetail(request.getDetail());
		heavyManageEquipmentType.setCreatedDate(new Date());
		heavyManageEquipmentType.setCreatedBy(UserLoginUtils.getCurrentUsername());
		heavyManageEquipmentType.setIsDelete(RICConstants.STATUS.NO);
			// save data
		ricHeavyManageEquipmentTypeRepository.save(heavyManageEquipmentType);
	}
	
	public void editHeavyeqp(Heavyeqp004SaveReq request) {
		logger.info("editHeavyeqp", request);
		RicHeavyManageEquipmentType heavyManageEquipmentType = null;
		heavyManageEquipmentType = new RicHeavyManageEquipmentType();
			// set data
		heavyManageEquipmentType = ricHeavyManageEquipmentTypeRepository.findById(request.getHeavyManageEquipmentTypeId()).get();
		heavyManageEquipmentType.setGlAccount(request.getGlAccount());
		heavyManageEquipmentType.setEquipmentType(request.getEquipmentType());
		heavyManageEquipmentType.setDetail(request.getDetail());
		heavyManageEquipmentType.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		heavyManageEquipmentType.setUpdatedDate(new Date());
			// save data
			ricHeavyManageEquipmentTypeRepository.save(heavyManageEquipmentType);
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<RicHeavyManageEquipmentType> dataExport = new ArrayList<RicHeavyManageEquipmentType>();
		dataExport = getallHeavyEquipments();

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
		
		String[] header = { "ลำดับที่", "GL Account", "ประเภทเครื่องทุ่นแรง"  };

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
		for (RicHeavyManageEquipmentType data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getGlAccount());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEquipmentType());
			cell.setCellStyle(tdCenter);
			rowNum++;
			index++;
		}

		
		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for(int i = 1 ; i<=3 ; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
