package aot.pos.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import aot.pos.model.RicPosRevenueCustomer;
import aot.pos.model.RicPosRevenueCustomerPayment;
import aot.pos.model.RicPosRevenueCustomerProduct;
import aot.pos.repository.Pos003Dao;
import aot.pos.repository.jpa.RicPosRevenueCustomerPaymentRepository;
import aot.pos.repository.jpa.RicPosRevenueCustomerProductRepository;
import aot.pos.repository.jpa.RicPosRevenueCustomerRepository;
import aot.pos.vo.request.Pos003Req;
import aot.pos.vo.request.PosRevenueCustomerPaymentReq;
import aot.pos.vo.request.PosRevenueCustomerProductReq;
import aot.pos.vo.request.PosRevenueCustomerReq;
import aot.pos.vo.response.Pos003Res;
import aot.pos.vo.response.PosRevenueCustomerPaymentRes;
import aot.pos.vo.response.PosRevenueCustomerProductRes;
import aot.pos.vo.response.PosRevenueCustomerRes;
import aot.sap.service.SAPREService;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.request.SapRERequest;
import aot.util.sap.domain.request.SapREdata;
import aot.util.sap.domain.response.SapREResponse;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.constant.CommonConstants.SendStatus;
import baiwa.util.ConvertDateUtils;

@Service
public class Pos003Service {

	private static final Logger logger = LoggerFactory.getLogger(Pos003Service.class);

	@Autowired
	private RicPosRevenueCustomerRepository ricPosRevenueCustomerRepository;

	@Autowired
	private RicPosRevenueCustomerProductRepository ricPosRevenueCustomerProductRepository;

	@Autowired
	private RicPosRevenueCustomerPaymentRepository ricPosRevenueCustomerPaymentRepository;

	@Autowired
	private Pos003Dao pos003Dao;

	@Autowired
	private SAPREService sapREService;

