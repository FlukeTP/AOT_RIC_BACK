package aot.common.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Service
public class RicNoGenerator {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getRicNo() {
		// RICBBYYMMxxxxxxxxxx
		String BB = UserLoginUtils.getUser().getAirportCode();
		String YYMM = ConvertDateUtils.formatDateToString(new Date(), ConvertDateUtils.YYMM, ConvertDateUtils.LOCAL_EN);
		return "RIC".concat(BB.substring(BB.length()-2,BB.length())).concat(YYMM).concat(StringUtils.leftPad(String.valueOf(getSequence()), 7, "0")); 
	}

	public Long getSequence() {
		String sql = "SELECT NEXT VALUE FOR ric_no_generator";
		return jdbcTemplate.queryForObject(sql, new Object[] {}, Long.class);
	}

}
