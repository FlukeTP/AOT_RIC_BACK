package aot.it.service;

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
import aot.it.model.RicItStaffPagePublicPageConfig;
import aot.it.repository.It0105Dao;
import aot.it.repository.jpa.RicItStaffPagePublicPageConfigRepository;
import aot.it.vo.request.It0105Req;
import aot.it.vo.response.It0105Res;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It0105Service {

	private static final Logger logger = LoggerFactory.getLogger(It0105Service.class);

	@Autowired
	private RicItStaffPagePublicPageConfigRepository ricItStaffPagePublicPageConfigRepository;

	@Autowired
	private It0105Dao it0105Dao;

	public List<It0105Res> getListAll(It0105Req request) throws Exception {

		logger.info("getListAll");

		List<It0105Res> list = new ArrayList<>();
		try {
			list = it0105Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

		return list;
	}

	public It0105Res findById(It0105Req request) throws Exception {

		logger.info("findById");
		RicItStaffPagePublicPageConfig data = null;
		It0105Res res = new It0105Res();
		try {
			data = ricItStaffPagePublicPageConfigRepository.findById(Long.valueOf(request.getItPageConfigId())).get();
			res.setItPageConfigId(data.getItPageConfigId());
			res.setAnnual(data.getAnnual());
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
	public void save(It0105Req request) throws Exception {
		logger.info("save");

		RicItStaffPagePublicPageConfig data = null;
		try {
			if (StringUtils.isNotEmpty(request.getItPageConfigId())) {
				data = ricItStaffPagePublicPageConfigRepository.findById(Long.valueOf(request.getItPageConfigId()))
						.get();
				// set data
				data.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				data.setUpdatedDate(new Date());
			} else {
				data = new RicItStaffPagePublicPageConfig();
				data.setCreatedBy(UserLoginUtils.getCurrentUsername());
				data.setCreatedDate(new Date());
				data.setIsDelete(RICConstants.STATUS.NO);
			}
			// set data
			data.setAnnual(request.getAnnual());
			data.setEffectiveDate(ConvertDateUtils.parseStringToDate(request.getEffectiveDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			data.setServiceType(request.getServiceType());
			data.setChargeRate(new BigDecimal(request.getChargeRate()));
			data.setRemark(request.getRemark());
			// save data
			ricItStaffPagePublicPageConfigRepository.save(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			throw e;
		}

	}
}
