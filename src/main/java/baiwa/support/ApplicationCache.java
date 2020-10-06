package baiwa.support;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.tmb.ecert.common.dao.CertificateDao;
//import com.tmb.ecert.common.dao.ListOfValueDao;
//import com.tmb.ecert.common.dao.ParameterConfigDao;
//import com.tmb.ecert.common.dao.RoleDao;
//import com.tmb.ecert.common.domain.Certificate;
//import com.tmb.ecert.common.domain.ListOfValue;
//import com.tmb.ecert.common.domain.ParameterConfig;
//import com.tmb.ecert.common.domain.RoleVo;
//

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import baiwa.module.model.MasterDataCacheItem;
import baiwa.module.service.MasterDataCacheService;

@Component
public class ApplicationCache {
	private static final HashMap< String , List<MasterDataCacheItem>> MASTER_DATA = new HashMap< String , List<MasterDataCacheItem>>();
	
	@Autowired
	MasterDataCacheService masterDateService;
	
	
	/** Reload */
	@PostConstruct
	public synchronized void loadCache() {
		reloadData();
	}
	
	public static HashMap< String , List<MasterDataCacheItem>> getMaster() {
		return MASTER_DATA;
	}
	
	
	public void reloadData() {
		MASTER_DATA.clear();
		HashMap<String, List<MasterDataCacheItem>> listMasterCache = masterDateService.getListMaster();
		MASTER_DATA.putAll(listMasterCache);
		
		System.out.println(listMasterCache.get("56565"));
	}	
}
