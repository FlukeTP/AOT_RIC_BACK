package aot.garbagedis.service;

import java.math.BigDecimal;
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
import aot.common.constant.GarbagedisConstants;
import aot.common.constant.RICConstants;
import aot.garbagedis.model.RicGarbagedisInfo;
import aot.garbagedis.model.RicGarbagedisReqDtl;
import aot.garbagedis.model.RicGarbagedisReqHdr;
import aot.garbagedis.repository.Garbagedis001Dao;
import aot.garbagedis.repository.jpa.RicGarbagedisInfoRepository;
import aot.garbagedis.repository.jpa.RicGarbagedisReqDtlRepository;
import aot.garbagedis.repository.jpa.RicGarbagedisReqHdrRepository;
import aot.garbagedis.vo.request.Garbagedis001Req;
import aot.garbagedis.vo.request.Garbagedis002DtlReq;
import aot.garbagedis.vo.request.Garbagedis002HdrReq;
import aot.garbagedis.vo.response.Garbagedis001Res;
import aot.garbagedis.vo.response.Garbagedis002HdrRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.constant.SAPConstants.DEPOSIT_TEXT;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Garbagedis001Service {

	private static final Logger logger = LoggerFactory.getLogger(Garbagedis001Service.class);

	@Autowired
	private RicGarbagedisInfoRepository ricGarbagedisInfoRepository;

	@Autowired
	private RicGarbagedisReqHdrRepository ricGarbagedisReqHdrRepository;

	@Autowired
	private RicGarbagedisReqDtlRepository ricGarbagedisReqDtlRepository;

	@Autowired
	private Garbagedis001Dao garbagedis001Dao;

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
				RicGarbagedisInfo garbagedisInfo = ricGarbagedisInfoRepository
						.findById(garInfoId).get();
				BigDecimal totalMoney = garbagedisInfo.getTotalMoney();
				BigDecimal vat = totalMoney.multiply(new BigDecimal(0.07));
				BigDecimal totally = totalMoney.add(vat);
				// set request 1_10
				dataSend = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE, DoctypeConstants.IH,
						garbagedisInfo.getTransactionNo());
				
				// Create Item1
				Item item1 = dataSend.getHeader().get(0).getItem().get(0);
				item1.setPostingKey("01"); // require
				item1.setAccount(garbagedisInfo.getCustomerCode()); // require
				item1.setAmount(NumberUtils.roundUpTwoDigit(totally).toString()); // require
				item1.setTaxCode("DS");
				item1.setCustomerBranch(garbagedisInfo.getCustomerBranch().split(":")[0]);
				item1.setText(SAPConstants.GARBAGEDISPOSAL.TEXT);
				item1.setWHTType1("R1");
				item1.setWHTCode1("02");
				item1.setWHTBaseAmount1(NumberUtils.roundUpTwoDigit(totalMoney).toString());
				itemList.add(item1);

				// Create Item2
				Item item2 = dataSend.getHeader().get(0).getItem().get(1);
				item2.setPostingKey("50"); // require
				item2.setAccount("4105090001"); // require
				item2.setAmount(NumberUtils.roundUpTwoDigit(totalMoney).negate().toString()); // require
				item2.setTaxCode("DS");
				item2.setText(SAPConstants.GARBAGEDISPOSAL.TEXT);
				item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
				item2.setPaService("12.1");
				item2.setPaChargesRate("27.0");
				item2.setContractNo(garbagedisInfo.getContractNo());
				itemList.add(item2);

				// Create Item3
				Item item3 = dataSend.getHeader().get(0).getItem().get(2);
				item3.setPostingKey("50"); // require
				item3.setAccount("2450101002"); // require
				item3.setAmount(NumberUtils.roundUpTwoDigit(vat).negate().toString()); // require
				item3.setTaxCode("DS");
				item3.setText(SAPConstants.GARBAGEDISPOSAL.TEXT);
				item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(totalMoney).toString());
				itemList.add(item3);

				dataSend.getHeader().get(0).setItem(itemList);
				/* __________________ call SAP __________________ */
				SapResponse dataRes = sapARService.callSAPAR(dataSend);

				/* _______________ set data sap and column table _______________ */
				SapConnectionVo reqConnection = new SapConnectionVo();
				reqConnection.setDataRes(dataRes);
				reqConnection.setDataSend(dataSend);
				reqConnection.setId(garbagedisInfo.getGarInfoId());
				reqConnection.setTableName("ric_garbagedis_info");
				reqConnection.setColumnId("gar_info_id");
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

	@Transactional(rollbackOn = { Exception.class })
	public void syncData() {
		List<RicGarbagedisInfo> dataSaveList = new ArrayList<RicGarbagedisInfo>();
		RicGarbagedisInfo dataSave = null;
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Long month = Long.valueOf(localDate.getMonthValue());
		Long year = Long.valueOf(localDate.getYear());
		BigDecimal generalWeight = new BigDecimal(0);
		BigDecimal hazardousWeight = new BigDecimal(0);
		BigDecimal infectiousWeight = new BigDecimal(0);
		BigDecimal generalMoney = new BigDecimal(0);
		BigDecimal hazardousMoney = new BigDecimal(0);
		BigDecimal infectiousMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		// sync and set new data
		List<RicGarbagedisReqHdr> hdrList = ricGarbagedisReqHdrRepository
				.findByServiceTypeAndCheckStartDateAndCheckEndDate(GarbagedisConstants.SERVICE_REQUEST_TYPE.OPERATING_AGREEMENT);
		for (int i = 0; i < hdrList.size(); i++) {
			RicGarbagedisReqHdr hdr = hdrList.get(i);
			List<RicGarbagedisReqDtl> dtlList = ricGarbagedisReqDtlRepository.findAllByGarReqId(hdr.getGarReqId());
			for (int j = 0; j < dtlList.size(); j++) {
				RicGarbagedisReqDtl dtl = dtlList.get(j);
				if (GarbagedisConstants.TRASH_TYPE.GENERAL_WASTE.equals(dtl.getTrashType())) {
					generalWeight = new BigDecimal(dtl.getTrashWeight());
					generalMoney = dtl.getMoneyAmount();
				} else if (GarbagedisConstants.TRASH_TYPE.HAZARDOUS_WASTE.equals(dtl.getTrashType())) {
					hazardousWeight = new BigDecimal(dtl.getTrashWeight());
					hazardousMoney = dtl.getMoneyAmount();
				} else if (GarbagedisConstants.TRASH_TYPE.INFECTIOUS_WASTE.equals(dtl.getTrashType())) {
					infectiousWeight = new BigDecimal(dtl.getTrashWeight());
					infectiousMoney = dtl.getMoneyAmount();
				}
			}
			totalMoney = totalMoney.add(generalMoney).add(hazardousMoney).add(infectiousMoney);
			// check data for update
			RicGarbagedisInfo oldInfo = ricGarbagedisInfoRepository.findByGarReqIdAndMonthsAndYears(hdr.getGarReqId(),
					month.toString(), year.toString());
			if (null != oldInfo) {
				dataSave = oldInfo;
				dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				dataSave.setUpdatedDate(new Date());
			} else {
				dataSave = new RicGarbagedisInfo();
				dataSave.setSapStatus(RICConstants.STATUS.PENDING);
				dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
				dataSave.setCreatedDate(new Date());
				dataSave.setIsDeleted(RICConstants.STATUS.NO);
			}
			dataSave.setGarReqId(hdr.getGarReqId());
			dataSave.setMonths(month.toString());
			dataSave.setYears(year.toString());
			dataSave.setCustomerCode(hdr.getCustomerCode());
			dataSave.setCustomerName(hdr.getCustomerName());
			dataSave.setCustomerBranch(hdr.getCustomerBranch());
			dataSave.setContractNo(hdr.getContractNo());
			dataSave.setRentalObject(hdr.getRentalObject());
			dataSave.setTrashLocation(hdr.getTrashLocation());
			dataSave.setStartDate(hdr.getStartDate());
			dataSave.setEndDate(hdr.getEndDate());
			dataSave.setGeneralWeight(generalWeight);
			dataSave.setHazardousWeight(hazardousWeight);
			dataSave.setInfectiousWeight(infectiousWeight);
			dataSave.setGeneralMoney(generalMoney);
			dataSave.setHazardousMoney(hazardousMoney);
			dataSave.setInfectiousMoney(infectiousMoney);
			dataSave.setTotalMoney(totalMoney);
			dataSave.setAddress(hdr.getAddress());
			dataSave.setRemark(hdr.getRemark());
			dataSave.setAirport(hdr.getAirport());
			dataSaveList.add(dataSave);
		}
		// save new data
		ricGarbagedisInfoRepository.saveAll(dataSaveList);
		// check data old month
		Long oldMonthNum = month - 1;
		if (oldMonthNum == 0) {
			oldMonthNum = 12l;
			year = year - 1;
		}
		List<RicGarbagedisInfo> oldMonthList = ricGarbagedisInfoRepository.findByMonthsAndYears(oldMonthNum.toString(),
				year.toString());
		if (oldMonthList.isEmpty()) {
			createDataInMonthBeforeNow(oldMonthNum, year);
		}
	}

	private void createDataInMonthBeforeNow(Long oldMonth, Long year) {
		List<RicGarbagedisInfo> dataSaveList = new ArrayList<RicGarbagedisInfo>();
		RicGarbagedisInfo dataSave = null;
		BigDecimal generalWeight = new BigDecimal(0);
		BigDecimal hazardousWeight = new BigDecimal(0);
		BigDecimal infectiousWeight = new BigDecimal(0);
		BigDecimal generalMoney = new BigDecimal(0);
		BigDecimal hazardousMoney = new BigDecimal(0);
		BigDecimal infectiousMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		List<RicGarbagedisReqHdr> hdrList = ricGarbagedisReqHdrRepository
				.findByServiceTypeAndCheckStartDateAndCheckEndDate(GarbagedisConstants.SERVICE_REQUEST_TYPE.OPERATING_AGREEMENT);
		for (int i = 0; i < hdrList.size(); i++) {
			RicGarbagedisReqHdr hdr = hdrList.get(i);
			dataSave = new RicGarbagedisInfo();
			dataSave.setGarReqId(hdr.getGarReqId());
			dataSave.setMonths(oldMonth.toString());
			dataSave.setYears(year.toString());
			dataSave.setCustomerCode(hdr.getCustomerCode());
			dataSave.setCustomerName(hdr.getCustomerName());
			dataSave.setCustomerBranch(hdr.getCustomerBranch());
			dataSave.setContractNo(hdr.getContractNo());
			dataSave.setRentalObject(hdr.getRentalObject());
			dataSave.setTrashLocation(hdr.getTrashLocation());
			dataSave.setStartDate(hdr.getStartDate());
			dataSave.setEndDate(hdr.getEndDate());
			dataSave.setGeneralWeight(generalWeight);
			dataSave.setHazardousWeight(hazardousWeight);
			dataSave.setInfectiousWeight(infectiousWeight);
			dataSave.setGeneralMoney(generalMoney);
			dataSave.setHazardousMoney(hazardousMoney);
			dataSave.setInfectiousMoney(infectiousMoney);
			dataSave.setTotalMoney(totalMoney);
			dataSave.setAddress(hdr.getAddress());
			dataSave.setRemark(hdr.getRemark());
			dataSave.setAirport(hdr.getAirport());
			dataSave.setSapStatus(RICConstants.STATUS.PENDING);
			dataSave.setCreatedBy(UserLoginUtils.getCurrentUsername());
			dataSave.setCreatedDate(new Date());
			dataSave.setIsDeleted(RICConstants.STATUS.NO);
			dataSaveList.add(dataSave);
		}
		// save new data
		ricGarbagedisInfoRepository.saveAll(dataSaveList);
	}

	public List<Garbagedis001Res> getAll(Garbagedis001Req req) {
		List<Garbagedis001Res> resList = garbagedis001Dao.getAll(req);
		return resList;
	}

	public Garbagedis002HdrRes findById(Garbagedis001Req req) {
		Garbagedis002HdrRes res = new Garbagedis002HdrRes();
		List<Garbagedis002DtlReq> dtlList = new ArrayList<>();
		if (StringUtils.isNotBlank(req.getGarInfoId())) {
			RicGarbagedisInfo info = ricGarbagedisInfoRepository.findById(Long.valueOf(req.getGarInfoId())).get();
			RicGarbagedisReqHdr hdr = ricGarbagedisReqHdrRepository.findById(Long.valueOf(info.getGarReqId())).get();
			res.setGarReqId(info.getGarReqId());
			res.setCustomerCode(info.getCustomerCode());
			res.setCustomerName(info.getCustomerName());
			res.setCustomerBranch(info.getCustomerBranch());
			res.setContractNo(info.getContractNo());
			res.setRentalObject(info.getRentalObject());
			res.setServiceType(hdr.getServiceType());
			res.setTrashLocation(info.getTrashLocation());
			res.setStartDate(ConvertDateUtils.formatDateToString(info.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setEndDate(ConvertDateUtils.formatDateToString(info.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			// res.setTotalChargeRates(info.getTotalChargeRates());
			res.setAddress(info.getAddress());
			res.setRemark(info.getRemark());
			List<RicGarbagedisReqDtl> dtlOldList = ricGarbagedisReqDtlRepository
					.findAllByGarReqId(Long.valueOf(info.getGarReqId()));
			BigDecimal totalTrashWeight = new BigDecimal(0);
			BigDecimal totalMoneyAmount = new BigDecimal(0);
			for (RicGarbagedisReqDtl dtlOld : dtlOldList) {
				Garbagedis002DtlReq dtl = new Garbagedis002DtlReq();
				dtl.setGarReqDtlId(dtlOld.getGarReqDtlId().toString());
				dtl.setGarReqId(info.getGarReqId().toString());
				dtl.setTrashType(dtlOld.getTrashType());
				dtl.setTrashSize(dtlOld.getTrashSize().toString());
				dtl.setChargeRates(dtlOld.getChargeRates().toString());
				if (GarbagedisConstants.TRASH_TYPE.GENERAL_WASTE.equals(dtl.getTrashType())) {
					dtl.setTrashWeight(info.getGeneralWeight().toString());
					dtl.setMoneyAmount(info.getGeneralMoney().toString());
				} else if (GarbagedisConstants.TRASH_TYPE.HAZARDOUS_WASTE.equals(dtl.getTrashType())) {
					dtl.setTrashWeight(info.getHazardousWeight().toString());
					dtl.setMoneyAmount(info.getHazardousMoney().toString());
				} else if (GarbagedisConstants.TRASH_TYPE.INFECTIOUS_WASTE.equals(dtl.getTrashType())) {
					dtl.setTrashWeight(info.getInfectiousWeight().toString());
					dtl.setMoneyAmount(info.getInfectiousMoney().toString());
				}
				dtlList.add(dtl);
			}
			totalTrashWeight = totalTrashWeight.add(info.getGeneralWeight()).add(info.getHazardousWeight())
					.add(info.getInfectiousWeight());
			totalMoneyAmount = totalMoneyAmount.add(info.getGeneralMoney()).add(info.getHazardousMoney())
					.add(info.getInfectiousMoney());
			res.setTotalTrashWeight(totalTrashWeight);
			res.setTotalMoneyAmount(totalMoneyAmount);
			res.setChargeRateDtl(dtlList);
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void updateData(Garbagedis002HdrReq req) {
		List<RicGarbagedisInfo> dataSaveList = new ArrayList<RicGarbagedisInfo>();
		RicGarbagedisInfo dataSave = null;

		BigDecimal generalWeight = new BigDecimal(0);
		BigDecimal hazardousWeight = new BigDecimal(0);
		BigDecimal infectiousWeight = new BigDecimal(0);
		BigDecimal generalMoney = new BigDecimal(0);
		BigDecimal hazardousMoney = new BigDecimal(0);
		BigDecimal infectiousMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		// set data
		List<Garbagedis002DtlReq> dtlList = req.getChargeRateDtl();
		for (int j = 0; j < dtlList.size(); j++) {
			Garbagedis002DtlReq dtl = dtlList.get(j);
			if (GarbagedisConstants.TRASH_TYPE.GENERAL_WASTE.equals(dtl.getTrashType())) {
				generalWeight = new BigDecimal(dtl.getTrashWeight());
				generalMoney = new BigDecimal(dtl.getMoneyAmount());
			} else if (GarbagedisConstants.TRASH_TYPE.HAZARDOUS_WASTE.equals(dtl.getTrashType())) {
				hazardousWeight = new BigDecimal(dtl.getTrashWeight());
				hazardousMoney = new BigDecimal(dtl.getMoneyAmount());
			} else if (GarbagedisConstants.TRASH_TYPE.INFECTIOUS_WASTE.equals(dtl.getTrashType())) {
				infectiousWeight = new BigDecimal(dtl.getTrashWeight());
				infectiousMoney = new BigDecimal(dtl.getMoneyAmount());
			}
		}
		totalMoney = totalMoney.add(generalMoney).add(hazardousMoney).add(infectiousMoney);
		// check data for update
		dataSave = ricGarbagedisInfoRepository.findById(Long.valueOf(req.getGarInfoId())).get();
		dataSave.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		dataSave.setUpdatedDate(new Date());
		dataSave.setCustomerCode(req.getCustomerCode());
		dataSave.setCustomerName(req.getCustomerName());
		dataSave.setCustomerBranch(req.getCustomerBranch());
		dataSave.setContractNo(req.getContractNo());
		dataSave.setRentalObject(req.getRentalObject());
		dataSave.setTrashLocation(req.getTrashLocation());
		dataSave.setStartDate(ConvertDateUtils.parseStringToDate(req.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		dataSave.setEndDate(ConvertDateUtils.parseStringToDate(req.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
				ConvertDateUtils.LOCAL_EN));
		dataSave.setGeneralWeight(generalWeight);
		dataSave.setHazardousWeight(hazardousWeight);
		dataSave.setInfectiousWeight(infectiousWeight);
		dataSave.setGeneralMoney(generalMoney);
		dataSave.setHazardousMoney(hazardousMoney);
		dataSave.setInfectiousMoney(infectiousMoney);
		dataSave.setTotalMoney(totalMoney);
		dataSave.setAddress(req.getAddress());
		dataSave.setRemark(req.getRemark());
		dataSaveList.add(dataSave);
		// save new data
		ricGarbagedisInfoRepository.saveAll(dataSaveList);
	}
}
