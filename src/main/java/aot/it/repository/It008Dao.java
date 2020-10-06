package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It008Req;
import aot.it.vo.response.It008Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It008Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It008Res> findData(It008Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_it_dedicated_cute_create_invoice ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");

		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add("%" +  request.getContractNo().replaceAll(" ", "%") + "%");
		}

		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND entreprenuer_code LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}

		sqlBuilder.append(" ORDER BY id ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), itReqRowMapper);
	}

	private RowMapper<It008Res> itReqRowMapper = new RowMapper<It008Res>() {
		@Override
		public It008Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It008Res vo = new It008Res();
			vo.setId(rs.getLong("id"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setLocation(rs.getString("location"));
			vo.setContractData(rs.getString("contract_data"));
			vo.setRequestStartDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("request_start_date")));
			vo.setRequestEndDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("request_end_date")));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));

			return vo;
		}
	};
}
