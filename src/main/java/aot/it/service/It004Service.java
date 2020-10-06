package aot.it.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
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

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.it.model.RicItOtherCreateInvoice;
import aot.it.repository.It004Dao;
import aot.it.repository.jpa.RicItOtherCreateInvoiceRepository;
import aot.it.vo.request.It004Req;
import aot.it.vo.response.It004Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_11;
import aot.util.sapreqhelper.SapArRequest_4_18;
import aot.util.sapreqhelper.SapArRequest_6_11;
import aot.util.sapreqhelper.SapArRequest_6_12;
import aot.util.sapreqhelper.SapArRequest_7_5;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It004Service {

	private static final Logger logger = LoggerFactory.getLogger(It004Service.class);

	@Autowired
	private RicItOtherCreateInvoiceRepository ricItOtherCreateInvoiceRepository;

	@Autowired
	private It004Dao it004Dao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapArRequest_1_11 sapArRequest_1_11;
	
	@Autowired
	private SapArRequest_4_18 sapArRequest_4_18;
	
	@Autowired
	private SapArRequest_6_11 sapArRequest_6_11;
	
	@Autowired
	private SapArRequest_6_12 sapArRequest_6_12;
	
	@Autowired
	private SapArRequest_7_5 sapArRequest_7_5;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(It004Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicItOtherCreateInvoice otherCreate = ricItOtherCreateInvoiceRepository
					.findById(Long.valueOf(request.getItOtherCreateInvoiceId())).get();

			if (SAPConstants.CHARGE_RATE_TYPE.LG_TH.equals(otherCreate.getChargeRatesType())) {
				if (SAPConstants.PAYMENT_TYPE.CASH_EN.equals(otherCreate.getPaymentType())) {
					dataSend = sapArRequest_6_12.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.IH, otherCreate);
				} else if (SAPConstants.PAYMENT_TYPE.BANK_GUARANTEE_UPPER.equals(otherCreate.getPaymentType())) {
					dataSend = sapArRequest_6_11.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.IH, otherCreate);
				}
			} else if (SAPConstants.CHARGE_RATE_TYPE.ONE_TIME_TH.equals(otherCreate.getChargeRatesType())) {
				if (SAPConstants.PAYMENT_TYPE.CASH_EN.equals(otherCreate.getPaymentType())) {
					dataSend = sapArRequest_4_18.getARRequestIt004(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.I8, otherCreate);
				} else if (SAPConstants.PAYMENT_TYPE.BILLING_EN.equals(otherCreate.getPaymentType())) {
					dataSend = sapArRequest_1_11.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.IH, otherCreate);
				}
			}

			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(otherCreate.getItOtherCreateInvoiceId());
			reqConnection.setTableName("ric_it_other_create_invoice");
			reqConnection.setColumnId("it_create_invoice_id");
			// reqConnection.setColumnInvoiceNo("invoice_no");
			// reqConnection.setColumnTransNo("transaction_no");
			// reqConnection.setColumnSapJsonReq("sap_json_req");
			// reqConnection.setColumnSapJsonRes("sap_json_res");
			// reqConnection.setColumnSapError("sap_error");
			// reqConnection.setColumnSapStatus("sap_status");

			/* __________________ set connection SAP __________________ */
			sapResponse = sapARService.setSapConnection(reqConnection);
			// update req table
