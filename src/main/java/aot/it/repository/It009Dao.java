package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It009Req;
import aot.it.vo.response.It009Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It009Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It009Res> findAll(It009Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT req.it_page_req_id, ");
		sqlBuilder.append(" 	req.customer_code, ");
		sqlBuilder.append(" 	req.customer_name, ");
		sqlBuilder.append(" 	req.customer_branch, ");
		sqlBuilder.append(" 	req.contract_no, ");
		sqlBuilder.append(" 	req.staff_type, ");
		sqlBuilder.append(" 	req.public_type, ");
		sqlBuilder.append(" 	req.staff_page_num, ");
		sqlBuilder.append(" 	req.public_page_num, ");
		sqlBuilder.append(" 	req.charge_rates, ");
		sqlBuilder.append(" 	req.vat, ");
		sqlBuilder.append(" 	req.total_amount, ");
		sqlBuilder.append(" 	req.status, ");
		sqlBuilder.append(" 	req.request_start_date, ");
		sqlBuilder.append(" 	req.request_end_date, ");
		sqlBuilder.append(" 	req.remark, ");
		sqlBuilder.append(" 	req.airport, ");
		sqlBuilder.append(" 	req.transaction_no, ");
		sqlBuilder.append(" 	req.invoice_no, ");
		sqlBuilder.append(" 	req.sap_status, ");
		sqlBuilder.append(" 	req.sap_error_desc, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN (sap.reverse_inv != 'X' AND sap.reverse_inv IS NOT NULL ");
		sqlBuilder.append(" 		AND sap.reverse_rec != 'X' AND sap.reverse_rec IS NOT NULL) ");
		sqlBuilder.append(" 		OR cnc.sap_status_lg = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_it_staff_page_public_page_req req ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" WHERE req.is_delete = 'N' ");
//		if (StringUtils.isNotEmpty(form.getCustomerName())) {
//			sqlBuilder.append(" AND cnc.customer_name LIKE ? ");
//			params.add("%" + form.getCustomerName().trim() + "%");
//		}
		sqlBuilder.append(" ORDER BY req.it_page_req_id DESC ");
		List<It009Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}

	private RowMapper<It009Res> listRowmapper = new RowMapper<It009Res>() {
		@Override
		public It009Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It009Res vo = new It009Res();
			vo.setItPageReqId(rs.getLong("it_page_req_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setStaffType(rs.getString("staff_page_num"));
			vo.setPublicType(rs.getString("public_page_num"));
			vo.setStaffPageNum(rs.getLong("staff_page_num"));
			vo.setPublicPageNum(rs.getLong("public_page_num"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setStatus(rs.getString("status"));
			vo.setRequestStartDate(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setRequestEndDate(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapErrorDesc(rs.getString("sap_error_desc"));
			vo.setShowButton(rs.getString("showButton"));
			return vo;
		}
	};
}
