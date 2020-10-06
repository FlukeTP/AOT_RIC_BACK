package aot.garbagedis.service;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.garbagedis.model.RicGarbagedisReqDtl;
import aot.garbagedis.model.RicGarbagedisReqHdr;
import aot.garbagedis.repository.Garbagedis002Dao;
import aot.garbagedis.repository.jpa.RicGarbagedisReqDtlRepository;
import aot.garbagedis.repository.jpa.RicGarbagedisReqHdrRepository;
import aot.garbagedis.vo.request.Garbagedis002DtlReq;
import aot.garbagedis.vo.request.Garbagedis002HdrReq;
import aot.garbagedis.vo.response.Garbagedis002HdrRes;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_4_13;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class Garbagedis002Service {

	private static final Logger logger = LoggerFactory.getLogger(Garbagedis002Service.class);

	@Autowired
	private RicGarbagedisReqHdrRepository ricGarbagedisReqHdrRepository;

	@Autowired
	private RicGarbagedisReqDtlRepository ricGarbagedisReqDtlRepository;

	@Autowired
	private Garbagedis002Dao garbagedis002Dao;

	@Autowired
	private SapArRequest_4_13 sapArRequest_4_13;

	@Autowired
	private SAPARService sapARService;

	public SapResponse sendSap(Garbagedis002HdrReq request) throws Exception {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		RicGarbagedisReqHdr garbagedisReqHdr = ricGarbagedisReqHdrRepository
				.findById(Long.valueOf(request.getGarReqId())).get();
		// set request 4_13
		dataSend = sapArRequest_4_13.getARRequest(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
				Long.valueOf(request.getGarReqId()), DoctypeConstants.I8);
		/* __________________ call SAP __________________ */
		SapResponse dataRes = sapARService.callSAPAR(dataSend);

		/* _______________ set data sap and column table _______________ */
		SapConnectionVo reqConnection = new SapConnectionVo();
		reqConnection.setDataRes(dataRes);
		reqConnection.setDataSend(dataSend);
		reqConnection.setId(garbagedisReqHdr.getGarReqId());
		reqConnection.setTableName("ric_garbagedis_req_hdr");
		reqConnection.setColumnId("gar_req_id");
		// reqConnection.setColumnInvoiceNo("invoice_no");
		// reqConnection.setColumnTransNo("transaction_no");
		// reqConnection.setColumnSapJsonReq("sap_json_req");
		// reqConnection.setColumnSapJsonRes("sap_json_res");
		// reqConnection.setColumnSapError("sap_error");
		// reqConnection.setColumnSapStatus("sap_status");

		/* __________________ set connection SAP __________________ */
		sapResponse = sapARService.setSapConnection(reqConnection);

		return sapResponse;
	}

	public List<Garbagedis002HdrRes> getAll(Garbagedis002HdrReq req) {
		List<Garbagedis002HdrRes> resList = garbagedis002Dao.getAll(req);
		return resList;
	}

	public Garbagedis002HdrRes findById(Garbagedis002HdrReq req) {
		Garbagedis002HdrRes res = new Garbagedis002HdrRes();
		List<Garbagedis002DtlReq> dtlList = new ArrayList<>();
		if (StringUtils.isNotBlank(req.getGarReqId())) {
			RicGarbagedisReqHdr hdr = ricGarbagedisReqHdrRepository.findById(Long.valueOf(req.getGarReqId())).get();
			res.setGarReqId(hdr.getGarReqId());
			res.setCustomerCode(hdr.getCustomerCode());
			res.setCustomerName(hdr.getCustomerName());
			res.setCustomerBranch(hdr.getCustomerBranch());
			res.setContractNo(hdr.getContractNo());
			res.setRentalObject(hdr.getRentalObject());
			res.setServiceType(hdr.getServiceType());
			res.setTrashLocation(hdr.getTrashLocation());
			res.setStartDate(ConvertDateUtils.formatDateToString(hdr.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setEndDate(ConvertDateUtils.formatDateToString(hdr.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			res.setTotalChargeRates(hdr.getTotalChargeRates());
			res.setAddress(hdr.getAddress());
			res.setRemark(hdr.getRemark());
			List<RicGarbagedisReqDtl> dtlOldList = ricGarbagedisReqDtlRepository
					.findAllByGarReqId(Long.valueOf(hdr.getGarReqId()));
			BigDecimal totalTrashWeight = new BigDecimal(0);
			BigDecimal totalMoneyAmount = new BigDecimal(0);
			for (RicGarbagedisReqDtl dtlOld : dtlOldList) {
				Garbagedis002DtlReq dtl = new Garbagedis002DtlReq();
				dtl.setGarReqDtlId(dtlOld.getGarReqDtlId().toString());
				dtl.setGarReqId(dtlOld.getGarReqId().toString());
				dtl.setTrashType(dtlOld.getTrashType());
				dtl.setTrashWeight(dtlOld.getTrashWeight().toString());
				dtl.setTrashSize(dtlOld.getTrashSize().toString());
				dtl.setChargeRates(dtlOld.getChargeRates().toString());
				dtl.setMoneyAmount(dtlOld.getMoneyAmount().toString());
				dtlList.add(dtl);
				totalTrashWeight = totalTrashWeight.add(new BigDecimal(dtlOld.getTrashWeight()));
				totalMoneyAmount = totalMoneyAmount.add(dtlOld.getMoneyAmount());
			}
			res.setTotalTrashWeight(totalTrashWeight);
			res.setTotalMoneyAmount(totalMoneyAmount);
			res.setChargeRateDtl(dtlList);
		}
		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(Garbagedis002HdrReq req) {
		logger.info("save", req);
		RicGarbagedisReqHdr hdr = null;
		RicGarbagedisReqDtl dtl = null;
		List<RicGarbagedisReqDtl> dtlList = new ArrayList<RicGarbagedisReqDtl>();
		if (StringUtils.isNotBlank(req.getGarReqId())) {
			hdr = ricGarbagedisReqHdrRepository.findById(Long.valueOf(req.getGarReqId())).get();
			hdr.setCustomerCode(req.getCustomerCode());
			hdr.setCustomerName(req.getCustomerName());
			hdr.setCustomerBranch(req.getCustomerBranch());
			hdr.setContractNo(req.getContractNo());
			hdr.setRentalObject(req.getRentalObject());
			hdr.setServiceType(req.getServiceType());
			hdr.setTrashLocation(req.getTrashLocation());
			hdr.setStartDate(ConvertDateUtils.parseStringToDate(req.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			hdr.setEndDate(ConvertDateUtils.parseStringToDate(req.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
//			hdr.setTotalChargeRates(new BigDecimal(req.getTotalChargeRates()));
			hdr.setAddress(req.getAddress());
			hdr.setRemark(req.getRemark());
			hdr.setAirport(UserLoginUtils.getUser().getAirportCode());
			hdr.setUpdatedBy(UserLoginUtils.getCurrentUsername());
			hdr.setUpdatedDate(new Date());
			for (Garbagedis002DtlReq dt : req.getChargeRateDtl()) {
				if (StringUtils.isNotBlank(dt.getGarReqDtlId())) {
					dtl = ricGarbagedisReqDtlRepository.findById(Long.valueOf(dt.getGarReqDtlId())).get();
					dtl.setUpdatedBy(UserLoginUtils.getCurrentUsername());
					dtl.setUpdatedDate(new Date());
				} else {
					dtl = new RicGarbagedisReqDtl();
					dtl.setCreatedBy(UserLoginUtils.getCurrentUsername());
					dtl.setCreatedDate(new Date());
					dtl.setIsDeleted(RICConstants.STATUS.NO);
				}
				dtl.setGarReqId(Long.valueOf(req.getGarReqId()));
				dtl.setTrashType(dt.getTrashType());
				dtl.setTrashWeight(Long.valueOf(dt.getTrashWeight()));
				dtl.setTrashSize(Long.valueOf(dt.getTrashSize()));
				dtl.setChargeRates(new BigDecimal(dt.getChargeRates()));
				dtl.setMoneyAmount(new BigDecimal(dt.getMoneyAmount()));
				dtlList.add(dtl);
			}
			ricGarbagedisReqHdrRepository.save(hdr);
			ricGarbagedisReqDtlRepository.saveAll(dtlList);
		} else {
			hdr = new RicGarbagedisReqHdr();
			hdr.setCustomerCode(req.getCustomerCode());
			hdr.setCustomerName(req.getCustomerName());
			hdr.setCustomerBranch(req.getCustomerBranch());
			hdr.setContractNo(req.getContractNo());
			hdr.setRentalObject(req.getRentalObject());
			hdr.setServiceType(req.getServiceType());
			hdr.setTrashLocation(req.getTrashLocation());
			hdr.setStartDate(ConvertDateUtils.parseStringToDate(req.getStartDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			hdr.setEndDate(ConvertDateUtils.parseStringToDate(req.getEndDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
//			hdr.setTotalChargeRates(new BigDecimal(req.getTotalChargeRates()));
			hdr.setAddress(req.getAddress());
			hdr.setRemark(req.getRemark());
			hdr.setAirport(UserLoginUtils.getUser().getAirportCode());
			hdr.setSapStatus(RICConstants.STATUS.PENDING);
			hdr.setCreatedBy(UserLoginUtils.getCurrentUsername());
			hdr.setCreatedDate(new Date());
			hdr.setIsDeleted(RICConstants.STATUS.NO);
			hdr = ricGarbagedisReqHdrRepository.save(hdr);
			for (Garbagedis002DtlReq dt : req.getChargeRateDtl()) {
				dtl = new RicGarbagedisReqDtl();
				dtl.setGarReqId(hdr.getGarReqId());
				dtl.setTrashType(dt.getTrashType());
				dtl.setTrashWeight(Long.valueOf(dt.getTrashWeight()));
				dtl.setTrashSize(Long.valueOf(dt.getTrashSize()));
				dtl.setChargeRates(new BigDecimal(dt.getChargeRates()));
				dtl.setMoneyAmount(new BigDecimal(dt.getMoneyAmount()));
				dtl.setCreatedBy(UserLoginUtils.getCurrentUsername());
				dtl.setCreatedDate(new Date());
				dtl.setIsDeleted(RICConstants.STATUS.NO);
				dtlList.add(dtl);
			}
			ricGarbagedisReqDtlRepository.saveAll(dtlList);
		}
	}
}
