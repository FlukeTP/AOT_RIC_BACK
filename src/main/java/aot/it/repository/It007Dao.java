package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It007Req;
import aot.it.vo.response.It007Res;
import aot.it.vo.response.It008Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It007Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It007Res> getAll(It007Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT");
		sqlBuilder.append(" 	list.*, "
				+ " sap.docno, "
				+ " sap.dzdocNo, "
				+ " req.request_start_date start_date, "
				+ " req.request_end_date end_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND list.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" ,CASE "
				+ " WHEN FORMAT (req.request_end_date, 'yyyyMMdd') < FORMAT (getdate(), 'yyyyMMdd') THEN 'X' "
			+ " ELSE '' "
			+ " END AS FLAG_ENDDATE ");
		sqlBuilder.append(" FROM ric_it_dedicated_cute_list list ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON list.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" LEFT JOIN ric_it_dedicated_cute_create_invoice req ");
		sqlBuilder.append(" ON list.dedicated_invoice_id = req.id ");
		sqlBuilder.append(" WHERE list.is_deleted = 'N' ");
		// find month and year
//		String[] date = request.getMonthly().split("/");
//		sqlBuilder.append(" AND list.months = ? ");
//		params.add(Long.valueOf(date[0]).toString());
//		sqlBuilder.append(" AND list.years = ? ");
//		params.add(date[1]);
		sqlBuilder.append(" AND PERIOD_MONTH = ? ");
		params.add(request.getPeriodMonth().trim());
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND entreprenuer_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}
		if (StringUtils.isNotBlank(request.getLocation())) {
			sqlBuilder.append(" AND location LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getLocation()));
		}
		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND entreprenuer_code LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		sqlBuilder.append(" ORDER BY id DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), itReqRowMapper);
	}

	private RowMapper<It007Res> itReqRowMapper = new RowMapper<It007Res>() {
		@Override
		public It007Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It007Res vo = new It007Res();
			vo.setId(rs.getLong("id"));
			vo.setDedicatedInvioceId(rs.getLong("dedicated_invoice_id"));
//			vo.setMonths(rs.getString("months"));
//			vo.setYears(rs.getString("years"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerBranch(rs.getString("entreprenuer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setLocation(rs.getString("location"));
			vo.setContractData(rs.getString("contract_data"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setRequestStartDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("start_date")));
			vo.setRequestEndDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("end_date")));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setFlagEndDate(rs.getString("FLAG_ENDDATE"));
			return vo;
		}
	};

	public List<It008Res> checkPeriodMonthBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_IT_DEDICATED_CUTE_CREATE_INVOICE REQ   ");
		sqlBuilder.append(" LEFT JOIN SAP_CUSTOMER CUST ");
		sqlBuilder.append(" 	ON REQ.entreprenuer_branch = CUST.PARTNER AND SUBSTRING(REQ.entreprenuer_branch, 1, 5) = CUST.ADR_KIND ");
		sqlBuilder.append(" WHERE 1=1 ");
		sqlBuilder.append(" AND NOT EXISTS( "
				+ " SELECT INFO.ID "
				+ " FROM RIC_IT_DEDICATED_CUTE_LIST INFO "
				+ " 	 WHERE REQ.ID = INFO.DEDICATED_INVOICE_ID "
				+ " 	AND PERIOD_MONTH = ? "
				+ ")" );
		params.add(periodMonth);
		sqlBuilder.append(" AND FORMAT (REQ.request_start_date, 'yyyyMM') <= ? ");
		params.add(periodMonth);
		sqlBuilder.append(" AND (FORMAT (REQ.request_end_date, 'yyyyMM') >= ? ) ");
		params.add(periodMonth);
		sqlBuilder.append(" AND REQ.IS_DELETE = 'N' ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<It008Res>() {
			@Override
			public It008Res mapRow(ResultSet rs, int arg1) throws SQLException {
				It008Res vo = new It008Res();
				vo.setId(rs.getLong("id"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setEntreprenuerBranch(rs.getString("entreprenuer_branch"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setLocation(rs.getString("location"));
				vo.setContractData(rs.getString("contract_data"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				return vo;
			}
		});
	}
}
