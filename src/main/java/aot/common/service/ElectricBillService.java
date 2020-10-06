package aot.common.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.ElectricConstants;
import aot.common.constant.LovConstants;
import aot.common.constant.RICConstants;
import aot.common.vo.request.ElectricBillReq;
import aot.common.vo.response.ElectricBillRes;
import aot.electric.model.RicElectricReq;
import aot.electric.model.SystemCalConfig;
import aot.electric.repository.Electric001Dao;
import aot.electric.repository.jpa.RicElectricReqRepository;
import baiwa.module.service.FwLovDetailService;
import baiwa.module.service.SysConstantService;
import baiwa.module.vo.request.FwLovDetailReq;
import baiwa.util.NumberUtils;

@Service
public class ElectricBillService {

	@Autowired
	private SysConstantService sysConstantService;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Autowired
	private FwLovDetailService fwLovDetailService;
	
	@Autowired
	private Electric001Dao electric001Dao;

	public ElectricBillRes calculate(ElectricBillReq request) throws Exception {
		ElectricBillRes response = new ElectricBillRes();

		/* _________ initial variables _________ */
		response.setTotalAmount(BigDecimal.ZERO);
		response.setServiceValue(BigDecimal.ZERO);
		response.setVat(BigDecimal.ZERO);
		response.setBaseValue(BigDecimal.ZERO);
		response.setFtValue(BigDecimal.ZERO);
		response.setEnergyValue(BigDecimal.ZERO);
		BigDecimal energyValue = BigDecimal.ZERO;
		BigDecimal amountMeter = null;
		BigDecimal FtValue = BigDecimal.ZERO;
		if (request.getCurrentAmount() != null) {
			amountMeter = request.getCurrentAmount();
		} else {
			amountMeter = request.getCurrentMeterValue().subtract(request.getBackwardMeterValue());
			if (amountMeter.signum() < 0) {
				amountMeter = BigDecimal.ZERO;
			}
		}
		
		/* _________ find Ft _________ */
		List<SystemCalConfig> FtRes = electric001Dao.findFtConfig();
		if(FtRes.size() > 0) {
			FtValue = NumberUtils.roundUpTwoDigit(FtRes.get(0).getValue());
		}

		/* _________ (amountMeter <= 50) --> FREE _________ */
		if (amountMeter.compareTo(NumberUtils.roundUpTwoDigit(50)) > 0) {
			switch (request.getType()) {
			case "1":
				// อัตราปกติปริมาณการใช้พลังงานไฟฟ้าไม่เกิน 150 หน่วยต่อเดือน
				if (amountMeter.compareTo(new BigDecimal(150)) <= 0) {

					if (amountMeter.compareTo(new BigDecimal(1)) >= 0
							&& amountMeter.compareTo(new BigDecimal(15)) > 0) {
						// 15 หน่วย (กิโลวัตต์ชั่วโมง) แรก (หน่วยที่ 1 – 15) หน่วยละ 2.3488 บาท
						energyValue = energyValue.add((new BigDecimal(2.3488).multiply(new BigDecimal(15))));

						if (amountMeter.compareTo(new BigDecimal(15)) > 0
								&& amountMeter.compareTo(new BigDecimal(25)) > 0) {
							// 10 หน่วยต่อไป (หน่วยที่ 16 – 25) หน่วยละ 2.9882 บาท
							energyValue = energyValue.add((new BigDecimal(2.9882).multiply(new BigDecimal(10))));

							if (amountMeter.compareTo(new BigDecimal(25)) > 0
									&& amountMeter.compareTo(new BigDecimal(35)) > 0) {
								// 10 หน่วยต่อไป (หน่วยที่ 26 – 35) หน่วยละ 3.2405 บาท
								energyValue = energyValue.add((new BigDecimal(3.2405).multiply(new BigDecimal(10))));

								if (amountMeter.compareTo(new BigDecimal(35)) > 0
										&& amountMeter.compareTo(new BigDecimal(100)) > 0) {
									// 65 หน่วยต่อไป (หน่วยที่ 36 – 100) หน่วยละ 3.6237 บาท
									energyValue = energyValue
											.add((new BigDecimal(3.6237).multiply(new BigDecimal(65))));

									if (amountMeter.compareTo(new BigDecimal(100)) > 0
											&& amountMeter.compareTo(new BigDecimal(150)) > 0) {
										// 50 หน่วยต่อไป (หน่วยที่ 101 – 150) หน่วยละ 3.7171 บาท
										energyValue = energyValue
												.add((new BigDecimal(3.7171).multiply(new BigDecimal(50))));

										if (amountMeter.compareTo(new BigDecimal(150)) > 0
												&& amountMeter.compareTo(new BigDecimal(400)) > 0) {
											// 250 หน่วยต่อไป (หน่วยที่ 151 – 400) หน่วยละ 4.2218 บาท
											energyValue = energyValue
													.add((new BigDecimal(4.2218).multiply(new BigDecimal(250))));

											if (amountMeter.compareTo(new BigDecimal(400)) > 0) {
												// เกินกว่า 400 หน่วย (หน่วยที่ 401 เป็นต้นไป) หน่วยละ 4.4217 บาท
												energyValue = energyValue.add((new BigDecimal(4.4217)
														.multiply(amountMeter.subtract(new BigDecimal(400)))));
											}
										} else {
											energyValue = energyValue.add((new BigDecimal(4.2218)
													.multiply(amountMeter.subtract(new BigDecimal(150)))));
										}
									} else {
										energyValue = energyValue.add((new BigDecimal(3.7171)
												.multiply(amountMeter.subtract(new BigDecimal(100)))));
									}
								} else {
									energyValue = energyValue.add((new BigDecimal(3.6237)
											.multiply(amountMeter.subtract(new BigDecimal(35)))));
								}
							} else {
								energyValue = energyValue.add(
										(new BigDecimal(3.2405).multiply(amountMeter.subtract(new BigDecimal(25)))));
							}

						} else {
							energyValue = energyValue
									.add((new BigDecimal(2.9882).multiply(amountMeter.subtract(new BigDecimal(15)))));
						}

					} else {
						energyValue = energyValue.add((new BigDecimal(2.3488).multiply(amountMeter)));
					}

				} else if (amountMeter.compareTo(new BigDecimal(150)) > 0) {
					// อัตราปกติปริมาณการใช้พลังงานไฟฟ้าเกินกว่า 150 หน่วยต่อเดือน

					if (amountMeter.compareTo(new BigDecimal(1)) >= 0
							&& amountMeter.compareTo(new BigDecimal(150)) > 0) {
						// 15 หน่วย (กิโลวัตต์ชั่วโมง) แรก (หน่วยที่ 1 – 15) หน่วยละ 2.3488 บาท
						energyValue = energyValue.add((new BigDecimal(3.2484).multiply(new BigDecimal(150))));

						if (amountMeter.compareTo(new BigDecimal(150)) > 0
								&& amountMeter.compareTo(new BigDecimal(400)) > 0) {
							energyValue = energyValue.add((new BigDecimal(4.2218).multiply(new BigDecimal(250))));
							if (amountMeter.compareTo(new BigDecimal(400)) > 0
									&& amountMeter.compareTo(new BigDecimal(400)) > 0) {
								energyValue = energyValue.add(
										(new BigDecimal(4.4217).multiply(amountMeter.subtract(new BigDecimal(400)))));
							}
						} else {
							energyValue = energyValue
									.add((new BigDecimal(4.2218).multiply(amountMeter.subtract(new BigDecimal(250)))));
						}
					} else {
						energyValue = energyValue
								.add((new BigDecimal(3.2484).multiply(amountMeter.subtract(new BigDecimal(150)))));
					}
				}

				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			case "6":
				break;
			case "7":
				break;
			}
			// ค่าพลังงานไฟฟ้า
			response.setEnergyValue(NumberUtils.roundUpTwoDigit(energyValue));

			// จำนวนพลังงานไฟฟ้า x ค่า Ft(หน่วย/สตางค์) // (-11.60*amountMeter)/100 -> Bath
			response.setFtValue(amountMeter.multiply(FtValue).multiply(NumberUtils.roundUpTwoDigit(0.01)));

			// ค่า service (ค่าพลังงานไฟฟ้า * electric_voltage_type)
			if (StringUtils.isNotBlank(request.getSerialNoMeter())) {
				FwLovDetailReq fwLovDetailReq = new FwLovDetailReq();
				fwLovDetailReq.setLovKey(LovConstants.ELECTRIC_VOLTAGE_TYPE);
				RicElectricReq reqRes = ricElectricReqRepository.findBySerialNo(request.getSerialNoMeter());
				if (ElectricConstants.CUSTOMER_TYPE.equals(reqRes.getCustomerType())) {
					if (ElectricConstants.VOLTAGE_LOW.equals(reqRes.getElectricVoltageType().trim())) {
						fwLovDetailReq.setLovCode(LovConstants.Lov_Code[1]);
					} else if (ElectricConstants.VOLTAGE_HIGH.equals(reqRes.getElectricVoltageType())) {
						fwLovDetailReq.setLovCode(LovConstants.Lov_Code[0]);
					} else {
						throw new Exception();
					}
				} else if (ElectricConstants.EMPLOYEE_TYPE.equals(reqRes.getCustomerType().trim())) {
					fwLovDetailReq.setLovCode(LovConstants.Lov_Code[1]);
				} else {
					throw new Exception();
				}
				response.setServiceValue(response.getEnergyValue().multiply(
						NumberUtils.roundUpTwoDigit(fwLovDetailService.findByLovCode(fwLovDetailReq).getDescTh2())));
			}

			// ค่าพลังงานไฟฟ้า + service = ค่าไฟฟ้าฐาน
			response.setBaseValue(NumberUtils.roundUpTwoDigit(energyValue.add(response.getServiceValue())));

			// (ค่าไฟฟ้าฐาน + ค่า Ft) x 7/100
			response.setVat((response.getBaseValue().add(response.getFtValue()))
					.multiply(new BigDecimal(sysConstantService.getConstantByKey(RICConstants.VAT).getConstantValue()))
					.setScale(2, BigDecimal.ROUND_HALF_EVEN));

			// ค่าไฟฟ้าฐาน + ค่าไฟฟ้าผันแปร (ft) + ภาษีมูลค่าเพิ่ม (vat) = ค่าไฟฟ้า
			response.setTotalAmount(NumberUtils
					.roundUpTwoDigit(response.getBaseValue().add(response.getFtValue()).add(response.getVat())));

			// cal percent
			if (request.getBackwardAmount().longValue() > 0) {
				response.setCalPercent(NumberUtils
						.roundUpTwoDigit((amountMeter.longValue() * 100L) / request.getBackwardAmount().longValue()));
			} else {
				response.setCalPercent(NumberUtils.roundUpTwoDigit(0));
			}
		}

		response.setAmountMeter(amountMeter);
		return response;
	}
}
