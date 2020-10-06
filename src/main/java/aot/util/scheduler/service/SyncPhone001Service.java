package aot.util.scheduler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.phone.model.RicPhoneInfo;
import aot.phone.model.RicPhoneReq;
import aot.phone.repository.Phone001Dao;
import aot.phone.repository.jpa.RicPhoneInfoRepository;
import aot.phone.repository.jpa.RicPhoneReqRepository;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class SyncPhone001Service {
	private static final Logger logger = LoggerFactory.getLogger(SyncPhone001Service.class);
	
	@Autowired
	private RicPhoneInfoRepository ricPhoneInfoRepository;
	
	@Autowired
	private RicPhoneReqRepository ricPhoneReqRepository;
	
	@Autowired
	private Phone001Dao phone001Dao;
	
	@Transactional
	public void syncData() throws IOException {
		logger.info("syncData Phone001 Start");
		long start = System.currentTimeMillis();

		String periodMonth = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
		Iterable<RicPhoneReq> ResReq = ricPhoneReqRepository.findAll();
		List<RicPhoneInfo> infoList = new ArrayList<RicPhoneInfo>();
		RicPhoneInfo info = null;
		for (RicPhoneReq entity : ResReq) {
			if(FLAG.N_FLAG.equals(entity.getFlagInfo())) {
				info = new RicPhoneInfo();
				info.setAddressCode(entity.getBranchCustomer());
				info.setEntreprenuerCode(entity.getEntrepreneurCode());
				info.setEntreprenuerName(entity.getEntrepreneurName());
				info.setPhoneNo(entity.getPhoneNo());
//				info.setSerialNoMeter();
//				info.setMaintenanceCharge();
//				info.setServiceEquipmentCharge();
//				info.setInternalLineCharge();
//				info.setOutterLineCharge();
//				info.setPhoneType();
//				info.setTotalCharge();
//				info.setVat();
//				info.setTotalChargeAll();
//				info.setContractNo();
//				info.setReceiptNo();
//				info.setAirport();
				info.setPeriodMonth(periodMonth);
				info.setCreatedBy(UserLoginUtils.getCurrentUsername());
				info.setCreatedDate(new Date());
				info.setIsDeleted(FLAG.N_FLAG);
				infoList.add(info);
				
				/* __________ update flag insert __________ */
				entity.setFlagInfo(FLAG.Y_FLAG);
				entity.setUpdatedDate(new Date());
				entity.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				ricPhoneReqRepository.save(entity);
			}
		}
		 ricPhoneInfoRepository.saveAll(infoList);

		long end = System.currentTimeMillis();
		logger.info("syncData Phone001 Success, using {} seconds", (float) (end - start) / 1000F);
	}

}
