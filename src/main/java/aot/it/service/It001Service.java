package aot.it.service;

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
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.CommunicateConstants;
import aot.common.constant.DoctypeConstants;
import aot.communicate.model.RicCommunicateReqHdr;
import aot.it.model.RicItNetworkCreateInvoice;
import aot.it.model.RicItNetworkCreateInvoiceMapping;
import aot.it.model.RicItNetworkServiceList;
import aot.it.repository.It001Dao;
import aot.it.repository.jpa.RicItNetworkCreateInvoiceMappingRepository;
import aot.it.repository.jpa.RicItNetworkCreateInvoiceRepository;
import aot.it.vo.request.It001DtlReq;
import aot.it.vo.request.It001Req;
import aot.it.vo.response.It001Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It001Service {

	@Autowired
	private RicItNetworkCreateInvoiceRepository hdrRepository;

	@Autowired
	private RicItNetworkCreateInvoiceMappingRepository dtlRepository;

	@Autowired
	private It001Dao it001Dao;

	@Autowired
	private SAPARService sapARService;

	private static final Logger logger = LoggerFactory.getLogger(It001Service.class);

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(It001Req request) {
		SapResponse sapResponse = new SapResponse();
		try {
			RicItNetworkCreateInvoice createInvoice = hdrRepository.findById(Long.valueOf(request.getNetworkCreateInvoiceId())).get();

			/* __________________ check line SAP __________________ */
			ArRequest dataSend = null;
//			switch (createInvoice.getPaymentType()) {
//			case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
//				dataSend = sapArRequest_6_8.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
//						DoctypeConstants.IH, communicateReq);
//				break;
//			case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_EN:
//				dataSend = sapArRequest_6_7.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
//						DoctypeConstants.IH, communicateReq);
//				break;
//			default:
//				throw new Exception("NOT FOUND 'PAYMENT TYPE'!!");
//			}
			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
//			reqConnection.setId(serviceList.getItNetworkServiceId());
			reqConnection.setTableName("ric_it_network_service_list");
			reqConnection.setColumnId("it_network_service_id");
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

	@Transactional(rollbackOn = { Exception.class })
	public void save(It001Req request) {
		// Header
		RicItNetworkCreateInvoice header = null;
		header = new RicItNetworkCreateInvoice();

		try {
			if (request.getNetworkCreateInvoiceId() != null) {
				header = hdrRepository.findById(request.getNetworkCreateInvoiceId()).get();
				// set data
				header.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				header.setUpdatedDate(new Date());
			} else {
				header = new RicItNetworkCreateInvoice();
				// set data
				header.setCreatedBy(UserLoginUtils.getCurrentUsername());
				header.setCreatedDate(new Date());
				header.setIsDelete("N");
			}
			header.setEntreprenuerCode(request.getEntreprenuerCode());
			header.setEntreprenuerName(request.getEntreprenuerName());
			header.setEntreprenuerBranch(request.getEntreprenuerBranch());
			header.setContractNo(request.getContractNo());
			header.setItLocation(request.getItLocation());
			header.setTotalAmount(request.getTotalAmount());
			header.setRentalObjectCode(request.getRentalObjectCode());
			header.setRequestStartDate(ConvertDateUtils.parseStringToDate(request.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			header.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getRequestEndDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			header.setRemark(request.getRemark());
			header.setIsDelete(FLAG.N_FLAG);
			// save data Header
			header = hdrRepository.save(header);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		// ==================================================================

		if (request.getDetailChargeRate() != null && request.getDetailChargeRate().size() > 0) {
			// DTL
			RicItNetworkCreateInvoiceMapping detail = null;
			List<RicItNetworkCreateInvoiceMapping> detailList = new ArrayList<>();

			for (It001DtlReq data : request.getDetailChargeRate()) {
				detail = new RicItNetworkCreateInvoiceMapping();
				detail.setIdNetworkCreateInvoice(header.getItNetworkConfigId());
				detail.setServiceType(data.getServiceType());
				detail.setCalculateInfo(data.getCalculateInfo());
				detail.setTotalAmount(data.getTotalAmount());
				detail.setChargeRate(data.getChargeRate() != null ? data.getChargeRate() : BigDecimal.ZERO);
				detail.setCreatedBy(UserLoginUtils.getCurrentUsername());
				detail.setCreatedDate(new Date());
				detailList.add(detail);
			}
			// save data DTL
			dtlRepository.saveAll(detailList);
		}
	}

	public List<It001Res> findData(It001Req request) {
		List<It001Res> dataRes = it001Dao.findData(request);
		return dataRes;
	}

	public It001Res getById(Long id) {
		It001Res data = new It001Res();
		RicItNetworkCreateInvoice header = hdrRepository.findById(id).get();

		data.setEntreprenuerCode(header.getEntreprenuerCode());
		data.setEntreprenuerName(header.getEntreprenuerName());
		data.setEntreprenuerBranch(header.getEntreprenuerBranch());
		data.setContractNo(header.getContractNo());
		data.setItLocation(header.getItLocation());
		data.setRentalObjectCode(header.getRentalObjectCode());
		data.setRequestStartDate(ConvertDateUtils.formatDateToString(header.getRequestStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		data.setRequestEndDate(ConvertDateUtils.formatDateToString(header.getRequestEndDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		data.setRemark(header.getRemark());
		data.setTotalAmount(header.getTotalAmount());
		List<RicItNetworkCreateInvoiceMapping> datalist = dtlRepository.findByIdHdr(id);
		data.setDetailChargeRate(datalist);
		return data;
	}

	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It001Req form = new It001Req();
		List<It001Res> dataExport = new ArrayList<It001Res>();
		dataExport = findData(form);

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

		String[] header = { "ลำดับที่", "ผู้ประกอบการ", "รหัสผู้ประกอบการ", "เลขที่สัญญา", "สถานที่ติดตั้ง",
				"พื้นที่เช่า", "รวมอัตราค่าภาระ", "วันที่ขอใช้บริการ", "วันที่ยกเลิก" };

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
		for (It001Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerName());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerCode());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getItLocation());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRentalObjectCode());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getTotalAmount(), true));
			cell.setCellStyle(tdRight);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStartDate());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestEndDate());
			cell.setCellStyle(tdLeft);

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
