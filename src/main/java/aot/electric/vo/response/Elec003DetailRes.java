package aot.electric.vo.response;

import java.util.ArrayList;
import java.util.List;

import aot.electric.model.RicElectricRateCharge;
import aot.electric.model.RicElectricReq;

public class Elec003DetailRes extends RicElectricReq{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5640027500556077624L;
	private String bankExpStr;
	private String requestStartDateStr;
	private String requestEndDateStr;
	private List<RicElectricRateCharge> rateCharge = new ArrayList<RicElectricRateCharge>();

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

	public List<RicElectricRateCharge> getRateCharge() {
		return rateCharge;
	}

	public void setRateCharge(List<RicElectricRateCharge> rateCharge) {
		this.rateCharge = rateCharge;
	}
}
