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
import aot.communicate.model.RicCommunicateHandheldTransceiverChargeRatesConfig;
import aot.communicate.repository.Communicate0061Dao;
import aot.communicate.repository.jpa.RicCommunicateHandheldTransceiverChargeRatesConfigRepository;
import aot.communicate.vo.request.Communicate0061Req;
import aot.communicate.vo.response.Communicate0061Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Communicate0061Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Communicate0061Service.class);

	@Autowired
	private RicCommunicateHandheldTransceiverChargeRatesConfigRepository ricCommuHandheldTransceiverChargeRatesConfigRepository;

	@Autowired
	private Communicate0061Dao communi0061Dao;

	public List<Communicate0061Res> getListAll(Communicate0061Req request) throws Exception {

		logger.info("getListAll");

		List<Communicate0061Res> list = new ArrayList<>();
		try {
			list = communi0061Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public Communicate0061Res findById(Communicate0061Req request) throws Exception {

		logger.info("findById");
		RicCommunicateHandheldTransceiverChargeRatesConfig data = null;
		Communicate0061Res res = new Communicate0061Res();
		try {
			data = ricCommuHandheldTransceiverChargeRatesConfigRepository.findById(Long.valueOf(request.getCommuTransceiverConfigId())).get();
			res.setCommuTransceiverConfigId(data.getCommuTransceiverConfigId());
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
	public void save(Communicate0061Req request) throws Exception {
		logger.info("save");

		RicCommunicateHandheldTransceiverChargeRatesConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getCommuTransceiverConfigId())) {
				data = ricCommuHandheldTransceiverChargeRatesConfigRepository.findById(Long.valueOf(request.getCommuTransceiverConfigId()))
						.get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicCommunicateHandheldTransceiverChargeRatesConfig();
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
			ricCommuHandheldTransceiverChargeRatesConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}

}
