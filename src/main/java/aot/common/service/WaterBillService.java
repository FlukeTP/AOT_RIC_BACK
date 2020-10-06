package aot.common.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.common.constant.WaterConstants;
import aot.common.vo.request.WaterBillReq;
import aot.common.vo.response.WaterBillRes;
import baiwa.module.service.SysConstantService;
import baiwa.util.NumberUtils;

@Service
public class WaterBillService {
	@Autowired
	private SysConstantService sysConstantService;

	public WaterBillRes calculate(WaterBillReq request) {
		WaterBillRes response = new WaterBillRes();
		/* _________ initial variables _________ */
		response.setTotalAmount(BigDecimal.ZERO);
		response.setServiceValue(BigDecimal.ZERO);
		response.setVat(BigDecimal.ZERO);
		response.setTotalChargeRates(BigDecimal.ZERO);
		BigDecimal baseValue = BigDecimal.ZERO;
		BigDecimal amountMeter = BigDecimal.ZERO;
		if (request.getCurrentAmount() != null) {
			amountMeter = request.getCurrentAmount();
		} else {
			amountMeter = request.getCurrentMeterValue().subtract(request.getBackwardMeterValue());
			if (amountMeter.signum() < 0) {
				amountMeter = BigDecimal.ZERO;
			}
		}
		if(request.getServiceValue() == null) {
			request.setServiceValue(BigDecimal.ZERO);
		}

		if (amountMeter.compareTo(BigDecimal.ZERO) > 0) {
			if (amountMeter.compareTo(new BigDecimal(0)) > 0 && amountMeter.compareTo(new BigDecimal(30)) > 0) {
				baseValue = baseValue.add((new BigDecimal(8.50).multiply(new BigDecimal(30))));

				if (amountMeter.compareTo(new BigDecimal(30)) > 0 && amountMeter.compareTo(new BigDecimal(40)) > 0) {
					baseValue = baseValue.add((new BigDecimal(10.03).multiply(new BigDecimal(40))));

					if (amountMeter.compareTo(new BigDecimal(40)) > 0
							&& amountMeter.compareTo(new BigDecimal(50)) > 0) {
						baseValue = baseValue.add((new BigDecimal(10.35).multiply(new BigDecimal(50))));

						if (amountMeter.compareTo(new BigDecimal(50)) > 0
								&& amountMeter.compareTo(new BigDecimal(60)) > 0) {
							baseValue = baseValue.add((new BigDecimal(10.68).multiply(new BigDecimal(60))));

							if (amountMeter.compareTo(new BigDecimal(60)) > 0
									&& amountMeter.compareTo(new BigDecimal(70)) > 0) {
								baseValue = baseValue.add((new BigDecimal(11).multiply(new BigDecimal(70))));

								if (amountMeter.compareTo(new BigDecimal(70)) > 0
										&& amountMeter.compareTo(new BigDecimal(80)) > 0) {
									baseValue = baseValue.add((new BigDecimal(11.33).multiply(new BigDecimal(80))));

									if (amountMeter.compareTo(new BigDecimal(80)) > 0) {
										baseValue = baseValue.add((new BigDecimal(12.50)
												.multiply(amountMeter.subtract(new BigDecimal(80)))));
									}
								} else {
									baseValue = baseValue.add(
											(new BigDecimal(11.33).multiply(amountMeter.subtract(new BigDecimal(70)))));
								}
							} else {
								baseValue = baseValue
										.add((new BigDecimal(11).multiply(amountMeter.subtract(new BigDecimal(60)))));
							}
						} else {
							baseValue = baseValue
									.add((new BigDecimal(10.68).multiply(amountMeter.subtract(new BigDecimal(50)))));
						}
					} else {
						baseValue = baseValue
								.add((new BigDecimal(10.35).multiply(amountMeter.subtract(new BigDecimal(40)))));
					}

				} else {
					baseValue = baseValue
							.add((new BigDecimal(10.03).multiply(amountMeter.subtract(new BigDecimal(30)))));
				}

			} else {
				baseValue = baseValue.add((new BigDecimal(8.50).multiply(amountMeter)));
			}

			if (WaterConstants.FLAG.X.equals(request.getType())) {
				response.setBaseValue(NumberUtils.roundUpTwoDigit(baseValue));
				response.setTotalChargeRates(response.getTotalChargeRates().add(response.getBaseValue()));
			}
			if (WaterConstants.FLAG.X.equals(request.getType2())) {
				response.setTreatmentFee(amountMeter.multiply(NumberUtils
						.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.TF).getConstantValue())));
				response.setTotalChargeRates(response.getTotalChargeRates().add(response.getTreatmentFee()));
			}
			if (WaterConstants.FLAG.X.equals(request.getType3())) {
				response.setRecycleAmount(amountMeter.multiply(NumberUtils.roundUpTwoDigit(
						sysConstantService.getConstantByKey(WaterConstants.RECYCLE).getConstantValue())));
				response.setTotalChargeRates(response.getTotalChargeRates().add(response.getRecycleAmount()));
			}
			response.setAmountMeter(NumberUtils.roundUpTwoDigit(amountMeter));
			// response.setTotalChargeRates(response.getBaseValue().add(request.getServiceValue().add(response.getTreatmentFee())));
			response.setTotalChargeRates(
					NumberUtils.roundUpTwoDigit(response.getTotalChargeRates().add(request.getServiceValue())));
			response.setVat(NumberUtils.roundUpTwoDigit(response.getTotalChargeRates().multiply(NumberUtils
					.roundUpTwoDigit(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue()))));
			response.setTotalAmount(NumberUtils.roundUpTwoDigit(response.getTotalChargeRates().add(response.getVat())));
		}
		return response;
	}
}
