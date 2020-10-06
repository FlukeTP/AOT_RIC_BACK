package aot.util.sap.domain.response;

public class SAPARResponseSuccess {
	private String TRANSNO;
	private String COMP;
	private String DOCNO;
	private String YEAR;

	public String getTRANSNO() {
		return TRANSNO;
	}

	public void setTRANSNO(String tRANSNO) {
		TRANSNO = tRANSNO;
	}

	public String getCOMP() {
		return COMP;
	}

	public void setCOMP(String cOMP) {
		COMP = cOMP;
	}

	public String getDOCNO() {
		return DOCNO;
	}

	public void setDOCNO(String dOCNO) {
		DOCNO = dOCNO;
	}

	public String getYEAR() {
		return YEAR;
	}

	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

}