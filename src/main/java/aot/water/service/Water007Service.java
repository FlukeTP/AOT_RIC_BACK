package aot.water.service;

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
import aot.common.constant.WaterConstants;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import aot.water.model.RicWaterMeter;
import aot.water.model.RicWaterRateCharge;
import aot.water.model.RicWaterReq;
import aot.water.model.RicWaterReqCancel;
import aot.water.repository.Water007Dao;
import aot.water.repository.jpa.RicWaterMeterRepository;
import aot.water.repository.jpa.RicWaterRateChargeRepository;
import aot.water.repository.jpa.RicWaterReqCancelRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import aot.water.vo.request.Water007Req;
import aot.water.vo.response.Water007Res;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water007Service {

	private static final Logger logger = LoggerFactory.getLogger(Water007Service.class);

	@Autowired
	private RicWaterReqCancelRepository ricWaterReqCancelRepository;

	@Autowired
	private RicWaterMeterRepository ricWaterMeterRepository;

	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;

	@Autowired
	private RicWaterRateChargeRepository ricWaterRateChargeRepository;

	@Autowired
	private SapRicControlDao sapRicControlDao;

	@Autowired
	private Water007Dao water007Dao;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private SAPARService sapARService;

	public SapResponse sendSap(Water007Req request) throws Exception {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		RicWaterReqCancel waterReqCancel = ricWaterReqCancelRepository
				.findById(Long.valueOf(request.getWaterCancelId())).get();
		// get ric_water_req
		RicWaterReq req = ricWaterReqRepository.findById(Long.valueOf(waterReqCancel.getReqId())).get();
		// set request 7_2
		dataSend = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
				DoctypeConstants.IQ, waterReqCancel.getTransactionNoLg());

		BigDecimal chageRate = new BigDecimal(0);
		BigDecimal chargeVat = new BigDecimal(0);
		BigDecimal totalChargeRate = new BigDecimal(0);
		if (StringUtils.isNotEmpty(req.getInsuranceRates())) {
			chageRate = new BigDecimal(req.getInsuranceRates());
		}
		if (StringUtils.isNotEmpty(req.getVatInsurance())) {
			chargeVat = new BigDecimal(req.getVatInsurance());
		}
		if (StringUtils.isNotEmpty(req.getTotalInsuranceChargeRates())) {
			totalChargeRate = new BigDecimal(req.getTotalInsuranceChargeRates());
		}
		// Create Item1
		Item item1 = dataSend.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("11"); // require
		item1.setAccount(request.getCustomerCode()); // require
		item1.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).negate().toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
		;
		// item1.setAssignment(waterReqCancel.getReceiptNoReqlg().concat(sapReqLg.getDzyear()));
		item1.setText(SAPConstants.WATER.TEXT);
		item1.setContractNo(request.getContractNo());
		// item1.setInvoiceRef(sapReqLg.getDzdocNo());
		// item1.setFiscalYear(sapReqLg.getDzyear());
		// item1.setLineItem("001");
		List<SapRicControl> sapControls = sapRicControlDao.findByRefkey1(request.getTransactionNoLg());
		if (sapControls.size() > 0) {
			String receipt = sapControls.get(0).getDzdocNo();
			String dzYear = sapControls.get(0).getDzyear();
			if (receipt != null && dzYear != null) {
				item1.setAssignment(receipt.concat(dzYear)); // receiptYYYY
			} else {
				throw new Exception("dzdocNo and dzyear is not null or empty !! => " + "dzdocNo: " + receipt
						+ ", dzyear: " + dzYear);
			}
		}
		itemList.add(item1);

		// Create Item2
		Item item2 = dataSend.getHeader().get(0).getItem().get(1);
		item2.setPostingKey("09"); // require
		item2.setAccount(request.getCustomerCode()); // require
		item2.setSpGL(SPECIAL_GL.SPGL_3);
		item2.setAmount(NumberUtils.roundUpTwoDigit(chageRate).toString()); // require
		item2.setTaxCode("O7");
		item2.setAssignment(request.getSerialNo());
		item2.setText(SAPConstants.WATER.TEXT);
		item2.setContractNo(request.getContractNo());
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		itemList.add(item2);

		// Create Item3
		Item item3 = dataSend.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("40"); // require
		item3.setAccount("2450101001"); // require
		item3.setAmount(NumberUtils.roundUpTwoDigit(chargeVat).toString()); // require
		item3.setTaxCode("O7");
		item3.setAssignment(request.getSerialNo());
		item3.setText(SAPConstants.WATER.TEXT);
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(chageRate).toString());
		itemList.add(item3);

		dataSend.getHeader().get(0).setItem(itemList);

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);

		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(waterReqCancel.getWaterCancelId());
		reqConnection.setTableName("ric_water_req_cancel");
		reqConnection.setColumnId("water_cancel_id");
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
			req.setRequestStatus(FLAG.N_FLAG);
