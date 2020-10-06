package aot.posControl.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.posControl.model.RicPosSaleProduct;
import aot.posControl.repository.RicPosSaleProductRepository;
import aot.posControl.vo.request.PosUploadRequest;
import aot.posControl.vo.response.RicPosSaleProductResponse;
import baiwa.util.ExcelUtils;
import baiwa.util.UserLoginUtils;

@Service
public class PosConUploadService {
	
	private static final Logger logger = LoggerFactory.getLogger(PosConUploadService.class);
	
	@Autowired
	private RicPosSaleProductRepository ricPosSaleProductRepository;
	
	@Autowired
	private PosSaleProductService posSaleProductService;
	
	@Transactional(rollbackOn = { Exception.class })
	public RicPosSaleProductResponse uploadExcel(PosUploadRequest req) throws Exception {
		RicPosSaleProductResponse res = new RicPosSaleProductResponse();
		List<List<String>> allLine = ExcelUtils.readExcel(req.getFileUpload());
		
		if(allLine.size() == 0) {
			res.setMessage("กรุณาตรวจสอบไฟล์");
		}else {
			res.setMessage(this.validateVal(allLine));
			if(StringUtils.isBlank(res.getMessage()) ) {		
				List<RicPosSaleProduct> entities = new ArrayList<RicPosSaleProduct>();
				RicPosSaleProduct entity = null;
				for (int i = 3; i < allLine.size(); i++) {
					
					List<String> column = allLine.get(i);
					if(StringUtils.isNotBlank(column.get(0)) ) {
						entity = this.setEntityVal(column, req.getFrequencyNo());
						entities.add(entity);
					}else {
						break;
					}
				}
				
				if(entities != null) {
					ricPosSaleProductRepository.saveAll(entities);
				}
			}
		}
		RicPosSaleProductResponse res2 = posSaleProductService.getSaleProduct(req.getFrequencyNo());
		res.setSaleProduct(res2.getSaleProduct());
		return res;
	}
	
	private RicPosSaleProduct setEntityVal( List<String> column, String frequencyId) {
		int j = 0;
		String saleNo = column.get(j);
		RicPosSaleProduct entity = ricPosSaleProductRepository.findByFrequencyReportIdAndSaleNo(Long.valueOf(frequencyId), saleNo );
		if(entity == null) {
			entity = new RicPosSaleProduct();
		}
		entity.setSaleNo(saleNo);
		entity.setSaleDate(column.get(++j));
		entity.setPosNo(column.get(++j));
		entity.setDocDate(column.get(++j));
		
		String val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setSaleType(null);
		}else {
			Integer saleType = Integer.parseInt(val);
			entity.setSaleType(saleType);
		}
		
		// column 6
		entity.setVoidDate(column.get(++j));
		entity.setVoidReason(column.get(++j));
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlSeq(null);
		}else {
			BigDecimal dtlSeq = new BigDecimal(val);
			entity.setDtlSeq(dtlSeq);
		}
		
		entity.setDtlProductCode(column.get(++j));
		entity.setDtlProductName(column.get(++j));
		
		//  column 11
		entity.setDtlReSalesType(column.get(++j));
		
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlVatType(null);
		}else {
			Integer dtlVatType = Integer.parseInt(val);
			entity.setDtlVatType(dtlVatType);
		}
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlVatRate(null);
		}else {
			BigDecimal dtlVatRat = new BigDecimal(val);
			entity.setDtlVatRate(dtlVatRat);
		}
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlProductQty(null);
		}else {
			BigDecimal dtlProductQty = new BigDecimal(val);
			entity.setDtlProductQty(dtlProductQty);
		}
		
		entity.setDtlUnitCode(column.get(++j));
		
		// column 16
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlUnitPrice(null);
		}else {
			BigDecimal dtlUnitPrice = new BigDecimal(val);
			entity.setDtlUnitPrice(dtlUnitPrice);
		}
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlDiscountAmt(null);
		}else {
			BigDecimal dtlDiscountAmt = new BigDecimal(val);
			entity.setDtlDiscountAmt(dtlDiscountAmt);
		}
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlVatAmt(null);
		}else {
			BigDecimal dtlVatAmt = new BigDecimal(val);
			entity.setDtlVatAmt(dtlVatAmt);
		}
		
		val = column.get(++j);
		if(StringUtils.isBlank(val)) {
			entity.setDtlAmtExcVat(null);
		}else {
			BigDecimal dtlAmtExcVat = new BigDecimal(val);
			entity.setDtlAmtExcVat(dtlAmtExcVat);
		}
		
		entity.setFrequencyReportId(Long.parseLong(frequencyId) );
		entity.setCreatedDate(UserLoginUtils.getDateNow());
		entity.setCreatedBy(UserLoginUtils.getCurrentUsername());
		entity.setUpdatedDate(UserLoginUtils.getDateNow());
		entity.setUpdatedBy(UserLoginUtils.getCurrentUsername());
		entity.setIsDelete("N");
		
		return entity;
	}

	private String validateVal(List<List<String>> allLine) {
		String message = ""; String saleDocNo = "";
		Set<String> lump = new HashSet<String>();
		  for (int i = 2 ; i< allLine.size(); i++)
		  {	saleDocNo = allLine.get(i).get(0);
		  	if(StringUtils.isNoneBlank(saleDocNo)) {
			    if (lump.contains(saleDocNo)) {
			    	message = saleDocNo +"ซ้ำ";
			    	break;
			    }
			    lump.add(saleDocNo);
		  	}else {
		  		break;
		  	}
		  }
		  
		return message;
	}
}
