package aot.common.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aot.electric.service.Electric001Service;
import aot.electric.service.Electric002Service;
import aot.electric.service.Electric003Service;
import aot.electric.service.Electric005Service;
import aot.electric.service.Electric006Service;
import aot.electric.service.Electric007Service;
import aot.electric.service.Electric008Service;
import aot.electric.service.Electric009Service;
import aot.phone.service.Phone001Service;
import aot.phone.service.Phone002Service;
import aot.phone.service.Phone003Service;
import aot.phone.service.Phone004Service;
import aot.water.service.Water001Service;
import aot.water.service.Water002Service;
import aot.water.service.Water003Service;
import aot.water.service.Water007Service;
import aot.water.service.Water008Service;
import aot.water.service.Water009Service;
import aot.water.service.Water010Service;
import aot.water.service.Water0102Service;
import aot.water.service.Water0111Service;
import aot.water.service.Water0112Service;
import aot.water.service.Water0113Service;
import aot.water.service.Water0114Service;
import aot.heavyeqp.service.Heavyeqp001Service;
import aot.heavyeqp.service.Heavyeqp002Service;
import aot.heavyeqp.service.Heavyeqp004Service;
import aot.it.service.It001Service;
import aot.it.service.It004Service;
import aot.it.service.It005Service;
import aot.it.service.It008Service;
import aot.it.service.It009Service;
import aot.it.service.It0101Service;
import aot.it.service.It0102Service;
import aot.it.service.It0103Service;
import aot.it.service.It0104Service;
import aot.firebrigade.service.Firebrigade001Service;
import aot.firebrigade.service.Firebrigade002Service;
import baiwa.util.ConvertDateUtils;

@Controller
@RequestMapping("export")
public class ExportController {
	private static final Logger logger = LoggerFactory.getLogger(ExportController.class);
	
	@Autowired
	private Electric001Service electric001Service;
	@Autowired
	private Electric002Service electric002Service;
	@Autowired
	private Electric003Service electric003Service;
	@Autowired
	private Electric005Service electric005Service;
	@Autowired
	private Electric006Service electric006Service;
	@Autowired
	private Electric007Service electric007Service;
	@Autowired
	private Electric008Service electric008Service;
	@Autowired
	private Electric009Service electric009Service;
	@Autowired
	private Water001Service water001Service;
	@Autowired
	private Water002Service water002Service;
	@Autowired
	private Water003Service water003Service;
	@Autowired
	private Water007Service water007Service;
	@Autowired
	private Water008Service water008Service;
	@Autowired
	private Water009Service water009Service;
	@Autowired
	private Water010Service water010Service;
	@Autowired
	private Water0102Service water0102Service;
	@Autowired
	private Water0111Service water0111Service;
	@Autowired
	private Water0112Service water0112Service;
	@Autowired
	private Water0113Service water0113Service;
	@Autowired
	private Water0114Service water0114Service;
	@Autowired
	private Phone001Service phone001Service;
	@Autowired
	private Phone002Service phone002Service;
	@Autowired
	private Phone003Service phone003Service;
	@Autowired
	private Phone004Service phone004Service;
	@Autowired
	private Heavyeqp001Service heavyeqp001Service;
	@Autowired
	private Heavyeqp002Service heavyeqp002Service;
	@Autowired
	private Heavyeqp004Service heavyeqp004Service;
	@Autowired
	private Firebrigade001Service firebrigade001Service;
	@Autowired
	private Firebrigade002Service firebrigade002Service;
	@Autowired
	private It001Service it001Service;
	@Autowired
	private It004Service it004Service;
	@Autowired
	private It005Service it005Service;
	@Autowired
	private It008Service it008Service;
	@Autowired
	private It009Service it009Service;
	@Autowired
	private It0101Service it0101Service;
	@Autowired
	private It0102Service it0102Service;
	@Autowired
	private It0103Service it0103Service;
	@Autowired
	private It0104Service it0104Service;
	
