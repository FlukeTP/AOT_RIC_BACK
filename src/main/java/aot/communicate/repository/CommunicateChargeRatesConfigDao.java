package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.communicate.vo.response.CommunicateConfigRes;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.NumberUtils;

@Repository
public class CommunicateChargeRatesConfigDao {
	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public CommunicateConfigRes handheidTransceiver(Date date) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM RIC_COMMUNICATE_HANDHEID_TRANSCEIVER_CHARGE_RATES_CONFIG ");
		sqlBuilder.append(" WHERE EFFECTIVE_DATE <= ? ");
		params.add(date);
		sqlBuilder.append(" ORDER BY EFFECTIVE_DATE DESC ");
		sqlBuilder.append(" OFFSET 0 ROWS ");
		sqlBuilder.append(" FETCH NEXT 1 ROWS ONLY ");
		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), new RowMapper<CommunicateConfigRes>() {
			@Override
			public CommunicateConfigRes mapRow(ResultSet rs, int arg1) throws SQLException {
				CommunicateConfigRes vo = new CommunicateConfigRes();
				vo.setId(rs.getLong("commu_transceiver_config_id"));
				vo.setChargeRateName(rs.getString("charge_rate_name"));
				vo.setServiceType(rs.getString("service_type"));
				vo.setChargeRate(rs.getBigDecimal("charge_rate"));
				vo.setInsuranceFee(NumberUtils.toDecimalFormat(rs.getBigDecimal("insurance_fee"), true));
				return vo;
			}
		});
	}
}
