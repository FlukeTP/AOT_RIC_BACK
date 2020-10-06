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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import aot.util.sapreqhelper.SapArRequest_6_3;
import aot.util.sapreqhelper.SapArRequest_6_4;
import aot.util.sapreqhelper.SapArRequest_7_2;
import aot.water.model.RicWaterMeter;
import aot.water.model.RicWaterRateCharge;
import aot.water.model.RicWaterReq;
import aot.water.model.RicWaterReqChange;
import aot.water.repository.Water002Dao;
import aot.water.repository.Water008Dao;
import aot.water.repository.jpa.RicWaterMeterRepository;
import aot.water.repository.jpa.RicWaterRateChargeRepository;
import aot.water.repository.jpa.RicWaterReqChangeRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water008Req;
import aot.water.vo.response.Water008Res;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water008Service {

	private static final Logger logger = LoggerFactory.getLogger(Water008Service.class);

	@Autowired
	private RicWaterReqChangeRepository ricWaterReqChangeRepository;

	@Autowired
	private RicWaterMeterRepository ricWaterMeterRepository;

	@Autowired
	private RicWaterRateChargeRepository ricWaterRateChargeRepository;

	@Autowired
	Water002Dao water002Dao;

	@Autowired
	Water008Dao water008Dao;

	@Autowired
	private SapArRequest_6_3 sapArRequest_6_3;
	
	@Autowired
	private SapArRequest_6_4 sapArRequest_6_4;
	
	@Autowired
	private SapArRequest_7_2 sapArRequest_7_2;

	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	@Autowired
	private SysConstantService sysConstantService;

	public SapResponse sendSap(Water008Req request) throws Exception {
		SapResponse sapResponse = new SapResponse();

			RicWaterReqChange waterReqChange = ricWaterReqChangeRepository
					.findById(Long.valueOf(request.getWaterChangeId())).get();
			RicWaterReq waterReq = ricWaterReqRepository.findById(Long.valueOf(waterReqChange.getReqId())).get();
			if (StringUtils.isNotBlank(request.getShowButtonCash())) {
				/* sapArRequest_7_2 */
				// set request
				ArRequest dataSend = sapArRequest_7_2.getARRequest(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, request.getWaterChangeId(), DoctypeConstants.IQ);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(waterReqChange.getWaterChangeId());
				reqConnection.setTableName("ric_water_req_change");
				reqConnection.setColumnId("water_change_id");
				reqConnection.setColumnInvoiceNo("invoice_no_cash");
				reqConnection.setColumnTransNo("transaction_no_cash");
				reqConnection.setColumnSapJsonReq("sap_json_req_cash");
				reqConnection.setColumnSapJsonRes("sap_json_res_cash");
				reqConnection.setColumnSapError("sap_error_desc_cash");
				reqConnection.setColumnSapStatus("sap_status_cash");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
				// update req table
				if (SAPConstants.SAP_SUCCESS.equals(sapResponse.getStatus())) {
					waterReq.setRequestStatus(FLAG.N_FLAG);
//					waterReq.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateChange(),
//				    ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
					waterReq.setFlagInfo(RICConstants.FLAG_INFO_END);
//					waterReq.setIsDelete(RICConstants.STATUS.YES);
					ricWaterReqRepository.save(waterReq);
				}
			}
			if (StringUtils.isNotBlank(request.getShowButtonLg())) {
				/* __________________ check line SAP __________________ */
				ArRequest dataSend = null;
				switch (waterReq.getPaymentType()) {
				case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_TH:
					dataSend = sapArRequest_6_4.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
							Long.valueOf(request.getWaterChangeId()), DoctypeConstants.IF, "WATER008");
					break;
//				case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_TH:
//					dataSend = sapArRequest_6_3.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
//							Long.valueOf(request.getWaterChangeId()), DoctypeConstants.IF, "WATER008");
//					break;
				default:
					throw new Exception("NOT FOUND 'PAYMENT TYPE'!!");
				}
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(waterReqChange.getWaterChangeId());
				reqConnection.setTableName("ric_water_req_change");
				reqConnection.setColumnId("water_change_id");
				reqConnection.setColumnInvoiceNo("invoice_no_lg");
				reqConnection.setColumnTransNo("transaction_no_lg");
				reqConnection.setColumnSapJsonReq("sap_json_req_lg");
				reqConnection.setColumnSapJsonRes("sap_json_res_lg");
				reqConnection.setColumnSapError("sap_error_desc_lg");
				reqConnection.setColumnSapStatus("sap_status_lg");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}

		return sapResponse;
	}

	public List<Water008Res> getListWaterReqChange(Water008Req request) throws Exception {

		logger.info("getAllReqChange");

		List<Water008Res> list = new ArrayList<>();
		try {
			list = water008Dao.findWaterChange(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	public List<RicWaterMeter> getListWaterMeter(@RequestBody Water003FindMeterReq request) throws Exception {

		logger.info("getListWaterMeter");

		List<RicWaterMeter> list = new ArrayList<>();
		try {
			list = water008Dao.getWaterMeterByStatus(request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	public Water008Res getWaterOleAndNewMeter(Water008Req req) {

		logger.info("getWaterOleAndNewMeter");

		Water008Res res = new Water008Res();
		if (StringUtils.isNotBlank(req.getOldSerialNo())) {
			RicWaterMeter oldMeter = ricWaterMeterRepository.findBySerialNo(req.getOldSerialNo());
			if (oldMeter != null) {
				res.setOldSerialNo(oldMeter.getSerialNo());
				res.setOldMeterName(oldMeter.getMeterName());
				res.setOldMeterType(oldMeter.getMeterType());
				res.setOldMeterLocation(oldMeter.getMeterLocation());
			} else {
				RicWaterReq reqRes = ricWaterReqRepository.findById(Long.valueOf(Long.valueOf(req.getReqId()))).get();
				res.setOldSerialNo(reqRes.getMeterSerialNo());
				res.setOldMeterName(reqRes.getMeterName());
				res.setOldMeterType(reqRes.getMeterType());
				res.setOldMeterLocation(reqRes.getInstallPosition());
			}
		}
		if (StringUtils.isNotBlank(req.getNewSerialNo())) {
			RicWaterMeter newMeter = ricWaterMeterRepository.findBySerialNo(req.getNewSerialNo());
			res.setNewSerialNo(newMeter.getSerialNo());
			res.setNewMeterName(newMeter.getMeterName());
			res.setNewMeterType(newMeter.getMeterType());
			res.setNewMeterLocation(newMeter.getMeterLocation());
		}

		return res;
	}

	public Water008Res findWaterReqChangeById(Water008Req request) throws Exception {

		logger.info("findReqChangeById");

		RicWaterReq req = null;
		RicWaterMeter meter = new RicWaterMeter();
		Water008Res res = null;
		RicWaterReqChange waterChange = null;
		// update
		if (StringUtils.isNotEmpty(request.getWaterChangeId())) {
			res = new Water008Res();
			waterChange = ricWaterReqChangeRepository.findById(Long.valueOf(request.getWaterChangeId())).get();
			req = ricWaterReqRepository.findById(Long.valueOf(waterChange.getReqId())).get();
			meter = ricWaterMeterRepository.findBySerialNo(waterChange.getOldSerialNo());
			if (meter != null) {
				res.setOldSerialNo(meter.getSerialNo());
				res.setOldMeterName(meter.getMeterName());
				res.setOldMeterType(meter.getMeterType());
				res.setOldMeterLocation(meter.getMeterLocation());
				BigDecimal sumChageRate = new BigDecimal(0);
				BigDecimal serviceCharge = new BigDecimal(0);
				BigDecimal totalChargeRate = new BigDecimal(0);
				List<RicWaterRateCharge> ricWaterRateChargeList = ricWaterRateChargeRepository
						.findByReqId(req.getReqId());
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
				res.setOldChargeRates(NumberUtils.roundUpTwoDigit(sumChageRate));
				res.setOldVat(NumberUtils.roundUpTwoDigit(serviceCharge));
				res.setOldTotalchargeRates(NumberUtils.roundUpTwoDigit(totalChargeRate));
			} else {
				res.setOldSerialNo(req.getMeterSerialNo());
				res.setOldMeterName(req.getMeterName());
				res.setOldMeterType(req.getMeterType());
				res.setOldMeterLocation(req.getInstallPosition());
				res.setOldChargeRates(NumberUtils.roundUpTwoDigit(req.getTotalInsuranceChargeRates()));
				res.setOldVat(NumberUtils.roundUpTwoDigit(
						sysConstantService.getSumVat(new BigDecimal(req.getTotalInsuranceChargeRates()))));
				res.setOldTotalchargeRates(NumberUtils.roundUpTwoDigit(
						sysConstantService.getTotalVat(new BigDecimal(req.getTotalInsuranceChargeRates()))));

			}
			res.setCustomerCode(waterChange.getCustomerCode());
			res.setCustomerName(waterChange.getCustomerName());
			res.setCustomerBranch(waterChange.getCustomerBranch());
			res.setContractNo(waterChange.getContractNo());
			
			if (StringUtils.isNotEmpty(waterChange.getNewSerialNo())) {
				meter = ricWaterMeterRepository.findBySerialNo(waterChange.getNewSerialNo());
				if (null == meter) {
					meter = new RicWaterMeter();
				}
			}
			res.setNewSerialNo(meter.getSerialNo());
			res.setNewMeterName(meter.getMeterName());
			res.setNewMeterType(meter.getMeterType());
			res.setNewMeterLocation(meter.getMeterLocation());
			res.setDateChange(ConvertDateUtils.formatDateToString(waterChange.getDateChange(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setRemark(waterChange.getRemark());
			res.setAirport(waterChange.getAirport());
			res.setInvoiceNoReqcash(waterChange.getInvoiceNoReqcash());
			res.setReceiptNoReqcash(waterChange.getReceiptNoReqcash());
			res.setInvoiceNoReqlg(waterChange.getInvoiceNoReqlg());
			res.setReceiptNoReqlg(waterChange.getReceiptNoReqlg());
		} else {
			// find data req from TABLE ric_electric_req
			if (StringUtils.isNotEmpty(request.getReqId())) {
				req = ricWaterReqRepository.findById(Long.valueOf(request.getReqId())).get();
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

			// find data meter
			if (StringUtils.isNotEmpty(req.getMeterSerialNo())) {
				meter = ricWaterMeterRepository.findBySerialNo(req.getMeterSerialNo());
			}

			// set data for return
			res = new Water008Res();
			if (meter != null) {
				res.setOldSerialNo(meter.getSerialNo());
				res.setOldMeterName(meter.getMeterName());
				res.setOldMeterType(meter.getMeterType());
				res.setOldMeterLocation(meter.getMeterLocation());
				BigDecimal sumChageRate = new BigDecimal(0);
				BigDecimal serviceCharge = new BigDecimal(0);
				BigDecimal totalChargeRate = new BigDecimal(0);
				List<RicWaterRateCharge> ricWaterRateChargeList = ricWaterRateChargeRepository
						.findByReqId(req.getReqId());
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
				res.setOldChargeRates(NumberUtils.roundUpTwoDigit(sumChageRate));
				res.setOldVat(NumberUtils.roundUpTwoDigit(serviceCharge));
				res.setOldTotalchargeRates(NumberUtils.roundUpTwoDigit(totalChargeRate));
			} else {
				res.setOldSerialNo(req.getMeterSerialNo());
				res.setOldMeterName(req.getMeterName());
				res.setOldMeterType(req.getMeterType());
				res.setOldMeterLocation(req.getInstallPosition());
				res.setOldChargeRates(NumberUtils.roundUpTwoDigit(req.getTotalInsuranceChargeRates()));
				res.setOldVat(NumberUtils.roundUpTwoDigit(
						sysConstantService.getSumVat(new BigDecimal(req.getTotalInsuranceChargeRates()))));
				res.setOldTotalchargeRates(NumberUtils.roundUpTwoDigit(
						sysConstantService.getTotalVat(new BigDecimal(req.getTotalInsuranceChargeRates()))));

			}
			res.setOldRentalAreaCode(req.getRentalAreaCode());
			res.setCustomerCode(req.getCustomerCode());
			res.setCustomerName(req.getCustomerName());
			res.setCustomerBranch(req.getCustomerBranch());
			res.setContractNo(req.getContractNo());
			res.setAirport(req.getAirport());
			res.setInvoiceNoReqcash(req.getInvoiceNoCash());
			res.setReceiptNoReqcash(sapReqCash.getDzdocNo());
			res.setInvoiceNoReqlg(req.getInvoiceNoLg());
			res.setReceiptNoReqlg(sapReqLg.getDzdocNo());
		}

		return res;
	}

	public Water008Res findWaterReqChangeByMeter(Water008Req request) throws Exception {

		logger.info("findReqWaterChangeByMeter");

		RicWaterReq req = null;
		RicWaterMeter meter = null;
		Water008Res res = null;
		try {
			// find data meter
			if (StringUtils.isNotEmpty(request.getNewSerialNo())) {
				meter = ricWaterMeterRepository.findBySerialNo(request.getNewSerialNo());
			}
			// find data req from TABLE ric_electric_req
			if (StringUtils.isNotEmpty(meter.getSerialNo())) {
				req = ricWaterReqRepository.findBySerialNo(meter.getSerialNo());
				if (null == req) {
					req = new RicWaterReq();
				}
			}
			// set data for return
			res = new Water008Res();
			res.setNewSerialNo(meter.getSerialNo());
			res.setNewMeterName(meter.getMeterName());
			res.setNewMeterType(meter.getMeterType());
			res.setNewMeterLocation(meter.getMeterLocation());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void saveWaterReqChange(Water008Req request) throws Exception {
		logger.info("saveWaterReqChange");

		RicWaterReqChange reqChange = null;
		if (StringUtils.isNotEmpty(request.getWaterChangeId())) {
			reqChange = ricWaterReqChangeRepository.findById(Long.valueOf(request.getWaterChangeId())).get();
			// set data
			reqChange.setNewChargeRates(
					null != request.getNewChargeRates() ? request.getNewChargeRates() : new BigDecimal(0));
			reqChange.setNewVat(null != request.getNewVat() ? request.getNewVat() : new BigDecimal(0));
			reqChange.setNewTotalchargeRates(
					null != request.getNewTotalchargeRates() ? request.getNewTotalchargeRates() : new BigDecimal(0));
			reqChange.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			reqChange.setUpdatedDate(new Date());
		} else {
			reqChange = new RicWaterReqChange();
			// set data
			reqChange.setNewChargeRates(new BigDecimal(0));
			reqChange.setNewVat(new BigDecimal(0));
			reqChange.setNewTotalchargeRates(new BigDecimal(0));
			reqChange.setSapStatusCash(RICConstants.STATUS.PENDING);
			reqChange.setSapStatusLg(RICConstants.STATUS.PENDING);
			reqChange.setReqId(Long.valueOf(request.getReqId()));
			reqChange.setCreatedBy(UserLoginUtils.getCurrentUsername());
			reqChange.setCreateDate(new Date());
			reqChange.setIsDelete(RICConstants.STATUS.NO);
		}
		reqChange.setCustomerCode(request.getCustomerCode());
		reqChange.setCustomerName(request.getCustomerName());
		reqChange.setCustomerBranch(request.getCustomerBranch());
		reqChange.setContractNo(request.getContractNo());
		reqChange.setNewSerialNo(request.getNewSerialNo());
		reqChange.setOldSerialNo(request.getOldSerialNo());
		reqChange.setOldChargeRates(request.getOldChargeRates());
		reqChange.setOldVat(request.getOldVat());
		reqChange.setOldTotalchargeRates(request.getOldTotalchargeRates());
		reqChange.setDateChange(ConvertDateUtils.parseStringToDate(request.getDateChange(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		reqChange.setRemark(request.getRemark());
		reqChange.setAirport(request.getAirport());
		reqChange.setInvoiceNoReqcash(request.getInvoiceNoReqcash());
		reqChange.setReceiptNoReqcash(request.getReceiptNoReqcash());
		reqChange.setInvoiceNoReqlg(request.getInvoiceNoReqlg());
		reqChange.setReceiptNoReqlg(request.getReceiptNoReqlg());
		// save data
		ricWaterReqChangeRepository.save(reqChange);
		
		/* update req */
		RicWaterReq req = ricWaterReqRepository.findById(reqChange.getReqId()).get();
		req.setRequestEndDate(reqChange.getDateChange());
		req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		req.setUpdatedDate(new Date());
		ricWaterReqRepository.save(req);

	}
	
	public ByteArrayOutputStream downloadTemplate(String customerName,String contractNo,String newSerialNo,String dateChange) throws Exception {
		Water008Req form = new Water008Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setNewSerialNo(newSerialNo);
		form.setDateChange(dateChange);
		List<Water008Res> dataExport = new ArrayList<Water008Res>();
		dataExport = getListWaterReqChange(form);

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
			    "ประเภทที่ขอใช้", "Serial No. มิเตอร์(เดิม)", "Serial No. มิเตอร์(ใหม่)",
			    "เงินประกัน", "วันที่สิ้นสุดมิเตอร์เดิม", "ใบแจ้งหนี้อัตราค่าภาระ", "ใบเสร็จอัตราค่าภาระ",
			    "เลขใบแจ้งหนี้เงินประกัน", "ใบเสร็จเงินประกัน", "คืนเงินประกัน", "", "เงินประกัน", "", ""  };
		String[] header2 = { "", "", "", "", "", "", "", "", "","", "", "", "",
				"หมายเลขยืนยันการยกเลิก จาก SAP", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP",
			    "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP" };
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 17));


		rowNum++;
		int index = 1;
		for (Water008Res data : dataExport) {
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
			cell.setCellValue(data.getNewMeterType());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getOldSerialNo());
			cell.setCellStyle(tdLeft);
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getNewSerialNo());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getOldTotalchargeRates().toString());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getDateChange());
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
			cell.setCellValue(data.getInvoiceNoCash());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getSapStatusCash());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNoLg());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceiptNoLg());
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
		for (int i = 1; i < 18; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}
}
