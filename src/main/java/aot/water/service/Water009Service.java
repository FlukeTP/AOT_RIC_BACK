package aot.water.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.DoctypeConstants;
import aot.common.service.RicNoGenerator;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import aot.util.sapreqhelper.SapArRequest_1_5;
import aot.water.model.RicWaterWasteDetail;
import aot.water.model.RicWaterWasteHeader;
import aot.water.repository.Water009Dao;
import aot.water.repository.jpa.RicWaterWasteDetailRepository;
import aot.water.repository.jpa.RicWaterWasteHeaderRepository;
import aot.water.vo.request.Water009DtlReq;
import aot.water.vo.request.Water009Req;
import aot.water.vo.response.Water009DtlRes;
import aot.water.vo.response.Water009Res;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water009Service {

	private static final Logger logger = LoggerFactory.getLogger(Water009Service.class);

	@Autowired
	private RicWaterWasteHeaderRepository headerRepository;

	@Autowired
	private RicWaterWasteDetailRepository detailRepository;

	@Autowired
	private Water009Dao water009Dao;

	@Autowired
	private CommonARRequest commonARRequest;
	
	@Autowired
	private SapArRequest_1_5 sapArRequest_1_5;
	
	@Autowired
	private SAPARService sapARService;

	@Autowired
	private RicNoGenerator ricNoGenerator;
	
	@Autowired
	private SysConstantService sysConstantService;

	@Transactional(rollbackOn = { Exception.class })
	public Water009Req save(Water009Req request) {
		logger.info("save Water009", request);

		// Header
		RicWaterWasteHeader header = null;
		header = new RicWaterWasteHeader();
		// set data
		header.setCustomerCode(request.getCustomerCode());
		header.setCustomerName(request.getCustomerName());
		header.setCustomerBranch(request.getCustomerBranch());
		header.setContractNo(request.getContractNo());
		header.setAirport(UserLoginUtils.getUser().getAirportCode());
		header.setRentalAreaCode(request.getRentalAreaCode());
		header.setRentalAreaName(request.getRentalAreaName());
		header.setPaymentType(request.getPaymentType());
		header.setRemark(request.getRemark());
		header.setCreatedBy(UserLoginUtils.getCurrentUsername());
		header.setCreatedDate(new Date());
		header.setIsDelete(FLAG.N_FLAG);
		// save data Header
		header = headerRepository.save(header);
		// ==================================================================

		if (request.getWater009DtlReq() != null && request.getWater009DtlReq().size() > 0) {
			// DTL
			RicWaterWasteDetail detail = null;
			List<RicWaterWasteDetail> detailList = new ArrayList<>();

			for (Water009DtlReq data : request.getWater009DtlReq()) {
				detail = new RicWaterWasteDetail();
				detail.setWasteHeaderId(header.getWasteHeaderId());
				detail.setServiceType(data.getServiceType());
				detail.setChargeRates(data.getChargeRate());
				detail.setUnit(data.getUnit());
				detail.setAmount(data.getAmount());
				detail.setVat(sysConstantService.getSumVat(data.getAmount()));
				detail.setNetAmount(sysConstantService.getTotalVat(data.getAmount()));
				detail.setCreatedBy(UserLoginUtils.getCurrentUsername());
				detail.setCreatedDate(new Date());
				detail.setIsDelete(FLAG.N_FLAG);
				detailList.add(detail);
			}
			// save data DTL
			detailRepository.saveAll(detailList);
		}
		return request;
	}

	public List<Water009Res> findData() {
		logger.info("findData");
		List<Water009Res> dataList = new ArrayList<>();
		dataList = water009Dao.findData();
		return dataList;
	}

	public Water009Res getById(String idStr) {
		logger.info("getById");
		RicWaterWasteHeader header = headerRepository.findById(Long.valueOf(idStr)).get();
		List<RicWaterWasteDetail> detailList = detailRepository.findByWasteHeaderId(Long.valueOf(idStr));
		Water009Res res = null;
		Water009DtlRes resDtl = null;
		List<Water009DtlRes> resDtlList = new ArrayList<>();
		try {
			// set header
			res = new Water009Res();
			res.setCustomerCode(header.getCustomerCode());
			res.setCustomerName(header.getCustomerName());
			res.setCustomerBranch(header.getCustomerBranch());
			res.setContractNo(header.getContractNo());
			res.setRentalAreaCode(header.getRentalAreaCode());
			res.setRentalAreaName(header.getRentalAreaName());
			res.setPaymentType(header.getPaymentType());
			res.setRemark(header.getRemark());

			// set detail
			if (detailList != null && detailList.size() > 0) {
				for (RicWaterWasteDetail detail : detailList) {
					resDtl = new Water009DtlRes();
					resDtl.setWasteDetailId(detail.getWasteDetailId());
					resDtl.setWasteHeaderId(detail.getWasteHeaderId());
					resDtl.setServiceType(detail.getServiceType());
					resDtl.setChargeRate(detail.getChargeRates());
					resDtl.setUnit(detail.getUnit());
					resDtl.setAmount(detail.getAmount());
					resDtl.setVat(detail.getVat());
					resDtl.setNetAmount(detail.getNetAmount());
					resDtlList.add(resDtl);
				}
			}
			res.setWater009DtlRes(resDtlList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	public List<Water009DtlRes> findDtlById(String idStr) {

		Water009DtlRes resDtl = null;
		List<Water009DtlRes> dataList = new ArrayList<>();

		List<RicWaterWasteDetail> detailList = detailRepository.findByWasteHeaderId(Long.valueOf(idStr));
		if (detailList != null && detailList.size() > 0) {
			for (RicWaterWasteDetail detail : detailList) {
				resDtl = new Water009DtlRes();
				resDtl.setWasteDetailId(detail.getWasteDetailId());
				resDtl.setWasteHeaderId(detail.getWasteHeaderId());
				resDtl.setServiceType(detail.getServiceType());
				resDtl.setChargeRate(detail.getChargeRates());
				resDtl.setUnit(detail.getUnit());
				resDtl.setAmount(detail.getAmount());
				resDtl.setVat(detail.getVat());
				resDtl.setNetAmount(detail.getNetAmount());
				dataList.add(resDtl);
			}
		}
		return dataList;
	}

//	public void update(String idStr, Water009Req request) {
//		logger.info("updateRateChargeConfig");
//		try {
//			// set header
//			RicWaterWasteHeader header = headerRepository.findById(Long.valueOf(idStr)).get();
//			header.setCustomerCode(request.getCustomerCode());
//			header.setCustomerName(request.getCustomerName());
//			header.setCustomerBranch(request.getCustomerBranch());
//			header.setContractNo(request.getContractNo());
//			header.setRentalArea(request.getRentalArea());
//			header.setRemark(request.getRemark());
//			header.setUpdatedBy("setUpdatedBy");
//			header.setUpdatedDate(new Date());
//			header = headerRepository.save(header);
//			// set detail
//			if (request.getWater009DtlReq() != null && request.getWater009DtlReq().size() > 0) {
//				RicWaterWasteDetail detail = null;
//				List<RicWaterWasteDetail> detailList = new ArrayList<>();
//				for (Water009DtlReq data : request.getWater009DtlReq()) {
//					if (data.getWasteDetailId() != null) {
//						detail = new RicWaterWasteDetail();
//						detail = detailRepository.findById(data.getWasteDetailId()).get();
//						detail.setServiceType(data.getServiceType());
//						detail.setChargeRates(data.getChargeRate());
//						detail.setUnit(data.getUnit());
//						detail.setAmount(data.getAmount());
//						detail.setVat(data.getVat());
//						detail.setNetAmount(data.getNetAmount());
//						detail.setUpdatedBy("setUpdatedBy");
//						detail.setUpdatedDate(new Date());
//						detailList.add(detail);
//					} else {
//						detail = new RicWaterWasteDetail();
//						detail.setWasteHeaderId(header.getWasteHeaderId());
//						detail.setServiceType(data.getServiceType());
//						detail.setChargeRates(data.getChargeRate());
//						detail.setUnit(data.getUnit());
//						detail.setAmount(data.getAmount());
//						detail.setVat(data.getVat());
//						detail.setNetAmount(data.getNetAmount());
//						detail.setCreatedBy("setCreatedBy");
//						detail.setCreatedDate(new Date());
//						detail.setIsDelete("N");
//						detailList.add(detail);
//					}
//				}
//				detailRepository.saveAll(detailList);
//			}
//
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//	}

	@Transactional(rollbackOn = { Exception.class })
	public void update(Water009Req request) {
		logger.info("update Water009", request);
		// Header
		try {
			RicWaterWasteHeader header = headerRepository.findById(request.getWasteHeaderId()).get();
//			header.setCustomerCode(request.getCustomerCode());
//			header.setCustomerName(request.getCustomerName());
//			header.setCustomerBranch(request.getCustomerBranch());
//			header.setContractNo(request.getContractNo());
//			header.setRentalArea(request.getRentalArea());
			header.setRemark(request.getRemark());
			header.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			header.setUpdatedDate(new Date());
			header = headerRepository.save(header);
			// ==================================================================
			if (request.getWater009DtlReq() != null && request.getWater009DtlReq().size() > 0) {
				RicWaterWasteDetail detail = null;
				List<RicWaterWasteDetail> detailList = new ArrayList<>();
				for (Water009DtlReq data : request.getWater009DtlReq()) {
					if (data.getWasteDetailId() != null) {
						detail = new RicWaterWasteDetail();
						detail = detailRepository.findById(data.getWasteDetailId()).get();
						detail.setServiceType(data.getServiceType());
						detail.setChargeRates(data.getChargeRate());
						detail.setUnit(data.getUnit());
						detail.setAmount(data.getAmount());
						detail.setVat(data.getVat());
						detail.setNetAmount(data.getNetAmount());
						detail.setUpdatedBy("setUpdatedBy");
						detail.setUpdatedDate(new Date());
						detailList.add(detail);
					} else {
						detail = new RicWaterWasteDetail();
						detail.setWasteHeaderId(header.getWasteHeaderId());
						detail.setServiceType(data.getServiceType());
						detail.setChargeRates(data.getChargeRate());
						detail.setUnit(data.getUnit());
						detail.setAmount(data.getAmount());
						detail.setVat(data.getVat());
						detail.setNetAmount(data.getNetAmount());
						detail.setCreatedBy("setCreatedBy");
						detail.setCreatedDate(new Date());
						detail.setIsDelete("N");
						detailList.add(detail);
					}
				}
				detailRepository.saveAll(detailList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void deleteDetail(String idStr) {
		logger.info("deleteDetail");
		try {
			detailRepository.deleteById(Long.valueOf(idStr));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public SapResponse sendSap(Long id) throws Exception {
		RicWaterWasteHeader hdr = headerRepository.findById(id).get();
		ArRequest dataSend = sapArRequest_1_5.getARRequestWater009(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE, DoctypeConstants.IF, hdr);
		
		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		
		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(hdr.getWasteHeaderId());
		reqConnection.setTableName("ric_water_waste_header");
		reqConnection.setColumnId("waste_header_id");
//		reqConnection.setColumnInvoiceNo("invoice_no");
//		reqConnection.setColumnTransNo("transaction_no");
//		reqConnection.setColumnSapJsonReq("sap_json_req");
//		reqConnection.setColumnSapJsonRes("sap_json_res");
//		reqConnection.setColumnSapError("sap_error");
//		reqConnection.setColumnSapStatus("sap_status");
		/* __________________ set connection SAP __________________ */
		return sapARService.setSapConnection(reqConnection);
	}

	public ByteArrayOutputStream downloadTemplate() throws IOException {
		List<Water009Res> dataExport = new ArrayList<Water009Res>();
		dataExport = findData();

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

		String[] header = { "ลำดับที่", "ผู้ประกอบการ", "เลขที่สัญญา", "ค่าความสกปรก", "จำนวนหน่วย", "ค่าปรับน้ำเสีย",
				"วันที่บันทึก", "ค่าปรับน้ำเสีย", "", "" };
		String[] header2 = { "", "", "", "", "", "", "", "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะ SAP" };

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
		row = sheet.createRow(rowNum);
		cellNum = 0;
		for (int i = 0; i < header2.length; i++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(header2[i]);
			cell.setCellStyle(TopicCenter);
			cell.setCellStyle(cellHeaderStyle);
			cellNum++;
		}

		// merge cell
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));

		rowNum++;
		int index = 1;
		for (Water009Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerName());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getServiceType());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getUnit()));
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getAmount()));
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNo());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
//			cell.setCellValue(data.getSapControlVo().getReverseRec());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatus());
			cell.setCellStyle(tdCenter);

			rowNum++;
			index++;
		}

		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i <= 9; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
