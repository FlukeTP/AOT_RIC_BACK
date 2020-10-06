package baiwa.module.model;

import java.util.HashMap;
import java.util.List;

public class MasterDataCache {
	private HashMap< String , List<MasterDataCacheItem>> item;

	public HashMap<String, List<MasterDataCacheItem>> getItem() {
		return item;
	}

	public void setItem(HashMap<String, List<MasterDataCacheItem>> item) {
		this.item = item;
	}

}