package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It0103Req;
import aot.it.vo.response.It0103Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It0103Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It0103Res> findAll(It0103Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT it_cute_config_id, ");
		sqlBuilder.append(" 	effective_date, ");
		sqlBuilder.append(" 	service_type, ");
		sqlBuilder.append(" 	charge_rate, ");
		sqlBuilder.append(" 	remark, ");
		sqlBuilder.append(" 	color_room, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_date IS NOT NULL THEN updated_date ");
		sqlBuilder.append(" 	ELSE created_date ");
		sqlBuilder.append(" 	END AS updated_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_by IS NOT NULL THEN updated_by ");
		sqlBuilder.append(" 	ELSE created_by ");
		sqlBuilder.append(" 	END AS updated_by ");
		sqlBuilder.append(" FROM ric_it_cute_training_charge_rates_config ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY it_cute_config_id DESC ");
		List<It0103Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<It0103Res> listRowmapper = new RowMapper<It0103Res>() {
		@Override
		public It0103Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It0103Res vo = new It0103Res();
			vo.setItCUTETrainingConfigId(rs.getLong("it_cute_config_id"));
			vo.setEffectiveDate(ConvertDateUtils.formatDateToString(rs.getDate("effective_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setServiceType(rs.getString("service_type"));
			vo.setChargeRate(rs.getBigDecimal("charge_rate"));
			vo.setRemark(rs.getString("remark"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setColorRoom(rs.getString("color_room"));
			return vo;
		}
	};
}
