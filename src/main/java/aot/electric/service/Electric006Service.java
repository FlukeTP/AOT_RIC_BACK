package aot.electric.service;

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

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.electric.model.RicElectricMeter;
import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricReq;
import aot.electric.model.RicElectricReqChange;
import aot.electric.repository.Electric006Dao;
import aot.electric.repository.jpa.RicElectricMeterRepository;
import aot.electric.repository.jpa.RicElectricRateChargeRepository;
import aot.electric.repository.jpa.RicElectricReqChangeRepository;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.electric.vo.request.Elec003FindMeterReq;
import aot.electric.vo.request.Electric006Req;
import aot.electric.vo.response.Electric006Res;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.PAYMENT_TYPE;
import aot.util.sap.constant.SAPConstants.SPECIAL_GL;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import aot.util.sapreqhelper.SapArRequest_7_1;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric006Service {

	private static final Logger logger = LoggerFactory.getLogger(Electric006Service.class);

	@Autowired
	private RicElectricReqChangeRepository ricElectricReqChangeRepository;

	@Autowired
	private RicElectricMeterRepository ricElectricMeterRepository;

	@Autowired
	private Electric006Dao electric006Dao;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private RicElectricRateChargeRepository ricElectricRateChargeRepository;

	@Autowired
	private SapArRequest_7_1 sapArRequest_7_1;

	@Autowired
	private SapRicControlDao sapRicControlDao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private CommonARRequest commonARRequest;

	public SapResponse sendSap(Electric006Req request) {
		SapResponse sapResponse = new SapResponse();

		try {
			RicElectricReqChange eleReqChange = ricElectricReqChangeRepository
					.findById(Long.valueOf(request.getReqChangeId())).get();
			RicElectricReq eleReq = ricElectricReqRepository.findByReqId(eleReqChange.getReqId());
			if (StringUtils.isNotBlank(request.getShowButtonCash())) {
				/* sapArRequest_7_1 */
				// set request
				ArRequest dataSend = sapArRequest_7_1.getARRequest(
						UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE, request.getReqChangeId(),
						DoctypeConstants.IR);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(eleReqChange.getReqChangeId());
				reqConnection.setTableName("ric_electric_req_change");
				reqConnection.setColumnId("req_change_id");
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
				    eleReq.setRequestStatus(FLAG.N_FLAG);
//				    eleReq.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateChange(),
//				    ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				    eleReq.setFlagInfo(RICConstants.FLAG_INFO_END);
//				    eleReq.setIsDelete(RICConstants.STATUS.YES);
				    ricElectricReqRepository.save(eleReq);
				}
			}
			if (StringUtils.isNotBlank(request.getShowButtonLg())) {
				/* sapArRequest_6_1/2 */
				// set request
				ArRequest dataSend = new ArRequest();
				List<Item> itemList = new ArrayList<Item>();
				
				// get branch
				String[] arrBranch = new String[] {};
				if (StringUtils.isNotBlank(eleReqChange.getCustomerBranch())) {
					arrBranch = eleReqChange.getCustomerBranch().split(":");
				}

				BigDecimal chageRate = new BigDecimal(0);
				BigDecimal chargeVat = new BigDecimal(0);
				BigDecimal totalChargeRate = new BigDecimal(0);
				List<RicElectricRateCharge> rateChargeList = ricElectricRateChargeRepository
						.findByReqId(eleReq.getReqId());
				for (RicElectricRateCharge rate : rateChargeList) {
					if (SAPConstants.CHARGE_TYPE_LG.equals(rate.getChargeType())) {
						chageRate = new BigDecimal(rate.getChargeRate());
						chargeVat = new BigDecimal(rate.getChargeVat());
						totalChargeRate = new BigDecimal(rate.getTotalChargeRate());
						break;
					}
				}

				dataSend = commonARRequest.getOneTemplate(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, DoctypeConstants.IA, eleReqChange.getTransactionNoLg());
				String payment = "";
				if (PAYMENT_TYPE.CASH_TH.equals(eleReq.getPaymentType())) {
					payment = PAYMENT_TYPE.CASH_EN;
				} else {
					payment = PAYMENT_TYPE.LG;
				}
				dataSend.getHeader().get(0).setRefKeyHeader2(payment);
				dataSend.getHeader().get(0).setDocHeaderText(eleReqChange.getContractNo());
				// Create Item1
				Item item = dataSend.getHeader().get(0).getItem().get(0);
				item.setPostingKey("09"); // require
				item.setAccount(eleReqChange.getCustomerCode()); // require
				item.setSpGL(SPECIAL_GL.SPGL_J);
				item.setAmount(NumberUtils.roundUpTwoDigit(totalChargeRate).toString()); // require
				item.setCustomerBranch(arrBranch[0].trim());
				if (PAYMENT_TYPE.CASH_TH.equals(eleReq.getPaymentType())) {
					item.setReferenceKey1(SAPConstants.DEPOSIT_TEXT.DEPOSIT_ELECTRICITY);
					item.setReferenceKey2("O7/" + NumberUtils.roundUpTwoDigit(chargeVat).toString());
					item.setReferenceKey3(NumberUtils.roundUpTwoDigit(chageRate).toString());
				} else {
					item.setReferenceKey1(ConvertDateUtils.formatDateToString(eleReq.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY_DASH, ConvertDateUtils.LOCAL_EN));
					item.setTextApplicationObject("DOC_ITEM");
					item.setTextID("0001");
					item.setLongText(eleReq.getBankName().concat("/").concat(eleReq.getBankBranch()).concat("/").concat(eleReq.getRemark()));
					item.setText(eleReq.getBankGuaranteeNo());
				}
				item.setContractNo(eleReqChange.getContractNo());

				itemList.add(item);
				dataSend.getHeader().get(0).setItem(itemList);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(eleReqChange.getReqChangeId());
				reqConnection.setTableName("ric_electric_req_change");
				reqConnection.setColumnId("req_change_id");
				reqConnection.setColumnInvoiceNo("invoice_no_lg");
				reqConnection.setColumnTransNo("transaction_no_lg");
				reqConnection.setColumnSapJsonReq("sap_json_req_lg");
				reqConnection.setColumnSapJsonRes("sap_json_res_lg");
				reqConnection.setColumnSapError("sap_error_desc_lg");
				reqConnection.setColumnSapStatus("sap_status_lg");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<Electric006Res> getListReqChange(Electric006Req request) throws Exception {

		logger.info("getAllReqChange");

		List<Electric006Res> list = new ArrayList<>();
		try {
			list = electric006Dao.findElec(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	public List<RicElectricMeter> getListElectricMeter(Elec003FindMeterReq req) throws Exception {

		logger.info("getListElectricMeter");

		List<RicElectricMeter> list = new ArrayList<>();
		try {
			list = electric006Dao.getElectricMeterByStatus(req);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return list;
	}

	public Electric006Res getElectricOleAndNewMeter(Electric006Req req) {

		logger.info("getElectricOleAndNewMeter");

		Electric006Res res = new Electric006Res();
		if (StringUtils.isNotBlank(req.getOldSerialNo())) {
			RicElectricMeter oldMeter = ricElectricMeterRepository.findBySerialNo(req.getOldSerialNo());
			if(oldMeter != null) {
				res.setOldSerialNo(oldMeter.getSerialNo());
				res.setOldMeterName(oldMeter.getMeterName());
				res.setOldMeterType(oldMeter.getMeterType());
				res.setOldMeterLocation(oldMeter.getMeterLocation());
				res.setOldFunctionalLocation(oldMeter.getFunctionalLocation());
			} else {
				RicElectricReq reqRes = ricElectricReqRepository.findById(Long.valueOf(req.getReqId())).get();
				res.setOldSerialNo(reqRes.getMeterSerialNo());
				res.setOldMeterName(reqRes.getMeterName());
				res.setOldMeterType(reqRes.getMeterType());
				res.setOldMeterLocation(reqRes.getInstallPosition());
				res.setOldFunctionalLocation(reqRes.getInstallPositionService());
			}
		}
		if (StringUtils.isNotBlank(req.getNewSerialNo())) {
			RicElectricMeter newMeter = ricElectricMeterRepository.findBySerialNo(req.getNewSerialNo());
			res.setNewSerialNo(newMeter.getSerialNo());
			res.setNewMeterName(newMeter.getMeterName());
			res.setNewMeterType(newMeter.getMeterType());
			res.setNewMeterLocation(newMeter.getMeterLocation());
			res.setNewFunctionalLocation(newMeter.getFunctionalLocation());
		}

		return res;
	}

	public Electric006Res findReqChangeById(Electric006Req request) throws Exception {

		logger.info("findReqChangeById");

		RicElectricReq req = new RicElectricReq();
		RicElectricMeter meter = new RicElectricMeter();
		Electric006Res res = null;
		RicElectricReqChange reqChange = null;
		try {
			// update
			if (StringUtils.isNotEmpty(request.getReqChangeId())) {
				reqChange = ricElectricReqChangeRepository.findById(Long.valueOf(request.getReqChangeId())).get();
				req = ricElectricReqRepository.findById(reqChange.getReqId()).get();
				
				// find data meter
//				if (StringUtils.isNotEmpty(reqChange.getOldSerialNo())) {
//					meter = ricElectricMeterRepository.findBySerialNo(reqChange.getOldSerialNo());
//					if (null == meter) {
//						meter = new RicElectricMeter();
//					}
//				}
				res = new Electric006Res();
				res.setCustomerCode(reqChange.getCustomerCode());
				res.setCustomerName(reqChange.getCustomerName());
				res.setCustomerBranch(reqChange.getCustomerBranch());
				res.setContractNo(reqChange.getContractNo());
				res.setVoltageType(reqChange.getVoltageType());
				res.setOldSerialNo(req.getMeterSerialNo());
				res.setOldMeterName(req.getMeterName());
				res.setOldMeterType(req.getMeterType());
				res.setOldMeterLocation(req.getInstallPosition());
				res.setOldChargeRates(reqChange.getOldChargeRates());
				res.setOldVat(reqChange.getOldVat());
				res.setOldTotalchargeRates(reqChange.getOldTotalchargeRates());
				res.setOldFunctionalLocation(req.getInstallPositionService());
				if (StringUtils.isNotEmpty(reqChange.getNewSerialNo())) {
					meter = ricElectricMeterRepository.findBySerialNo(reqChange.getNewSerialNo());
					if (null == meter) {
						meter = new RicElectricMeter();
					}
				}
				res.setNewSerialNo(meter.getSerialNo());
				res.setNewMeterName(meter.getMeterName());
				res.setNewMeterType(meter.getMeterType());
				res.setNewMeterLocation(meter.getMeterLocation());
				res.setNewFunctionalLocation(meter.getFunctionalLocation());
				res.setDateChange(ConvertDateUtils.formatDateToString(reqChange.getDateChange(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRemark(reqChange.getRemark());
				res.setAirport(reqChange.getAirport());
				res.setInvoiceNoReqcash(reqChange.getInvoiceNoReqcash());
				res.setReceiptNoReqcash(reqChange.getReceiptNoReqcash());
				res.setInvoiceNoReqlg(reqChange.getInvoiceNoReqlg());
				res.setReceiptNoReqlg(reqChange.getReceiptNoReqlg());
			} else {
				// find data req from TABLE ric_electric_req
				if (StringUtils.isNotEmpty(request.getReqId())) {
					req = ricElectricReqRepository.findById(Long.valueOf(request.getReqId())).get();
				}
				// find data meter
//				if (StringUtils.isNotEmpty(req.getMeterSerialNo())) {
//					meter = ricElectricMeterRepository.findBySerialNo(req.getMeterSerialNo());
//					if (null == meter) {
//						meter = new RicElectricMeter();
//					}
//				}
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
				res = new Electric006Res();
				res.setCustomerCode(req.getCustomerCode());
				res.setCustomerName(req.getCustomerName());
				res.setCustomerBranch(req.getCustomerBranch());
				res.setContractNo(req.getContractNo());
				res.setVoltageType(req.getVoltageType());
				res.setOldSerialNo(req.getMeterSerialNo());
				res.setOldMeterName(req.getMeterName());
				res.setOldMeterType(req.getMeterType());
				res.setOldMeterLocation(req.getInstallPosition());
				BigDecimal sumChageRate = new BigDecimal(0);
				BigDecimal serviceCharge = new BigDecimal(0);
				BigDecimal totalChargeRate = new BigDecimal(0);
				List<RicElectricRateCharge> ricElectricRateChargeList = ricElectricRateChargeRepository
						.findByReqId(req.getReqId());
				for (RicElectricRateCharge rate : ricElectricRateChargeList) {
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
				res.setOldFunctionalLocation(req.getInstallPositionService());
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

	public Electric006Res findReqChangeByMeter(Electric006Req request) throws Exception {

		logger.info("findReqChangeByMeter");

		RicElectricReq req = new RicElectricReq();
		RicElectricMeter meter = new RicElectricMeter();
		Electric006Res res = null;
		try {
			// find data meter
			if (StringUtils.isNotEmpty(request.getNewSerialNo())) {
				meter = ricElectricMeterRepository.findBySerialNo(request.getNewSerialNo());
			}
			// find data req from TABLE ric_electric_req
			if (StringUtils.isNotEmpty(meter.getSerialNo())) {
				req = ricElectricReqRepository.findBySerialNo(meter.getSerialNo());
				if (null == req) {
					req = new RicElectricReq();
				}
			}
			// set data for return
			res = new Electric006Res();
			res.setNewSerialNo(meter.getSerialNo());
			res.setNewMeterName(meter.getMeterName());
			res.setNewMeterType(meter.getMeterType());
			res.setNewMeterLocation(meter.getMeterLocation());
			res.setNewFunctionalLocation(meter.getFunctionalLocation());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void saveReqChange(Electric006Req request) throws Exception {
		logger.info("saveReqChange");

		RicElectricReqChange reqChange = null;
		if (StringUtils.isNotEmpty(request.getReqChangeId())) {
			reqChange = ricElectricReqChangeRepository.findById(Long.valueOf(request.getReqChangeId())).get();
			// set data
			reqChange.setNewChargeRates(
					null != request.getNewChargeRates() ? request.getNewChargeRates() : new BigDecimal(0));
			reqChange.setNewVat(null != request.getOldVat() ? request.getOldVat() : new BigDecimal(0));
			reqChange.setNewTotalchargeRates(
					null != request.getOldTotalchargeRates() ? request.getOldTotalchargeRates() : new BigDecimal(0));
			reqChange.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			reqChange.setUpdatedDate(new Date());
		} else {
			reqChange = new RicElectricReqChange();
			// set data
			reqChange.setNewChargeRates(new BigDecimal(0));
			reqChange.setNewVat(new BigDecimal(0));
			reqChange.setNewTotalchargeRates(new BigDecimal(0));
			reqChange.setSapStatusCash(RICConstants.STATUS.PENDING);
			reqChange.setSapStatusLg(RICConstants.STATUS.PENDING);
			reqChange.setReqId(Long.valueOf(request.getReqId()));
			reqChange.setCreatedBy(UserLoginUtils.getCurrentUsername());
			reqChange.setCreateDate(new Date());
			reqChange.setIsDelete(FLAG.N_FLAG);
		}
		reqChange.setCustomerCode(request.getCustomerCode());
		reqChange.setCustomerName(request.getCustomerName());
		reqChange.setCustomerBranch(request.getCustomerBranch());
		reqChange.setContractNo(request.getContractNo());
		reqChange.setVoltageType(request.getVoltageType());
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
		ricElectricReqChangeRepository.save(reqChange);
		
		/* update request */
		RicElectricReq req = ricElectricReqRepository.findById(reqChange.getReqId()).get();
		req.setRequestEndDate(reqChange.getDateChange());
		req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		req.setUpdatedDate(new Date());
		ricElectricReqRepository.save(req);
	}
	
	public ByteArrayOutputStream downloadTemplate(String customerName,String contractNo,String newSerialNo,String dateChange) throws Exception {
		Electric006Req form = new Electric006Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setNewSerialNo(newSerialNo);
		form.setDateChange(dateChange);
		List<Electric006Res> dataExport = new ArrayList<Electric006Res>();
		dataExport = getListReqChange(form);

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
		for (Electric006Res data : dataExport) {
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
			cell.setCellValue(data.getVoltageType());
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
