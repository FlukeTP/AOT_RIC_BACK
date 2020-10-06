
package aot.util.sap.domain.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SapREdata {

    @SerializedName("BUKRS")
    @Expose
    private String companyCode;
    
    @SerializedName("RECNNR")
    @Expose
    private String contractNo;
    
    @SerializedName("RESRTMRPTERMNO")
    @Expose
    private String termNumber;

    @SerializedName("RESRRHYTHMTYPE")
    @Expose
    private String typeFrequency;

    @SerializedName("RESRRPFROM")
    @Expose
    private String reportFrom;

    @SerializedName("RESRRPTO")
    @Expose
    private String reportTo;

    @SerializedName("RESRRPVERSION")
    @Expose
    private String versionReport;

    @SerializedName("RESRNOSALES")
    @Expose
    private String saleReport;

    @SerializedName("RESRQUANTITY")
    @Expose
    private String reportUnit;
    
    @SerializedName("RESRSALESNET_BCA")
    @Expose
    
    private String reportNet;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(String termNumber) {
		this.termNumber = termNumber;
	}

	public String getTypeFrequency() {
		return typeFrequency;
	}

	public void setTypeFrequency(String typeFrequency) {
		this.typeFrequency = typeFrequency;
	}

	public String getReportFrom() {
		return reportFrom;
	}

	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

	public String getVersionReport() {
		return versionReport;
	}

	public void setVersionReport(String versionReport) {
		this.versionReport = versionReport;
	}

	public String getSaleReport() {
		return saleReport;
	}

	public void setSaleReport(String saleReport) {
		this.saleReport = saleReport;
	}

	public String getReportUnit() {
		return reportUnit;
	}

	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}

	public String getReportNet() {
		return reportNet;
	}

	public void setReportNet(String reportNet) {
		this.reportNet = reportNet;
	}
}
