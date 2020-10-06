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
import aot.communicate.model.RicCommunicateChangeAirlineLogoChargeRatesConfig;
import aot.communicate.repository.Communicate0062Dao;
import aot.communicate.repository.jpa.RicCommunicateChangeAirlineLogoChargeRatesConfigRepository;
import aot.communicate.vo.request.Communicate0062Req;
import aot.communicate.vo.response.Communicate0062Res;
import aot.it.service.It0105Service;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate0062Service {

	private static final Logger logger = LoggerFactory.getLogger(It0105Service.class);

	@Autowired
	private RicCommunicateChangeAirlineLogoChargeRatesConfigRepository ricCommuChangeAirlineLogoChargeRatesConfigRepository;

	@Autowired
	private Communicate0062Dao communi0062Dao;

	public List<Communicate0062Res> getListAll(Communicate0062Req request) throws Exception {

		logger.info("getListAll");

		List<Communicate0062Res> list = new ArrayList<>();
		try {
			list = communi0062Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public Communicate0062Res findById(Communicate0062Req request) throws Exception {

		logger.info("findById");
		RicCommunicateChangeAirlineLogoChargeRatesConfig data = null;
		Communicate0062Res res = new Communicate0062Res();
		try {
			data = ricCommuChangeAirlineLogoChargeRatesConfigRepository.findById(Long.valueOf(request.getCommuLogoConfigId())).get();
			res.setCommuLogoConfigId(data.getCommuLogoConfigId());
			res.setEffectiveDate(ConvertDateUtils.formatDateToString(data.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			res.setServiceType(data.getServiceType());
			res.setChargeRate(data.getChargeRate());
			res.setRemark(data.getRemark());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Communicate0062Req request) throws Exception {
		logger.info("save");

		RicCommunicateChangeAirlineLogoChargeRatesConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getCommuLogoConfigId())) {
				data = ricCommuChangeAirlineLogoChargeRatesConfigRepository.findById(Long.valueOf(request.getCommuLogoConfigId()))
						.get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicCommunicateChangeAirlineLogoChargeRatesConfig();
				data.setCreatedBy(UserLoginUtils.getCurrentUsername());
				data.setCreatedDate(new Date());
				data.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			data.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setServiceType(request.getServiceType());
			data.setChargeRate(new BigDecimal(request.getChargeRate()));
			data.setRemark(request.getRemark());
			// save data
			ricCommuChangeAirlineLogoChargeRatesConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}
}
