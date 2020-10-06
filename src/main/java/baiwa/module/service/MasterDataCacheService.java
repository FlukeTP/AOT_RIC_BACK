package baiwa.module.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baiwa.common.bean.ResponseData;
import baiwa.constant.ProjectConstant.RESPONSE_MESSAGE;
import baiwa.constant.ProjectConstant.RESPONSE_STATUS;
import baiwa.module.model.MasterDataCacheItem;
import baiwa.module.model.MasterReq;
import baiwa.module.repository.MasterDataCacheDao;
import baiwa.support.ApplicationCache;

@Service
public class MasterDataCacheService {
	
	@Autowired
	private MasterDataCacheDao masterDataCacheDao;
	
	public HashMap<String, List<MasterDataCacheItem>> getListMaster(){
		List<String> header = masterDataCacheDao.getHeaderKey();
		List<MasterDataCacheItem> itemDetail = null;
		HashMap<String, List<MasterDataCacheItem>> allItem =  new HashMap<String, List<MasterDataCacheItem>>();
		
		for (String item : header) {
			itemDetail = masterDataCacheDao.getDetailItem(item);
			if(StringUtils.isNotBlank(item)) {
				allItem.put(item, itemDetail);
			}
		}
		return allItem;
	}
	
	
	public ResponseData<List<MasterDataCacheItem>> getDrondown( MasterReq req ){
		HashMap<String, List<MasterDataCacheItem>> listCache = ApplicationCache.getMaster();
		
		ResponseData<List<MasterDataCacheItem>> response = new  ResponseData<List<MasterDataCacheItem>>();
		response.setData(listCache.get(req.getKeyword()));
		response.setMessage(RESPONSE_MESSAGE.SUCCESS);
		response.setStatus(RESPONSE_STATUS.SUCCESS);
		return response;
	}
}
