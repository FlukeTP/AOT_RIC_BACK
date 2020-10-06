package aot.pos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.pos.vo.response.PosRevenueCustomerRes;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Pos003Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public List<PosRevenueCustomerRes> findByCondition() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * ");
		sqlBuilder.append(" FROM ric_pos_revenue_customer cus ");
		sqlBuilder.append(" LEFT JOIN ( ");
		sqlBuilder.append(" 	SELECT ");
		sqlBuilder.append(" 		SUM(including_vat_sale) AS including_vat_sale, ");
		sqlBuilder.append(" 		SUM(excluding_vat_sale) AS excluding_vat_sale, ");
		sqlBuilder.append(" 		sale_date ");
		sqlBuilder.append(" 	FROM ");
		sqlBuilder.append(" 		ric_pos_revenue_customer_product ");
		sqlBuilder.append(" 	GROUP BY ");
		sqlBuilder.append(" 		sale_date) pro ON ");
		sqlBuilder.append(" 	cus.start_sale_date = pro.sale_date ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");

		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), posRevenueCustomerRowMapper);
	}

	private RowMapper<PosRevenueCustomerRes> posRevenueCustomerRowMapper = new RowMapper<PosRevenueCustomerRes>() {
		@Override
		public PosRevenueCustomerRes mapRow(ResultSet rs, int arg1) throws SQLException {
			PosRevenueCustomerRes vo = new PosRevenueCustomerRes();
			vo.setRevCusId(rs.getLong("rev_cus_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setStartSaleDate(ConvertDateUtils.formatDateToString(rs.getDate("start_sale_date"), ConvertDateUtils.DD_MM_YYYY));
			vo.setEndSaleDate(ConvertDateUtils.formatDateToString(rs.getDate("end_sale_date"), ConvertDateUtils.DD_MM_YYYY));
			vo.setIncludingVatSale(rs.getBigDecimal("including_vat_sale"));
			vo.setExcludingVatSale(rs.getBigDecimal("excluding_vat_sale"));
			vo.setFileName(rs.getString("file_name"));
			vo.setSentStatus(rs.getString("sent_status"));
			vo.setCreatedDate(ConvertDateUtils.formatDateToString(rs.getDate("created_date"), ConvertDateUtils.DD_MM_YYYY));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapErrorDesc(rs.getString("sap_error_desc"));
			return vo;
		}
	};
}
