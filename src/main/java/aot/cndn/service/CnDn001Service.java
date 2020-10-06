package aot.cndn.service;

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

import aot.cndn.model.RicCnDn;
import aot.cndn.repository.CnDn001Dao;
import aot.cndn.repository.jpa.RicCnDnRepository;
import aot.cndn.vo.request.CnDn001Req;
import aot.cndn.vo.request.SapCnDnReq;
import aot.cndn.vo.response.CnDn001Res;
import aot.common.constant.CnDnConstants;
import aot.common.constant.DoctypeConstants;
import aot.common.constant.ElectricConstants;
import aot.common.constant.RICConstants;
import aot.electric.model.RicElectricInfo;
import aot.electric.repository.jpa.RicElectricInfoRepository;
import aot.sap.model.SapRicControl;
import aot.sap.repository.SapRicControlDao;
import aot.sap.service.SAPARService;
import aot.sap.vo.SapConnectionVo;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.SapArRequest_2_0;
import aot.util.sapreqhelper.SapArRequest_2_1;
import aot.util.sapreqhelper.SapArRequest_3_0;
import aot.util.sapreqhelper.SapArRequest_3_1;
import aot.water.model.RicWaterInfo;
import aot.water.model.RicWaterReq;
import aot.water.repository.jpa.RicWaterInfoRepository;
import aot.water.repository.jpa.RicWaterReqRepository;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class CnDn001Service {

	private static final Logger logger = LoggerFactory.getLogger(CnDn001Service.class);

	@Autowired
	private RicCnDnRepository ricCnDnRepository;

	@Autowired
	private RicElectricInfoRepository ricElectricInfoRepository;

	@Autowired
	private RicWaterInfoRepository ricWaterInfoRepository;
	
	@Autowired
	private RicWaterReqRepository ricWaterReqRepository;

	@Autowired
	private CnDn001Dao cnDn001Dao;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private SapArRequest_2_0 sapArRequest_2_0;

	@Autowired
	private SapArRequest_2_1 sapArRequest_2_1;

	@Autowired
	private SapArRequest_3_0 sapArRequest_3_0;

	@Autowired
	private SapArRequest_3_1 sapArRequest_3_1;

	@Autowired
	private SapRicControlDao sapRicControlDao;

	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(CnDn001Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		SapCnDnReq cndnReq = new SapCnDnReq();
		SapRicControl sap = null;
		try {
			// save data
			RicCnDn dataSave = save(request);
			if (CnDnConstants.DOC_TYPE_CONSTANT.ELECTRICITY.equals(request.getDocType())) {
				if (CnDnConstants.REQUEST_TYPE_CONSTANT.PERMANENT.equals(request.getRequestType())) {
					RicElectricInfo elecInfo = ricElectricInfoRepository.findById(Long.valueOf(request.getId())).get();
					sap = sapRicControlDao.findByRefkey1(elecInfo.getTransactionNo()).get(0);

					cndnReq.setAssignment(elecInfo.getSerialNoMeter());
					cndnReq.setText(SAPConstants.ELECTRIC.TEXT);
					cndnReq.setReferenceKey1(NumberUtils.roundUpTwoDigit(elecInfo.getBaseValue()).toString());
					cndnReq.setReferenceKey2(NumberUtils.roundUpTwoDigit(elecInfo.getFtValue()).toString());
					cndnReq.setReferenceKey3(NumberUtils
							.roundUpTwoDigit(elecInfo.getCurrentAmount().subtract(elecInfo.getBackwardAmount()))
							.toString());
					if (ElectricConstants.REQUEST_TYPE.PACKAGES.equals(elecInfo.getRequestType())) {
						cndnReq.setLongText(elecInfo.getRoCode().concat("/").concat(elecInfo.getSerialNoMeter()));
					} else {
						BigDecimal currentMeterValue = new BigDecimal(elecInfo.getCurrentMeterValue());
						BigDecimal backwardMeterValue = elecInfo.getBackwardMeterValue();
						BigDecimal unitUsage = currentMeterValue.subtract(backwardMeterValue);
						// รหัสพื้นที่ /เลข serial มิตเตอร์ (เลขจด ปจบ - เลขจดครั้งก่อน = จน.ที่ใช้ unit)
						// TM-WEST1 / 400049 (40124 - 40000 = 124 Unit)
						String longText = elecInfo.getRoCode() + " / " + elecInfo.getSerialNoMeter() + " ("
								+ currentMeterValue + " - " + backwardMeterValue + " = " + unitUsage + " Unit)";
						cndnReq.setLongText(longText);
					}
				} else {
					sap = sapRicControlDao.findByRefkey1(request.getOldTransactionNo()).get(0);
				}

				cndnReq.setPaService("12.1");
				cndnReq.setPaChargesRate("9.1");
				cndnReq.setTextID("0001");

			} else if (CnDnConstants.DOC_TYPE_CONSTANT.WATER.equals(request.getDocType())) {
				if (CnDnConstants.REQUEST_TYPE_CONSTANT.PERMANENT.equals(request.getRequestType())) {
					RicWaterInfo waterInfo = ricWaterInfoRepository.findById(Long.valueOf(request.getId())).get();
					RicWaterReq waterReq = ricWaterReqRepository.findById(Long.valueOf(waterInfo.getIdReq())).get();
					sap = sapRicControlDao.findByRefkey1(waterInfo.getTransactionNo()).get(0);

					cndnReq.setAssignment(waterInfo.getSerialNoMeter());
					
					if (ElectricConstants.REQUEST_TYPE.PACKAGES.equals(waterReq.getRequestType())) {
						cndnReq.setLongText(waterInfo.getRoCode().concat("/").concat(waterInfo.getSerialNoMeter()));
					} else {
						BigDecimal currentMeterValue = new BigDecimal(waterInfo.getCurrentMeterValue());
						BigDecimal backwardMeterValue = waterInfo.getBackwardMeterValue();
						BigDecimal unitUsage = currentMeterValue.subtract(backwardMeterValue);
						// รหัสพื้นที่ / Water  Meter @ (เลขจด ปัจจุบัน- เลขจดครั้งก่อน = จำนวนที่ใช้) * อัตรา
						// TM-WEST1 / 400049 @ (40050 – 40000 = 50) * 25.00
						String longText = waterInfo.getRoCode() + " / " + waterInfo.getSerialNoMeter() + " @ ("
								+ currentMeterValue + " - " + backwardMeterValue + " = " + unitUsage + ")";
						cndnReq.setLongText(longText);
					}
				} else {
					sap = sapRicControlDao.findByRefkey1(request.getOldTransactionNo()).get(0);
				}
				
				cndnReq.setText(SAPConstants.WATER.TEXT);
				cndnReq.setPaService("12.1");
				cndnReq.setPaChargesRate("9.2");
				cndnReq.setTextID("0001");
			} else if (CnDnConstants.DOC_TYPE_CONSTANT.TELEPHONE.equals(request.getDocType())) {
				sap = sapRicControlDao.findByRefkey1(request.getOldTransactionNo()).get(0);

				cndnReq.setText(SAPConstants.PHONE.TEXT);
				cndnReq.setPaService("12.1");
				cndnReq.setPaChargesRate("9.3");
			} else if (CnDnConstants.DOC_TYPE_CONSTANT.EQUIPMENT.equals(request.getDocType())
					|| CnDnConstants.DOC_TYPE_CONSTANT.FIREBRIGADE.equals(request.getDocType())
					|| CnDnConstants.DOC_TYPE_CONSTANT.COMMUNICATE.equals(request.getDocType())) {
				if (CnDnConstants.DOC_TYPE_CONSTANT.EQUIPMENT.equals(request.getDocType())) {
					cndnReq.setText(SAPConstants.EQUIPMENT.TEXT);
				} else if (CnDnConstants.DOC_TYPE_CONSTANT.FIREBRIGADE.equals(request.getDocType())) {
					cndnReq.setText(SAPConstants.FIREBRIGADE.TEXT);
				} else {
					cndnReq.setText(SAPConstants.COMMUNICATE.TEXT);
				}

				sap = sapRicControlDao.findByRefkey1(request.getOldTransactionNo()).get(0);
				cndnReq.setPaService("12.1");
				cndnReq.setPaChargesRate("9.3");
			} else {
				if (CnDnConstants.DOC_TYPE_CONSTANT.IT.equals(request.getDocType())) {
					cndnReq.setText(SAPConstants.IT.TEXT);
				}
				if (CnDnConstants.DOC_TYPE_CONSTANT.GARBAGEDISPOSAL.equals(request.getDocType())) {
					cndnReq.setText(SAPConstants.GARBAGEDISPOSAL.TEXT);
				}
				sap = sapRicControlDao.findByRefkey1(request.getOldTransactionNo()).get(0);
				cndnReq.setPaService("12.1");
				cndnReq.setPaChargesRate("27.0");
			}

			cndnReq.setTransactionNo(request.getTransactionNo());
			cndnReq.setDoctype(mapDoctype(request.getCnDn(), request.getDocType()));
			cndnReq.setAccount(request.getCustomerCode());
			cndnReq.setGlAccount(request.getGlAccount());
			cndnReq.setAmount(new BigDecimal(request.getAmount()));
			cndnReq.setVat(new BigDecimal(request.getTotalAmount()).subtract(new BigDecimal(request.getAmount())));
			cndnReq.setTotalAmount(new BigDecimal(request.getTotalAmount()));
			cndnReq.setCustomerBranch(request.getCustomerBranch().split(":")[0]);
			cndnReq.setProfitCenter("110001");
			cndnReq.setContractNo(request.getContractNo());
			cndnReq.setInvoiceRef(sap.getDocno());
			cndnReq.setFiscalYear(StringUtils.isNotBlank(sap.getYear()) ? sap.getYear() : "");
			cndnReq.setLineItem("001");
			cndnReq.setTextApplicationObject(mapDoctype(request.getCnDn(), request.getDocType()));

			// set data for sap
			dataSend = mapArRequest(request, cndnReq);
			/* __________________ call SAP __________________ */
			SapResponse dataRes = sapARService.callSAPAR(dataSend);

			/* _______________ set data sap and column table _______________ */
			SapConnectionVo reqConnection = new SapConnectionVo();
			reqConnection.setDataRes(dataRes);
			reqConnection.setDataSend(dataSend);
			reqConnection.setId(dataSave.getCnDnId());
			reqConnection.setTableName("ric_cn_dn");
			reqConnection.setColumnId("cn_dn_id");
//			reqConnection.setColumnInvoiceNo("invoice_no");
//			reqConnection.setColumnTransNo("transaction_no");
//			reqConnection.setColumnSapJsonReq("sap_json_req");
//			reqConnection.setColumnSapJsonRes("sap_json_res");
//			reqConnection.setColumnSapError("sap_error");
//			reqConnection.setColumnSapStatus("sap_status");

			/* __________________ set connection SAP __________________ */
			sapResponse = sapARService.setSapConnection(reqConnection);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<CnDn001Res> getAllList(CnDn001Req request) throws Exception {

		logger.info("getAllList");

		List<CnDn001Res> list = new ArrayList<>();
		try {
			list = cnDn001Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public CnDn001Res findById(CnDn001Req request) throws Exception {

		logger.info("findById");

		CnDn001Res res = null;

		try {
			RicCnDn cndn = ricCnDnRepository.findById(Long.valueOf(request.getCnDnId())).get();
			res = new CnDn001Res();
			res.setCnDnId(cndn.getCnDnId());
			res.setCustomerCode(cndn.getCustomerCode());
			res.setCustomerName(cndn.getCustomerName());
			res.setCustomerBranch(cndn.getCustomerBranch());
			res.setContractNo(cndn.getContractNo());
			res.setOldInvoiceNo(cndn.getOldInvoiceNo());
			res.setOldReceiptNo(cndn.getOldReceiptNo());
			res.setDocType(cndn.getDocType());
			res.setSapType(cndn.getSapType());
			res.setCnDn(cndn.getCnDn());
			res.setAmount(cndn.getAmount());
			res.setTotalAmount(cndn.getTotalAmount());
			res.setGlAccount(cndn.getGlAccount());
			res.setRemark(cndn.getRemark());
			res.setAirport(cndn.getAirport());
			res.setTransactionNo(cndn.getTransactionNo());
			res.setInvoiceNo(cndn.getInvoiceNo());
			res.setSapStatus(cndn.getSapStatus());
			res.setSapError(cndn.getSapError());
			res.setRequestType(cndn.getRequestType());
			res.setOldTotalAmount(cndn.getOldTotalAmount());
			res.setOldTransactionNo(cndn.getOldTransactionNo());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public RicCnDn save(CnDn001Req request) throws Exception {
		logger.info("save");

		RicCnDn cndn = null;
		try {
			if (StringUtils.isNotEmpty(request.getCnDnId())) {
				cndn = ricCnDnRepository.findById(Long.valueOf(request.getCnDnId())).get();
				// set data
				cndn.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				cndn.setUpdatedDate(new Date());
			} else {
				cndn = new RicCnDn();
				// set data
				cndn.setSapStatus(RICConstants.STATUS.PENDING);
				cndn.setCreatedBy(UserLoginUtils.getCurrentUsername());
				cndn.setCreateDate(new Date());
				cndn.setIsDelete(RICConstants.STATUS.NO);
			}
			cndn.setCustomerCode(request.getCustomerCode());
			cndn.setCustomerName(request.getCustomerName());
			cndn.setCustomerBranch(request.getCustomerBranch());
			cndn.setOldInvoiceNo(request.getOldInvoiceNo());
			cndn.setOldReceiptNo(request.getOldReceiptNo());
			cndn.setDocType(request.getDocType());
			cndn.setSapType(request.getSapType());
			cndn.setCnDn(request.getCnDn());
			cndn.setAmount(new BigDecimal(request.getAmount()));
			cndn.setTotalAmount(new BigDecimal(request.getTotalAmount()));
			cndn.setGlAccount(request.getGlAccount());
			cndn.setRemark(request.getRemark());
			cndn.setAirport(UserLoginUtils.getUser().getAirportCode());
			cndn.setRequestType(request.getRequestType());
			cndn.setContractNo(request.getContractNo());
			cndn.setOldTotalAmount(new BigDecimal(request.getOldTotalAmount()));
			cndn.setOldTransactionNo(request.getOldTransactionNo());
			// save data
			return ricCnDnRepository.save(cndn);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}

	public String mapDoctype(String cndn, String doctype) {
		String resString = "";
		if (CnDnConstants.CN.equals(cndn)) {
			switch (doctype) {
			case CnDnConstants.DOC_TYPE_CONSTANT.ELECTRICITY:
				resString = DoctypeConstants.IR;
				break;

			case CnDnConstants.DOC_TYPE_CONSTANT.WATER:
				resString = DoctypeConstants.IQ;
				break;

			case CnDnConstants.DOC_TYPE_CONSTANT.TELEPHONE:
				resString = DoctypeConstants.IW;
				break;

			default:
				resString = DoctypeConstants.IX;
				break;
			}
		} else {
			switch (doctype) {
			case CnDnConstants.DOC_TYPE_CONSTANT.ELECTRICITY:
				resString = DoctypeConstants.IJ;
				break;

			case CnDnConstants.DOC_TYPE_CONSTANT.WATER:
				resString = DoctypeConstants.II;
				break;

			case CnDnConstants.DOC_TYPE_CONSTANT.TELEPHONE:
				resString = DoctypeConstants.IO;
				break;

			default:
				resString = DoctypeConstants.IP;
				break;
			}
		}
		return resString;
	}

	public ArRequest mapArRequest(CnDn001Req request, SapCnDnReq sapReq) {
		ArRequest response = null;
		if (CnDnConstants.CN.equals(request.getCnDn())) {
			if (StringUtils.isNotBlank(request.getOldReceiptNo())) {
				response = sapArRequest_2_1.getARRequest(sapReq);
			} else {
				response = sapArRequest_2_0.getARRequest(sapReq);
			}
		} else {
			if (StringUtils.isNotBlank(request.getOldReceiptNo())) {
				response = sapArRequest_3_1.getARRequest(sapReq);
			} else {
				response = sapArRequest_3_0.getARRequest(sapReq);
			}
		}
		return response;
	}

}
