package baiwa.module.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import baiwa.module.model.SystemConstant;
import baiwa.module.repository.SystemConstantDao;
import baiwa.module.repository.jpa.SystemConstantRepository;
import baiwa.module.vo.request.SysConstantReq;
import baiwa.util.NumberUtils;

@Service
public class SysConstantService {
	
	@Autowired
	private SystemConstantRepository systemConstantRepository;
	
	@Autowired
	private SystemConstantDao systemConstantDao; 
	
	public List<SystemConstant> list(SysConstantReq form) {
		List<SystemConstant> constants = new ArrayList<SystemConstant>();
		constants = systemConstantDao.list(form);
		return constants;
	}
	
	public SystemConstant listdata(SysConstantReq form) {
		SystemConstant constants = new SystemConstant();
		constants = systemConstantRepository.findById(form.getConstantId()).get();
		return constants;
	}
	
	public void saveConstant(SysConstantReq request) {
		SystemConstant systemConstant = null;
		systemConstant = new SystemConstant();
		// set data
		systemConstant.setConstantKey(request.getConstantKey());
		systemConstant.setConstantValue(request.getConstantValue());
		systemConstant.setCreatedBy("Phattartapong krintavee");
		systemConstant.setCreatedDate(new Date());
		// save data
		systemConstantRepository.save(systemConstant);
	}
	
	public void editConstant(SysConstantReq request) {
		SystemConstant systemConstant = null;
		systemConstant = new SystemConstant();
		// set data
		systemConstant = systemConstantRepository.findById(request.getConstantId()).get();
		systemConstant.setConstantKey(request.getConstantKey());
		systemConstant.setConstantValue(request.getConstantValue());
		systemConstant.setUpdatedBy("Phattartapong krintavee");
		systemConstant.setUpdatedDate(new Date());
		// save data
		systemConstantRepository.save(systemConstant);
	}

	public SystemConstant getConstantByKey(String key) {
		return systemConstantRepository.findByConstantKey(key);
	}
	
	public BigDecimal getTotalVat(BigDecimal number) {
		BigDecimal vat = NumberUtils.roundUpTwoDigit(systemConstantRepository.findByConstantKey(RICConstants.VAT).getConstantValue());
		return number.multiply(vat).add(number);
	}
	
	public BigDecimal getSumVat(BigDecimal number) {
		BigDecimal vat = NumberUtils.roundUpTwoDigit(systemConstantRepository.findByConstantKey(RICConstants.VAT).getConstantValue());
		return number.multiply(vat);
	}

}
