package aot.water.vo.response;

import java.util.ArrayList;
import java.util.List;

import aot.water.model.RicWaterRateCharge;
import aot.water.model.RicWaterReq;

public class Water003DetailRes extends RicWaterReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4033135815957500013L;
	private String bankExpStr;
	private String requestStartDateStr;
	private String requestEndDateStr;
	private List<RicWaterRateCharge> rateCharge = new ArrayList<RicWaterRateCharge>();

	public String getBankExpStr() {
		return bankExpStr;
	}

	public void setBankExpStr(String bankExpStr) {
		this.bankExpStr = bankExpStr;
	}

	public String getRequestStartDateStr() {
		return requestStartDateStr;
	}

	public void setRequestStartDateStr(String requestStartDateStr) {
		this.requestStartDateStr = requestStartDateStr;
	}

	public String getRequestEndDateStr() {
		return requestEndDateStr;
	}

	public void setRequestEndDateStr(String requestEndDateStr) {
		this.requestEndDateStr = requestEndDateStr;
	}

	public List<RicWaterRateCharge> getRateCharge() {
		return rateCharge;
	}

	public void setRateCharge(List<RicWaterRateCharge> rateCharge) {
		this.rateCharge = rateCharge;
	}

}
