package aot.it.service;

import java.io.IOException;
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

import aot.common.constant.DoctypeConstants;
import aot.it.model.RicItNetworkCreateInvoiceMapping;
import aot.it.model.RicItNetworkServiceList;
import aot.it.repository.It011Dao;
import aot.it.repository.jpa.RicItNetworkCreateInvoiceMappingRepository;
import aot.it.repository.jpa.RicItNetworkCreateInvoiceRepository;
import aot.it.repository.jpa.RicItNetworkServiceListRepository;
import aot.it.vo.request.It001Req;
import aot.it.vo.request.It011Req;
import aot.it.vo.response.It001Res;
import aot.it.vo.response.It011Res;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It011Service {

	private static final Logger logger = LoggerFactory.getLogger(It011Service.class);

	@Autowired
	private RicItNetworkServiceListRepository ricItNetworkserviceListRepository;

	@Autowired
	private RicItNetworkCreateInvoiceRepository ricItNetworkCreateInvoiceRepository;

	@Autowired
	private RicItNetworkCreateInvoiceMappingRepository dtlRepository;
	
	@Autowired
	private It011Dao it011Dao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private CommonARRequest commonARRequest;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(List<Long> request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		List<Item> itemList = new ArrayList<Item>();
		try {
			for (int i = 0; i < request.size(); i++) {
				Long garInfoId = request.get(i);
				RicItNetworkServiceList serviceList = ricItNetworkserviceListRepository.findById(garInfoId).get();
				BigDecimal totalAmount = serviceList.getTotalAmount();
				BigDecimal vat = totalAmount.multiply(new BigDecimal(0.07));
				BigDecimal totally = totalAmount.add(vat);
				// set request 1_11
				dataSend = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(),
						SAPConstants.COMCODE, DoctypeConstants.IH, serviceList.getTransactionNo());

				// Create Item1
				Item item1 = dataSend.getHeader().get(0).getItem().get(0);
				item1.setPostingKey("01"); // require
				item1.setAccount(serviceList.getCustomerCode()); // require
				item1.setAmount(NumberUtils.roundUpTwoDigit(totally).toString()); // require
				item1.setTaxCode("DS");
//				item1.setCustomerBranch(serviceList.getCustomerBranch().split(":")[0]);
				item1.setText(SAPConstants.IT.TEXT);
				item1.setWHTType1("R1");
				item1.setWHTCode1("03");
				item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(totalAmount).toString());
				itemList.add(item1);

				// Create Item2
				Item item2 = dataSend.getHeader().get(0).getItem().get(1);
				item2.setPostingKey("50"); // require
				item2.setAccount("4105170002"); // require
				item2.setAmount(NumberUtils.roundUpTwoDigit(totalAmount).negate().toString()); // require
				item2.setTaxCode("DS");
				item2.setText(SAPConstants.IT.TEXT);
				item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
				item2.setPaService("12.1");
				item2.setPaChargesRate("27.0");
				item2.setContractNo(serviceList.getContractNo());
				itemList.add(item2);

				// Create Item3
				Item item3 = dataSend.getHeader().get(0).getItem().get(2);
				item3.setPostingKey("50"); // require
				item3.setAccount("2450101002"); // require
				item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
				item3.setTaxCode("DS");
				item3.setText(SAPConstants.IT.TEXT);
				item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(totalAmount).toString());
				itemList.add(item3);

				dataSend.getHeader().get(0).setItem(itemList);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(serviceList.getItNetworkServiceId());
				reqConnection.setTableName("ric_it_network_service_list");
				reqConnection.setColumnId("it_network_service_id");
				// reqConnection.setColumnInvoiceNo("invoice_no");
				// reqConnection.setColumnTransNo("transaction_no");
				// reqConnection.setColumnSapJsonReq("sap_json_req");
				// reqConnection.setColumnSapJsonRes("sap_json_res");
				// reqConnection.setColumnSapError("sap_error");
				// reqConnection.setColumnSapStatus("sap_status");

				/* __________________ set connection SAP __________________ */
				sapResponse = sapARService.setSapConnection(reqConnection);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

//	@Transactional(rollbackOn = { Exception.class })
//	public void syncData() {
//		List<RicItNetworkServiceList> dataSaveList = new ArrayList<RicItNetworkServiceList>();
//		RicItNetworkServiceList dataSave = null;
//		Date date = new Date();
//		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		Long month = Long.valueOf(localDate.getMonthValue());
//		Long year = Long.valueOf(localDate.getYear());
//		// sync and set new data
//		List<RicItNetworkCreateInvoice> createInvoiceList = ricItNetworkCreateInvoiceRepository.findByCheckStartDateAndCheckEndDate();
//		for (int i = 0; i < createInvoiceList.size(); i++) {
//			RicItNetworkCreateInvoice createInvoice = createInvoiceList.get(i);
//			// check data for update
//			RicItNetworkServiceList oldData = ricItNetworkserviceListRepository
//					.findByNetworkCreateInvoiceIdAndMonthsAndYears(createInvoice.getItNetworkConfigId(),
//							month.toString(), year.toString());
//			if (null != oldData) {
//				dataSave = oldData;
//				dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
//				dataSave.setUpdatedDate(new Date());
//			} else {
//				dataSave = new RicItNetworkServiceList();
//				dataSave.setSapStatus(RICConstants.STATUS.PENDING);
//				dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
//				dataSave.setCreatedDate(new Date());
//				dataSave.setIsDeleted(RICConstants.STATUS.NO);
//			}
//			dataSave.setNetworkCreateInvoiceId(createInvoice.getItNetworkConfigId());
//			dataSave.setMonths(month.toString());
//			dataSave.setYears(year.toString());
//			dataSave.setCustomerCode(createInvoice.getEntreprenuerCode());
//			dataSave.setCustomerName(createInvoice.getEntreprenuerName());
////			dataSave.setCustomerBranch(hdr.getCustomerBranch());
//			dataSave.setContractNo(createInvoice.getContractNo());
//			dataSave.setRentalObject(createInvoice.getRentalObjectCode());
//			dataSave.setItLocation(createInvoice.getItLocation());
//			dataSave.setTotalAmount(createInvoice.getTotalAmount());
//			dataSave.setStartDate(createInvoice.getRequestStartDate());
//			dataSave.setEndDate(createInvoice.getRequestEndDate());
//			dataSave.setRemark(createInvoice.getRemark());
////			dataSave.setAirport(createInvoice.getAirport());
//			dataSaveList.add(dataSave);
//		}
//		// save new data
//		ricItNetworkserviceListRepository.saveAll(dataSaveList);
//		// check data old month
//		Long oldMonthNum = month - 1;
//		if (oldMonthNum == 0) {
//			oldMonthNum = 12l;
//			year = year - 1;
//		}
//		List<RicItNetworkServiceList> oldMonthList = ricItNetworkserviceListRepository
//				.findByMonthsAndYears(oldMonthNum.toString(), year.toString());
//		if (oldMonthList.isEmpty()) {
//			createDataInMonthBeforeNow(oldMonthNum, year);
//		}
//	}
	
//	private void createDataInMonthBeforeNow(Long oldMonth, Long year) {
//		List<RicItNetworkServiceList> dataSaveList = new ArrayList<RicItNetworkServiceList>();
//		RicItNetworkServiceList dataSave = null;
//		BigDecimal totalAmount = new BigDecimal(0);
//		List<RicItNetworkCreateInvoice> createInvoiceList = ricItNetworkCreateInvoiceRepository.findByCheckStartDateAndCheckEndDate();
//		for (int i = 0; i < createInvoiceList.size(); i++) {
//			RicItNetworkCreateInvoice createInvoice = createInvoiceList.get(i);
//			dataSave = new RicItNetworkServiceList();
//			dataSave.setNetworkCreateInvoiceId(createInvoice.getItNetworkConfigId());
//			dataSave.setMonths(oldMonth.toString());
//			dataSave.setYears(year.toString());
//			dataSave.setCustomerCode(createInvoice.getEntreprenuerCode());
//			dataSave.setCustomerName(createInvoice.getEntreprenuerName());
////			dataSave.setCustomerBranch(hdr.getCustomerBranch());
//			dataSave.setContractNo(createInvoice.getContractNo());
//			dataSave.setRentalObject(createInvoice.getRentalObjectCode());
//			dataSave.setItLocation(createInvoice.getItLocation());
//			dataSave.setStartDate(createInvoice.getRequestStartDate());
//			dataSave.setEndDate(createInvoice.getRequestEndDate());
//			dataSave.setTotalAmount(totalAmount);
//			dataSave.setRemark(createInvoice.getRemark());
////			dataSave.setAirport(createInvoice.getAirport());
//			dataSave.setSapStatus(RICConstants.STATUS.PENDING);
//			dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
//			dataSave.setCreatedDate(new Date());
//			dataSave.setIsDeleted(RICConstants.STATUS.NO);
//			dataSaveList.add(dataSave);
//		}
//		// save new data
//		ricItNetworkserviceListRepository.saveAll(dataSaveList);
//	}
	
	@Transactional
	public Integer syncData(String periodMonth) throws IOException {
		logger.info("syncData It011 Start");
		long start = System.currentTimeMillis();
		/*
		 * data initial from register
		 * */
		List<RicItNetworkServiceList> infoList = new ArrayList<RicItNetworkServiceList>();
		RicItNetworkServiceList info = null;
		List<It001Res> ResReq = it011Dao.checkPeriodMonthBeforeSyncData(periodMonth);
		for (It001Res initData : ResReq) {
			/* _________ set data register _________ */
			info = setDataRegister(initData);
			info.setPeriodMonth(periodMonth);

//			/* __________ find and calculate config __________ */
//			BigDecimal chargeRate = BigDecimal.ZERO;
//			try {
//				CommunicateConfigRes dataRes = communicateChargeRatesConfigDao.handheidTransceiver(new Date());
//				chargeRate = dataRes.getChargeRate();
//			} catch (Exception e) {
//				logger.info("findChargeRatesConfig => data is null");
//			}
//			
//			/* __________ check and calculate charge rate __________ */
//			String periodMonthReq = ConvertDateUtils.formatDateToString(initData.getRequestDate(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
//			String periodMonthCancel = ConvertDateUtils.formatDateToString(initData.getCancelDate(), ConvertDateUtils.YYYYMM, ConvertDateUtils.LOCAL_EN);
//			Calendar calReq = Calendar.getInstance();
//			calReq.setTime(initData.getRequestDate());
//			int dayReq = calReq.get(Calendar.DATE);
//			int days = 0;
//			int maxDayOfMonth = 0;
//			Calendar calCancel = Calendar.getInstance();
//			if (periodMonth.equals(periodMonthReq)) {
//				if (periodMonth.equals(periodMonthCancel)) {
//					/* __________ request and cancel this period month __________ */
//					calCancel.setTime(initData.getCancelDate());
//					maxDayOfMonth = calCancel.getActualMaximum(Calendar.DAY_OF_MONTH);
//					days = calCancel.get(Calendar.DATE) - dayReq;
//				} else {
//					/* __________ request this period month __________ */
//					maxDayOfMonth = calReq.getActualMaximum(Calendar.DAY_OF_MONTH);
//					days = maxDayOfMonth - dayReq;
//				}
//				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate, info.getPhoneAmount(), avgOfMonth(days, maxDayOfMonth)));
//			} else if(periodMonth.equals(periodMonthCancel)) {
//				/* __________ cancel this period month __________ */
//				calCancel.setTime(initData.getCancelDate());
//				maxDayOfMonth = calCancel.getActualMaximum(Calendar.DAY_OF_MONTH);
//				days = calCancel.get(Calendar.DATE);
//				info.setTotalChargeRates(calculateTotalChargeRate(chargeRate, info.getPhoneAmount(), avgOfMonth(days, maxDayOfMonth)));
//			} else {
//				info.setTotalChargeRates(NumberUtils.roundUpTwoDigit(chargeRate.multiply(info.getPhoneAmount())));
//			}
//			
//			info.setVat(NumberUtils.roundUpTwoDigit(sysConstantService.getSumVat(info.getTotalChargeRates())));
//			info.setTotalAll(NumberUtils.roundUpTwoDigit(info.getTotalChargeRates().add(info.getVat())));
			
			/* __________ add to list info __________ */
			infoList.add(info);
		}
		ricItNetworkserviceListRepository.saveAll(infoList);
		
		long end = System.currentTimeMillis();
		logger.info("syncData It011 Success, using {} seconds", (float) (end - start) / 1000F);
		return infoList.size();
	}
	
	private RicItNetworkServiceList setDataRegister(It001Res createInvoice) {
		RicItNetworkServiceList entity = new RicItNetworkServiceList();
		entity.setNetworkCreateInvoiceId(createInvoice.getNetworkCreateInvoiceId());
//		entity.setMonths(oldMonth.toString());
//		entity.setYears(year.toString());
		entity.setCustomerCode(createInvoice.getEntreprenuerCode());
		entity.setCustomerName(createInvoice.getEntreprenuerName());
		entity.setContractNo(createInvoice.getContractNo());
		entity.setRentalObject(createInvoice.getRentalObjectCode());
		entity.setItLocation(createInvoice.getItLocation());
//		entity.setStartDate(ConvertDateUtils.DDMMYYYYToDate(createInvoice.getRequestStartDate()));
//		entity.setEndDate(ConvertDateUtils.DDMMYYYYToDate(createInvoice.getRequestEndDate()));
		entity.setTotalAmount(createInvoice.getTotalAmount());
		entity.setRemark(createInvoice.getRemark());
		entity.setCustomerBranch(createInvoice.getEntreprenuerBranch());
		entity.setCreatedBy(UserLoginUtils.getCurrentUsername());
		entity.setCreatedDate(new Date());
		entity.setIsDeleted(FLAG.N_FLAG);
		return entity;
	}

	public List<It011Res> getAll(It011Req req) {
		List<It011Res> resList = it011Dao.getAll(req);
		return resList;
	}

	public It001Res findById(It001Req req) {
		It001Res data = null;
		if (StringUtils.isNotBlank(req.getItNetworkServiceId())) {
			RicItNetworkServiceList serviceList = ricItNetworkserviceListRepository.findById(Long.valueOf(req.getItNetworkServiceId())).get();
			
			data = new It001Res();
			data.setEntreprenuerCode(serviceList.getCustomerCode());
			data.setEntreprenuerName(serviceList.getCustomerName());
			data.setEntreprenuerBranch(serviceList.getCustomerBranch());
			data.setContractNo(serviceList.getContractNo());
			data.setItLocation(serviceList.getItLocation());
			data.setRentalObjectCode(serviceList.getRentalObject());
//			data.setRequestStartDate(ConvertDateUtils.formatDateToString(serviceList.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
//					ConvertDateUtils.LOCAL_EN));
//			data.setRequestEndDate(ConvertDateUtils.formatDateToString(serviceList.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
//					ConvertDateUtils.LOCAL_EN));
			data.setRemark(serviceList.getRemark());
			data.setTotalAmount(serviceList.getTotalAmount());
			List<RicItNetworkCreateInvoiceMapping> datalist = dtlRepository.findByIdHdr(serviceList.getNetworkCreateInvoiceId());
			data.setDetailChargeRate(datalist);
		}
		return data;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void updateData(It001Req req) {
		List<RicItNetworkServiceList> dataSaveList = new ArrayList<RicItNetworkServiceList>();
		RicItNetworkServiceList dataSave = null;
		// set data
		// check data for update
		dataSave = ricItNetworkserviceListRepository.findById(Long.valueOf(req.getItNetworkServiceId())).get();
		dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setUpdatedDate(new Date());
		dataSave.setCustomerCode(req.getEntreprenuerCode());
		dataSave.setCustomerName(req.getEntreprenuerName());
		dataSave.setCustomerBranch(req.getEntreprenuerBranch());
		dataSave.setContractNo(req.getContractNo());
		dataSave.setRentalObject(req.getRentalObjectCode());
		dataSave.setItLocation(req.getItLocation());
//		dataSave.setStartDate(ConvertDateUtils.parseStringToDate(req.getRequestStartDate(), ConvertDateUtils.DD_MM_YYYY,
//				ConvertDateUtils.LOCAL_EN));
//		dataSave.setEndDate(ConvertDateUtils.parseStringToDate(req.getRequestEndDate(), ConvertDateUtils.DD_MM_YYYY,
//				ConvertDateUtils.LOCAL_EN));
		dataSave.setTotalAmount(req.getTotalAmount());
		dataSave.setRemark(req.getRemark());
		dataSaveList.add(dataSave);
		// save new data
		ricItNetworkserviceListRepository.saveAll(dataSaveList);
	}
}