//			req.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateCancel(),
//					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			req.setFlagInfo(RICConstants.FLAG_INFO_END);
			req.setIsDelete(FLAG.Y_FLAG);
			ricWaterReqRepository.save(req);
		}
		return sapResponse;
	}

	public List<Water007Res> getListWaterReqCancel(Water007Req request) throws Exception {

		logger.info("getListWaterReqCancel");

		List<Water007Res> list = new ArrayList<>();
		try {
			list = water007Dao.findWaterCancel(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public Water007Res findWaterReqCancelById(Water007Req request) throws Exception {

		logger.info("findWaterReqCancelById");

		RicWaterReq req = null;
		RicWaterMeter meter = null;
		Water007Res res = null;
		RicWaterReqCancel reqCancel = null;
		try {
			// update
			if (StringUtils.isNotEmpty(request.getWaterCancelId())) {
				reqCancel = ricWaterReqCancelRepository.findById(Long.valueOf(request.getWaterCancelId())).get();
				// find data meter
				if (StringUtils.isNotEmpty(reqCancel.getSerialNo())) {
					meter = ricWaterMeterRepository.findBySerialNo(reqCancel.getSerialNo());
					if (null == meter) {
						meter = new RicWaterMeter();
					}
				}
				// set data for return
				res = new Water007Res();
				res.setCustomerCode(reqCancel.getCustomerCode());
				res.setCustomerName(reqCancel.getCustomerName());
				res.setCustomerBranch(reqCancel.getCustomerBranch());
				res.setContractNo(reqCancel.getContractNo());
				res.setSerialNo(meter.getSerialNo());
				res.setMeterName(meter.getMeterName());
				res.setMeterType(meter.getMeterType());
				res.setMeterLocation(meter.getMeterLocation());
				res.setChargeRates(reqCancel.getChargeRates());
				res.setVat(reqCancel.getVat());
				res.setTotalchargeRates(reqCancel.getTotalchargeRates());
				res.setDateCancel(ConvertDateUtils.formatDateToString(reqCancel.getDateCancel(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setInvoiceNoLg(reqCancel.getInvoiceNoLg());
				res.setRemark(reqCancel.getRemark());
				res.setAirport(reqCancel.getAirport());
				res.setInvoiceNoReqcash(reqCancel.getInvoiceNoReqcash());
				res.setReceiptNoReqcash(reqCancel.getReceiptNoReqcash());
				res.setInvoiceNoReqlg(reqCancel.getInvoiceNoReqlg());
				res.setReceiptNoReqlg(reqCancel.getReceiptNoReqlg());
			} else {
				// find data req from TABLE ric_water_req
				if (StringUtils.isNotEmpty(request.getReqId())) {
					req = ricWaterReqRepository.findById(Long.valueOf(request.getReqId())).get();
				}
				// find data meter
				if (StringUtils.isNotEmpty(req.getMeterSerialNo())) {
					meter = ricWaterMeterRepository.findBySerialNo(req.getMeterSerialNo());
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
				res = new Water007Res();
				res.setCustomerCode(req.getCustomerCode());
				res.setCustomerName(req.getCustomerName());
				res.setCustomerBranch(req.getCustomerBranch());
				res.setContractNo(req.getContractNo());
				res.setSerialNo(meter.getSerialNo());
				res.setMeterName(meter.getMeterName());
				res.setMeterType(meter.getMeterType());
				res.setMeterLocation(meter.getMeterLocation());
				BigDecimal sumChageRate = new BigDecimal(0);
				BigDecimal serviceCharge = new BigDecimal(0);
				BigDecimal totalChargeRate = new BigDecimal(0);
				List<RicWaterRateCharge> ricWaterRateChargeList = ricWaterRateChargeRepository
						.findByReqId(req.getReqId());
				if (WaterConstants.REQUEST_TYPE.OTHER_TH.equals(req.getRequestType())) {
					if (null != ricWaterRateChargeList) {
						for (RicWaterRateCharge rate : ricWaterRateChargeList) {
							if (StringUtils.isNotEmpty(rate.getChargeRate())) {
								sumChageRate = sumChageRate.add(new BigDecimal(rate.getChargeRate()));
							}
							if (StringUtils.isNotEmpty(rate.getChargeVat())) {
								serviceCharge = serviceCharge.add(new BigDecimal(rate.getChargeVat()));
							}
							if (StringUtils.isNotEmpty(rate.getTotalChargeRate())) {
								totalChargeRate = totalChargeRate.add(new BigDecimal(rate.getTotalChargeRate()));
							}
						}						
					}
				} else {
					if (StringUtils.isNotEmpty(req.getInsuranceRates())) {
						sumChageRate = new BigDecimal(req.getInsuranceRates());
					}
					if (StringUtils.isNotEmpty(req.getVatInsurance())) {
						serviceCharge = new BigDecimal(req.getVatInsurance());
					}
					if (StringUtils.isNotEmpty(req.getTotalInsuranceChargeRates())) {
						totalChargeRate = new BigDecimal(req.getTotalInsuranceChargeRates());
					}
				}
				res.setChargeRates(NumberUtils.roundUpTwoDigit(sumChageRate));
				res.setVat(NumberUtils.roundUpTwoDigit(serviceCharge));
				res.setTotalchargeRates(NumberUtils.roundUpTwoDigit(totalChargeRate));
				res.setRentalAreaCode(req.getRentalAreaCode());
				res.setAirport(req.getAirport());
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
	public void saveWaterReqCancel(Water007Req request) throws Exception {
		logger.info("saveWaterReqCancel");

		RicWaterReqCancel reqCancel = null;
		if (StringUtils.isNotEmpty(request.getWaterCancelId())) {
			reqCancel = ricWaterReqCancelRepository.findById(Long.valueOf(request.getWaterCancelId())).get();
			// set data
			reqCancel.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			reqCancel.setUpdatedDate(new Date());
		} else {
			reqCancel = new RicWaterReqCancel();
			reqCancel.setSapStatusLg(RICConstants.STATUS.PENDING);
			reqCancel.setCreatedBy(UserLoginUtils.getCurrentUsername());
			reqCancel.setCreateDate(new Date());
			reqCancel.setIsDelete(RICConstants.STATUS.NO);
		}
		// set data
		reqCancel.setCustomerCode(request.getCustomerCode());
		reqCancel.setCustomerName(request.getCustomerName());
		reqCancel.setCustomerBranch(request.getCustomerBranch());
		reqCancel.setContractNo(request.getContractNo());
		reqCancel.setSerialNo(request.getSerialNo());
		reqCancel.setChargeRates(request.getChargeRates());
		reqCancel.setVat(request.getVat());
		reqCancel.setTotalchargeRates(request.getTotalchargeRates());
		reqCancel.setDateCancel(ConvertDateUtils.parseStringToDate(request.getDateCancel(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		reqCancel.setRemark(request.getRemark());
		reqCancel.setReqId(Long.valueOf(request.getReqId()));
		reqCancel.setAirport(request.getAirport());
		reqCancel.setInvoiceNoReqcash(request.getInvoiceNoReqcash());
		reqCancel.setReceiptNoReqcash(request.getReceiptNoReqcash());
		reqCancel.setInvoiceNoReqlg(request.getInvoiceNoReqlg());
		reqCancel.setReceiptNoReqlg(request.getReceiptNoReqlg());
		// save data
		ricWaterReqCancelRepository.save(reqCancel);
		
		/* update req */
		RicWaterReq req = ricWaterReqRepository.findById(reqCancel.getReqId()).get();
		req.setRequestEndDate(reqCancel.getDateCancel());
		req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		req.setUpdatedDate(new Date());
		ricWaterReqRepository.save(req);
	}
	
	public ByteArrayOutputStream downloadTemplate(String customerName,String contractNo,String SerialNo,String dateCancel) throws Exception {
		Water007Req form = new Water007Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setSerialNo(SerialNo);
		form.setDateCancel(dateCancel);;
		List<Water007Res> dataExport = new ArrayList<Water007Res>();
		dataExport = getListWaterReqCancel(form);

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
			    "ประเภทที่ขอใช้", "ประเภทมิเตอร์", "Serial No. มิเตอร์", "ชื่อมิเตอร์",
			    "เงินประกัน", "สถานที่ตั้งมิเตอร์น้ำ", "วันที่สิ้นสุดการใช้งาน", "ใบแจ้งหนี้อัตราค่าภาระ",
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
		for (Water007Res data : dataExport) {
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
			cell.setCellValue(data.getRequestType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSerialNo());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterName());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTotalchargeRates().toString());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRentalAreaCode());
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
		for (int i = 1; i < 17; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
