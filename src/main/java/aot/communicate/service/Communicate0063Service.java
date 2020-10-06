package aot.communicate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.communicate.model.RicCommunicateFlightInfoChargeRatesConfig;
import aot.communicate.repository.Communicate0063Dao;
import aot.communicate.repository.jpa.RicCommunicateFlightInfoChargeRatesConfigRepository;
import aot.communicate.vo.request.Communicate0063Req;
import aot.communicate.vo.response.Communicate0063Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate0063Service {

	private static final Logger logger = LoggerFactory.getLogger(Communicate0063Service.class);

	@Autowired
	private RicCommunicateFlightInfoChargeRatesConfigRepository ricCommuFlightInfoChargeRatesConfigRepository;

	@Autowired
	private Communicate0063Dao communi0063Dao;

	public List<Communicate0063Res> getListAll(Communicate0063Req request) throws Exception {

		logger.info("getListAll");

		List<Communicate0063Res> list = new ArrayList<>();
		try {
			list = communi0063Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public Communicate0063Res getEffectiveDate(Communicate0063Req request) throws Exception {

		logger.info("getListAll");

		Communicate0063Res data = new Communicate0063Res();
		try {
			data = communi0063Dao.findEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return data;
	}

	public Communicate0063Res findById(Communicate0063Req request) throws Exception {

		logger.info("findById");
		RicCommunicateFlightInfoChargeRatesConfig data = null;
		Communicate0063Res res = new Communicate0063Res();
		try {
			data = ricCommuFlightInfoChargeRatesConfigRepository
					.findById(Long.valueOf(request.getCommuFlightInfoConfigId())).get();
			res.setCommuFlightInfoConfigId(data.getCommuFlightInfoConfigId());
			res.setEffectiveDate(ConvertDateUtils.formatDateToString(data.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setServiceType(data.getServiceType());
			res.setChargeRateName(data.getChargeRateName());
			res.setChargeRate(data.getChargeRate());
			res.setInsuranceFee(data.getInsuranceFee());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Communicate0063Req request) throws Exception {
		logger.info("save");

		RicCommunicateFlightInfoChargeRatesConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getCommuFlightInfoConfigId())) {
				data = ricCommuFlightInfoChargeRatesConfigRepository
						.findById(Long.valueOf(request.getCommuFlightInfoConfigId())).get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicCommunicateFlightInfoChargeRatesConfig();
				data.setCreatedBy(UserLoginUtils.getCurrentUsername());
				data.setCreatedDate(new Date());
				data.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			data.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setServiceType(request.getServiceType());
			data.setChargeRateName(request.getChargeRateName());
			data.setChargeRate(new BigDecimal(request.getChargeRate()));
			data.setInsuranceFee(new BigDecimal(request.getInsuranceFee()));
			data.setRemark(request.getRemark());
			// save data
			ricCommuFlightInfoChargeRatesConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}

}
