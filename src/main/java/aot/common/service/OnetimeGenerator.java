package aot.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.common.constant.RICConstants;
import baiwa.common.util.paramconfig.ParamConfig;
import baiwa.util.UserLoginUtils;

@Service
public class OnetimeGenerator {
	
	@Autowired
	private ParamConfig paramConfig;
	
	public String getGenerate(String category) {
		final String prefix = paramConfig.getParamConfig(RICConstants.ONETIME.PREFIX);
		String categoryCode = "04";
		if(RICConstants.CATEGORY.ELECTRICITY.equals(category)) {
			categoryCode = "01";
		}else if(RICConstants.CATEGORY.WATER.equals(category)) {
			categoryCode = "02";
		}else if(RICConstants.CATEGORY.PHONE.equals(category)) {
			categoryCode = "03";
		}
		String buPlace = UserLoginUtils.getUser().getAirportCode();
		return  prefix.concat(buPlace.substring(buPlace.length()-2,buPlace.length())).concat(categoryCode);
	}

	

}
