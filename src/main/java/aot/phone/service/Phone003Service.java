package aot.phone.service;

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

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.phone.model.RicPhoneRateCharge;
import aot.phone.model.RicPhoneReq;
import aot.phone.model.RicPhoneReqCancel;
import aot.phone.repository.Phone003Dao;
import aot.phone.repository.jpa.RicPhoneRateChargeRepository;
import aot.phone.repository.jpa.RicPhoneReqCancelRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import aot.phone.vo.request.Phone003Req;
import aot.phone.vo.response.Phone003Res;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_7_3;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

/**
 * Created by imake on 17/07/2019
 */
@Service
public class Phone003Service {
    private static final Logger logger = LoggerFactory.getLogger(Phone003Service.class);
    
    @Autowired
	private RicPhoneReqRepository ricPhoneReqRepository;
    
    @Autowired
	private RicPhoneRateChargeRepository ricPhoneRateChargeRepository;
    
    @Autowired
	private RicPhoneReqCancelRepository ricPhoneReqCancelRepository;

	@Autowired
	private Phone003Dao phone003Dao;

	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_7_3 sapArRequest_7_3;

	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	public SapResponse sendSap(Phone003Req request) throws Exception {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		
		RicPhoneReqCancel phoneReqCancel = ricPhoneReqCancelRepository
				.findById(Long.valueOf(request.getPhoneCancelId())).get();
		RicPhoneReq phoneReq = ricPhoneReqRepository.findByPhoneNo(phoneReqCancel.getPhoneNo());
		dataSend = sapArRequest_7_3.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
				request.getPhoneCancelId(), DoctypeConstants.IW);

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);

		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(phoneReqCancel.getPhoneCancelId());
		reqConnection.setTableName("ric_phone_req_cancel");
		reqConnection.setColumnId("phone_cancel_id");
		reqConnection.setColumnInvoiceNo("invoice_no_lg");
		reqConnection.setColumnTransNo("transaction_no_lg");
		reqConnection.setColumnSapJsonReq("sap_json_req_lg");
		reqConnection.setColumnSapJsonRes("sap_json_res_lg");
		reqConnection.setColumnSapError("sap_error_desc_lg");
		reqConnection.setColumnSapStatus("sap_status_lg");

		/* __________________ set connection SAP __________________ */
		sapResponse = sapARService.setSapConnection(reqConnection);
		// update req table
		if (SAPConstants.SAP_SUCCESS.equals(sapResponse.getStatus())) {
			phoneReq.setRequestStatus(RICConstants.STATUS.NO);
			// phoneReq.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateCancel(),
			// ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			phoneReq.setFlagInfo(RICConstants.FLAG_INFO_END);
			phoneReq.setIsDelete(RICConstants.STATUS.YES);
			ricPhoneReqRepository.save(phoneReq);
		}

		return sapResponse;
	}

	public List<Phone003Res> getListPhoneReqCancel(Phone003Req request) throws Exception {
		logger.info("getListPhoneReqCancel");
		return phone003Dao.findPhoneCancel(request);
	}

	public Phone003Res findPhoneReqCancelById(Phone003Req request) throws Exception {

		logger.info("findPhoneReqCancelById");

		Phone003Res res = null;
		RicPhoneReq req = null;
		List<RicPhoneRateCharge> rate = null;
		BigDecimal chargeRate = new BigDecimal(0), vat = new BigDecimal(0), totalChargeRate = new BigDecimal(0);
		RicPhoneReqCancel reqCancel = null;
		try {
			// update
			if (StringUtils.isNotEmpty(request.getPhoneCancelId())) {
				reqCancel = ricPhoneReqCancelRepository.findById(Long.valueOf(request.getPhoneCancelId())).get();
				if (StringUtils.isNotEmpty(reqCancel.getPhoneNo())) {
					req = ricPhoneReqRepository.findByPhoneNo(reqCancel.getPhoneNo());
					if (null == req) {
						req = new RicPhoneReq();
					}
				}
				if (StringUtils.isNotEmpty(String.valueOf(req.getPhoneReqId()))) {
					rate = ricPhoneRateChargeRepository.findByReqId(String.valueOf(req.getPhoneReqId()));
					if (null != rate) {
						for (RicPhoneRateCharge r : rate) {
							chargeRate = chargeRate.add(r.getChargeRates());
							vat = vat.add(r.getVat());
							totalChargeRate = totalChargeRate.add(r.getTotalChargeRates());
						}
					}
				}
				// set data for return
				res = new Phone003Res();
				res.setCustomerCode(reqCancel.getCustomerCode());
				res.setCustomerName(reqCancel.getCustomerName());
				res.setCustomerBranch(reqCancel.getCustomerBranch());
				res.setContractNo(reqCancel.getContractNo());
				res.setPhoneNo(reqCancel.getPhoneNo());
				res.setRentalAreaCode(req.getRentalAreaCode());
				res.setChargeRates(NumberUtils.roundUpTwoDigit(chargeRate));
				res.setVat(NumberUtils.roundUpTwoDigit(vat));
				res.setTotalchargeRates(NumberUtils.roundUpTwoDigit(totalChargeRate));
				res.setReqStartDate(ConvertDateUtils.formatDateToString(req.getRequestStartDate(), ConvertDateUtils.DD_MM_YYYY));
				res.setReqEndDate(ConvertDateUtils.formatDateToString(req.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY));
				res.setDateCancel(ConvertDateUtils.formatDateToString(reqCancel.getDateCancel(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setInvoiceNoLg(reqCancel.getInvoiceNoLg());
				res.setRemark(reqCancel.getRemark());
				res.setAirport(reqCancel.getAirport());
				res.setInvoiceNoReqcash(reqCancel.getInvoiceNoReqcash());
				res.setReceiptNoReqcash(reqCancel.getReceiptNoReqcash());
				res.setInvoiceNoReqlg(reqCancel.getInvoiceNoReqlg());
				res.setReceiptNoReqlg(reqCancel.getReceiptNoReqlg());
			} else {
				req = ricPhoneReqRepository.findById(Long.valueOf(request.getPhoneReqId())).get();
				if (StringUtils.isNotEmpty(String.valueOf(req.getPhoneReqId()))) {
					rate = ricPhoneRateChargeRepository.findByReqId(String.valueOf(req.getPhoneReqId()));
					if (null != rate) {
						for (RicPhoneRateCharge r : rate) {
							chargeRate = chargeRate.add(r.getChargeRates());
							vat = vat.add(r.getVat());
							totalChargeRate = totalChargeRate.add(r.getTotalChargeRates());
						}
					}
				}
				// get recieptNo from sap
				List<SapRicControl> sapReqCashList = sapRicControlDao.findByRefkey1(req.getTransactionNoCash());
				SapRicControl sapReqCash = new SapRicControl();
				if (0 < sapReqCashList.size()) {
					sapReqCash = sapReqCashList.get(0);
				}
				List<SapRicControl> sapReqLgList = sapRicControlDao.findByRefkey1(req.getTransactionNoLg());
				SapRicControl sapReqLg = new SapRicControl();
				if (0 < sapReqLgList.size()) {
					sapReqLg = sapReqLgList.get(0);
				}
				// set data for return
				res = new Phone003Res();
				res.setCustomerCode(req.getEntrepreneurCode());
				res.setCustomerName(req.getEntrepreneurName());
				res.setCustomerBranch(req.getBranchCustomer());
				res.setContractNo(req.getContractNo());
				res.setPhoneNo(req.getPhoneNo());
				res.setRentalAreaCode(req.getRentalAreaCode());
				res.setChargeRates(chargeRate);
				res.setVat(vat);
				res.setTotalchargeRates(totalChargeRate);
				res.setReqStartDate(ConvertDateUtils.formatDateToString(req.getRequestStartDate(), ConvertDateUtils.DD_MM_YYYY));
				res.setReqEndDate(ConvertDateUtils.formatDateToString(req.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY));
				res.setInvoiceNoReqcash(req.getInvoiceNoCash());
				res.setReceiptNoReqcash(sapReqCash.getDzdocNo());
				res.setInvoiceNoReqlg(req.getInvoiceNoLg());
				res.setReceiptNoReqlg(sapReqLg.getDzdocNo());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void savePhoneReqCancel(Phone003Req request) throws Exception {
		logger.info("savePhoneReqCancel");

		RicPhoneReqCancel reqCancel = null;
		try {
			if (StringUtils.isNotEmpty(request.getPhoneCancelId())) {
				reqCancel = ricPhoneReqCancelRepository.findById(Long.valueOf(request.getPhoneCancelId())).get();
				// set data
				reqCancel.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				reqCancel.setUpdatedDate(new Date());
			} else {
				reqCancel = new RicPhoneReqCancel();
				reqCancel.setSapStatusLg(RICConstants.STATUS.PENDING);
				reqCancel.setCreatedBy(UserLoginUtils.getCurrentUsername());
				reqCancel.setCreatedDate(new Date());
				reqCancel.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			reqCancel.setCustomerCode(request.getCustomerCode());
			reqCancel.setCustomerName(request.getCustomerName());
			reqCancel.setCustomerBranch(request.getCustomerBranch());
			reqCancel.setContractNo(request.getContractNo());
			reqCancel.setPhoneNo(request.getPhoneNo());
			reqCancel.setDateCancel(ConvertDateUtils.parseStringToDate(request.getDateCancel(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			reqCancel.setRemark(request.getRemark());
			reqCancel.setAirport(request.getAirport());
			reqCancel.setPhoneReqId(Long.valueOf(request.getPhoneReqId()));
			reqCancel.setInvoiceNoReqcash(request.getInvoiceNoReqcash());
			reqCancel.setReceiptNoReqcash(request.getReceiptNoReqcash());
			reqCancel.setInvoiceNoReqlg(request.getInvoiceNoReqlg());
			reqCancel.setReceiptNoReqlg(request.getReceiptNoReqlg());
			// save data
			ricPhoneReqCancelRepository.save(reqCancel);
			
			/* update end date request */
			RicPhoneReq phoneReq = ricPhoneReqRepository.findByPhoneNo(reqCancel.getPhoneNo());
			phoneReq.setRequestEndDate(reqCancel.getDateCancel());
			phoneReq.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			phoneReq.setUpdatedDate(new Date());
			ricPhoneReqRepository.save(phoneReq);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
	
	public ByteArrayOutputStream downloadTemplate(String customerName,String contractNo,String phoneNo) throws Exception {
		Phone003Req form = new Phone003Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setPhoneNo(phoneNo);
		List<Phone003Res> dataExport = new ArrayList<Phone003Res>();
		dataExport = getListPhoneReqCancel(form);

		XSSFWorkbook workbook = new XSSFWorkbook();
		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle tdStyle = ExcelUtils.createTdCellStyle(workbook);
		CellStyle TopicCenterlite = ExcelUtils.createTopicCenterliteStyle(workbook);
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
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

		String[] header = {
				  "ลำดับที่", "รหัสผู้ประกอบการ", "ผู้ประกอบการ", "เลขที่สัญญา",
				  "เลขหมาย", "เงินประกัน", "วันที่สิ้นสุดการใช้งาน", "ใบแจ้งหนี้อัตราค่าภาระ",
				  "ใบเสร็จอัตราค่าภาระ", "เลขใบแจ้งหนี้เงินประกัน", "ใบเสร็จเงินประกัน", "หมายเลขยืนยันการยกเลิก จาก SAP",
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
		int index = 1;
		for (Phone003Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerCode());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getCustomerName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getPhoneNo());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getTotalchargeRates()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDateCancel());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoReqcash());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptNoReqcash());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoReqlg());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptNoReqlg());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoLg());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatusLg());
			cell.setCellStyle(tdLeft);


			rowNum++;
			index++;
		}

		
		// set width
		int width = 76;
		sheet.setColumnWidth(0, width * 20);
		for (int i = 1; i <= 13; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