//			if (SAPConstants.SAP_SUCCESS.equals(sapResponse.getStatus())) {
//			    eleReq.setRequestStatus(RICConstants.STATUS.NO);
//			    eleReq.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateCancel(),
//			    ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//			    eleReq.setFlagInfo(RICConstants.FLAG_INFO_END);
//			    eleReq.setIsDelete(RICConstants.STATUS.YES);
//			    ricElectricReqRepository.save(eleReq);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}
	
	public SapResponse sendSapCancel(It004Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicItOtherCreateInvoice otherCreate = ricItOtherCreateInvoiceRepository
					.findById(Long.valueOf(request.getItOtherCreateInvoiceId())).get();
			dataSend = sapArRequest_7_5.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, DoctypeConstants.IX,otherCreate.getItOtherCreateInvoiceId());

			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(otherCreate.getItOtherCreateInvoiceId());
			reqConnection.setTableName("ric_it_other_create_invoice");
			reqConnection.setColumnId("it_create_invoice_id");
			reqConnection.setColumnInvoiceNo("invoice_no_cancel");
			reqConnection.setColumnTransNo("transaction_no_cancel");
			reqConnection.setColumnSapJsonReq("sap_json_req_cancel");
			reqConnection.setColumnSapJsonRes("sap_json_res_cancel");
			reqConnection.setColumnSapError("sap_error_cancel");
			reqConnection.setColumnSapStatus("sap_status_cancel");

			/* __________________ set connection SAP __________________ */
			sapResponse = sapARService.setSapConnection(reqConnection);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<It004Res> getListAll(It004Req request) throws Exception {

		logger.info("getListAll");

		List<It004Res> list = new ArrayList<>();
		try {
			list = it004Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public It004Res findById(It004Req request) throws Exception {

		logger.info("findById");

		It004Res res = new It004Res();

		try {
			// update
			if (StringUtils.isNotEmpty(request.getItOtherCreateInvoiceId())) {
				RicItOtherCreateInvoice req = ricItOtherCreateInvoiceRepository
						.findById(Long.valueOf(request.getItOtherCreateInvoiceId())).get();
				// set data for return
				res.setEntreprenuerCode(req.getEntreprenuerCode());
				res.setEntreprenuerName(req.getEntreprenuerName());
				res.setEntreprenuerBranch(req.getEntreprenuerBranch());
				res.setContractNo(req.getContractNo());
				res.setOtherType(req.getOtherType());
				res.setChargeRatesType(req.getChargeRatesType());
				res.setChargeRates(req.getChargeRates());
				res.setRentalObject(req.getRentalObject());
				res.setTotalChargeRates(req.getTotalChargeRates());
				res.setTotalAmount(req.getTotalAmount());
				res.setRequestStartDate(ConvertDateUtils.formatDateToString(req.getRequestStartDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRequestEndDate(ConvertDateUtils.formatDateToString(req.getRequestEndDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRemark(req.getRemark());
				res.setAirport(req.getAirport());
				res.setPaymentType(req.getPaymentType());
				res.setBankName(req.getBankName());
				res.setBankBranch(req.getBankBranch());
				res.setBankExplanation(req.getBankExplanation());
				res.setBankGuaranteeNo(req.getBankGuaranteeNo());
				res.setBankExpNo(ConvertDateUtils.formatDateToString(req.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
						ConvertDateUtils.LOCAL_EN));
				res.setReceiptNo(req.getReceiptNo());
				res.setInvoiceNo(req.getInvoiceNo());
				res.setSapStatus(req.getSapStatus());
				res.setSapError(req.getSapError());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(It004Req request) throws Exception {
		logger.info("save");

		RicItOtherCreateInvoice req = null;
		try {
			if (StringUtils.isNotEmpty(request.getItOtherCreateInvoiceId())) {
				req = ricItOtherCreateInvoiceRepository.findById(Long.valueOf(request.getItOtherCreateInvoiceId()))
						.get();
				// set data
				req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				req.setUpdatedDate(new Date());
			} else {
				req = new RicItOtherCreateInvoice();
				// set data
				req.setSapStatus(RICConstants.STATUS.PENDING);
				req.setCreatedBy(UserLoginUtils.getCurrentUsername());
				req.setCreatedDate(new Date());
				req.setIsDelete(RICConstants.STATUS.NO);
			}
			req.setEntreprenuerCode(request.getEntreprenuerCode());
			req.setEntreprenuerName(request.getEntreprenuerName());
			req.setEntreprenuerBranch(request.getEntreprenuerBranch());
			req.setContractNo(request.getContractNo());
			req.setOtherType(request.getOtherType());
			req.setRentalObject(request.getRentalObject());
			req.setChargeRatesType(request.getChargeRatesType());
			req.setChargeRates(request.getChargeRates());
			req.setTotalChargeRates(request.getTotalChargeRates());
			req.setTotalAmount(request.getTotalAmount());
			req.setRequestStartDate(ConvertDateUtils.parseStringToDate(request.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			req.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getRequestEndDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			req.setRemark(request.getRemark());
			req.setAirport(UserLoginUtils.getUser().getAirportCode());
			req.setPaymentType(request.getPaymentType());
			req.setBankName(request.getBankName());
			req.setBankBranch(request.getBankBranch());
			req.setBankExplanation(request.getBankExplanation());
			req.setBankGuaranteeNo(request.getBankGuaranteeNo());
			req.setBankExpNo(ConvertDateUtils.parseStringToDate(request.getBankExpNo(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			// save data
			ricItOtherCreateInvoiceRepository.save(req);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It004Req form = new It004Req();
		List<It004Res> dataExport = new ArrayList<It004Res>();
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

		String[] header = { "ลำดับที่", "ผู้ประกอบการ", "รหัสผู้ประกอบการ", "เลขที่สัญญา", "ประเภทบริการ",
				"ประเภทอัตราค่าภาระ", "อัตราค่าภาระ", "จำนวนหน่วย", "จำนวนเงิน(รวมภาษีมูลค่าเพิ่ม)",
				"วันที่ขอใช้บริการ", "ท่าอากาศยาน" };

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
		for (It004Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerCode());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getOtherType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getChargeRatesType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getChargeRates(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getTotalAmount(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getTotalChargeRates(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStartDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getAirport());
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
