package aot.posControl.vo.response;

import java.util.List;

import aot.posControl.model.RicPosFrequencyReport;

public class PosConUserDetailResponse {
	private String companyDesc;
	private String companyCode;
	private String contract;
	private List<RicPosFrequencyReport> frequencyReport;
	
	public String getCompanyDesc() {
		return companyDesc;
	}
	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public List<RicPosFrequencyReport> getFrequencyReport() {
		return frequencyReport;
	}
	public void setFrequencyReport(List<RicPosFrequencyReport> frequencyReport) {
		this.frequencyReport = frequencyReport;
	}
	
}
