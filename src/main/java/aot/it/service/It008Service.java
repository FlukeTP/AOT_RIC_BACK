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

import aot.it.model.RicItDedicatedCUTECreateInvoice;
import aot.it.model.RicItDedicatedCUTECreateInvoiceMapping;
import aot.it.repository.It008Dao;
import aot.it.repository.jpa.RicItDedicatedCUTECreateInvoiceMappingRepository;
import aot.it.repository.jpa.RicItDedicatedCUTECreateInvoiceRepository;
import aot.it.vo.request.It008DtlReq;
import aot.it.vo.request.It008Req;
import aot.it.vo.response.It008Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.ExcelUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It008Service {

	@Autowired
	private RicItDedicatedCUTECreateInvoiceRepository hdrRepository;

	@Autowired
	private RicItDedicatedCUTECreateInvoiceMappingRepository dtlRepository;

	@Autowired
	private It008Dao it008Dao;
	
	private static final Logger logger = LoggerFactory.getLogger(It008Service.class);

	@Transactional(rollbackOn = { Exception.class })
	public void save(It008Req request) {
		// Header
		RicItDedicatedCUTECreateInvoice header = null;
		header = new RicItDedicatedCUTECreateInvoice();

		try {
			if (request.getId()!=null) {
				header = hdrRepository.findById(request.getId()).get();
				// set data
				header.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				header.setUpdatedDate(new Date());
			} else {
				header = new RicItDedicatedCUTECreateInvoice();
				// set data
				header.setCreatedBy(UserLoginUtils.getCurrentUsername());
				header.setCreatedDate(new Date());
				header.setIsDelete("N");
			}
			header.setEntreprenuerCode(request.getEntreprenuerCode());
			header.setEntreprenuerName(request.getEntreprenuerName());
			header.setEntreprenuerBranch(request.getEntreprenuerBranch());
			header.setContractNo(request.getContractNo());
			header.setLocation(request.getLocation());
			header.setTotalAmount(request.getTotalAmount());
			header.setContractData(request.getContractData());
			header.setRequestStartDate(ConvertDateUtils.parseStringToDate(request.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			header.setRequestEndDate(ConvertDateUtils.parseStringToDate(request.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			header.setIsDelete("N");
			// save data Header
			header = hdrRepository.save(header);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		// ==================================================================

		if (request.getDetailInfo() != null && request.getDetailInfo().size() > 0) {
			// DTL
			dtlRepository.deleteByIdHeader(request.getId());
			RicItDedicatedCUTECreateInvoiceMapping detail = null;
			List<RicItDedicatedCUTECreateInvoiceMapping> detailList = new ArrayList<>();

			for (It008DtlReq data : request.getDetailInfo()) {
				detail = new RicItDedicatedCUTECreateInvoiceMapping();
				detail.setIdHeader(header.getId());
				detail.setServiceType(data.getServiceType());
				detail.setEquipmentAmount(data.getEquipmentAmount());
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

	public List<It008Res> findData(It008Req request) {
		List<It008Res> dataRes = it008Dao.findData(request);
		return dataRes;
	}

	public It008Res getById(Long id) {
		It008Res data = new It008Res();
		RicItDedicatedCUTECreateInvoice header = hdrRepository.findById(id).get();

		data.setEntreprenuerCode(header.getEntreprenuerCode());
		data.setEntreprenuerName(header.getEntreprenuerName());
		data.setEntreprenuerBranch(header.getEntreprenuerBranch());
		data.setContractNo(header.getContractNo());
		data.setLocation(header.getLocation());
		data.setContractData(header.getContractData());
		data.setRequestStartDate(ConvertDateUtils.formatDateToString(header.getRequestStartDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		data.setRequestEndDate(ConvertDateUtils.formatDateToString(header.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		data.setTotalAmount(header.getTotalAmount());
		List<RicItDedicatedCUTECreateInvoiceMapping> datalist = dtlRepository.findByIdHdr(id);
		data.setDetailInfo(datalist);
		return data;
	}
	
	public ByteArrayOutputStream downloadTemplate() throws Exception {
		It008Req form = new It008Req();
		List<It008Res> dataExport = new ArrayList<It008Res>();
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

		String[] header = {
			    "ลำดับที่", "ชื่อผู้ประกอบการ", "เลขที่สัญญา", "สถานที่ติดตั้ง", "จำนวนเงินค่าบริการ/เดือน(รวมภาษีมูลค่าเพิ่ม)",
			    "วันที่เริ่มใช้งาน", "วันที่สิ้นสุดการใช้งาน"};

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
		for (It008Res data : dataExport) {
			cellNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(cellNum);
			cell.setCellValue(index);
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getEntreprenuerName() +" "+ data.getEntreprenuerCode());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getContractNo());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getLocation());
			cell.setCellStyle(tdLeft);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestStartDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(data.getRequestEndDate());
			cell.setCellStyle(tdCenter);

			cellNum++;
			cell = row.createCell(cellNum);
			cell.setCellValue(NumberUtils.toDecimalFormat(data.getTotalAmount(), true));
			cell.setCellStyle(tdRight);
			
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