	public SapREResponse sendSap(Pos003Req request) {
		SapREResponse sapResponse = new SapREResponse();
		SapRERequest dataSendList = new SapRERequest();
		List<SapREdata> dataList = new ArrayList<>();
		SapREdata data = null;
		try {
			RicPosRevenueCustomer dataFindHdr = ricPosRevenueCustomerRepository
					.findById(request.getHeader().getRevCusId()).get();
			List<RicPosRevenueCustomerProduct> productFindList = ricPosRevenueCustomerProductRepository
					.findBySaleDate(dataFindHdr.getStartSaleDate());
			if (null != productFindList && productFindList.size() > 0) {
				for (RicPosRevenueCustomerProduct pro : productFindList) {
					data = new SapREdata();
					data.setCompanyCode(dataFindHdr.getCustomerCode());
					data.setContractNo(pro.getContractNo());
//					data.setTermNumber("");
//					data.setTypeFrequency("");
//					data.setSaleReport("");
//					data.setReportUnit("");
//					data.setReportNet("");
					data.setReportFrom(ConvertDateUtils.formatDateToString(dataFindHdr.getStartSaleDate(),
							ConvertDateUtils.DD_MM_YYYY_DOT));
					data.setReportTo(ConvertDateUtils.formatDateToString(dataFindHdr.getEndSaleDate(),
							ConvertDateUtils.DD_MM_YYYY_DOT));
					dataList.add(data);
				}
				dataSendList.setData(dataList);
			}

			// convert Oop dataSend to Json string
			ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
			String json = mapper.writeValueAsString(dataSendList);
			dataFindHdr.setSapJsonReq(json.replaceAll(" ", ""));
			ricPosRevenueCustomerRepository.save(dataFindHdr);

			sapResponse = sapREService.callSAPRE(dataSendList);
			// check status
			if (SAPConstants.SAP_SUCCESS.equals(sapResponse.getStatus())) {
				/* SAP_SUCCESS */
				sapResponse.setMessage(sapResponse.getRawJsonStringFromSAP());
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status
				dataFindHdr.setSapJsonRes(sapResponse.getRawJsonStringFromSAP());
				dataFindHdr.setSapStatus(sapResponse.getStatus());
				ricPosRevenueCustomerRepository.save(dataFindHdr);

			} else if (SAPConstants.SAP_FAIL.equals(sapResponse.getStatus())) {
				/* หากเชื่อมต่อ SAP ได้แต่มี response error */
				/* SAP_FAIL */
				sapResponse.setMessage(sapResponse.getRawJsonStringFromSAP());
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status and des
				dataFindHdr.setSapErrorDesc(sapResponse.getRawJsonStringFromSAP());
				dataFindHdr.setSapJsonRes(sapResponse.getRawJsonStringFromSAP());
				dataFindHdr.setSapStatus(sapResponse.getStatus());
				ricPosRevenueCustomerRepository.save(dataFindHdr);
			} else if (SAPConstants.SAP_CONNECTION_FAIL.equals(sapResponse.getStatus())) {
				/* หากไม่สามารถเชื่อมต่อ SAP ได้ */
				/* SAP_CONNECTION_FAIL */
				sapResponse.setMessage("ไม่สามารถเชื่อมต่อ SAP ได้");
				sapResponse.setMessageType(sapResponse.getStatus());

				// save response and status and des
				dataFindHdr.setSapErrorDesc("ไม่สามารถเชื่อมต่อ SAP ได้");
				dataFindHdr.setSapStatus(sapResponse.getStatus());
				ricPosRevenueCustomerRepository.save(dataFindHdr);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sapResponse;
	}

	public List<PosRevenueCustomerRes> getRevenueCustomerList() {
		List<PosRevenueCustomerRes> cusList = pos003Dao.findByCondition();
		return cusList;
	}

	@Transactional
	public void save(Pos003Req req) throws IOException {
		saveRevenueCustomer(req);
		saveRevenueCustomerProduct(req);
		saveRevenueCustomerPayment(req);
		saveFile(req);
	}

	public void saveRevenueCustomer(Pos003Req request) {
		RicPosRevenueCustomer cus = null;
		PosRevenueCustomerReq req = request.getHeader();
		if (null == req.getRevCusId()) {
			cus = new RicPosRevenueCustomer();
			cus.setCustomerCode(req.getCustomerCode());
			cus.setCustomerName(req.getCustomerName());
			cus.setContractNo(req.getContractNo());
			cus.setStartSaleDate(ConvertDateUtils.parseStringToDate(req.getStartSaleDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			cus.setEndSaleDate(ConvertDateUtils.parseStringToDate(req.getEndSaleDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			cus.setFileName(req.getFileName());
			cus.setSentStatus(SendStatus.UNSENT);
			cus.setCreatedDate(new Date());
			cus.setCreatedBy("Tester");
			cus.setIsDelete(FLAG.N_FLAG);
		} else {
			cus = ricPosRevenueCustomerRepository.findById(req.getRevCusId()).get();
			cus.setCustomerCode(req.getCustomerCode());
			cus.setCustomerName(req.getCustomerName());
			cus.setContractNo(req.getContractNo());
			cus.setStartSaleDate(ConvertDateUtils.parseStringToDate(req.getStartSaleDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			cus.setEndSaleDate(ConvertDateUtils.parseStringToDate(req.getEndSaleDate(), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			cus.setFileName(req.getFileName());
			cus.setSentStatus(SendStatus.UNSENT);
			cus.setUpdatedDate(new Date());
			cus.setUpdatedBy("Tester");
		}
		ricPosRevenueCustomerRepository.save(cus);
	}

	private Boolean checkListProduct(Long reqId, String contractNo, Date saleDate) {
		boolean isTrue = false;
		List<RicPosRevenueCustomerProduct> product = ricPosRevenueCustomerProductRepository
				.findByContractNoAndSaleDateY(contractNo, saleDate);
		if (null != reqId) {
			for (RicPosRevenueCustomerProduct pro : product) {
				if (reqId.equals(pro.getCusProId())) {
					isTrue = true;
					break;
				}
			}
		}
		return isTrue;
	}

	@Transactional
	public void saveRevenueCustomerProduct(Pos003Req request) {
		List<PosRevenueCustomerProductReq> req = request.getProduct();
		List<RicPosRevenueCustomerProduct> productSaveList = new ArrayList<>();
		RicPosRevenueCustomerProduct productSave = null;
		// setAll isDelete to 'Y'
		List<RicPosRevenueCustomerProduct> productOldList = ricPosRevenueCustomerProductRepository
				.findByContractNoAndSaleDate(request.getHeader().getContractNo(),
						ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate() + " 00:00:00",
								ConvertDateUtils.DD_MM_YYYY_HHMMSS, ConvertDateUtils.LOCAL_EN));
		for (RicPosRevenueCustomerProduct pro : productOldList) {
			pro.setIsDelete(FLAG.Y_FLAG);
		}
		ricPosRevenueCustomerProductRepository.saveAll(productOldList);

		for (PosRevenueCustomerProductReq rq : req) {
			if (productOldList.size() > 0 && checkListProduct(rq.getCusProId(), request.getHeader().getContractNo(),
					ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate() + " 00:00:00",
							ConvertDateUtils.DD_MM_YYYY_HHMMSS, ConvertDateUtils.LOCAL_EN))) {
				productSave = ricPosRevenueCustomerProductRepository.findById(rq.getCusProId()).get();
				productSave.setContractNo(request.getHeader().getContractNo());
				productSave.setSaleDate(ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				productSave.setProductType(rq.getProductType());
				productSave.setIncludingVatSale(new BigDecimal(rq.getIncludingVatSale()));
				productSave.setExcludingVatSale(new BigDecimal(rq.getExcludingVatSale()));
				productSave.setReceiptNum(Long.valueOf(rq.getReceiptNum()));
				productSave.setUpdatedDate(new Date());
				productSave.setUpdatedBy("Tester");
				productSave.setIsDelete(FLAG.N_FLAG);
			} else {
				productSave = new RicPosRevenueCustomerProduct();
				productSave.setContractNo(request.getHeader().getContractNo());
				productSave.setSaleDate(ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				productSave.setProductType(rq.getProductType());
				productSave.setIncludingVatSale(new BigDecimal(rq.getIncludingVatSale()));
				productSave.setExcludingVatSale(new BigDecimal(rq.getExcludingVatSale()));
				productSave.setReceiptNum(Long.valueOf(rq.getReceiptNum()));
				productSave.setCreatedDate(new Date());
				productSave.setCreatedBy("Tester");
				productSave.setIsDelete(FLAG.N_FLAG);
			}
			productSaveList.add(productSave);
		}
		ricPosRevenueCustomerProductRepository.saveAll(productSaveList);
	}

	private Boolean checkListPayment(Long reqId, String contractNo, Date saleDate) {
		boolean isTrue = false;
		List<RicPosRevenueCustomerPayment> payment = ricPosRevenueCustomerPaymentRepository
				.findByContractNoAndSaleDateY(contractNo, saleDate);
		if (null != reqId) {
			for (RicPosRevenueCustomerPayment pay : payment) {
				if (reqId.equals(pay.getCusPayId())) {
					isTrue = true;
					break;
				}
			}			
		}
		return isTrue;
	}

	@Transactional
	public void saveRevenueCustomerPayment(Pos003Req request) {
		List<PosRevenueCustomerPaymentReq> req = request.getPayment();
		List<RicPosRevenueCustomerPayment> paymentSaveList = new ArrayList<>();
		RicPosRevenueCustomerPayment paymentSave = null;
		// setAll isDelete to 'Y'
		List<RicPosRevenueCustomerPayment> paymentOldList = ricPosRevenueCustomerPaymentRepository
				.findByContractNoAndSaleDate(request.getHeader().getContractNo(),
						ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate() + " 00:00:00",
								ConvertDateUtils.DD_MM_YYYY_HHMMSS, ConvertDateUtils.LOCAL_EN));
		for (RicPosRevenueCustomerPayment pay : paymentOldList) {
			pay.setIsDelete("Y");
		}
		ricPosRevenueCustomerPaymentRepository.saveAll(paymentOldList);

		for (PosRevenueCustomerPaymentReq rq : req) {
			if (paymentOldList.size() > 0 && checkListPayment(rq.getCusPayId(), request.getHeader().getContractNo(),
					ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate() + " 00:00:00",
							ConvertDateUtils.DD_MM_YYYY_HHMMSS, ConvertDateUtils.LOCAL_EN))) {
				paymentSave = ricPosRevenueCustomerPaymentRepository.findById(rq.getCusPayId()).get();
				paymentSave.setContractNo(request.getHeader().getContractNo());
				paymentSave.setSaleDate(ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				paymentSave.setPaymentType(rq.getPaymentType());
				paymentSave.setCurrency(rq.getCurrency());
				paymentSave.setExchangeRate(new BigDecimal(rq.getExchangeRate()));
				paymentSave.setAmount(new BigDecimal(rq.getAmount()));
				paymentSave.setAmountBaht(new BigDecimal(rq.getAmountBaht()));
				paymentSave.setUpdatedDate(new Date());
				paymentSave.setUpdatedBy("Tester");
				paymentSave.setIsDelete("N");
			} else {
				paymentSave = new RicPosRevenueCustomerPayment();
				paymentSave.setContractNo(request.getHeader().getContractNo());
				paymentSave.setSaleDate(ConvertDateUtils.parseStringToDate(request.getHeader().getStartSaleDate(),
						ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				paymentSave.setPaymentType(rq.getPaymentType());
				paymentSave.setCurrency(rq.getCurrency());
				paymentSave.setExchangeRate(new BigDecimal(rq.getExchangeRate()));
				paymentSave.setAmount(new BigDecimal(rq.getAmount()));
				paymentSave.setAmountBaht(new BigDecimal(rq.getAmountBaht()));
				paymentSave.setCreatedDate(new Date());
				paymentSave.setCreatedBy("Tester");
				paymentSave.setIsDelete(FLAG.N_FLAG);
			}
			paymentSaveList.add(paymentSave);
		}
		ricPosRevenueCustomerPaymentRepository.saveAll(paymentSaveList);
	}

	public void saveFile(Pos003Req req) throws IOException {
		if (null != req.getFile()) {
			byte[] byteArray = req.getFile().getBytes();
		}
	}

	public Pos003Res getValueEdit(Long id) {
		Pos003Res dataRes = new Pos003Res();
		RicPosRevenueCustomer dataFindHdr = ricPosRevenueCustomerRepository.findById(id).get();
		PosRevenueCustomerRes header = new PosRevenueCustomerRes();
		List<RicPosRevenueCustomerProduct> productFindList = ricPosRevenueCustomerProductRepository
				.findBySaleDate(dataFindHdr.getStartSaleDate());
		List<PosRevenueCustomerProductRes> productList = new ArrayList<>();
		PosRevenueCustomerProductRes product = null;
		List<RicPosRevenueCustomerPayment> paymentFindList = ricPosRevenueCustomerPaymentRepository
				.findBySaleDate(dataFindHdr.getStartSaleDate());
		List<PosRevenueCustomerPaymentRes> paymentList = new ArrayList<>();
		PosRevenueCustomerPaymentRes payment = null;
		header.setRevCusId(dataFindHdr.getRevCusId());
		header.setContractNo(dataFindHdr.getContractNo());
		header.setCustomerCode(dataFindHdr.getCustomerCode());
		header.setCustomerName(dataFindHdr.getCustomerName());
		header.setStartSaleDate(ConvertDateUtils.formatDateToString(dataFindHdr.getStartSaleDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		header.setEndSaleDate(ConvertDateUtils.formatDateToString(dataFindHdr.getEndSaleDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		header.setFileName(dataFindHdr.getFileName());
		dataRes.setHeader(header);
		if (null != productFindList && productFindList.size() > 0) {
			for (RicPosRevenueCustomerProduct pro : productFindList) {
				product = new PosRevenueCustomerProductRes();
				product.setCusProId(pro.getCusProId());
				product.setContractNo(pro.getContractNo());
				product.setProductType(pro.getProductType());
				product.setIncludingVatSale(pro.getIncludingVatSale());
				product.setExcludingVatSale(pro.getExcludingVatSale());
				product.setReceiptNum(pro.getReceiptNum());

				productList.add(product);
			}
			dataRes.setProduct(productList);
		}
		if (null != paymentFindList && paymentFindList.size() > 0) {
			for (RicPosRevenueCustomerPayment pay : paymentFindList) {
				payment = new PosRevenueCustomerPaymentRes();
				payment.setCusPayId(pay.getCusPayId());
				payment.setContractNo(pay.getContractNo());
				payment.setBranch(pay.getBranch());
				payment.setPaymentType(pay.getPaymentType());
				payment.setCurrency(pay.getCurrency());
				payment.setExchangeRate(pay.getExchangeRate());
				payment.setAmount(pay.getAmount());
				payment.setAmountBaht(pay.getAmountBaht());

				paymentList.add(payment);
			}
			dataRes.setPayment(paymentList);
		}
		return dataRes;
	}

}
