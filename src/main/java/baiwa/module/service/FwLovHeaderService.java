package baiwa.module.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.module.model.FwLovDetail;
import baiwa.module.model.FwLovHeader;
import baiwa.module.repository.FwLovHeaderDao;
import baiwa.module.repository.jpa.FwLovDetailRepository;
import baiwa.module.repository.jpa.FwLovHeaderRepository;
import baiwa.module.vo.request.FwLovDetailFormReq;
import baiwa.module.vo.request.FwLovDetailReq;
import baiwa.module.vo.request.FwLovHReq;

@Service
public class FwLovHeaderService {

	private static final Logger logger = LoggerFactory.getLogger(FwLovHeaderService.class);

	@Autowired
	private FwLovHeaderRepository fwLovHeaderRepository;

	@Autowired
	private FwLovDetailRepository fwLovDetailRepository;

	@Autowired
	private FwLovHeaderDao fwLovHeaderDao;

	public String saveLovHearder(FwLovHReq request) {

		FwLovHeader fwLovHeader = null;
		String msg = "";
		try {
			fwLovHeader = fwLovHeaderRepository.findBylovKey(request.getLovKey());
			// check data base
			if (fwLovHeader == null) {
				//  don't have any data
				fwLovHeader = new FwLovHeader();
				fwLovHeader.setLovKey(request.getLovKey().trim());
				fwLovHeader.setDescripton(request.getDescripton());
				fwLovHeader.setCreatedBy("Phattartapong krintavee");
				fwLovHeader.setCreateDate(new Date());
				fwLovHeader.setIsDelete("N");
				fwLovHeaderRepository.save(fwLovHeader);
				msg = RESPONSE_MESSAGE.SAVE.SUCCESS;
			} else {
				// have data
				msg = RESPONSE_MESSAGE.SAVE.DUPLICATE_DATA;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return msg;

	}

	public void editLovHearder(FwLovDetailFormReq request) {
		FwLovHeader fwLovHeader = null;
		fwLovHeader = new FwLovHeader();
		fwLovHeader = fwLovHeaderRepository.findById(request.getFwLovHReq().getLovHeaderId()).get();
		// set data
		fwLovHeader.setLovKey(request.getFwLovHReq().getLovKey());
		fwLovHeader.setDescripton(request.getFwLovHReq().getDescripton());
		fwLovHeader.setUpdatedBy("Phattartapong krintavee");
		fwLovHeader.setUpdatedDate(new Date());
		// save data
		fwLovHeaderRepository.save(fwLovHeader);

		if (request.getFwLovDetailReqs() != null && request.getFwLovDetailReqs().size() > 0) {
			FwLovDetail val1 = null;
			List<FwLovDetail> fwLovDetails = new ArrayList<>();
			for (FwLovDetailReq data1 : request.getFwLovDetailReqs()) {
				val1 = new FwLovDetail();
				if (data1.getLovDetailId() != null) {
					val1 = fwLovDetailRepository.findById(data1.getLovDetailId()).get();
					val1.setLovKey(data1.getLovKey());
					val1.setLovCode(data1.getLovCode());
					val1.setDescTh1(data1.getDescTh1());
					val1.setDescTh2(data1.getDescTh2());
					val1.setDescEn1(data1.getDescEn1());
					val1.setDescEn2(data1.getDescEn2());
					val1.setOrderNo(data1.getOrderNo());
					val1.setUpdatedBy("Phattartapong krintavee");
					val1.setUpdatedDate(new Date());
					val1 = fwLovDetailRepository.save(val1);
				} else {
					val1.setLovKey(data1.getLovKey());
					val1.setLovCode(data1.getLovCode());
					val1.setDescTh1(data1.getDescTh1());
					val1.setDescTh2(data1.getDescTh2());
					val1.setDescEn1(data1.getDescEn1());
					val1.setDescEn2(data1.getDescEn2());
					val1.setOrderNo(data1.getOrderNo());
					val1.setCreatedBy("Phattartapong krintavee");
					val1.setCreateDate(new Date());
					fwLovDetails.add(val1);
				}
			}
			fwLovDetailRepository.saveAll(fwLovDetails);
		}
	}

	public List<FwLovHeader> list(FwLovHReq form) {
		List<FwLovHeader> fwLovList = new ArrayList<FwLovHeader>();
		fwLovList = fwLovHeaderDao.list(form);
		return fwLovList;
	}

	public FwLovHeader listdata(FwLovHReq form) {
		FwLovHeader fwLovListData = new FwLovHeader();
		fwLovListData = fwLovHeaderRepository.findById(form.getLovHeaderId()).get();
		return fwLovListData;
	}

}
