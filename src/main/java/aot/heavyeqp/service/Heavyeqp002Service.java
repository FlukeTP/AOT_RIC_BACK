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
import aot.heavyeqp.model.RicManageHeavyEquipment;
import aot.heavyeqp.repository.Heavyeqp002Dao;
import aot.heavyeqp.repository.jpa.RicManageHeavyEquipmentRepository;
import aot.heavyeqp.vo.request.Heavyeqp002Req;
import aot.heavyeqp.vo.request.Heavyeqp002SaveReq;
import aot.phone.model.RicPhoneRateChargeConfig;
import aot.phone.vo.request.Phone001Req;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Heavyeqp002Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp002Service.class);
	
	@Autowired
	Heavyeqp002Dao heavyeqp002Dao;

	@Autowired
	private RicManageHeavyEquipmentRepository ricManageHeavyEquipmentRepository;
	
	
	public List<RicManageHeavyEquipment> getallHeavyEquipments() {
		List<RicManageHeavyEquipment> manageHeavyEquipments = new ArrayList<RicManageHeavyEquipment>();
		manageHeavyEquipments = heavyeqp002Dao.getallHeavyEquipments();
		return manageHeavyEquipments;
	}
	
	public RicManageHeavyEquipment listEditId(Heavyeqp002Req form) {
		RicManageHeavyEquipment manageHeavyEquipment = new RicManageHeavyEquipment();	
		manageHeavyEquipment = ricManageHeavyEquipmentRepository.findById(form.getManageHeavyEquipmentId()).get();
		return manageHeavyEquipment;
	}
	
	public void saveHeavyeqp(Heavyeqp002SaveReq request) {
		logger.info("saveHeavyeqp", request);
		RicManageHeavyEquipment manageHeavyEquipment = null;
		   manageHeavyEquipment = new RicManageHeavyEquipment();
			// set data
			manageHeavyEquipment.setEquipmentCode(request.getEquipmentCode());
			manageHeavyEquipment.setEquipmentType(request.getEquipmentType());
			manageHeavyEquipment.setEquipmentNo(request.getEquipmentNo());
			manageHeavyEquipment.setStatus(request.getStatus());
			manageHeavyEquipment.setResponsiblePerson(request.getResponsiblePerson());
			manageHeavyEquipment.setRemark(request.getRemark());
			manageHeavyEquipment.setNumberLicensePlate(request.getNumberLicensePlate());
			manageHeavyEquipment.setLicensePlate(request.getLicensePlate());
			manageHeavyEquipment.setRemark(request.getRemark());
			manageHeavyEquipment.setCreatedDate(new Date());
			manageHeavyEquipment.setCreatedBy(UserLoginUtils.getCurrentUsername());
			manageHeavyEquipment.setIsDelete(RICConstants.STATUS.NO);
			// save data
			ricManageHeavyEquipmentRepository.save(manageHeavyEquipment);
	}
	
	public void editHeavyeqp(Heavyeqp002SaveReq request) {
		logger.info("editHeavyeqp", request);
		RicManageHeavyEquipment manageHeavyEquipment = null;
		   manageHeavyEquipment = new RicManageHeavyEquipment();
			// set data
		   manageHeavyEquipment = ricManageHeavyEquipmentRepository.findById(request.getManageHeavyEquipmentId()).get();
			manageHeavyEquipment.setEquipmentCode(request.getEquipmentCode());
			manageHeavyEquipment.setEquipmentType(request.getEquipmentType());
			manageHeavyEquipment.setEquipmentNo(request.getEquipmentNo());
			manageHeavyEquipment.setStatus(request.getStatus());
			manageHeavyEquipment.setResponsiblePerson(request.getResponsiblePerson());
			manageHeavyEquipment.setRemark(request.getRemark());
			manageHeavyEquipment.setNumberLicensePlate(request.getNumberLicensePlate());
			manageHeavyEquipment.setLicensePlate(request.getLicensePlate());
			manageHeavyEquipment.setRemark(request.getRemark());
			manageHeavyEquipment.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			manageHeavyEquipment.setUpdatedDate(new Date());
			
			// save data
			ricManageHeavyEquipmentRepository.save(manageHeavyEquipment);
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<RicManageHeavyEquipment> dataExport = new ArrayList<RicManageHeavyEquipment>();
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
		
		String[] header = { "ลำดับที่", "รหัสเครื่องทุ่นแรง", "ประเภทเครื่องทุ่นแรง", "หมายเลขเครื่องทุ่นแรง", "สถานะ", "ผู้รับผิดชอบ", "หมายเหตุ" };

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
		for (RicManageHeavyEquipment data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEquipmentCode());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEquipmentType());
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEquipmentNo());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getStatus());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getResponsiblePerson());
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
