package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It0102Req;
import aot.it.vo.response.It0102Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It0102Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It0102Res> findAll(It0102Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT it_other_config_id, ");
		sqlBuilder.append(" 	effective_date, ");
		sqlBuilder.append(" 	service_type, ");
		sqlBuilder.append(" 	charge_rate_type, ");
		sqlBuilder.append(" 	charge_rate, ");
		sqlBuilder.append(" 	remark, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_date IS NOT NULL THEN updated_date ");
		sqlBuilder.append(" 	ELSE created_date ");
		sqlBuilder.append(" 	END AS updated_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_by IS NOT NULL THEN updated_by ");
		sqlBuilder.append(" 	ELSE created_by ");
		sqlBuilder.append(" 	END AS updated_by ");
		sqlBuilder.append(" FROM ric_it_other_charge_rates_config ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY it_other_config_id DESC ");
		List<It0102Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<It0102Res> listRowmapper = new RowMapper<It0102Res>() {
		@Override
		public It0102Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It0102Res vo = new It0102Res();
			vo.setItOtherConfigId(rs.getLong("it_other_config_id"));
			vo.setEffectiveDate(ConvertDateUtils.formatDateToString(rs.getDate("effective_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setChargeRateType(rs.getString("charge_rate_type"));
			vo.setServiceType(rs.getString("service_type"));
			vo.setChargeRate(rs.getBigDecimal("charge_rate"));
			vo.setRemark(rs.getString("remark"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setUpdatedBy(rs.getString("updated_by"));
			return vo;
		}
	};
}
