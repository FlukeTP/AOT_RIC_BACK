package aot.posControl.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.posControl.constant.PosConConstants;
import aot.posControl.model.RicPosFrequencyReport;
import aot.posControl.repository.PosConRepository;
import aot.posControl.repository.RicPosFrequencyReportRepoitory;
import aot.posControl.vo.request.PosConUserDetailRequest;
import aot.posControl.vo.response.PosConUserDetailResponse;
import baiwa.constant.CommonConstants;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class PosConService {
	
	private static final Logger logger = LoggerFactory.getLogger(PosConService.class);
	
	@Autowired
	private PosConRepository posConRepository ;
	
	@Autowired
	private RicPosFrequencyReportRepoitory ricPosFrequencyReportRepoitory;
	
	
	public PosConUserDetailResponse getuserDetail(PosConUserDetailRequest req) throws Exception {
		PosConUserDetailResponse userRes  = new PosConUserDetailResponse();
		List<RicPosFrequencyReport> frqList = posConRepository.getFreqReport(req);
		if(frqList.size()== 0) {
			List<HashMap<String, String>> freq = posConRepository.getFrequency(req);
			Date startDate, endDate;
			int fequency = 0;
			String customerBranch = "", customerName = "", customerCode = "", saleType = "", saleTypeName = "", contractNo = "", reportingRule = "";
			for(HashMap<String, String> item : freq) {
				userRes.setCompanyCode(item.get("customer_code"));
				userRes.setCompanyDesc(item.get("customer_name"));
				userRes.setContract(item.get("contract_no"));
				
				startDate = ConvertDateUtils.parseStringToDate(item.get("frequency_start"), ConvertDateUtils.YYYY_MM_DD , ConvertDateUtils.LOCAL_EN );
				endDate = ConvertDateUtils.parseStringToDate(item.get("frequency_end"), ConvertDateUtils.YYYY_MM_DD , ConvertDateUtils.LOCAL_EN );
				fequency = Integer.parseInt(item.get("frequency"));	
				customerBranch = item.get("customer_branch");
				customerName = item.get("customer_name");
				customerCode = item.get("customer_code");
				saleType = item.get("sales_type");
				saleTypeName = item.get("sales_type_name");
				contractNo = item.get("contract_no");
				reportingRule = item.get("reporting_rule_no");
				if(PosConConstants.FREQUENCY_UNIT.MONTH.equals(item.get("frequency_unit"))) {
					this.insertFeqMonth(startDate, endDate, contractNo, fequency, customerCode, customerName, customerBranch, saleType, saleTypeName, reportingRule);
				}else {
					this.insertFeqDay(startDate, endDate, contractNo, fequency, customerCode, customerName, customerBranch, saleType, saleTypeName, reportingRule);
				}
			}
			frqList = posConRepository.getFreqReport(req);
		}else {
			List<HashMap<String, String>> freq = posConRepository.getFrequency(req);
			for(HashMap<String, String> item : freq) {
				userRes.setCompanyCode(item.get("customer_code"));
				userRes.setCompanyDesc(item.get("customer_name"));
				userRes.setContract(item.get("contract_no"));
			}
		}
		userRes.setFrequencyReport(frqList);
		return userRes;
	}
	private void insertFeqMonth(Date freqStartDate, Date freqEndDate, String contractNo, int fequency, String customerCode, String customerName, String customerBranch, String saleType, String saleTypeName, String reportingRuleNo) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
		cal.setTime(freqStartDate);
		RicPosFrequencyReport entity = null;
		String username = UserLoginUtils.getCurrentUsername();
		
		Date endDate = null, startDate = freqStartDate;
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = cal.getTime();
		while((endDate.compareTo(freqEndDate) < 0 || endDate.compareTo(freqEndDate) == 0)) {	
			entity = new RicPosFrequencyReport();
			entity.setContractNo(contractNo);
			entity.setCreatedBy(username);
			entity.setCreatedDate(new Date());
			entity.setCustomerBranch(customerBranch);
			entity.setCustomerCode(customerCode);
			entity.setCustomerName(customerName);
			entity.setEndDate(endDate);
			entity.setIsDelete(CommonConstants.FLAG.N_FLAG);
			entity.setSaleType(saleType);
			entity.setSaleTypeName(saleTypeName);
			entity.setStartDate(startDate);
			entity.setReportingRuleNo(reportingRuleNo);
			ricPosFrequencyReportRepoitory.save(entity);
			
			cal.add(Calendar.DATE, 1);
			
			startDate = cal.getTime();
			
			cal.add(Calendar.MONTH, fequency-1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate =  cal.getTime();
		}

	}
	private void insertFeqDay(Date freqStartDate, Date freqEndDate, String contractNo, int fequency, String customerCode, String customerName, String customerBranch, String saleType, String saleTypeName, String reportingRuleNo) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
		cal.setTime(freqStartDate);
		RicPosFrequencyReport entity = null;
		String username = UserLoginUtils.getCurrentUsername();
		
		Date endDate = null, startDate = freqStartDate;
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = cal.getTime();
		while((endDate.compareTo(freqEndDate) < 0 || endDate.compareTo(freqEndDate) == 0 )) {	
			entity = new RicPosFrequencyReport();
			entity.setContractNo(contractNo);
			entity.setCreatedBy(username);
			entity.setCreatedDate(new Date());
			entity.setCustomerBranch(customerBranch);
			entity.setCustomerCode(customerCode);
			entity.setCustomerName(customerName);
			entity.setEndDate(endDate);
			entity.setIsDelete(CommonConstants.FLAG.N_FLAG);
			entity.setSaleType(saleType);
			entity.setSaleTypeName(saleTypeName);
			entity.setStartDate(startDate);
			entity.setReportingRuleNo(reportingRuleNo);
			ricPosFrequencyReportRepoitory.save(entity);
			
			cal.add(Calendar.DATE, 1);
			
			startDate = cal.getTime();
			
			cal.add(Calendar.DATE, fequency);
			endDate =  cal.getTime();
		}
	}
	
	
//	public static void main(String args[]) throws ParseException {
//		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
//		cal.setTime(new Date());
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int day = cal.getActualMaximum(Calendar.DATE);
//		if()
//		System.out.println("day " + cal.getTime());
//		System.out.println("month " + month);
//		System.out.println("year " + year);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = sdf.parse("2009-12-31");
//        Date date2 = sdf.parse("2010-01-31");
//
//        System.out.println("date1 : " + sdf.format(date1));
//        System.out.println("date2 : " + sdf.format(date2));
//
//        if (date1.compareTo(date2) > 0) {
//            System.out.println("Date1 is after Date2");
//        } else if (date1.compareTo(date2) < 0) {
//            System.out.println("Date1 is before Date2");
//        } else if (date1.compareTo(date2) == 0) {
//            System.out.println("Date1 is equal to Date2");
//        } else {
//            System.out.println("How to get here?");
//        }
//	}
	
}
