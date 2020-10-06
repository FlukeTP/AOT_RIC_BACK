package aot.posControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.posControl.repository.ReportingRuleRepository;
import aot.posControl.vo.request.PosConUserDetailRequest;
import aot.posControl.vo.response.ReportingRuleResponse;

@Service
public class ReportingRuleService {
	
	@Autowired
	private ReportingRuleRepository reportingRuleRepository; 
	
	public List<ReportingRuleResponse> getReportingRuleBYcontract(PosConUserDetailRequest req) throws Exception{
		List<ReportingRuleResponse> ls = reportingRuleRepository.reportingRuleBYcontract(req);
		return ls;
		
	}
}
