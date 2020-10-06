package aot.communicate.vo.request;

public class Communicate007Req {
	private Long id;
	private String entreprenuerCode;
	private String entreprenuerName;
	private String periodMonth;
	private String contractNo;
	private String transactionReq;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntreprenuerName() {
		return entreprenuerName;
	}

	public void setEntreprenuerName(String entreprenuerName) {
		this.entreprenuerName = entreprenuerName;
	}

	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getEntreprenuerCode() {
		return entreprenuerCode;
	}

	public void setEntreprenuerCode(String entreprenuerCode) {
		this.entreprenuerCode = entreprenuerCode;
	}

	public String getTransactionReq() {
		return transactionReq;
	}

	public void setTransactionReq(String transactionReq) {
		this.transactionReq = transactionReq;
	}
	
	
}
