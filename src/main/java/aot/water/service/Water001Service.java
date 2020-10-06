package aot.water.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

import aot.common.constant.DoctypeConstants;
import aot.common.constant.ElectricConstants;
import aot.common.service.WaterBillService;
import aot.common.vo.request.WaterBillReq;
import aot.common.vo.response.WaterBillRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_1_2;
import aot.util.sapreqhelper.SapArRequest_1_5;
import aot.water.model.RicWaterInfo;
import aot.water.model.RicWaterReq;
import aot.water.repository.Water001Dao;
import aot.water.repository.jpa.RicWaterInfoRepository;
import aot.water.repository.jpa.RicWaterMaintenanceConfigRepository;
import aot.water.vo.request.Water001Req;
import aot.water.vo.response.Water001Res;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Water001Service {
	private static final Logger logger = LoggerFactory.getLogger(Water001Service.class);

	@Autowired
	private RicWaterInfoRepository ricWaterInfoRepository;

	@Autowired
	private Water001Dao water001Dao;
	
	@Autowired
	private WaterBillService waterBillService;
	
	@Autowired
	private SapArRequest_1_5 sapArRequest_1_5;
	
	@Autowired
	private SapArRequest_1_1_2 sapArRequest_1_1_2;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private RicWaterMaintenanceConfigRepository ricWaterMaintenanceConfigRepository;

	public List<Water001Res> findListOfMonth(Water001Req request) throws Exception {
		List<Water001Res> response = water001Dao.findByCondition(request);
//		for (Water001Res water001Res : response) {
//			water001Res.setFlagCheck(false);
//			water001Res.setMeterDateStr(ConvertDateUtils.formatDateToString(water001Res.getMeterDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
//		}
		return response;
	}

	public List<Water001Res> findDropdownSerialNo() {
		List<RicWaterInfo> resFilter = water001Dao.findDropdownSerialNo();
		List<Water001Res> response = new ArrayList<Water001Res>();
		Water001Res entity = null;
		for (RicWaterInfo ricWaterInfo : resFilter) {
			entity = new Water001Res();
			entity.setSerialNoMeter(ricWaterInfo.getSerialNoMeter());
			response.add(entity);
		}
		return response;
	}

	public ByteArrayOutputStream downloadTemplate(String periodMonth, String idStr) throws Exception {
		/* split id and find data to write excel */
		List<Water001Res> resSearch = new ArrayList<Water001Res>();
		Water001Req reqInfo = null;
		for (String id : idStr.split(",")) {
			reqInfo = new Water001Req();
			reqInfo.setPeriodMonth(periodMonth);
			reqInfo.setId(Long.valueOf(id));
			resSearch.add(findListOfMonth(reqInfo).get(0));
		}

		/* style */
		XSSFWorkbook workbook = new XSSFWorkbook();
//		CellStyle thStyle = ExcelUtils.createThCellStyle(workbook);
		CellStyle tdStyle = ExcelUtils.createTdCellStyle(workbook);
//		CellStyle TopicCenterlite = ExcelUtils.createTopicCenterliteStyle(workbook);
		CellStyle tdLeft = ExcelUtils.createLeftCellStyle(workbook);
		CellStyle tdRight = ExcelUtils.createRightCellStyle(workbook);
//		CellStyle TopicRight = ExcelUtils.createTopicRightStyle(workbook);
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

		String[] header = { "เลขที่สัญญา", "ผู้ประกอบการ/พนักงาน", "ชื่อมาตรวัดน้ำ", "Serial No. มาตรวัดน้ำ", "จดครั้งก่อน", "วันที่จดเลข",
				"จดครั้งนี้", "จำนวนที่ใช้(ลบ.ม.)" };
		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(cellNum);

		rowNum = 0;
		row = sheet.createRow(rowNum);
		
		cell = row.createCell(cellNum);
		cell.setCellValue("ประจำเดือน: ");
		cellNum++;
		
		cell = row.createCell(cellNum);
		cell.setCellValue(ConvertDateUtils.formatDateToString(ConvertDateUtils.parseStringToDate(periodMonth, ConvertDateUtils.YYYYMM), ConvertDateUtils.MM_YYYY));
		cellNum++;
		
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

		for (Water001Res r : resSearch) {
			if (!SAPConstants.SAP_SUCCESS.equals(r.getSapStatus())) {
				rowNum++;
				cellNum = 0;
				row = sheet.createRow(rowNum);

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
				if (r.getBackwardMeterValue() != null) {
					cell.setCellValue(r.getBackwardMeterValue().doubleValue());
				} else {
					cell.setCellValue("");
				}
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
				// cell.setCellValue(r.getCurrentAmount().doubleValue());
				cell.setCellValue("");
				cell.setCellStyle(tdStyle);
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
	public void save(List<Water001Res> request) {
		RicWaterInfo resFind = null;
		WaterBillReq reqCal = null;
		for (Water001Res req : request) {
			/* find data */
			resFind = new RicWaterInfo();
			resFind = ricWaterInfoRepository.findById(req.getWaterInfoId()).get();
			
			/* calculate */
			reqCal = new WaterBillReq();
			reqCal.setType(resFind.getWaterType1());
			reqCal.setType2(resFind.getWaterType2());
			reqCal.setType3(resFind.getWaterType3());
			reqCal.setCurrentMeterValue(StringUtils.isNotBlank(req.getCurrentMeterValue())? new BigDecimal(req.getCurrentMeterValue()): BigDecimal.ZERO);
			reqCal.setBackwardMeterValue(resFind.getBackwardMeterValue());
			reqCal.setServiceValue(resFind.getServiceValue());
			if (req.getCurrentAmount() != null) {
				if (resFind.getCurrentAmount().longValue() != req.getCurrentAmount().longValue()) {
					reqCal.setCurrentAmount(req.getCurrentAmount());
				}
			}
			WaterBillRes resCalculate = waterBillService.calculate(reqCal);
			
			/* save */
			Date dateNow = new Date();
//			if ("0".equals(ricWaterInfo.getCurrentMeterValue()) || "".equals(ricWaterInfo.getCurrentMeterValue())) {
//				resFind.setMeterDate(null);
//			} else {
//				resFind.setMeterDate(dateNow);
//			}
			if (StringUtils.isNotBlank(req.getMeterDateStr())) {
				resFind.setMeterDate(ConvertDateUtils.parseStringToDate(req.getMeterDateStr(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			} else {
				resFind.setMeterDate(null);
			}
			resFind.setRecycleAmount(resCalculate.getRecycleAmount());
			resFind.setTreatmentFee(resCalculate.getTreatmentFee());
			resFind.setCurrentMeterValue(req.getCurrentMeterValue());
			resFind.setCurrentAmount(resCalculate.getAmountMeter());
			resFind.setVat(resCalculate.getVat());
			resFind.setBaseValue(resCalculate.getBaseValue());
			resFind.setTotalChargeRates(resCalculate.getTotalChargeRates());
			resFind.setTotalAmount(resCalculate.getTotalAmount());
			resFind.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			resFind.setUpdatedDate(dateNow);
			ricWaterInfoRepository.save(resFind);
		}
	}

	@Transactional
	public List<Water001Res> uploadExcel(MultipartFile file) throws Exception {
		List<List<String>> allLine = ExcelUtils.readExcel(file);
		String[] periodMonthArr = {};
		String periodMonth = "";
		if(allLine.size() > 0) {
			periodMonthArr = allLine.get(0).get(1).split("/");
			periodMonth = periodMonthArr[1].concat(periodMonthArr[0]);
		}
		
		/* update field currentMeterValue */
		RicWaterInfo entity = null;
		WaterBillReq reqCal = null;
		Date dateNow = new Date();
		for (int i = 0; i < allLine.size(); i++) {
			entity = new RicWaterInfo();
			reqCal = new WaterBillReq();
			for (int j = 0; j < allLine.get(i).size(); j++) {
				/* SerialNoMeter */
				if (i > 1 && j == 3) {
					entity = ricWaterInfoRepository.findBySerialNoMeterAndPeriodMonth(allLine.get(i).get(j), periodMonth);
				}
				/* MeterDateStr */
				if (i > 1 && j == 5) {
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
				if (i > 1 && j == 6) {
//					if ("0".equals(allLine.get(i).get(j)) || "".equals(allLine.get(i).get(j))) {
//						entity.setMeterDate(null);
//					} else {
//						entity.setMeterDate(dateNow);
//					}
					entity.setCurrentMeterValue(allLine.get(i).get(j));
				}
				/* CurrentAmount */
				if (i > 1 && j == 7) {
					if(StringUtils.isNotBlank(allLine.get(i).get(j))) {
						reqCal.setCurrentAmount(NumberUtils.roundUpTwoDigit(allLine.get(i).get(j)));
					}
				}
			}
			if (i > 1) {
				/* calculate */
				reqCal.setType(entity.getWaterType1());
				reqCal.setType2(entity.getWaterType2());
				reqCal.setType3(entity.getWaterType3());
				reqCal.setCurrentMeterValue(StringUtils.isNotBlank(entity.getCurrentMeterValue())? new BigDecimal(entity.getCurrentMeterValue()): BigDecimal.ZERO);
				reqCal.setBackwardMeterValue(entity.getBackwardMeterValue());
				reqCal.setServiceValue(entity.getServiceValue());
				WaterBillRes resCalculate = waterBillService.calculate(reqCal);
				
				/* save */
				entity.setRecycleAmount(resCalculate.getRecycleAmount());
				entity.setTreatmentFee(resCalculate.getTreatmentFee());
				entity.setCurrentAmount(resCalculate.getAmountMeter());
				entity.setVat(resCalculate.getVat());
				entity.setBaseValue(resCalculate.getBaseValue());
				entity.setTotalChargeRates(resCalculate.getTotalChargeRates());
				entity.setTotalAmount(resCalculate.getTotalAmount());
				entity.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				entity.setUpdatedDate(dateNow);
				ricWaterInfoRepository.save(entity);
			}
		}
		/* search current month */
		List<Water001Res> response = new ArrayList<Water001Res>();
		Water001Req reqInfo = new Water001Req();
		reqInfo.setPeriodMonth(
				ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
		response = findListOfMonth(reqInfo);

		return response;
	}

//	public Integer countCheckBeforeSynData() {
//		/* _______ check Data Empty _______ */
//		Water001Req searchReq = new Water001Req();
//		searchReq.setPeriodMonth(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
//		List<Water001Res> resSearch = water001Dao.findByCondition(searchReq);
//		if (resSearch.size() == 0) {
//			water001Dao.updateFlagInfo(FLAG.Y_FLAG, FLAG.N_FLAG, null);
//		}
//		return water001Dao.countByFlagInfoBeforeSyncData();
//	}

	public List<SapResponse> sendSap(List<Long> idx) throws Exception {
		List<SapResponse> responseSap = new ArrayList<SapResponse>();
		
		/* _____________ find all by id _____________ */
		Iterable<RicWaterInfo> checkboxList = ricWaterInfoRepository.findAllById(idx);
		
		/* _____________ find keyOfContractNo _____________ */
//		HashSet<String> keyOfContractNo = new HashSet<String>(); 
//		checkboxList.forEach(arr -> keyOfContractNo.add(arr.getContractNo()));
		
		/* _____________ find keyOfContractNo _____________ */
		HashSet<String> keyOfContractNo = new HashSet<String>();
		for (RicWaterInfo arr : checkboxList) {
			if (StringUtils.isBlank(arr.getContractNo()) && ElectricConstants.EMPLOYEE_TYPE.equals(arr.getCustomerType())) {
				keyOfContractNo.add("E-" + arr.getWaterInfoId());
			} else {
				keyOfContractNo.add(arr.getContractNo());
			}
		}
		
		/* _____________ filter group by key _____________ */
		for (String key : keyOfContractNo) {
			List<RicWaterInfo> groupByContractNo = new ArrayList<>();
			if (key.indexOf("E-") > -1) {
				groupByContractNo = ((Collection<RicWaterInfo>) checkboxList).stream()
						.filter(obj -> key.split("-")[1].equals(obj.getWaterInfoId().toString()))
						.collect(Collectors.toList());
			} else {
				groupByContractNo = ((Collection<RicWaterInfo>) checkboxList).stream()
						.filter(obj -> key.equals(obj.getContractNo())).collect(Collectors.toList());
			}
			
//			List<RicWaterInfo> groupByContractNo = ((Collection<RicWaterInfo>) checkboxList).stream().filter(obj -> key.equals(obj.getContractNo())).collect(Collectors.toList());
			ArRequest dataSend = null;
			String busPlace = UserLoginUtils.getUser().getAirportCode();
			/* _____________ set ARrequest sap _____________ */
			if (groupByContractNo.size() == 1) {

				RicWaterInfo resGroup = groupByContractNo.get(0);
				if (ElectricConstants.CUSTOMER_TYPE.equals(resGroup.getCustomerType())) {
					/* _________ customer _________ */
					dataSend = sapArRequest_1_5.getARRequest(busPlace, SAPConstants.COMCODE, DoctypeConstants.IF,
							resGroup);
				} else {
					/* _________ employee _________ */
					dataSend = sapArRequest_1_1_2.getARRequest(busPlace, SAPConstants.COMCODE, DoctypeConstants.IF,
							resGroup);
				}
			} else {
				dataSend = sapArRequest_1_5.getARRequestDynamic(busPlace, SAPConstants.COMCODE, DoctypeConstants.IF,
						groupByContractNo);
			}
			
			/* _____________ loop set req, res, status sap _____________ */
			for (RicWaterInfo water001Req : groupByContractNo) {
				/* set data sap */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);
				
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(water001Req.getWaterInfoId());
				reqConnection.setTableName("ric_water_info");
				reqConnection.setColumnId("water_info_id");
//				reqConnection.setColumnInvoiceNo("invoice_no");
//				reqConnection.setColumnTransNo("transaction_no");
//				reqConnection.setColumnSapJsonReq("sap_json_req");
//				reqConnection.setColumnSapJsonRes("sap_json_res");
//				reqConnection.setColumnSapError("sap_error");
//				reqConnection.setColumnSapStatus("sap_status");
				responseSap.add(sapARService.setSapConnection(reqConnection));
			}
		}
		return responseSap;
	}
	
	@Transactional
	public Integer syncData(String periodMonth) throws IOException {
		logger.info("syncData Water001 Start");
		long start = System.currentTimeMillis();
		/* 
		 * check previous month 
		 * */
		Water001Req previousMonthReq = new Water001Req();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ConvertDateUtils.parseStringToDate(periodMonth, ConvertDateUtils.YYYYMM));
		cal.add(Calendar.MONTH, -1);
		previousMonthReq.setPeriodMonth(ConvertDateUtils.formatDateToString(cal.getTime(), ConvertDateUtils.YYYYMM));
		List<RicWaterInfo> previousMonthList = water001Dao.findByPeriodMonth(previousMonthReq);
		/*
		 * data initial from register
		 * */
		List<RicWaterInfo> infoList = new ArrayList<RicWaterInfo>();
		RicWaterInfo info = null;
		List<RicWaterReq> ResReq = water001Dao.checkCancelDateBeforeSyncData(periodMonth);
		for (RicWaterReq initData : ResReq) {
			/* _________ set data register _________ */
			info = setDataRegister(initData);
			info.setPeriodMonth(periodMonth);

			List<RicWaterInfo> dataFilter = previousMonthList.stream()
					.filter(obj -> initData.getMeterSerialNo().equals(obj.getSerialNoMeter()))
					.collect(Collectors.toList());
			if (dataFilter.size() > 0) {
				/* _________ old user _________ */
				RicWaterInfo d = dataFilter.get(0);
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
		ricWaterInfoRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData Water001 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicWaterInfo setDataRegister(RicWaterReq initData) {
		RicWaterInfo info = new RicWaterInfo();
		info.setRoCode(initData.getRentalAreaCode());
		info.setRoName(initData.getRentalAreaName());
		info.setCustomerType(initData.getCustomerType());
		info.setWaterType1(FLAG.X_FLAG); //default
//		info.setWaterType1(initData.getWaterType1());
		info.setWaterType2(initData.getWaterType2());
		info.setWaterType3(initData.getWaterType3());
		info.setContractNo(initData.getContractNo());
		info.setEntreprenuerCode(initData.getCustomerCode());
		info.setEntreprenuerName(initData.getCustomerName());
		info.setSerialNoMeter(initData.getMeterSerialNo());
		info.setServiceValue(ricWaterMaintenanceConfigRepository.findById(Long.valueOf(initData.getMeterType())).get().getChargeRates());
		info.setMeterName(initData.getMeterName());
		// info.setTreatmentFee(new BigDecimal(150));
		info.setCreatedBy(UserLoginUtils.getCurrentUsername());
		info.setCreatedDate(new Date());
		info.setIsDeleted(FLAG.N_FLAG);
		info.setCustomerBranch(initData.getCustomerBranch());
		info.setIdReq(initData.getReqId());
		return info;
	}
	
	public static void main(String[] args) {
		String str = "hi there";
		System.out.println("hi".equals(str.substring(0, 2)));
	}
}
