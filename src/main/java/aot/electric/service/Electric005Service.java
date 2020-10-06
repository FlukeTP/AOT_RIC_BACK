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
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.electric.model.RicElectricMeter;
import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricReq;
import aot.electric.model.RicElectricReqCancel;
import aot.electric.repository.Electric005Dao;
import aot.electric.repository.jpa.RicElectricMeterRepository;
import aot.electric.repository.jpa.RicElectricRateChargeRepository;
import aot.electric.repository.jpa.RicElectricReqCancelRepository;
import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.electric.vo.request.Electric005Req;
import aot.electric.vo.response.Electric005Res;
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
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric005Service {

	private static final Logger logger = LoggerFactory.getLogger(Electric005Service.class);

	@Autowired
	private RicElectricReqCancelRepository ricElectricReqCancelRepository;

	@Autowired
	private RicElectricMeterRepository ricElectricMeterRepository;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private RicElectricRateChargeRepository ricElectricRateChargeRepository;

	@Autowired
	private Electric005Dao electric005Dao;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapRicControlDao sapRicControlDao;
	
	@Autowired
	private SysConstantService sysConstantService;

	public SapResponse sendSap(Electric005Req request) throws Exception {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		RicElectricReqCancel eleReqCancel = ricElectricReqCancelRepository
				.findById(Long.valueOf(request.getReqCancelId())).get();
		RicElectricReq eleReq = ricElectricReqRepository.findByReqId(eleReqCancel.getReqId());

		// BigDecimal chageRate = new BigDecimal(0);
		// BigDecimal chargeVat = new BigDecimal(0);
		// BigDecimal totalChargeRate = new BigDecimal(0);
		// List<RicElectricRateCharge> ricElectricRateChargeList =
		// ricElectricRateChargeRepository
		// .findByReqId(eleReq.getReqId());
		// for (RicElectricRateCharge rate : ricElectricRateChargeList) {
		// if (SAPConstants.CHARGE_TYPE_LG.equals(rate.getChargeType())) {
		// chageRate = new BigDecimal(rate.getChargeRate());
		// chargeVat = new BigDecimal(rate.getChargeVat());
		// totalChargeRate = new BigDecimal(rate.getTotalChargeRate()).negate();
		// break;
		// }
		// }
		// set request 7_1
		dataSend = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
				DoctypeConstants.IR, eleReqCancel.getTransactionNoLg());
		// Create Item1
		Item item1 = dataSend.getHeader().get(0).getItem().get(0);
		item1.setPostingKey("11"); // require
		item1.setAccount(request.getCustomerCode()); // require
		item1.setAmount(NumberUtils
				.roundUpTwoDigit(sysConstantService.getTotalVat(eleReq.getTotalChargeRate()).negate()).toString()); // require
		item1.setTaxCode("O7");
		item1.setPmtMethod("5");
		item1.setText(SAPConstants.ELECTRIC.TEXT);
		item1.setContractNo(request.getContractNo());
		// String assignment =
		// eleReqCancel.getReceiptNoReqlg().concat(StringUtils.isNotBlank(sapReqLg.getDzyear())
		// ? sapReqLg.getDzyear() : "");
		// item1.setAssignment(assignment);
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
		item2.setSpGL(SPECIAL_GL.SPGL_4);
		item2.setAmount(NumberUtils.roundUpTwoDigit(eleReq.getTotalChargeRate()).toString()); // require
		item2.setTaxCode("O7");
		item2.setAssignment(request.getSerialNo());
		item2.setText(SAPConstants.ELECTRIC.TEXT);
		item2.setContractNo(request.getContractNo());
		item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
		itemList.add(item2);

		// Create Item3
		Item item3 = dataSend.getHeader().get(0).getItem().get(2);
		item3.setPostingKey("40"); // require
		item3.setAccount("2450101001"); // require
		item3.setAmount(
				NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(eleReq.getTotalChargeRate())).toString()); // require
		item3.setTaxCode("O7");
		item2.setAssignment(request.getSerialNo());
		item3.setText(SAPConstants.ELECTRIC.TEXT);
		item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(eleReq.getTotalChargeRate()).toString());
		itemList.add(item3);

		dataSend.getHeader().get(0).setItem(itemList);

		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);

		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(eleReqCancel.getReqCancelId());
		reqConnection.setTableName("ric_electric_req_cancel");
		reqConnection.setColumnId("req_cancel_id");
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
			eleReq.setRequestStatus(FLAG.N_FLAG);
			// eleReq.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getDateCancel(),
			// ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			eleReq.setFlagInfo(RICConstants.FLAG_INFO_END);
			eleReq.setIsDelete(FLAG.Y_FLAG);
			ricElectricReqRepository.save(eleReq);
		}
		return sapResponse;
	}

	public List<Electric005Res> getListReqCancel(Electric005Req request) throws Exception {

		logger.info("getAllRateChargeConfig");

		List<Electric005Res> list = new ArrayList<>();
		try {
			list = electric005Dao.findElec(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public Electric005Res findReqCancelById(Electric005Req request) throws Exception {

		logger.info("findReqCancelById");

		RicElectricReq req = null;
		RicElectricMeter meter = null;
		Electric005Res res = null;
		RicElectricReqCancel reqCancel = null;
		// update
		if (StringUtils.isNotEmpty(request.getReqCancelId())) {
			reqCancel = ricElectricReqCancelRepository.findById(Long.valueOf(request.getReqCancelId())).get();
			// find data meter
			meter = ricElectricMeterRepository.findBySerialNo(reqCancel.getSerialNo());
			if (null != meter) {
				res.setSerialNo(meter.getSerialNo());
				res.setMeterName(meter.getMeterName());
				res.setMeterType(meter.getMeterType());
				res.setMeterLocation(meter.getMeterLocation());
				res.setFunctionalLocation(meter.getFunctionalLocation());
			} else {
				res = setDetailOwnMeter(Long.valueOf(request.getReqId()));
			}
			
			// set data for return
			res = new Electric005Res();
			res.setCustomerCode(reqCancel.getCustomerCode());
			res.setCustomerName(reqCancel.getCustomerName());
			res.setCustomerBranch(reqCancel.getCustomerBranch());
			res.setContractNo(reqCancel.getContractNo());
			res.setVoltageType(reqCancel.getVoltageType());
			
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
			// find data req from TABLE ric_electric_req
			if (StringUtils.isNotEmpty(request.getReqId())) {
				req = ricElectricReqRepository.findById(Long.valueOf(request.getReqId())).get();
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
			res = new Electric005Res();
			// find data meter
			meter = ricElectricMeterRepository.findBySerialNo(req.getMeterSerialNo());
			if (null != meter) {
				meter = new RicElectricMeter();
				res.setSerialNo(meter.getSerialNo());
				res.setMeterName(meter.getMeterName());
				res.setMeterType(meter.getMeterType());
				res.setMeterLocation(meter.getMeterLocation());
				res.setFunctionalLocation(meter.getFunctionalLocation());
			} else {
				res = setDetailOwnMeter(Long.valueOf(request.getReqId()));
			}
			res.setCustomerCode(req.getCustomerCode());
			res.setCustomerName(req.getCustomerName());
			res.setCustomerBranch(req.getCustomerBranch());
			res.setContractNo(req.getContractNo());
			res.setVoltageType(req.getVoltageType());
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
			res.setChargeRates(NumberUtils.roundUpTwoDigit(sumChageRate));
			res.setVat(NumberUtils.roundUpTwoDigit(serviceCharge));
			res.setTotalchargeRates(NumberUtils.roundUpTwoDigit(totalChargeRate));
			res.setAirport(req.getAirport());
			res.setInvoiceNoReqcash(req.getInvoiceNoCash());
			res.setReceiptNoReqcash(sapReqCash.getDzdocNo());
			res.setInvoiceNoReqlg(req.getInvoiceNoLg());
			res.setReceiptNoReqlg(sapReqLg.getDzdocNo());
		}
		return res;
	}
	
	private Electric005Res setDetailOwnMeter(Long ReqId) {
		Electric005Res res = new Electric005Res();
		RicElectricReq reqRes = ricElectricReqRepository.findById(Long.valueOf(ReqId)).get();
		res.setSerialNo(reqRes.getMeterSerialNo());
		res.setMeterName(reqRes.getMeterName());
		res.setMeterType(reqRes.getMeterType());
		res.setMeterLocation(reqRes.getInstallPosition());
		res.setFunctionalLocation(reqRes.getInstallPositionService());
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void saveReqCancel(Electric005Req request) throws Exception {
		logger.info("saveReqCancel");

		RicElectricReqCancel reqCancel = null;
		if (StringUtils.isNotEmpty(request.getReqCancelId())) {
			reqCancel = ricElectricReqCancelRepository.findById(Long.valueOf(request.getReqCancelId())).get();
			// set data
			reqCancel.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			reqCancel.setUpdatedDate(new Date());
		} else {
			reqCancel = new RicElectricReqCancel();
			// set data
			reqCancel.setReqId(Long.valueOf(request.getReqId()));
			reqCancel.setSapStatusLg(RICConstants.STATUS.PENDING);
			reqCancel.setCreatedBy(UserLoginUtils.getCurrentUsername());
			reqCancel.setCreateDate(new Date());
			reqCancel.setIsDelete(FLAG.N_FLAG);
		}
		reqCancel.setCustomerCode(request.getCustomerCode());
		reqCancel.setCustomerName(request.getCustomerName());
		reqCancel.setCustomerBranch(request.getCustomerBranch());
		reqCancel.setContractNo(request.getContractNo());
		reqCancel.setVoltageType(request.getVoltageType());
		reqCancel.setSerialNo(request.getSerialNo());
		reqCancel.setChargeRates(request.getChargeRates());
		reqCancel.setVat(request.getVat());
		reqCancel.setTotalchargeRates(request.getTotalchargeRates());
		reqCancel.setDateCancel(ConvertDateUtils.parseStringToDate(request.getDateCancel(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		reqCancel.setRemark(request.getRemark());
		reqCancel.setAirport(request.getAirport());
		reqCancel.setInvoiceNoReqcash(request.getInvoiceNoReqcash());
		reqCancel.setReceiptNoReqcash(request.getReceiptNoReqcash());
		reqCancel.setInvoiceNoReqlg(request.getInvoiceNoReqlg());
		reqCancel.setReceiptNoReqlg(request.getReceiptNoReqlg());
		// save data
		ricElectricReqCancelRepository.save(reqCancel);
		
		/* update request */
		RicElectricReq req = ricElectricReqRepository.findById(reqCancel.getReqId()).get();
		req.setRequestEndDate(reqCancel.getDateCancel());
		req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		req.setUpdatedDate(new Date());
		ricElectricReqRepository.save(req);
	}

	public ByteArrayOutputStream downloadTemplate(String customerName, String contractNo, String serial,
			String dateCancel) throws Exception {
		Electric005Req form = new Electric005Req();
		form.setCustomerName(customerName);
		form.setContractNo(contractNo);
		form.setSerialNo(serial);
		form.setDateCancel(dateCancel);
		List<Electric005Res> dataExport = new ArrayList<Electric005Res>();
		dataExport = getListReqCancel(form);

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

		String[] header = { "ลำดับที่", "รหัสผู้ประกอบการ", "ผู้ประกอบการ", "เลขที่สัญญา", "ประเภทที่ขอใช้",
				"Serial No. มิเตอร์", "ชื่อมิเตอร์", "ประเภทมิเตอร์", "เงินประกัน", "Functional Location",
				"วันที่สิ้นสุดการใช้งาน", "ใบแจ้งหนี้อัตราค่าภาระ", "ใบเสร็จอัตราค่าภาระ", "เลขใบแจ้งหนี้เงินประกัน",
				"ใบเสร็จเงินประกัน", "หมายเลขยืนยันการยกเลิก จาก SAP", "สถานะการส่งข้อมูลเข้าสู่ระบบ SAP" };
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
		for (Electric005Res data : dataExport) {
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
			cell.setCellValue(data.getSerialNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterName());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getMeterType());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getTotalchargeRates().toString());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getFunctionalLocation());
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
