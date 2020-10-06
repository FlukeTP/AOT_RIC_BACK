package aot.it.vo.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import aot.it.model.RicItDedicatedCUTECreateInvoiceMapping;

public class It008Res {
	
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String entreprenuerBranch;
	private String contractNo;
	private String location;
	private String contractData;
	private String requestStartDate;
	private String requestEndDate;
	private BigDecimal totalAmount; 
	private List<RicItDedicatedCUTECreateInvoiceMapping> detailInfo = new ArrayList<RicItDedicatedCUTECreateInvoiceMapping>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}
	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}
	public String getEntreprenuerName() {
		return entreprenuerName;
	}
	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}
	public String getEntreprenuerBranch() {
		return entreprenuerBranch;
	}
	public void setEntreprenuerBranch(String entreprenuerBranch) {
		this.entreprenuerBranch = entreprenuerBranch;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContractData() {
		return contractData;
	}
	public void setContractData(String contractData) {
		this.contractData = contractData;
	}
	public String getRequestStartDate() {
		return requestStartDate;
	}
	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}
	public String getRequestEndDate() {
		return requestEndDate;
	}
	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<RicItDedicatedCUTECreateInvoiceMapping> getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(List<RicItDedicatedCUTECreateInvoiceMapping> detailInfo) {
		this.detailInfo = detailInfo;
	}
	
	
}
