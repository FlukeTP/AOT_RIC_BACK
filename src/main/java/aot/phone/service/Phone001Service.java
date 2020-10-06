package aot.phone.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.common.repository.CustomerRepository;
import aot.common.service.RicNoGenerator;
import aot.common.vo.response.ContractNoRes;
import aot.common.vo.response.CustomerRes;
import aot.phone.model.RicPhoneInfo;
import aot.phone.repository.Phone001Dao;
import aot.phone.repository.jpa.RicPhoneInfoRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import aot.phone.vo.request.Phone001Req;
import aot.phone.vo.response.Phone001Res;
import aot.phone.vo.response.Phone002Res;
import aot.sap.service.SAPARService;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_7;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.module.service.SysConstantService;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Phone001Service {
    private static final Logger logger = LoggerFactory.getLogger(Phone001Service.class);
    
    @Autowired
    private Phone001Dao phone001Dao;
    
    @Autowired
    private RicPhoneReqRepository ricPhoneReqRepository;
    
    @Autowired
    private RicPhoneInfoRepository ricPhoneInfoRepository;
    
    @Autowired
	private SapArRequest_1_7 sapArRequest_1_7;
	
	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private RicNoGenerator ricNoGenerator;
	
	@Autowired
	private SysConstantService sysConstantService;
	
	@Autowired
	private CustomerRepository customerRepository;
    
	public List<Phone001Res> search(Phone001Req request) {
		List<Phone001Res> response = phone001Dao.findByCondition(request);
		for (Phone001Res r : response) {
			r.setFlagCheck(false);
		}
		return response;
	}

	public Integer checkBeforeSynData() {
		/* _______ check Data Empty _______ */
		Phone001Req searchReq = new Phone001Req();
		searchReq.setPeriodMonth(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
		List<Phone001Res> resSearch = phone001Dao.findByCondition(searchReq);
		if (resSearch.size() == 0) {
			phone001Dao.updateFlagInfo();
		}
		return ricPhoneReqRepository.countByFlagInfo(FLAG.N_FLAG);
	}
	
	public ByteArrayOutputStream downloadTemplate(String periodMonth) throws Exception {
		/* find data to write excel */
		Phone001Req reqInfo = new Phone001Req();
		reqInfo.setPeriodMonth(periodMonth);
		List<Phone001Res> dataExport = search(reqInfo);

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
		
		String[] header = {
			    "ลำดับที่", "รหัสที่อยู่", "ผู้ประกอบการ", "ค่าโทรศัพท์", "ค่าบำรุงรักษา", "ค่าบริการเครื่องพ่วง",
			    "ค่าเช่าคู่สายภายใน", "ค่าเช่าคู่สายภายนอก", "รวมเงิน(บาท)", "ภาษีมูลค่าเพิ่ม", "ยอดรวมค่าบริการทั้งหมด",
			    "ค่าโทรศัพท์", "", ""};

		String[] header2 = {
			    "", "", "", "", "", "", "", "", "", "", "",
			    "เลขที่ใบแจ้งหนี้", "เลขที่ใบเสร็จ", "สถานะ SAP"};

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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 13));

		rowNum++;
		int index = 1;
		for (Phone001Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerCode()+"-"+data.getAddressCode());
			cell.setCellStyle(tdLeft);
			
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerName());
			cell.setCellStyle(tdLeft);
			
			BigDecimal TotalCharge = BigDecimal.ZERO;
			TotalCharge = TotalCharge.add(data.getLocAmt().add(data.getLocSvc().add(data.getLngAmt())));
			TotalCharge = TotalCharge.add(data.getLngSvc().add(data.getOvsAmt().add(data.getOvsSvc())));

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(TotalCharge));
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getMaintenanceCharge()));
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getServiceEquipmentCharge()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getInternalLineCharge()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getOutterLineCharge()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getTotalCharge()));
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getVat()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(String.valueOf(data.getTotalChargeAll()));
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getInvoiceNo());
			cell.setCellStyle(tdLeft);
			
			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getReceipt());
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
		for (int i = 1; i <= 16; i++) {
			sheet.setColumnWidth(i, width * 60);
		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);
		return outByteStream;
	}

	@Transactional
	public void uploadExcel(Phone001Req file) throws Exception {
		logger.info(" ################# read excel Phone001 Start ################# ");
		long start = System.currentTimeMillis();
//		List<List<String>> allLine = ExcelUtils.readExcel(file);
		/* ________ count data by period month ________ */
		Integer count = ricPhoneInfoRepository.countByPeriodMonth(file.getPeriodMonth());
		if(count > 0) {
//			ricPhoneInfoRepository.deleteByPeriodMonth(file.getPeriodMonth());
			phone001Dao.deleteByPeriodMonth(file.getPeriodMonth());
		}
		List<Phone001Res> allLines = new ArrayList<Phone001Res>();
		Phone001Res line = null;
		Set<String> keyCustCode = new TreeSet<String>();
		Workbook workbook = WorkbookFactory.create(file.getFile().getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		int i = 0;
		for (Row row : sheet) {
			int j = 0;
			if (i > 0) {
				line = new Phone001Res();
				for (Cell cell : row) {
					String cellValue = ExcelUtils.getCellValueAsString(cell);
					switch (cell.getColumnIndex()) {
					case 0:
						line.setPhoneNo(cellValue);
						break;
					case 1:
						if(cellValue.charAt(0) == '4') {
							line.setCustomerType("E");
						} else {
							line.setCustomerType("C");
						}
						break;
					case 2:
						line.setEntreprenuerCode(cellValue);
						break;
					case 3:
						line.setAddressCode(cellValue);
						break;
					case 5:
						line.setLocAmt(new BigDecimal(cellValue));
						break;
					case 6:
						line.setLocSvc(new BigDecimal(cellValue));
						break;
					case 7:
						line.setLngAmt(new BigDecimal(cellValue));
						break;
					case 8:
						line.setLngSvc(new BigDecimal(cellValue));
						break;
					case 9:
						line.setOvsAmt(new BigDecimal(cellValue));
						break;
					case 10:
						line.setOvsSvc(new BigDecimal(cellValue));
						break;
					case 11:
						line.setMaintenanceCharge(new BigDecimal(cellValue));
						break;
					case 12:
						line.setServiceEquipmentCharge(new BigDecimal(cellValue));
						break;
					case 13:
						line.setTotalCharge(new BigDecimal(cellValue));
						break;
					case 14:
						line.setTotalChargeAll(new BigDecimal(cellValue));
						break;
					case 15:
						line.setColinestat(cellValue);
						break;

					default:
						break;
					}
					j++;
				}
				keyCustCode.add(line.getEntreprenuerCode().concat("-").concat(line.getAddressCode()));
				allLines.add(line);
			}
			i++;
		}
		
		/* _________ loop key _________ */
		for (String key : keyCustCode) {
			if(key.charAt(0) != '6' || key.charAt(0) != '7' || key.charAt(0) != '9') {
				/* _________ filter _________ */
				List<Phone001Res> dataFilter = allLines.stream().filter(obj -> key.equals(obj.getEntreprenuerCode().concat("-").concat(obj.getAddressCode()))).collect(Collectors.toList());
				if(dataFilter.size() > 0) {
					/* _________ loop group data _________ */
					BigDecimal maintenanceCharge = BigDecimal.ZERO;
					BigDecimal serviceEquipmentCharge = BigDecimal.ZERO;
					BigDecimal internalLineCharge = BigDecimal.ZERO;
					BigDecimal outterLineCharge = BigDecimal.ZERO;
					BigDecimal totalCharge = BigDecimal.ZERO;
					BigDecimal totalChargeAll = BigDecimal.ZERO;
					BigDecimal locAmt = BigDecimal.ZERO;
					BigDecimal locSvc = BigDecimal.ZERO;
					BigDecimal lngAmt = BigDecimal.ZERO;
					BigDecimal lngSvc = BigDecimal.ZERO;
					BigDecimal ovsAmt = BigDecimal.ZERO;
					BigDecimal ovsSvc = BigDecimal.ZERO;
					for (Phone001Res resF : dataFilter) {
						maintenanceCharge = maintenanceCharge.add(resF.getMaintenanceCharge());
						serviceEquipmentCharge = serviceEquipmentCharge.add(resF.getServiceEquipmentCharge());
						totalChargeAll = totalChargeAll.add(resF.getTotalChargeAll());
						totalCharge = totalCharge.add(resF.getTotalCharge());
						switch (resF.getColinestat()) {
						case "0":
							locAmt = locAmt.add(resF.getLocAmt());
							locSvc = locSvc.add(resF.getLocSvc());
							lngAmt = lngAmt.add(resF.getLngAmt());
							lngSvc = lngSvc.add(resF.getLngSvc());
							ovsAmt = ovsAmt.add(resF.getOvsAmt());
							ovsSvc = ovsSvc.add(resF.getOvsSvc());
							break;
						case "1":
							internalLineCharge = internalLineCharge.add(resF.getLocAmt()).add(resF.getLocSvc()).add(resF.getLngAmt()).add(resF.getLngSvc()).add(resF.getOvsAmt()).add(resF.getOvsSvc());
							break;
						case "2":
							outterLineCharge = outterLineCharge.add(resF.getLocAmt()).add(resF.getLocSvc()).add(resF.getLngAmt()).add(resF.getLngSvc()).add(resF.getOvsAmt()).add(resF.getOvsSvc());
							break;
						default:
							break;
						}
					}
					
					RicPhoneInfo reqSave = new RicPhoneInfo();
					CustomerRes reqCust = new CustomerRes();
					reqCust.setCustomerCode("000" + dataFilter.get(0).getEntreprenuerCode());
					List<CustomerRes> custList = customerRepository.getCustomers(reqCust);
					if(custList.size() > 0){
						reqSave.setCustomerBranch(custList.get(0).getAdrKind());
						reqSave.setEntreprenuerName(custList.get(0).getName1());
						List<ContractNoRes> nameList = customerRepository.getContractNo("000" + dataFilter.get(0).getEntreprenuerCode(), custList.get(0).getAdrKind());
						if(nameList.size() > 0) {
							reqSave.setContractNo(nameList.get(0).getContractNo());
						}
					}			
					
					reqSave.setCustomerType(dataFilter.get(0).getCustomerType());
					reqSave.setAddressCode(dataFilter.get(0).getAddressCode());
					reqSave.setEntreprenuerCode(dataFilter.get(0).getEntreprenuerCode());
					reqSave.setPeriodMonth(file.getPeriodMonth());
//					reqSave.setPeriodMonth(ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN));
					reqSave.setMaintenanceCharge(NumberUtils.roundUpTwoDigit(maintenanceCharge));
					reqSave.setServiceEquipmentCharge(NumberUtils.roundUpTwoDigit(serviceEquipmentCharge));
					reqSave.setInternalLineCharge(NumberUtils.roundUpTwoDigit(internalLineCharge));
					reqSave.setOutterLineCharge(NumberUtils.roundUpTwoDigit(outterLineCharge));
					reqSave.setVat(NumberUtils.roundUpTwoDigit(totalCharge.multiply(new BigDecimal(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue()))));
					reqSave.setTotalCharge(NumberUtils.roundUpTwoDigit(totalCharge));
					reqSave.setTotalChargeAll(NumberUtils.roundUpTwoDigit(totalChargeAll));
					reqSave.setLngAmt(NumberUtils.roundUpTwoDigit(lngAmt));
					reqSave.setLngSvc(NumberUtils.roundUpTwoDigit(lngSvc));
					reqSave.setLocAmt(NumberUtils.roundUpTwoDigit(locAmt));
					reqSave.setLocSvc(NumberUtils.roundUpTwoDigit(locSvc));
					reqSave.setOvsAmt(NumberUtils.roundUpTwoDigit(ovsAmt));
					reqSave.setOvsSvc(NumberUtils.roundUpTwoDigit(ovsSvc));
					reqSave.setIsDeleted(FLAG.N_FLAG);
					reqSave.setCreatedDate(new Date());
					reqSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
					/* must change save to Batch */
					ricPhoneInfoRepository.save(reqSave);
				}
			}
		}
		
		long end = System.currentTimeMillis();
		logger.info(" ################# read excel Phone001 end, using {} seconds ################# ", (float) (end - start) / 1000F);
	}

	public List<SapResponse> sendSap(List<Long> idx) throws JsonProcessingException {
		List<SapResponse> responseSap = new ArrayList<SapResponse>();

		/* find all by id */
		Iterable<RicPhoneInfo> dataUpdate = ricPhoneInfoRepository.findAllById(idx);
		for (RicPhoneInfo phone001Req : dataUpdate) {
			ArRequest dataSend = sapArRequest_1_7.getARRequest(
					UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE, DoctypeConstants.IG,
					phone001Req);

			/* set data sap */
			try {
				ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
				String json = mapper.writeValueAsString(dataSend);
				phone001Req.setSapJsonReq(json.replaceAll(" ", ""));
				ricPhoneInfoRepository.save(phone001Req);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper2 = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			SapResponse dataRes = sapARService.callSAPAR(dataSend);
			if (SAPConstants.SAP_SUCCESS.equals(dataRes.getStatus())) {
				dataRes.setStatus(SAPConstants.SAP_SUCCESS);
				dataRes.setMessage(dataRes.getRawJsonStringFromSAP());
				dataRes.setMessageType(dataRes.getStatus());

				phone001Req.setTransactionNo(dataRes.getSapARResponseSuccess().getTRANSNO());
				phone001Req.setInvoiceNo(String.valueOf(dataRes.getSapARResponseSuccess().getDOCNO()));
				phone001Req.setSapJsonRes(dataRes.getRawJsonStringFromSAP());
				phone001Req.setSapError(mapper2.writeValueAsString(dataRes.getSapARResponseSuccess()));
			} else if (SAPConstants.SAP_FAIL.equals(dataRes.getStatus())) {
				dataRes.setStatus(SAPConstants.SAP_FAIL);
				dataRes.setMessage(dataRes.getMessage());
				dataRes.setMessageType(dataRes.getStatus());

				// set data response error
				phone001Req.setSapError(dataRes.getRawJsonStringFromSAP());
				phone001Req.setSapJsonRes(dataRes.getRawJsonStringFromSAP());
				phone001Req.setSapError(mapper2.writeValueAsString(dataRes.getSapARResponseFail()));
			} else if (SAPConstants.SAP_CONNECTION_FAIL.equals(dataRes.getStatus())) {
				dataRes.setStatus(SAPConstants.SAP_CONNECTION_FAIL);
				dataRes.setMessage(SAPConstants.SAP_CONNECTION_FAIL_MSG);
				dataRes.setMessageType(dataRes.getStatus());

				// set data can't connect base
				phone001Req.setSapError(SAPConstants.SAP_CONNECTION_FAIL_MSG);
				phone001Req.setTransactionNo(dataSend.getHeader().get(0).getTransactionNo());
			}
			phone001Req.setSapStatus(dataRes.getStatus());
			ricPhoneInfoRepository.save(phone001Req);
			responseSap.add(dataRes);
		}
		return responseSap;
	}
}
