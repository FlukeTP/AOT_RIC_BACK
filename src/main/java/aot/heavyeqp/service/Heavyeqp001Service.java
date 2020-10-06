package aot.heavyeqp.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.heavyeqp.model.RicHeavyEquipmentRevenue;
import aot.heavyeqp.repository.Heavyeqp001Dao;
import aot.heavyeqp.repository.jpa.RicHeavyEquipmentRevenueRepository;
import aot.heavyeqp.vo.request.Heavyeqp001Req;
import aot.heavyeqp.vo.request.Heavyeqp001SaveReq;
import aot.heavyeqp.vo.response.Heavyeqp001Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_10;
import aot.util.sapreqhelper.SapArRequest_4_8;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Heavyeqp001Service {

	private static final Logger logger = LoggerFactory.getLogger(Heavyeqp001Service.class);

	@Autowired
	private RicHeavyEquipmentRevenueRepository ricHeavyEquipmentRevenueRepository;

	@Autowired
	private SapArRequest_1_10 sapArRequest_1_10;

	@Autowired
	private SapArRequest_4_8 sapArRequest_4_8;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private Heavyeqp001Dao heavyeqp001Dao;

	public void saveHeavyeqpSC(Heavyeqp001SaveReq request) {
		logger.info("saveHeavyeqpSC", request);
		RicHeavyEquipmentRevenue ricHeavyEquipmentRevenue = null;
		ricHeavyEquipmentRevenue = new RicHeavyEquipmentRevenue();
		// set data
		ricHeavyEquipmentRevenue.setEntreprenuerServiceCode(request.getEntreprenuerServiceCode());
		ricHeavyEquipmentRevenue.setEntreprenuerServiceName(request.getEntreprenuerServiceName());
		ricHeavyEquipmentRevenue.setEntreprenuerServiceNo(request.getEntreprenuerServiceNo());
		ricHeavyEquipmentRevenue.setCustomerCode(request.getCustumerCode());
		ricHeavyEquipmentRevenue.setCustomerName(request.getCustumerName());
		ricHeavyEquipmentRevenue.setCustomerBranch(request.getCustumerBranch());
		ricHeavyEquipmentRevenue.setContractNo(request.getContractNo());
		ricHeavyEquipmentRevenue.setEquipmentType(request.getEquipmentType());
		ricHeavyEquipmentRevenue.setLicensePlate(request.getLicensePlate());
		ricHeavyEquipmentRevenue.setNumberLicensePlate(request.getNumberLicensePlate());
		ricHeavyEquipmentRevenue.setStartDate(ConvertDateUtils.parseStringToDate(request.getStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		ricHeavyEquipmentRevenue.setEndDate(ConvertDateUtils.parseStringToDate(request.getEndDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		ricHeavyEquipmentRevenue.setPeriodTime(request.getPeriodTime());
		BigDecimal AllFees = new BigDecimal(request.getAllFees());
		ricHeavyEquipmentRevenue.setAllFees(AllFees);
		BigDecimal Vat = new BigDecimal(request.getVat());
		ricHeavyEquipmentRevenue.setVat(Vat);
		BigDecimal DriverRates = new BigDecimal(request.getDriverRates());
		ricHeavyEquipmentRevenue.setDriverRates(DriverRates);
		BigDecimal TotalMone = new BigDecimal(request.getTotalMoney());
		ricHeavyEquipmentRevenue.setTotalMoney(TotalMone);
		ricHeavyEquipmentRevenue.setPaymentType(request.getPaymentType());
		ricHeavyEquipmentRevenue.setResponsiblePerson(request.getResponsiblePerson());
		ricHeavyEquipmentRevenue.setRemark(request.getRemark());
		ricHeavyEquipmentRevenue.setCreatedDate(new Date());
		ricHeavyEquipmentRevenue.setCreatedBy(UserLoginUtils.getCurrentUsername());
		ricHeavyEquipmentRevenue.setIsDeleted(RICConstants.STATUS.NO);
		ricHeavyEquipmentRevenue.setSapStatus(RICConstants.STATUS.PENDING);
		// save data
		ricHeavyEquipmentRevenueRepository.save(ricHeavyEquipmentRevenue);
	}

	public List<Heavyeqp001Res> getHeavyEquipment(Heavyeqp001Req req) {
		List<Heavyeqp001Res> ricHeavyEquipmentRevenues = new ArrayList<Heavyeqp001Res>();
		ricHeavyEquipmentRevenues = heavyeqp001Dao.getDataByDate(req);
		return ricHeavyEquipmentRevenues;
	}

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(Heavyeqp001Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicHeavyEquipmentRevenue ricHeavyEquipmentRevenue = ricHeavyEquipmentRevenueRepository
					.findById(request.getHeavyEquipmentRevenueId()).get();
			// set request
			if (CommunicateConstants.PAYMENT_TYPE.BILL.DESC_TH.equals(ricHeavyEquipmentRevenue.getPaymentType())) {
				dataSend = sapArRequest_1_10.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, DoctypeConstants.IH, Long.valueOf(request.getHeavyEquipmentRevenueId()),
						"HEAVY001");
			} else {
				dataSend = sapArRequest_4_8.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getHeavyEquipmentRevenueId(), DoctypeConstants.I8);
			}

			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(ricHeavyEquipmentRevenue.getHeavyEquipmentRevenueId());
			reqConnection.setTableName("ric_heavy_equipment_revenue");
			reqConnection.setColumnId("heavy_equipment_revenue_id");
			// reqConnection.setColumnInvoiceNo("invoice_no");
			// reqConnection.setColumnTransNo("transaction_no");
			// reqConnection.setColumnSapJsonReq("sap_json_req");
			// reqConnection.setColumnSapJsonRes("sap_json_res");
			// reqConnection.setColumnSapError("sap_error");
			// reqConnection.setColumnSapStatus("sap_status");

			/* __________________ set connection SAP __________________ */
			sapResponse = sapARService.setSapConnection(reqConnection);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public ByteArrayOutputStream downloadTemplate(String startDate) throws Exception {
		/* find data to write excel */
		Heavyeqp001Req reqInfo = new Heavyeqp001Req();
		reqInfo.setStartDate(startDate);
		List<Heavyeqp001Res> dataExport = getHeavyEquipment(reqInfo);

		/* style */
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

		String[] header = { "ลำดับที่", "ผู้ประกอบการ", "ประเภท", "จำนวนเงินรวม", "วันเวลาที่เริ่ม",
				"วันเวลาที่สิ้นสุด", "รวมเวลาใช้งาน", "ผู้รับผิดชอบ", "อัตราค่าภาระ", "", "" };

		String[] header2 = { "", "", "", "", "", "", "", "", "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ",
				"สถานะการส่งข้อมูลเข้าสู่ระบบ SAP" };

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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));

		rowNum++;
		int index = 1;
		for (Heavyeqp001Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEquipmentType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getTotalMoney()));
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getStartDate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEndDate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPeriodTime());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getResponsiblePerson());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatus());
			cell.setCellStyle(tdLeft);

			rowNum++;
			index++;
		}

		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i <= 10; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

}
