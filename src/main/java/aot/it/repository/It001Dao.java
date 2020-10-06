package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It001Req;
import aot.it.vo.response.It001Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It001Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It001Res> findData(It001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_it_network_create_invoice ");
//		sqlBuilder.append(" LEFT JOIN ric_it_network_create_invoice_mapping "); 
//		sqlBuilder.append(" ON ric_it_network_create_invoice.network_create_invoice_id ");
//		sqlBuilder.append(" = ric_it_network_create_invoice_mapping.id_network_create_invoice ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");

		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add("%" +  request.getContractNo().replaceAll(" ", "%") + "%");
		}

		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND entreprenuer_code LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND entreprenuer_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		
		if (StringUtils.isNotBlank(request.getRequestStartDate())) {
			sqlBuilder.append(" AND request_start_date LIKE ? ");
			params.add(request.getRequestStartDate().concat("%"));
		}

		sqlBuilder.append(" ORDER BY network_create_invoice_id ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), itReqRowMapper);
	}

	private RowMapper<It001Res> itReqRowMapper = new RowMapper<It001Res>() {
		@Override
		public It001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It001Res vo = new It001Res();
			vo.setNetworkCreateInvoiceId(rs.getLong("network_create_invoice_id"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setEntreprenuerBranch(rs.getString("entreprenuer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setItLocation(rs.getString("it_location"));
			vo.setRentalObjectCode(rs.getString("rental_object_code"));
			vo.setRemark(rs.getString("remark"));
			vo.setRequestStartDate(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRequestEndDate(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));

			return vo;
		}
	};
}
