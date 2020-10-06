package baiwa.module.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.module.model.FwLovDetail;
import baiwa.module.repository.jpa.FwLovDetailRepository;
import baiwa.module.vo.request.FwLovDetailFormReq;
import baiwa.module.vo.request.FwLovDetailReq;
import baiwa.module.vo.request.FwLovHReq;

@Service
public class FwLovDetailService {

	@Autowired
	private FwLovDetailRepository fwLovDetailRepository;

	public void saveLovDetil(FwLovDetailFormReq vo) {
			FwLovDetail val1 = null;
			List<FwLovDetail> fwLovDetails = new ArrayList<>();	
			for (FwLovDetailReq data1 : vo.getFwLovDetailReqs()) {
				val1 = new FwLovDetail();
						val1.setLovCode(data1.getLovCode());
						val1.setDescTh1(data1.getDescTh1());
						val1.setDescTh2(data1.getDescTh2());
						val1.setDescEn1(data1.getDescEn1());
						val1.setDescEn2(data1.getDescEn2());
						val1.setOrderNo(data1.getOrderNo());
						val1.setLovKey(vo.getLovKey());
						fwLovDetails.add(val1);
			}
			fwLovDetailRepository.saveAll(fwLovDetails);
		}
	
	
	public List<FwLovDetail> listDataDetail(FwLovHReq form) {
		List<FwLovDetail> fwLovDetailsList = new ArrayList<FwLovDetail>();
		fwLovDetailsList = fwLovDetailRepository.findByLovKey(form.getLovKey());
		return fwLovDetailsList;
	}
	
	public FwLovDetail findByLovCode(FwLovDetailReq form) {
		return fwLovDetailRepository.findByLovKeyAndLovCode(form.getLovKey(), form.getLovCode());
	}
	
	
}
