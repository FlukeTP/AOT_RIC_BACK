package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It011Req;
import aot.it.vo.response.It001Res;
import aot.it.vo.response.It011Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It011Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It011Res> getAll(It011Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" 	list.*, "
				+ " sap.docno, "
				+ " sap.dzdocNo, "
				+ " req.request_start_date start_date, "
				+ " req.request_end_date end_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND list.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 	ELSE 'X' ");
		sqlBuilder.append(" END AS showButton ");
		sqlBuilder.append(" ,CASE "
				+ " WHEN FORMAT (req.request_end_date, 'yyyyMMdd') < FORMAT (getdate(), 'yyyyMMdd') THEN 'X' "
			+ " ELSE '' "
			+ " END AS FLAG_ENDDATE ");
		sqlBuilder.append(" FROM ric_it_network_service_list list ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON list.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" LEFT JOIN ric_it_network_create_invoice req ");
		sqlBuilder.append(" ON list.network_create_invoice_id = req.network_create_invoice_id ");
		sqlBuilder.append(" WHERE list.is_deleted = 'N' ");
		// find month and year
//		String[] date = request.getMonthly().split("/");
//		sqlBuilder.append(" AND list.months = ? ");
//		params.add(Long.valueOf(date[0]).toString());
//		sqlBuilder.append(" AND list.years = ? ");
//		params.add(date[1]);
		sqlBuilder.append(" AND PERIOD_MONTH = ? ");
		params.add(request.getPeriodMonth().trim());
		if (StringUtils.isNotBlank(request.getCustomerName())) {
			sqlBuilder.append(" AND customer_name LIKE ? ");
			params.add("%" + request.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add("%" + request.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(request.getItLocation())) {
			sqlBuilder.append(" AND trash_location LIKE ? ");
			params.add("%" + request.getItLocation().trim() + "%");
		}
		if (StringUtils.isNotBlank(request.getRentalObject())) {
			sqlBuilder.append(" AND rental_object LIKE ? ");
			params.add("%" + request.getRentalObject().trim() + "%");
		}
		sqlBuilder.append(" ORDER BY it_network_service_id DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), itReqRowMapper);
	}

	private RowMapper<It011Res> itReqRowMapper = new RowMapper<It011Res>() {
		@Override
		public It011Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It011Res vo = new It011Res();
			vo.setItNetworkServiceId(rs.getLong("it_network_service_id"));
			vo.setNetworkCreateInvoiceId(rs.getLong("network_create_invoice_id"));
//			vo.setMonths(rs.getString("months"));
//			vo.setYears(rs.getString("years"));
			vo.setYears(rs.getString("period_month"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setItLocation(rs.getString("it_location"));
			vo.setRentalObject(rs.getString("rental_object"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setStartDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("start_date")));
			vo.setEndDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("end_date")));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
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
	
	public List<It001Res> checkPeriodMonthBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_IT_NETWORK_CREATE_INVOICE REQ  ");
		sqlBuilder.append(" LEFT JOIN SAP_CUSTOMER CUST ");
		sqlBuilder.append(" 	ON REQ.entreprenuer_branch = CUST.PARTNER AND SUBSTRING(REQ.entreprenuer_branch, 1, 5) = CUST.ADR_KIND ");
		sqlBuilder.append(" WHERE 1=1 ");
		sqlBuilder.append(" AND NOT EXISTS( "
				+ " SELECT INFO.network_create_invoice_id "
				+ " FROM RIC_IT_NETWORK_SERVICE_LIST INFO "
				+ " 	WHERE REQ.network_create_invoice_id = INFO.network_create_invoice_id "
				+ " 	AND PERIOD_MONTH = ? "
				+ ")" );
		params.add(periodMonth);
		sqlBuilder.append(" AND FORMAT (REQ.request_start_date, 'yyyyMM') <= ? ");
		params.add(periodMonth);
		sqlBuilder.append(" AND (FORMAT (REQ.request_end_date, 'yyyyMM') >= ? ) ");
		params.add(periodMonth);
		sqlBuilder.append(" AND REQ.IS_DELETE = 'N' ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<It001Res>() {
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
				vo.setRequestStartDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("request_start_date")));
				vo.setRequestEndDate(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("request_end_date")));
				vo.setRemark(rs.getString("remark"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				return vo;
			}
		});
	}
}
