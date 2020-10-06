package aot.it.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.it.model.RicItDedicatedCUTECreateInvoice;
import aot.it.model.RicItDedicatedCUTECreateInvoiceMapping;
import aot.it.model.RicItDedicatedCUTEList;
import aot.it.model.RicItNetworkServiceList;
import aot.it.repository.It007Dao;
import aot.it.repository.jpa.RicItDedicatedCUTECreateInvoiceMappingRepository;
import aot.it.repository.jpa.RicItDedicatedCUTECreateInvoiceRepository;
import aot.it.repository.jpa.RicItDedicatedCUTEListRepository;
import aot.it.vo.request.It007Req;
import aot.it.vo.request.It008Req;
import aot.it.vo.response.It001Res;
import aot.it.vo.response.It007Res;
import aot.it.vo.response.It008Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_1_12;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It007Service {

	private static final Logger logger = LoggerFactory.getLogger(It007Service.class);

	@Autowired
	private RicItDedicatedCUTEListRepository ricItDedicatedCUTEListRepository;

	@Autowired
	private RicItDedicatedCUTECreateInvoiceRepository ricItDedicatedCUTECreateInvoiceRepository;

	@Autowired
	private RicItDedicatedCUTECreateInvoiceMappingRepository dtlRepository;
	
	@Autowired
	private It007Dao it007Dao;

	@Autowired
	private SAPARService sapARService;
	
	@Autowired
	private SapArRequest_1_12 sapArRequest_1_12;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(List<Long> request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			for (int i = 0; i < request.size(); i++) {
				Long garInfoId = request.get(i);
				RicItDedicatedCUTEList serviceList = ricItDedicatedCUTEListRepository.findById(garInfoId).get();
				// set request 1_12
				dataSend = sapArRequest_1_12.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
						DoctypeConstants.IH, garInfoId);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(serviceList.getId());
				reqConnection.setTableName("ric_it_dedicated_cute_list");
				reqConnection.setColumnId("id");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

//	@Transactional(rollbackOn = { Exception.class })
//	public void syncData(String periodMonth) {
//		List<RicItDedicatedCUTEList> dataSaveList = new ArrayList<RicItDedicatedCUTEList>();
//		RicItDedicatedCUTEList dataSave = null;
//		Date date = new Date();
//		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		Long month = Long.valueOf(localDate.getMonthValue());
//		Long year = Long.valueOf(localDate.getYear());
//		// sync and set new data
//		List<RicItDedicatedCUTECreateInvoice> createInvoiceList = ricItDedicatedCUTECreateInvoiceRepository.findByCheckStartDate();
//		for (int i = 0; i < createInvoiceList.size(); i++) {
//			RicItDedicatedCUTECreateInvoice createInvoice = createInvoiceList.get(i);
//			// check data for update
//			RicItDedicatedCUTEList oldData = ricItDedicatedCUTEListRepository
//					.findByDedicatedInvoiceIdAndMonthsAndYears(createInvoice.getId(),month.toString(),year.toString());
//			if (null != oldData) {
//				dataSave = oldData;
//				dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
//				dataSave.setUpdatedDate(new Date());
//			} else {
//				dataSave = new RicItDedicatedCUTEList();
//				dataSave.setSapStatus(RICConstants.STATUS.PENDING);
//				dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
//				dataSave.setCreatedDate(new Date());
//				dataSave.setIsDeleted(RICConstants.STATUS.NO);
//				dataSave.setDedicatedInvoiceId(createInvoice.getId());
//				dataSave.setMonths(month.toString());
//				dataSave.setYears(year.toString());
//				dataSave.setEntreprenuerCode(createInvoice.getEntreprenuerCode());
//				dataSave.setEntreprenuerName(createInvoice.getEntreprenuerName());
//				dataSave.setEntreprenuerBranch(createInvoice.getEntreprenuerBranch());
//				dataSave.setContractNo(createInvoice.getContractNo());
//				dataSave.setContractData(createInvoice.getContractData());
//				dataSave.setLocation(createInvoice.getLocation());
//				dataSave.setTotalAmount(createInvoice.getTotalAmount());
//				dataSave.setRequestStartDate(createInvoice.getRequestStartDate());
//				dataSave.setRequestEndDate(createInvoice.getRequestEndDate());
//			}
//			dataSaveList.add(dataSave);
//		}
//		// save new data
//		ricItDedicatedCUTEListRepository.saveAll(dataSaveList);
//		// check data old month
//		Long oldMonthNum = month - 1;
//		if (oldMonthNum == 0) {
//			oldMonthNum = 12l;
//			year = year - 1;
//		}
//		List<RicItDedicatedCUTEList> oldMonthList = ricItDedicatedCUTEListRepository.findByMonthsAndYears(oldMonthNum.toString(), year.toString());
//		if (oldMonthList.isEmpty()) {
//			createDataInMonthBeforeNow(oldMonthNum, year);
//		}
//	}

//	private void createDataInMonthBeforeNow(Long oldMonth, Long year) {
//		List<RicItDedicatedCUTEList> dataSaveList = new ArrayList<RicItDedicatedCUTEList>();
//		RicItDedicatedCUTEList dataSave = null;
//		List<RicItDedicatedCUTECreateInvoice> createInvoiceList = ricItDedicatedCUTECreateInvoiceRepository.findByCheckStartDateAndCheckEndDate();
//		for (int i = 0; i < createInvoiceList.size(); i++) {
//			RicItDedicatedCUTECreateInvoice createInvoice = createInvoiceList.get(i);
//			dataSave = new RicItDedicatedCUTEList();
//			dataSave.setDedicatedInvoiceId(createInvoice.getId());
//			dataSave.setMonths(oldMonth.toString());
//			dataSave.setYears(year.toString());
//			dataSave.setEntreprenuerCode(createInvoice.getEntreprenuerCode());
//			dataSave.setEntreprenuerName(createInvoice.getEntreprenuerName());
//			dataSave.setEntreprenuerBranch(createInvoice.getEntreprenuerBranch());
//			dataSave.setContractNo(createInvoice.getContractNo());
//			dataSave.setContractData(createInvoice.getContractData());
//			dataSave.setLocation(createInvoice.getLocation());
//			dataSave.setRequestStartDate(createInvoice.getRequestStartDate());
//			dataSave.setRequestEndDate(createInvoice.getRequestEndDate());
//			dataSave.setTotalAmount(createInvoice.getTotalAmount());
//			dataSave.setSapStatus(RICConstants.STATUS.PENDING);
//			dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
//			dataSave.setCreatedDate(new Date());
//			dataSave.setIsDeleted(RICConstants.STATUS.NO);
//			dataSaveList.add(dataSave);
//		}
//		// save new data
//		ricItDedicatedCUTEListRepository.saveAll(dataSaveList);
//	}
	
	@Transactional
	public Integer syncData(String periodMonth) {
		logger.info("syncData It007 Start");
		long start = System.currentTimeMillis();
		/*
		 * data initial from register
		 * */
		List<RicItDedicatedCUTEList> infoList = new ArrayList<RicItDedicatedCUTEList>();
		RicItDedicatedCUTEList info = null;
		List<It008Res> ResReq = it007Dao.checkPeriodMonthBeforeSyncData(periodMonth);
		for (It008Res initData : ResReq) {
			/* _________ set data register _________ */
			info = setDataRegister(initData);
			info.setPeriodMonth(periodMonth);
			
			/* __________ add to list info __________ */
			infoList.add(info);
		}
		ricItDedicatedCUTEListRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData It007 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicItDedicatedCUTEList setDataRegister(It008Res createInvoice) {
		RicItDedicatedCUTEList entity = new RicItDedicatedCUTEList();
		entity = new RicItDedicatedCUTEList();
		entity.setDedicatedInvoiceId(createInvoice.getId());
		entity.setEntreprenuerCode(createInvoice.getEntreprenuerCode());
		entity.setEntreprenuerName(createInvoice.getEntreprenuerName());
		entity.setEntreprenuerBranch(createInvoice.getEntreprenuerBranch());
		entity.setContractNo(createInvoice.getContractNo());
		entity.setContractData(createInvoice.getContractData());
		entity.setLocation(createInvoice.getLocation());
		entity.setTotalAmount(createInvoice.getTotalAmount());
		entity.setSapStatus(RICConstants.STATUS.PENDING);
		entity.setCreatedBy(UserLoginUtils.getCurrentUsername());
		entity.setCreatedDate(new Date());
		entity.setIsDeleted(FLAG.N_FLAG);
		return entity;
	}

	public List<It007Res> getAll(It007Req req) {
		List<It007Res> resList = it007Dao.getAll(req);
		return resList;
	}

	public It008Res findById(It008Req req) {
		It008Res data = null;
		if (StringUtils.isNotBlank(req.getId().toString())) {
			RicItDedicatedCUTEList dedicatedCUTEList = ricItDedicatedCUTEListRepository.findById(Long.valueOf(req.getId())).get();
			
			data = new It008Res();
			data.setEntreprenuerCode(dedicatedCUTEList.getEntreprenuerCode());
			data.setEntreprenuerName(dedicatedCUTEList.getEntreprenuerName());
			data.setEntreprenuerBranch(dedicatedCUTEList.getEntreprenuerBranch());
			data.setContractNo(dedicatedCUTEList.getContractNo());
			data.setLocation(dedicatedCUTEList.getLocation());
			data.setContractData(dedicatedCUTEList.getContractData());
			data.setTotalAmount(dedicatedCUTEList.getTotalAmount());
			List<RicItDedicatedCUTECreateInvoiceMapping> datalist = dtlRepository.findByIdHdr(dedicatedCUTEList.getDedicatedInvoiceId());
			data.setDetailInfo(datalist);
		}
		return data;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void updateData(It008Req req) {
		List<RicItDedicatedCUTEList> dataSaveList = new ArrayList<RicItDedicatedCUTEList>();
		RicItDedicatedCUTEList dataSave = null;
		// set data
		dataSave = ricItDedicatedCUTEListRepository.findById(Long.valueOf(req.getId())).get();
		dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setUpdatedDate(new Date());
		dataSave.setEntreprenuerCode(req.getEntreprenuerCode());
		dataSave.setEntreprenuerName(req.getEntreprenuerName());
		dataSave.setEntreprenuerBranch(req.getEntreprenuerBranch());
		dataSave.setContractNo(req.getContractNo());
		dataSave.setContractData(req.getContractData());
		dataSave.setLocation(req.getLocation());
		dataSave.setTotalAmount(req.getTotalAmount());
		dataSaveList.add(dataSave);
		// save new data
		ricItDedicatedCUTEListRepository.saveAll(dataSaveList);
	}
}