	@GetMapping("/download-template-info/{flag}/{idStr}")
	public void ExportExcel(@PathVariable String flag, @PathVariable String idStr, HttpServletResponse response) throws Exception {
		String periodMonth = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
		String fileName = null;
		ByteArrayOutputStream outByteStream = null;
		String[] extractStr = null;
		
		//================== Field in Database ===============
		String customerCode=null;
		String customerName=null;
		String contractNo=null;
		String requestStatus=null;
		String rentalAreaName=null;
		String installPositionService=null;
		String customerType=null;
		String serial=null;
		String status=null;
		String meterNp=null;
		String meterName=null;
		String meterLocation=null;
		String dateCancel=null;
		String newSerialNo=null;
		String dateChange=null;
		String entrepreneurName=null;
		String phoneNo=null;
		String startDate=null;
		String courseName=null;
		String effectiveDate=null;
	
		switch (flag) {
		case "ELECTRIC001":
			// set fileName
			fileName = URLEncoder.encode("Template-electric001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = electric001Service.downloadTemplate(periodMonth, idStr);
			break;
		case "ELECTRIC002":
			// set fileName
			fileName = URLEncoder.encode("Template-electric002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			serial=extractStr[0];
			status=extractStr[1];
			if(extractStr[0].equals("-"))
			{
				serial="";
			}
			
			if(extractStr[1].equals("-"))
			{
				status="";
			}

			outByteStream = electric002Service.downloadTemplate(serial,status);
			break;
		case "ELECTRIC003":
			// set fileName
			fileName = URLEncoder.encode("Template-electric003".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerCode=extractStr[0];
			customerName=extractStr[1];
			contractNo=extractStr[2];
			requestStatus=extractStr[3];
			rentalAreaName=extractStr[4];
			installPositionService=extractStr[5];
			customerType=extractStr[6];
			
			if(extractStr[0].equals("-"))
			{
				customerCode="";
			}
			if(extractStr[1].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[2].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[3].equals("-"))
			{
				requestStatus="";
			}
			
			if(extractStr[4].equals("-"))
			{
				rentalAreaName="";
			}
			if(extractStr[5].equals("-"))
			{
				installPositionService="";
			}
			if(extractStr[6].equals("-"))
			{
				customerType="";
			}

			outByteStream = electric003Service.downloadTemplate(customerCode,customerName,contractNo,requestStatus,rentalAreaName,installPositionService,customerType);
			break;
		case "ELECTRIC005":
			// set fileName
			fileName = URLEncoder.encode("Template-electric005".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			serial=extractStr[2];
			dateCancel=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				serial="";
			}
			
			if(extractStr[3].equals("-"))
			{
				dateCancel="";
			}

			outByteStream = electric005Service.downloadTemplate(customerName,contractNo,serial,dateCancel);
			break;
		case "ELECTRIC006":
			// set fileName
			fileName = URLEncoder.encode("Template-electric006".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			newSerialNo=extractStr[2];
			dateChange=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				serial="";
			}
			
			if(extractStr[3].equals("-"))
			{
				dateCancel="";
			}

			outByteStream = electric006Service.downloadTemplate(customerName,contractNo,serial,dateCancel);
			break;
		case "ELECTRIC007":
			// set fileName
			fileName = URLEncoder.encode("Template-electric007".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = electric007Service.downloadTemplate();
			break;
		case "ELECTRIC008":
			// set fileName
			fileName = URLEncoder.encode("Template-electric008".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = electric008Service.downloadTemplate();
			break;
		case "ELECTRIC009":
			// set fileName
			fileName = URLEncoder.encode("Template-electric009".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = electric009Service.downloadTemplate();
			break;
		case "WATER001":
			// set fileName
			fileName = URLEncoder.encode("Template-water001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water001Service.downloadTemplate(periodMonth, idStr);
			break;
		case "WATER002":
			// set fileName
			fileName = URLEncoder.encode("Template-water002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			serial=extractStr[0];
			status=extractStr[1];
			meterNp=extractStr[2];
			if(extractStr[0].equals("-"))
			{
				serial="";
			}
			
			if(extractStr[1].equals("-"))
			{
				status="";
			}
			if(extractStr[2].equals("-"))
			{
				meterNp="";
			}
			
			outByteStream = water002Service.downloadTemplate(serial,status,meterNp);
			break;
		case "WATER003":
			// set fileName
			fileName = URLEncoder.encode("Template-water002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerCode=extractStr[0];
			customerName=extractStr[1];
			contractNo=extractStr[2];
			requestStatus=extractStr[3];
			rentalAreaName=extractStr[4];
			installPositionService=extractStr[5];
			customerType=extractStr[6];
			
			if(extractStr[0].equals("-"))
			{
				customerCode="";
			}
			if(extractStr[1].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[2].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[3].equals("-"))
			{
				requestStatus="";
			}
			
			if(extractStr[4].equals("-"))
			{
				rentalAreaName="";
			}
			if(extractStr[5].equals("-"))
			{
				installPositionService="";
			}
			if(extractStr[6].equals("-"))
			{
				customerType="";
			}
			
			outByteStream = water003Service.downloadTemplate(customerCode,customerName,contractNo,requestStatus,rentalAreaName,installPositionService,customerType);
			break;
		case "WATER007":
			// set fileName
			fileName = URLEncoder.encode("Template-water007".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			newSerialNo=extractStr[2];
			dateChange=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				newSerialNo="";
			}
			
			if(extractStr[3].equals("-"))
			{
				dateChange="";
			}

			outByteStream = water007Service.downloadTemplate(customerName,contractNo,newSerialNo,dateChange);
			break;
		case "WATER008":
			// set fileName
			fileName = URLEncoder.encode("Template-water008".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			newSerialNo=extractStr[2];
			dateChange=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				serial="";
			}
			
			if(extractStr[3].equals("-"))
			{
				dateCancel="";
			}

			outByteStream = water008Service.downloadTemplate(customerName,contractNo,serial,dateCancel);
			break;
		case "WATER009":
			// set fileName
			fileName = URLEncoder.encode("Template-water009".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water009Service.downloadTemplate();
			break;
		case "WATER0101":
			// set fileName
			fileName = URLEncoder.encode("Template-water010_1".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water010Service.downloadTemplate();
			break;
		case "WATER0102":
			// set fileName
			fileName = URLEncoder.encode("Template-water010_2".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water0102Service.downloadTemplate();
			break;
		case "WATER0111":
			// set fileName
			fileName = URLEncoder.encode("Template-water011_1".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water0111Service.downloadTemplate();
			break;
		case "WATER0112":
			// set fileName
			fileName = URLEncoder.encode("Template-water011_2".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water0112Service.downloadTemplate();
			break;
		case "WATER0113":
			// set fileName
			fileName = URLEncoder.encode("Template-water011_3".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water0113Service.downloadTemplate();
			break;
		case "WATER0114":
			// set fileName
			fileName = URLEncoder.encode("Template-water011_4".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = water0114Service.downloadTemplate();
			break;
		case "PHONE001":
			// set fileName
			fileName = URLEncoder.encode("Template-phone001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			String dateSearch=extractStr[0];
			if(extractStr[0].equals("-"))
			{
				dateSearch="";
			}
			outByteStream = phone001Service.downloadTemplate(dateSearch);
			break;
		case "PHONE002":
			// set fileName
			fileName = URLEncoder.encode("Template-phone002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			entrepreneurName=extractStr[0];
			contractNo=extractStr[1];
			phoneNo=extractStr[2];
			requestStatus=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				entrepreneurName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				phoneNo="";
			}
			
			if(extractStr[3].equals("-"))
			{
				requestStatus="";
			}
			
			outByteStream = phone002Service.downloadTemplate(entrepreneurName ,contractNo ,phoneNo  ,requestStatus);
			break;
		case "PHONE003":
			// set fileName
			fileName = URLEncoder.encode("Template-phone003".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			phoneNo=extractStr[2];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				phoneNo="";
			}
			
			
			outByteStream = phone003Service.downloadTemplate(customerName ,contractNo ,phoneNo);
			break;
		case "PHONE004":
			// set fileName
			fileName = URLEncoder.encode("Template-phone004".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = phone004Service.downloadTemplate();
			break;
		case "EQP001":
			// set fileName
			fileName = URLEncoder.encode("Template-eqp001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			startDate=extractStr[0];
			
			outByteStream = heavyeqp001Service.downloadTemplate(startDate);
			break;
		case "EQP002":
			// set fileName
			fileName = URLEncoder.encode("Template-eqp002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment	
			outByteStream = heavyeqp002Service.downloadTemplate();
			break;
		case "EQP004":
			// set fileName
			fileName = URLEncoder.encode("Template-eqp004".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment	
			outByteStream = heavyeqp004Service.downloadTemplate();
			break;
		case "FIREBRIGADE001":
			// set fileName
			fileName = URLEncoder.encode("Template-firebrigade001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			customerName=extractStr[0];
			contractNo=extractStr[1];
			courseName=extractStr[2];
			startDate=extractStr[3];
			if(extractStr[0].equals("-"))
			{
				customerName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				contractNo="";
			}
			
			if(extractStr[2].equals("-"))
			{
				courseName="";
			}
			
			if(extractStr[3].equals("-"))
			{
				startDate="";
			}
			
			outByteStream = firebrigade001Service.downloadTemplate(customerName,contractNo,courseName,startDate);
			break;
		case "FIREBRIGADE002":
			// set fileName
			fileName = URLEncoder.encode("Template-firebrigade002".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			extractStr = idStr.split(",");
			courseName=extractStr[0];
			effectiveDate=extractStr[1];
			if(extractStr[0].equals("-"))
			{
				courseName="";
			}
			
			if(extractStr[1].equals("-"))
			{
				effectiveDate="";
			}
			
			outByteStream = firebrigade002Service.downloadTemplate(courseName,effectiveDate);
			break;
		case "IT001":
			// set fileName
			fileName = URLEncoder.encode("Template-it001".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it001Service.downloadTemplate();
			break;
		case "IT004":
			// set fileName
			fileName = URLEncoder.encode("Template-it004".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it004Service.downloadTemplate();
			break;
		case "IT005":
			// set fileName
			fileName = URLEncoder.encode("Template-it005".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it005Service.downloadTemplate();
			break;
		case "IT008":
			// set fileName
			fileName = URLEncoder.encode("Template-it008".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it008Service.downloadTemplate();
			break;
		case "IT009":
			// set fileName
			fileName = URLEncoder.encode("Template-it009".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it008Service.downloadTemplate();
			break;
		case "IT0101":
			// set fileName
			fileName = URLEncoder.encode("Template-it0101".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it0101Service.downloadTemplate();
			break;
		case "IT0102":
			// set fileName
			fileName = URLEncoder.encode("Template-it0102".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it0102Service.downloadTemplate();
			break;
		case "IT0103":
			// set fileName
			fileName = URLEncoder.encode("Template-it0103".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it0103Service.downloadTemplate();
			break;
		case "IT0104":
			// set fileName
			fileName = URLEncoder.encode("Template-it0104".concat("-").concat(periodMonth), "UTF-8");
			// write it as an excel attachment
			outByteStream = it0104Service.downloadTemplate();
			break;
		default:
			break;
		}
		
		byte[] outArray = outByteStream.toByteArray();
		response.setContentType("application/octet-stream") ;
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
		OutputStream outStream = response.getOutputStream();
		outStream.write(outArray);
		outStream.flush();
		outStream.close();
	}
}
