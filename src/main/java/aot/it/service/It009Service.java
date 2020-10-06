package aot.it.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import aot.common.constant.DoctypeConstants;
import aot.common.constant.RICConstants;
import aot.common.service.CustomerService;
import aot.common.service.OnetimeGenerator;
import aot.common.vo.response.CustomerRes;
import aot.it.model.RicItStaffPagePublicPageReq;
import aot.it.repository.It009Dao;
import aot.it.repository.jpa.RicItStaffPagePublicPageReqRepository;
import aot.it.vo.request.It009Req;
import aot.it.vo.response.It009Res;
import aot.sap.service.SAPARService;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.ArRequest;
import aot.util.sap.domain.request.Item;
import aot.util.sap.domain.response.SapResponse;
import aot.util.sapreqhelper.CommonARRequest;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;
import baiwa.util.UserLoginUtils;

@Service
public class It009Service {

	private static final Logger logger = LoggerFactory.getLogger(It009Service.class);

	@Autowired
	private RicItStaffPagePublicPageReqRepository ricItStaffPagePublicPageReqRepository;

	@Autowired
	private It009Dao it009Dao;

	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private SAPARService sapARService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OnetimeGenerator oneTime;
	
	@Transactional(rollbackOn = { Exception.class })
	public SapResponse sendSap(It009Req request) {
		SapResponse sapResponse = new SapResponse();
		ArRequest dataSend = new ArRequest();
		try {
			RicItStaffPagePublicPageReq req = ricItStaffPagePublicPageReqRepository
					.findById(Long.valueOf(request.getItPageReqId())).get();
			/* sapArRequest_5_X */
			dataSend = commonARRequest.getThreeTemplate(UserLoginUtils.getUser().getAirportCode(), SAPConstants.COMCODE,
					DoctypeConstants.I8, req.getTransactionNo());

			// sapCustomerAdrr
			CustomerRes sapAdrr = customerService.getSAPCustomerAddress(req.getCustomerCode(),
					StringUtils.isNotBlank(req.getCustomerBranch()) ? req.getCustomerBranch() : null);

			// Create add data header
			dataSend.getHeader().get(0).setParkDocument("X");

			// Create Item1
			Item item1 = dataSend.getHeader().get(0).getItem().get(0);
			item1.setPostingKey("01");
			item1.setAccount(oneTime.getGenerate(RICConstants.CATEGORY.OTHER));
			item1.setSpGL(null);
			item1.setAmount(NumberUtils.roundUpTwoDigit(req.getTotalAmount()).toString());
			item1.setTaxCode("O7");
			item1.setTaxBaseAmount(null);
			item1.setAlternativeRecon(null);
			item1.setPaymentTerm("Z001");
			item1.setPmtMethod(null);
			item1.setCustomerBranch(null);
			item1.setTaxNumber3(StringUtils.isNotBlank(sapAdrr.getTaxNumber()) ? sapAdrr.getTaxNumber() : "NO DATA");
			item1.setName1(StringUtils.isNotBlank(sapAdrr.getName1()) ? sapAdrr.getName1() : "NO DATA");
			item1.setName2(StringUtils.isNotBlank(sapAdrr.getName2()) ? sapAdrr.getName2() : "NO DATA");
			item1.setName3(StringUtils.isNotBlank(sapAdrr.getName3()) ? sapAdrr.getName3() : "NO DATA");
			item1.setName4(StringUtils.isNotBlank(sapAdrr.getName4()) ? sapAdrr.getName4() : "NO DATA");
			item1.setStreet(StringUtils.isNotBlank(sapAdrr.getStreet()) ? sapAdrr.getStreet() : "NO DATA");
			// check street4,5 field add City
			if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && sapAdrr.getStreet4().length() > 35) {
				item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
			} else if (StringUtils.isNotBlank(sapAdrr.getStreet4()) && StringUtils.isNotBlank(sapAdrr.getStreet5())) {
				String data = sapAdrr.getStreet4() + " " + sapAdrr.getStreet5();
				if (data.length() > 35) {
					item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
				} else {
					if (StringUtils.isNotBlank(sapAdrr.getCity())) {
						item1.setCity(sapAdrr.getStreet4() + " , " + sapAdrr.getStreet5() + " " + sapAdrr.getCity());
					} else {
						item1.setCity("NO DATA");
					}
				}
			} else {
				item1.setCity(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
			}
			item1.setTaxNumber5(StringUtils.isNotBlank(sapAdrr.getCity()) ? sapAdrr.getCity() : "NO DATA");
			item1.setPostalCode(StringUtils.isNotBlank(sapAdrr.getPostCode()) ? sapAdrr.getPostCode() : "NO DATA");
			item1.setCountry(StringUtils.isNotBlank(sapAdrr.getCountry()) ? sapAdrr.getCountry() : "NO DATA");
//			item1.setAssignment(req.get);
			item1.setText(req.getRemark());
			item1.setContractNo(req.getContractNo());

			// Create Item2
			Item item2 = dataSend.getHeader().get(0).getItem().get(1);
			item2.setPostingKey("50");
			item2.setAccount("4105120010");
			item2.setAmount(NumberUtils.roundUpTwoDigit(req.getChargeRates()).negate().toString());
			item2.setTaxCode("O7");
//			item2.setAssignment(fire.getCourseName());
			item2.setText(req.getRemark());
			item2.setProfitCenter(UserLoginUtils.getUser().getProfitCenter());
			item2.setPaService("12.1");
			item2.setPaChargesRate("9.3");

			Item item3 = dataSend.getHeader().get(0).getItem().get(2);

			// Create Item3
			item3.setPostingKey("50");
			item3.setAccount("2450101001");
			item3.setAmount(NumberUtils.roundUpTwoDigit(req.getVat()).negate().toString());
			item3.setTaxCode("O7");
			item3.setTaxBaseAmount(NumberUtils.roundUpTwoDigit(req.getChargeRates()).toString());
//			item3.setAssignment(fire.getCourseName());
			item3.setText(req.getRemark());

			List<Item> itemList = new ArrayList<Item>();
			itemList.add(item1);
			itemList.add(item2);
			itemList.add(item3);
			dataSend.getHeader().get(0).setItem(itemList);

			// convert Oop dataSend to Json string
			ObjectMapper mapper1 = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			String json1 = mapper1.writeValueAsString(dataSend);

			// save SapJsonReq
			req.setTransactionNo(dataSend.getHeader().get(0).getTransactionNo());
			req.setSapJsonReq(json1.replaceAll(" ", ""));
			ricItStaffPagePublicPageReqRepository.save(req);

			// call sap
			sapResponse = sapARService.callSAPAR(dataSend);

			// check status
			if (SAPConstants.SAP_SUCCESS.equals(sapResponse.getStatus())) {
				/* SAP_SUCCESS */
				sapResponse.setMessage(sapResponse.getRawJsonStringFromSAP());
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status
				req.setInvoiceNo(sapResponse.getSapARResponseSuccess().getDOCNO());
				req.setSapJsonRes(sapResponse.getRawJsonStringFromSAP());
				req.setSapStatus(sapResponse.getStatus());
				req.setTransactionNo(sapResponse.getSapARResponseSuccess().getTRANSNO());
			} else if (SAPConstants.SAP_FAIL.equals(sapResponse.getStatus())) {
				/* หากเชื่อมต่อ SAP ได้แต่มี response error */
				/* SAP_FAIL */
				sapResponse.setMessage(sapResponse.getRawJsonStringFromSAP());
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status and des
				req.setSapErrorDesc(sapResponse.getRawJsonStringFromSAP());
				req.setSapJsonRes(sapResponse.getRawJsonStringFromSAP());
				req.setSapStatus(sapResponse.getStatus());
			} else if (SAPConstants.SAP_CONNECTION_FAIL.equals(sapResponse.getStatus())) {
				/* หากไม่สามารถเชื่อมต่อ SAP ได้ */
				/* SAP_CONNECTION_FAIL */
				sapResponse.setMessage(SAPConstants.SAP_CONNECTION_FAIL_MSG);
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status and des
				req.setSapErrorDesc(SAPConstants.SAP_CONNECTION_FAIL_MSG);
				req.setSapStatus(sapResponse.getStatus());
				req.setTransactionNo(dataSend.getHeader().get(0).getTransactionNo());
			}
			ricItStaffPagePublicPageReqRepository.save(req);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<It009Res> getListAll(It009Req request) throws Exception {

		logger.info("getListAll");

		List<It009Res> list = new ArrayList<>();
		try {
			list = it009Dao.findAll(request);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return list;
	}

	public It009Res findById(It009Req request) throws Exception {

		logger.info("findById");

		It009Res res = new It009Res();

		try {
			// update
			if (StringUtils.isNotEmpty(request.getItPageReqId())) {
				RicItStaffPagePublicPageReq req = ricItStaffPagePublicPageReqRepository
						.findById(Long.valueOf(request.getItPageReqId())).get();
				// set data for return
				res.setCustomerCode(req.getCustomerCode());
				res.setCustomerName(req.getCustomerName());
				res.setCustomerBranch(req.getCustomerBranch());
				res.setContractNo(req.getContractNo());
				res.setStaffType(req.getStaffType());
				res.setPublicType(req.getPublicType());
				res.setStaffPageNum(req.getStaffPageNum());
				res.setPublicPageNum(req.getPublicPageNum());
				res.setChargeRates(req.getChargeRates());
				res.setVat(req.getVat());
				res.setTotalAmount(req.getTotalAmount());
				res.setStatus(req.getStatus());
				res.setRequestStartDate(ConvertDateUtils.formatDateToString(req.getRequestStartDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRequestEndDate(ConvertDateUtils.formatDateToString(req.getRequestEndDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				res.setRemark(req.getRemark());
				res.setAirport(req.getAirport());
				res.setTransactionNo(req.getTransactionNo());
				res.setInvoiceNo(req.getInvoiceNo());
				res.setSapStatus(req.getSapStatus());
				res.setSapErrorDesc(req.getSapErrorDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return res;
	}

	@Transactional(rollbackOn = { Exception.class })
	public void save(It009Req request) throws Exception {
		logger.info("save");

		RicItStaffPagePublicPageReq req = null;
		try {
			if (StringUtils.isNotEmpty(request.getItPageReqId())) {
				req = ricItStaffPagePublicPageReqRepository.findById(Long.valueOf(request.getItPageReqId())).get();
				// set data
				req.setUpdatedBy(UserLoginUtils.getCurrentUsername());
				req.setUpdatedDate(new Date());
			} else {
				req = new RicItStaffPagePublicPageReq();
				// set data
				req.setSapStatus(RICConstants.STATUS.PENDING);
				req.setCreatedBy(UserLoginUtils.getCurrentUsername());
				req.setCreatedDate(new Date());
				req.setIsDelete(RICConstants.STATUS.NO);
			}
			req.setCustomerCode(request.getCustomerCode());
			req.setCustomerName(request.getCustomerName());
			req.setCustomerBranch(UserLoginUtils.getCurrentUserBean().getBranchCode());
			req.setContractNo(request.getContractNo());
			req.setStaffType(request.getStaffType());
			req.setPublicType(request.getPublicType());
			req.setStaffPageNum(Long.valueOf(request.getStaffPageNum()));
			req.setPublicPageNum(Long.valueOf(request.getPublicPageNum()));
			req.setChargeRates(request.getChargeRates());
			req.setVat(request.getVat());
			req.setTotalAmount(request.getTotalAmount());
			req.setStatus(request.getStatus());
			req.setRequestStartDate(ConvertDateUtils.parseStringToDate(request.getRequestStartDate(),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			req.setRemark(request.getRemark());
			req.setAirport(UserLoginUtils.getUser().getAirportCode());
			// save data
			ricItStaffPagePublicPageReqRepository.save(req);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

	}
}
