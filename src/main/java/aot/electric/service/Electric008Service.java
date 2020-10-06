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

import aot.electric.model.RicElectricChargeMapping;
import aot.electric.model.RicElectricChargeTypeConfig;
import aot.electric.repository.Electric008Dao;
import aot.electric.repository.jpa.RicElectricChargeMappingRepository;
import aot.electric.repository.jpa.RicElectricChargeTypeConfigRepository;
import aot.electric.vo.request.Electric008DtlReq;
import aot.electric.vo.request.Electric008Req;
import aot.electric.vo.response.Electric007Res;
import aot.electric.vo.response.Electric008DtlRes;
import aot.electric.vo.response.Electric008Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;

@Service
public class Electric008Service {
	private static final Logger logger = LoggerFactory.getLogger(Electric008Service.class);

	@Autowired
	private RicElectricChargeTypeConfigRepository chargeTypeConfigRepository;

	@Autowired
	private RicElectricChargeMappingRepository chargeMappingRepository;

	@Autowired
	private Electric008Dao electric008Dao;

	public void saveRateChargeConfig(Electric008Req request) {
		logger.info("saveTypeConfig", request);

		// Header
		RicElectricChargeTypeConfig typeConfig = null;
		try {
			typeConfig = new RicElectricChargeTypeConfig();
			// set data
			typeConfig.setElectricType(request.getElectricType());
			typeConfig.setRateType(request.getRateType());
			typeConfig.setServiceChargeRates(request.getServiceChargeRates());
			typeConfig.setDescription(request.getDescription());
			typeConfig.setCreatedBy("setCreatedBy");
			typeConfig.setCreateDate(new Date());
			typeConfig.setIsDelete("N");
			// save data Header
			typeConfig = chargeTypeConfigRepository.save(typeConfig);
			// ==================================================================

			if (request.getElectric008DtlReq() != null && request.getElectric008DtlReq().size() > 0) {
				// DTL
				RicElectricChargeMapping val = null;
				List<RicElectricChargeMapping> chargeMappings = new ArrayList<>();

				for (Electric008DtlReq data : request.getElectric008DtlReq()) {
					val = new RicElectricChargeMapping();
					val.setTypeConfigId(typeConfig.getTypeConfigId());
					val.setVoltageType(data.getVoltageType());
					val.setStartRange(data.getStartRange());
					val.setEndRange(data.getEndRange());
					val.setChargeRates(data.getChargeRates());
					val.setCreatedBy("setCreatedBy");
					val.setCreatedDate(new Date());
					val.setIsDelete("N");
					chargeMappings.add(val);
				}
				// save data DTL
				chargeMappingRepository.saveAll(chargeMappings);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public List<Electric008Res> findRateTypeConfig() {
		logger.info("findRateTypeConfig");

		List<Electric008Res> dataList = new ArrayList<>();

		try {
			dataList = electric008Dao.findData();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return dataList;
	}

	public Electric008Res getById(String idStr) {
		logger.info("getById id={}", idStr);

		Electric008Res res = null;
		Electric008DtlRes resDtl = null;
		List<Electric008DtlRes> resDtlList = new ArrayList<>();
		try {
			RicElectricChargeTypeConfig header = chargeTypeConfigRepository.findById(Long.valueOf(idStr)).get();
			// set header
			res = new Electric008Res();
			res.setElectricType(header.getElectricType());
			res.setRateType(header.getRateType());
			res.setServiceChargeRates(header.getServiceChargeRates());
			res.setUpdatedBy(header.getUpdatedBy());
			res.setUpdatedDate(ConvertDateUtils.formatDateToString(header.getUpdatedDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setDescription(header.getDescription());

			// set detail
			List<RicElectricChargeMapping> detailList = chargeMappingRepository.findByTypeConfigId(Long.valueOf(idStr));
			if (detailList != null && detailList.size() > 0) {
				for (RicElectricChargeMapping detail : detailList) {
					resDtl = new Electric008DtlRes();
					resDtl.setChargeMappingId(detail.getChargeMappingId());
					resDtl.setTypeConfigId(detail.getTypeConfigId());
					resDtl.setChargeRates(detail.getChargeRates());
					resDtl.setStartRange(detail.getStartRange());
					resDtl.setEndRange(detail.getEndRange());
					resDtl.setVoltageType(detail.getVoltageType());
					resDtlList.add(resDtl);
				}
			}
			res.setElectric008DtlRes(resDtlList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}
	
	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<Electric008Res> dataExport = new ArrayList<Electric008Res>();
		dataExport = findRateTypeConfig();

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
		
		String[] header = { "ลำดับที่", "ประเภทค่าไฟ", "ประเภทอัตรา", "ประเภทแรงดัน", "วันที่ปรับปรุง", "ปรับปรุงโดย", "คำอธิบาย" };
		
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
		for (Electric008Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getElectricType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRateType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getVoltageType());
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
			cell.setCellValue(data.getDescription());
			cell.setCellStyle(tdCenter);


			rowNum++;
			index++;
		}

		
		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		sheet.setColumnWidth(1, width * 100);
		sheet.setColumnWidth(2, width * 100);
		sheet.setColumnWidth(3, width * 60);
		sheet.setColumnWidth(4, width * 60);
		sheet.setColumnWidth(5, width * 50);
		sheet.setColumnWidth(6, width * 50);

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}


//	public void update(String idStr, Electric008Req request) {
//		logger.info("updateRateChargeConfig");
//		try {
//			// set header
//			RicElectricChargeTypeConfig header = chargeTypeConfigRepository.findById(Long.valueOf(idStr)).get();
//			header.setElectricType(request.getElectricType());
//			header.setRateType(request.getRateType());
//			header.setServiceChargeRates(request.getServiceChargeRates());
//			header.setDescription(request.getDescription());
//			header.setUpdatedBy("setUpdatedBy");
//			header.setUpdatedDate(new Date());
//			header = chargeTypeConfigRepository.save(header);
//			// set detail
//			if (request.getElectric008DtlReq() != null && request.getElectric008DtlReq().size() > 0) {
//				RicElectricChargeMapping detail = null;
//				List<RicElectricChargeMapping> detailList = new ArrayList<>();
//				for (Electric008DtlReq data : request.getElectric008DtlReq()) {
//					if (data.getChargeMappingId() != null) {
//						detail = new RicElectricChargeMapping();
//						detail = chargeMappingRepository.findById(data.getChargeMappingId()).get();
//						detail.setVoltageType(data.getVoltageType());
//						detail.setStartRange(data.getStartRange());
//						detail.setEndRange(data.getEndRange());
//						detail.setChargeRates(data.getChargeRates());
//						detail.setUpdatedBy("setUpdatedBy");
//						detail.setUpdatedDate(new Date());
//						detailList.add(detail);
//					} else {
//						detail = new RicElectricChargeMapping();
//						detail.setTypeConfigId(header.getTypeConfigId());
//						detail.setVoltageType(data.getVoltageType());
//						detail.setStartRange(data.getStartRange());
//						detail.setEndRange(data.getEndRange());
//						detail.setChargeRates(data.getChargeRates());
//						detail.setCreatedBy("setCreatedBy");
//						detail.setCreatedDate(new Date());
//						detail.setIsDelete("N");
//						detailList.add(detail);
//					}
//				}
//				chargeMappingRepository.saveAll(detailList);
//			}
//
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//	}

	public void update(Electric008Req request) {
		logger.info("updateRateChargeConfig", request);

		try {
			// Header
			RicElectricChargeTypeConfig header = chargeTypeConfigRepository.findById(request.getTypeConfigId()).get();
			// set data
			header.setElectricType(request.getElectricType());
			header.setRateType(request.getRateType());
			header.setServiceChargeRates(request.getServiceChargeRates());
			header.setDescription(request.getDescription());
			header.setUpdatedBy("setUpdatedBy");
			header.setUpdatedDate(new Date());
			header = chargeTypeConfigRepository.save(header);
			// save data Header
			header = chargeTypeConfigRepository.save(header);
			// ==================================================================
			// set detail
			if (request.getElectric008DtlReq() != null && request.getElectric008DtlReq().size() > 0) {
				RicElectricChargeMapping detail = null;
				List<RicElectricChargeMapping> detailList = new ArrayList<>();
				for (Electric008DtlReq data : request.getElectric008DtlReq()) {
					if (data.getChargeMappingId() != null) {
						detail = new RicElectricChargeMapping();
						detail = chargeMappingRepository.findById(data.getChargeMappingId()).get();
						detail.setVoltageType(data.getVoltageType());
						detail.setStartRange(data.getStartRange());
						detail.setEndRange(data.getEndRange());
						detail.setChargeRates(data.getChargeRates());
						detail.setUpdatedBy("setUpdatedBy");
						detail.setUpdatedDate(new Date());
						detailList.add(detail);
					} else {
						detail = new RicElectricChargeMapping();
						detail.setTypeConfigId(header.getTypeConfigId());
						detail.setVoltageType(data.getVoltageType());
						detail.setStartRange(data.getStartRange());
						detail.setEndRange(data.getEndRange());
						detail.setChargeRates(data.getChargeRates());
						detail.setCreatedBy("setCreatedBy");
						detail.setCreatedDate(new Date());
						detail.setIsDelete("N");
						detailList.add(detail);
					}
				}
				chargeMappingRepository.saveAll(detailList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void deleteDetail(String idStr) {
		logger.info("deleteDetail");
		try {
			chargeMappingRepository.deleteById(Long.valueOf(idStr));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
