package aot.firebrigade.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.firebrigade.vo.request.Firebrigade002Req;
import aot.firebrigade.vo.response.Firebrigade002Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Firebrigade002Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Firebrigade002Res> findFirebrigadeRateChargeConfig(Firebrigade002Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * ");
		sqlBuilder.append(" FROM ric_firebrigade_rate_charge_config ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
		if (StringUtils.isNotEmpty(form.getCourseName())) {
			sqlBuilder.append(" AND course_name LIKE ? ");
			params.add("%" + form.getCourseName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getEffectiveDate())) {
			sqlBuilder.append(" AND effective_date = ? ");
			params.add(form.getEffectiveDate());
		}
		sqlBuilder.append(" ORDER BY rate_config_id DESC ");
		List<Firebrigade002Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Firebrigade002Res> listRowmapper = new RowMapper<Firebrigade002Res>() {
		@Override
		public Firebrigade002Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Firebrigade002Res vo = new Firebrigade002Res();
			vo.setRateConfigId(Integer.toString(rs.getInt("rate_config_id")));
			vo.setCourseName(rs.getString("course_name"));
			vo.setChargeRates(rs.getBigDecimal("charge_rate"));
			vo.setEffectiveDate(ConvertDateUtils.formatDateToString(rs.getDate("effective_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setCreateDate(ConvertDateUtils.formatDateToString(rs.getDate("created_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setUnit(rs.getString("unit"));
			return vo;
		}
	};
}
