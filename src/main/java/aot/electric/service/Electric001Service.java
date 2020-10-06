package aot.electric.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.ElectricConstants;
import aot.common.service.ElectricBillService;
import aot.common.vo.request.ElectricBillReq;
import aot.common.vo.response.ElectricBillRes;
import aot.electric.model.RicElectricInfo;
import aot.electric.model.RicElectricReq;
import aot.electric.repository.Electric001Dao;
import aot.electric.repository.jpa.RicElectricInfoRepository;
import aot.electric.vo.request.Electric001Req;
import aot.electric.vo.response.Electric001Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_1;
import aot.util.sapreqhelper.SapArRequest_1_1_1;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Electric001Service {
	private static final Logger logger = LoggerFactory.getLogger(Electric001Service.class);

	@Autowired
	private RicElectricInfoRepository ricElectricInfoRepository;

	@Autowired
	private Electric001Dao electric001Dao;

	@Autowired
	private ElectricBillService electricBillService;

	@Autowired
	private SapArRequest_1_1 sapArRequest_1_1;
	
	@Autowired
	private SapArRequest_1_1_1 sapArRequest_1_1_1;

	@Autowired
	private SAPARService sapARService;

	public List<Electric001Res> findListOfMonth(Electric001Req request) throws Exception {
		List<Electric001Res> response = electric001Dao.findByCondition(request);
		return response;
	}

	public List<Electric001Res> findDropdownSerialNo() {
		List<RicElectricInfo> resFilter = electric001Dao.findDropdownSerialNo();
		List<Electric001Res> response = new ArrayList<Electric001Res>();
		Electric001Res entity = null;
		for (RicElectricInfo ricElectricInfo : resFilter) {
			entity = new Electric001Res();
			entity.setSerialNoMeter(ricElectricInfo.getSerialNoMeter());
			response.add(entity);
		}
		return response;
	}

	public ByteArrayOutputStream downloadTemplate(String periodMonth, String idStr) throws Exception {
		/* split id and find data to write excel */
		List<Electric001Res> resSearch = new ArrayList<Electric001Res>();
		Electric001Req reqInfo = null;
		for (String id : idStr.split(",")) {
			reqInfo = new Electric001Req();
			reqInfo.setPeriodMonth(periodMonth);
			reqInfo.setId(Long.valueOf(id));
			resSearch.add(findListOfMonth(reqInfo).get(0));
		}

		/* style */
		XSSFWorkbook workbook = new XSSFWorkbook();
		// CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle tdStyle = ExcelUtils.createTdCellStyle(workbook);
//		CellStyle tdCenter = ExcelUtils.createCenterCellStyle(workbook);
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
		CellStyle TopicCenter = ExcelUtils.createTopicCenterStyle(workbook);
		CellStyle bgTd = ExcelUtils.createTdColorStyle(workbook, new XSSFColor(Color.LIGHT_GRAY));
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

		String[] header = { "เลขที่สัญญา", "ผู้ประกอบการ/พนักงาน", "ชื่อมิเตอร์", "Serial No. มิเตอร์", "หมายเลขหน้าปัดย้อนหลัง",
				"หน่วยย้อนหลัง", "วันที่จดเลข", "หมายเลขหน้าปัดปัจจุบัน", "หน่วยปัจจุบัน" };
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		rowNum = 0;
		row = sheet.createRow(rowNum);

		cell = row.createCell(cellNum);
		cell.setCellValue("ประจำเดือน: ");
		cellNum++;

		cell = row.createCell(cellNum);
		cell.setCellValue(ConvertDateUtils.formatDateToString(
				ConvertDateUtils.parseStringToDate(periodMonth, ConvertDateUtils.YYYYMM), ConvertDateUtils.MM_YYYY));
		rowNum++;
		rowNum++;
		row = sheet.createRow(rowNum);
		cellNum = 0;
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(cellNum);
			cell.setCellValue(header[i]);
			cell.setCellStyle(TopicCenter);
			cell.setCellStyle(cellHeaderStyle);
			cellNum++;
		}
		
		DecimalFormat df = new DecimalFormat("###,##0.##");
		for (Electric001Res r : resSearch) {
			if (!SAPConstants.SAP_SUCCESS.equals(r.getSapStatus())) {
				rowNum++;
				cellNum = 0;
				row = sheet.createRow(rowNum);

				// symbol check '✔'

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getContractNo());
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getEntreprenuerName());
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getMeterName());
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getSerialNoMeter());
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getBackwardMeterValue() != null ? df.format(r.getBackwardMeterValue()) : "");
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getBackwardAmount() != null ? df.format(r.getBackwardAmount()) : "");
				cell.setCellStyle(tdStyle);
				cell.setCellStyle(bgTd);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getMeterDateStr());
				cell.setCellStyle(tdStyle);
				cellNum++;

				cell = row.createCell(cellNum);
				cell.setCellValue(r.getCurrentMeterValue());
				cell.setCellStyle(tdStyle);
				cellNum++;

				cell = row.createCell(cellNum);
				// cell.setCellValue(r.getCurrentAmount()!=null?
				// df.format(r.getCurrentAmount()): "");
				cell.setCellStyle(tdStyle);
				cellNum++;
			}
		}

		// set width
		int width = 100;
		for (int i = 0; i < header.length; i++) {
			sheet.setColumnWidth(i, width * 50);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

	@Transactional
	public void save(List<Electric001Res> request) throws Exception {
		RicElectricInfo resFind = null;
		ElectricBillReq reqCal = null;
		for (Electric001Res req : request) {
			/* find data */
			resFind = new RicElectricInfo();
			resFind = ricElectricInfoRepository.findById(req.getElectricInfoId()).get();
			resFind.setCurrentMeterValue(req.getCurrentMeterValue());

			/* calculate */
			reqCal = new ElectricBillReq();
			reqCal.setSerialNoMeter(resFind.getSerialNoMeter());
			reqCal.setType(resFind.getVoltageType());
			reqCal.setCurrentMeterValue(StringUtils.isNotBlank(req.getCurrentMeterValue())? new BigDecimal(req.getCurrentMeterValue()): BigDecimal.ZERO);
			reqCal.setBackwardMeterValue(resFind.getBackwardMeterValue());
			reqCal.setBackwardAmount(resFind.getBackwardAmount());
			if (req.getCurrentAmount() != null) {
				if (resFind.getCurrentAmount().longValue() != req.getCurrentAmount().longValue()) {
					reqCal.setCurrentAmount(req.getCurrentAmount());
				}
			}
			ElectricBillRes resCalculate = electricBillService.calculate(reqCal);

			/* save */
			Date dateNow = new Date();
			resFind.setEnergyValue(resCalculate.getEnergyValue());
			resFind.setFtValue(resCalculate.getFtValue());
			resFind.setCurrentAmount(resCalculate.getAmountMeter());
			resFind.setCalPercent(resCalculate.getCalPercent());
			resFind.setVat(resCalculate.getVat());
			resFind.setBaseValue(resCalculate.getBaseValue());
			resFind.setServiceValue(resCalculate.getServiceValue());
			resFind.setTotalAmount(resCalculate.getTotalAmount());
			resFind.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			resFind.setUpdatedDate(dateNow);
			if(StringUtils.isNotBlank(req.getMeterDateStr())) {
				resFind.setMeterDate(ConvertDateUtils.parseStringToDate(req.getMeterDateStr(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			} else {
				resFind.setMeterDate(null);
			}
//			if ("0".equals(ricElectricInfo.getCurrentMeterValue()) || "".equals(ricElectricInfo.getCurrentMeterValue())) {
//				resFind.setMeterDate(null);
//			} else {
//				resFind.setMeterDate(dateNow);
//			}
			ricElectricInfoRepository.save(resFind);
		}
	}

	@Transactional
	public List<Electric001Res> uploadExcel(MultipartFile file) throws Exception {
		List<List<String>> allLine = ExcelUtils.readExcel(file);
		String[] periodMonthArr = {};
		String periodMonth = "";
		if (allLine.size() > 0) {
			periodMonthArr = allLine.get(0).get(1).split("/");
			periodMonth = periodMonthArr[1].concat(periodMonthArr[0]);
		}

		/* update field currentMeterValue */
		RicElectricInfo entity = null;
		ElectricBillReq reqCal = null;
		Date dateNow = new Date();
		for (int i = 0; i < allLine.size(); i++) {
			entity = new RicElectricInfo();
			reqCal = new ElectricBillReq();
			for (int j = 0; j < allLine.get(i).size(); j++) {
				/* SerialNoMeter */
				if (i > 1 && j == 3) {
					entity = ricElectricInfoRepository.findBySerialNoMeterAndPeriodMonth(allLine.get(i).get(j),
							periodMonth);
				}
				/* BackwardAmount */
				if (i > 1 && j == 5) {
					entity.setBackwardAmount(NumberUtils.roundUpTwoDigit(allLine.get(i).get(j)));
				}
				/* MeterDateStr */
				if (i > 1 && j == 6) {
					if (StringUtils.isNotBlank(allLine.get(i).get(j))) {
						String dateStr = allLine.get(i).get(j).trim().split(" ")[0];
						String yearExcel = dateStr.split("/")[2];
						String yearNow = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYY, ConvertDateUtils.LOCAL_EN);
						if (yearNow.equals(yearExcel)) {
							entity.setMeterDate(ConvertDateUtils.parseStringToDate(dateStr, ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
						} else {
							entity.setMeterDate(ConvertDateUtils.parseStringToDate(dateStr, ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_TH));
						}
					} else {
						entity.setMeterDate(null);
					}
				}
				/* CurrentMeterValue */
				if (i > 1 && j == 7) {
//					if ("0".equals(allLine.get(i).get(j)) || "".equals(allLine.get(i).get(j))) {
//						entity.setMeterDate(null);
//					} else {
//						entity.setMeterDate(dateNow);
//					}
					entity.setCurrentMeterValue(allLine.get(i).get(j));
				}
				/* CurrentAmount */
				if (i > 1 && j == 8) {
					if(StringUtils.isNotBlank(allLine.get(i).get(j))) {
						reqCal.setCurrentAmount(NumberUtils.roundUpTwoDigit(allLine.get(i).get(j)));
					}
				}
			}
			if (i > 1) {
				/* calculate */
				reqCal.setSerialNoMeter(entity.getSerialNoMeter());
				reqCal.setType(entity.getVoltageType());
				reqCal.setCurrentMeterValue(StringUtils.isNotBlank(entity.getCurrentMeterValue())
						? new BigDecimal(entity.getCurrentMeterValue())
						: BigDecimal.ZERO);
				reqCal.setBackwardMeterValue(entity.getBackwardMeterValue());
				reqCal.setBackwardAmount(entity.getBackwardAmount());
				ElectricBillRes resCalculate = electricBillService.calculate(reqCal);

				/* save */
				entity.setEnergyValue(resCalculate.getEnergyValue());
				entity.setFtValue(resCalculate.getFtValue());
				entity.setCurrentAmount(resCalculate.getAmountMeter());
				entity.setCalPercent(resCalculate.getCalPercent());
				entity.setVat(resCalculate.getVat());
				entity.setBaseValue(resCalculate.getBaseValue());
				entity.setServiceValue(resCalculate.getServiceValue());
				entity.setTotalAmount(resCalculate.getTotalAmount());
				entity.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				entity.setUpdatedDate(dateNow);
				ricElectricInfoRepository.save(entity);
			}
		}
		/* search current month */
		List<Electric001Res> response = new ArrayList<Electric001Res>();
		Electric001Req reqInfo = new Electric001Req();
		reqInfo.setPeriodMonth(
				ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
		response = findListOfMonth(reqInfo);

		return response;
	}
	
//	public Integer checkBeforeSynData() {
//		/* _______ check Data Empty _______ */
//		Electric001Req searchReq = new Electric001Req();
//		searchReq.setPeriodMonth(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
//		List<Electric001Res> resSearch = electric001Dao.findByCondition(searchReq);
//		if (resSearch.size() == 0) {
//			electric001Dao.updateFlagInfo(FLAG.Y_FLAG, FLAG.N_FLAG, null);
//		}
//		return electric001Dao.countByFlagInfoBeforeSyncData();
//	}

	public List<SapResponse> sendSap(List<Long> idx) throws Exception {
		List<SapResponse> responseSap = new ArrayList<SapResponse>();

		/* _____________ find all by id _____________ */
		Iterable<RicElectricInfo> checkboxList = ricElectricInfoRepository.findAllById(idx);

		/* _____________ find keyOfContractNo _____________ */
		HashSet<String> keyOfContractNo = new HashSet<String>();
		for (RicElectricInfo arr : checkboxList) {
			if (StringUtils.isBlank(arr.getContractNo()) && ElectricConstants.EMPLOYEE_TYPE.equals(arr.getCustomerType())) {
				keyOfContractNo.add("E-" + arr.getElectricInfoId());
			} else {
				keyOfContractNo.add(arr.getContractNo());
			}
		}

		/* _____________ filter group by key _____________ */
		for (String key : keyOfContractNo) {
			List<RicElectricInfo> groupByContractNo = new ArrayList<>();
			if (key.indexOf("E-") > -1) {
				groupByContractNo = ((Collection<RicElectricInfo>) checkboxList).stream()
						.filter(obj -> key.split("-")[1].equals(obj.getElectricInfoId().toString()))
						.collect(Collectors.toList());
			} else {
				groupByContractNo = ((Collection<RicElectricInfo>) checkboxList).stream()
						.filter(obj -> key.equals(obj.getContractNo())).collect(Collectors.toList());
			}
			
			ArRequest dataSend = null;
			/* _____________ set ARrequest sap _____________ */
			if (groupByContractNo.size() == 1) {
				RicElectricInfo resGroup = groupByContractNo.get(0);
				if(ElectricConstants.CUSTOMER_TYPE.equals(resGroup.getCustomerType())) {
					/* _________ customer _________ */
					dataSend = sapArRequest_1_1.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.IA, resGroup);
				} else {
					/* _________ employee _________ */
					dataSend = sapArRequest_1_1_1.getARRequest(UserLoginUtils.getUser().getAirportCode(),
							SAPConstants.COMCODE, DoctypeConstants.IA, resGroup);
				}
			} else {
				dataSend = sapArRequest_1_1.getARRequestDynamic(
						UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
						DoctypeConstants.IA, groupByContractNo);
			}

			/* _____________ loop set req, res, status sap _____________ */
			for (RicElectricInfo elec001Req : groupByContractNo) {
				responseSap.add(setResponseSap(dataSend, elec001Req));
			}
		}
		return responseSap;
	}
	
	private SapResponse setResponseSap(ArRequest dataSend, RicElectricInfo elec001Req) throws JsonProcessingException {
		SapResponse dataRes = sapARService.callSAPAR(dataSend);
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(elec001Req.getElectricInfoId());
		reqConnection.setTableName("ric_electric_info");
		reqConnection.setColumnId("electric_info_id");
//		reqConnection.setColumnInvoiceNo("invoice_no");
//		reqConnection.setColumnTransNo("transaction_no");
//		reqConnection.setColumnSapJsonReq("sap_json_req");
//		reqConnection.setColumnSapJsonRes("sap_json_res");
//		reqConnection.setColumnSapError("sap_error");
//		reqConnection.setColumnSapStatus("sap_status");
		SapResponse sapResponse = sapARService.setSapConnection(reqConnection);
		return sapResponse;
	}
	
	@Transactional
	public Integer syncData(String periodMonth) throws IOException {
		logger.info("syncData Electric001 Start");
		long start = System.currentTimeMillis();
		/* 
		 * check previous month 
		 * */
		Electric001Req previousMonthReq = new Electric001Req();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ConvertDateUtils.parseStringToDate(periodMonth, ConvertDateUtils.YYYYMM));
		cal.add(Calendar.MONTH, -1);
		previousMonthReq.setPeriodMonth(ConvertDateUtils.formatDateToString(cal.getTime(), ConvertDateUtils.YYYYMM));
		List<RicElectricInfo> previousMonthList = electric001Dao.findByPeriodMonth(previousMonthReq);
		/*
		 * data initial from register
		 * */
		List<RicElectricInfo> infoList = new ArrayList<RicElectricInfo>();
		RicElectricInfo info = null;
		List<RicElectricReq> ResReq = electric001Dao.checkCancelDateBeforeSyncData(periodMonth);
		for (RicElectricReq initData : ResReq) {
			/* _________ set data register _________ */
			info = setDataRegister(initData);
			info.setPeriodMonth(periodMonth);

			List<RicElectricInfo> dataFilter = previousMonthList.stream()
					.filter(obj -> initData.getMeterSerialNo().equals(obj.getSerialNoMeter()))
					.collect(Collectors.toList());
			if (dataFilter.size() > 0) {
				/* _________ old user _________ */
				RicElectricInfo d = dataFilter.get(0);
				info.setBackwardMeterValue(StringUtils.isBlank(d.getCurrentMeterValue()) ? BigDecimal.ZERO
						: NumberUtils.roundUpTwoDigit(d.getCurrentMeterValue()));
				info.setBackwardAmount(d.getCurrentAmount());
			} else {
				/* _________ new user _________ */
				info.setBackwardMeterValue(initData.getDefaultMeterNo());
				info.setBackwardAmount(BigDecimal.ZERO);
			}
			
			/* __________ add to list info __________ */
			infoList.add(info);
			
			/* __________ update flag insert __________ */
//			communicate007Dao.updateFlagInfo(FLAG.N_FLAG, FLAG.Y_FLAG, initData.getId());
		}
		ricElectricInfoRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData Electric001 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicElectricInfo setDataRegister(RicElectricReq initData) {
		RicElectricInfo info = new RicElectricInfo();
		info.setRoCode(initData.getRentalAreaCode());
		info.setRoName(initData.getRentalAreaName());
		info.setCustomerType(initData.getCustomerType());
		info.setContractNo(initData.getContractNo());
		info.setVoltageType(initData.getVoltageType());
		info.setEntreprenuerCode(initData.getCustomerCode());
		info.setEntreprenuerName(initData.getCustomerName());
		info.setCustomerBranch(initData.getCustomerBranch());
//		info.setFtValue(new BigDecimal(sysConstantService.getConstantByKey(RICConstants.FT).getConstantValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN)); // ft = -11.60 สตางค์ /หน่วย
		info.setSerialNoMeter(initData.getMeterSerialNo());
		info.setMeterName(initData.getMeterName());
		info.setCustomerBranch(initData.getCustomerBranch());
		info.setCreatedBy(UserLoginUtils.getCurrentUsername());
		info.setCreatedDate(new Date());
		info.setIsDeleted(FLAG.N_FLAG);
		info.setRequestType(initData.getRequestType());
		info.setVoltageType(initData.getVoltageType());
		info.setIdReq(initData.getReqId());
		return info;
	}
}
