package aot.garbagedis.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import aot.garbagedis.model.RicTrashSize;
import aot.garbagedis.model.RicTrashSizeServiceFee;
import aot.garbagedis.repository.jpa.RicTrashSizeRepository;
import aot.garbagedis.repository.jpa.RicTrashSizeServiceFeeRepository;
import aot.garbagedis.vo.request.Garbagedis003SaveFeeReq;
import aot.garbagedis.vo.request.Garbagedis003SaveReq;
import baiwa.util.UserLoginUtils;

@Service
public class Garbagedis003Service {

	private static final Logger logger = LoggerFactory.getLogger(Garbagedis003Service.class);

	@Autowired
	private RicTrashSizeRepository ricTrashSizeRepository;

	@Autowired
	private RicTrashSizeServiceFeeRepository ricTrashSizeServiceFeeRepository;

	public List<RicTrashSize> getlistTrashSize() {
		List<RicTrashSize> trashSizes = new ArrayList<RicTrashSize>();
		trashSizes = (List<RicTrashSize>) ricTrashSizeRepository.findAll();
		return trashSizes;
	}

	public List<RicTrashSizeServiceFee> getlistTrashFee() {
		List<RicTrashSizeServiceFee> trashSizeServiceFees = new ArrayList<RicTrashSizeServiceFee>();
		trashSizeServiceFees = (List<RicTrashSizeServiceFee>) ricTrashSizeServiceFeeRepository.findAll();
		return trashSizeServiceFees;
	}

	public void saveTrashSize(Garbagedis003SaveReq request) {
		logger.info("saveTrashSize", request);
		RicTrashSize ricTrashSize = null;
		ricTrashSize = new RicTrashSize();
		// set data
		ricTrashSize.setYearly(request.getYearly());
		BigDecimal TrashSize = new BigDecimal(request.getTrashSize());
		ricTrashSize.setTrashSize(TrashSize);
		BigDecimal ChargeRates = new BigDecimal(request.getChargeRates());
		ricTrashSize.setChargeRates(ChargeRates);
		ricTrashSize.setRemark(request.getRemark());
		ricTrashSize.setCreatedDate(new Date());
		ricTrashSize.setCreatedBy(UserLoginUtils.getCurrentUsername());
		ricTrashSize.setIsDeleted(RICConstants.STATUS.NO);
		// save data
		ricTrashSizeRepository.save(ricTrashSize);
	}

	public void saveTrashFee(Garbagedis003SaveFeeReq request) {
		logger.info("saveTrashFee", request);
		RicTrashSizeServiceFee ricTrashSizeServiceFee = null;
		ricTrashSizeServiceFee = new RicTrashSizeServiceFee();
		// set data
		ricTrashSizeServiceFee.setYearly(request.getYearly());
		ricTrashSizeServiceFee.setTrashType(request.getTrashType());
		BigDecimal TrashSize = new BigDecimal(request.getTrashSize());
		ricTrashSizeServiceFee.setTrashSize(TrashSize);
		BigDecimal ChargeRates = new BigDecimal(request.getChargeRates());
		ricTrashSizeServiceFee.setChargeRates(ChargeRates);
		ricTrashSizeServiceFee.setRemark(request.getRemark());
		ricTrashSizeServiceFee.setCreatedDate(new Date());
		ricTrashSizeServiceFee.setCreatedBy(UserLoginUtils.getCurrentUsername());
		ricTrashSizeServiceFee.setIsDeleted(RICConstants.STATUS.NO);
		// save data
		ricTrashSizeServiceFeeRepository.save(ricTrashSizeServiceFee);
	}

	public void editTrashSize(Garbagedis003SaveReq request) {
		logger.info("editTrashSize", request);
		RicTrashSize ricTrashSize = null;
		ricTrashSize = new RicTrashSize();
		ricTrashSize = ricTrashSizeRepository.findById(request.getTrashSizeId()).get();
		// set data
		ricTrashSize.setYearly(request.getYearly());
		BigDecimal TrashSize = new BigDecimal(request.getTrashSize());
		ricTrashSize.setTrashSize(TrashSize);
		BigDecimal ChargeRates = new BigDecimal(request.getChargeRates());
		ricTrashSize.setChargeRates(ChargeRates);
		ricTrashSize.setRemark(request.getRemark());
		ricTrashSize.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		ricTrashSize.setUpdatedDate(new Date());
		// save data
		ricTrashSizeRepository.save(ricTrashSize);
	}

	public void editTrashfee(Garbagedis003SaveFeeReq request) {
		logger.info("editTrashFee", request);
		RicTrashSizeServiceFee ricTrashSizeServiceFee = null;
		ricTrashSizeServiceFee = new RicTrashSizeServiceFee();
		ricTrashSizeServiceFee = ricTrashSizeServiceFeeRepository.findById(request.getTrashSizeServiceFeeId()).get();
		// set data
		ricTrashSizeServiceFee.setYearly(request.getYearly());
		ricTrashSizeServiceFee.setTrashType(request.getTrashType());
		BigDecimal TrashSize = new BigDecimal(request.getTrashSize());
		ricTrashSizeServiceFee.setTrashSize(TrashSize);
		BigDecimal ChargeRates = new BigDecimal(request.getChargeRates());
		ricTrashSizeServiceFee.setChargeRates(ChargeRates);
		ricTrashSizeServiceFee.setRemark(request.getRemark());
		ricTrashSizeServiceFee.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		ricTrashSizeServiceFee.setUpdatedDate(new Date());
		// save data
		ricTrashSizeServiceFeeRepository.save(ricTrashSizeServiceFee);
	}

}
