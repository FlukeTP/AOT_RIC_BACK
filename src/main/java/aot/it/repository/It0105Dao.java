package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It0105Req;
import aot.it.vo.response.It0105Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It0105Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It0105Res> findAll(It0105Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT it_page_config_id, ");
		sqlBuilder.append(" 	annual, ");
		sqlBuilder.append(" 	effective_date, ");
		sqlBuilder.append(" 	service_type, ");
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
		sqlBuilder.append(" FROM ric_it_staff_page_public_page_config ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
//		if (StringUtils.isNotEmpty(form.getCustomerName())) {
//			sqlBuilder.append(" AND cnc.customer_name LIKE ? ");
//			params.add("%" + form.getCustomerName().trim() + "%");
//		}
		sqlBuilder.append(" ORDER BY it_page_config_id DESC ");
		List<It0105Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<It0105Res> listRowmapper = new RowMapper<It0105Res>() {
		@Override
		public It0105Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It0105Res vo = new It0105Res();
			vo.setItPageConfigId(rs.getLong("it_page_config_id"));
			vo.setAnnual(rs.getString("annual"));
			vo.setEffectiveDate(ConvertDateUtils.formatDateToString(rs.getDate("effective_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
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
