package aot.phone.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import aot.phone.model.RicPhoneRateChargeConfig;
import aot.phone.repository.jpa.RicPhoneRateChargeConfigRepository;
import aot.phone.vo.request.Phone004request;
import aot.phone.vo.response.Phone004response;
import aot.water.model.RicWaterServiceChargeRatesConfig;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;

/**
 * Created by imake on 17/07/2019
 */
@Service
public class Phone004Service {
    private static final Logger logger = LoggerFactory.getLogger(Phone004Service.class);
    
    @Autowired
    private RicPhoneRateChargeConfigRepository  ricPhoneRateChargeConfigRepository;
    
    public List<RicPhoneRateChargeConfig> getList() {
    	return ricPhoneRateChargeConfigRepository.findAllCharge();
    }
    
    public Phone004response getDetail(String id) {
    	
    	Optional<RicPhoneRateChargeConfig> obj = ricPhoneRateChargeConfigRepository.findById(Long.parseLong(id));
    	
    	RicPhoneRateChargeConfig ricPhone = obj.get();
    	Phone004response res = new Phone004response();
    	
    	res.setAirport(ricPhone.getAirport());
    	res.setChargeRate(ricPhone.getChargeRate().toString());
    	res.setCreatedBy(ricPhone.getCreatedBy());
    	res.setCreatedDate(ConvertDateUtils.formatDateToString(ricPhone.getCreatedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
    	res.setId(ricPhone.getRateConfigId().toString());
    	res.setPhoneType(ricPhone.getPhoneType());
    	res.setRemark(ricPhone.getRemark());
    	res.setServiceType(ricPhone.getServiceType());
    	res.setUpdatedBy(ricPhone.getUpdatedBy());
    	res.setUpdatedDate(ConvertDateUtils.formatDateToString(ricPhone.getUpdatedDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
    	res.setValidDate(ConvertDateUtils.formatDateToString(ricPhone.getValidDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
    	return res;
    }

    public Phone004response saveReq(Phone004request req) {
    	Phone004response res = new Phone004response();
		RicPhoneRateChargeConfig entity = new RicPhoneRateChargeConfig();
		entity.setValidDate(ConvertDateUtils.parseStringToDate(req.getValidDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		entity.setAirport("");
		entity.setChargeRate(new BigDecimal(req.getRateCharge()) );
		entity.setPhoneType(req.getPhoneType());
		entity.setServiceType(req.getServiceType());
		entity.setRemark(req.getRemark());
		entity.setCreatedBy("admin");
		entity.setCreatedDate(new Date());
		
		entity.setUpdatedBy("admin");
		entity.setUpdatedDate(new Date());
		entity.setIsDelete("N");
		
		RicPhoneRateChargeConfig newupdate = null;
		if(StringUtils.isNoneBlank(req.getId())) {
			entity.setRateConfigId(Long.parseLong(req.getId()) );
			newupdate = ricPhoneRateChargeConfigRepository.save(entity);
		}else {
			newupdate = ricPhoneRateChargeConfigRepository.save(entity);
		}
		res.setId(newupdate.getRateConfigId().toString());

    	return res;
    }
    
    public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<RicPhoneRateChargeConfig> dataExport = new ArrayList<RicPhoneRateChargeConfig>();
		dataExport = getList();

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
		
		String[] header = { "ลำดับที่", "วันที่มีผล", "ประเภท", "ประเภทการบริการ", "อัตราค่าภาระ", "วันที่ปรับปรุง", "ปรับปรุงโดย", "หมายเหตุ" };

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
		for (RicPhoneRateChargeConfig data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(ConvertDateUtils.formatDateToString(data.getValidDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPhoneType());
			cell.setCellStyle(tdCenter);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getChargeRate()));
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
