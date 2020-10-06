package aot.phone.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.phone.vo.request.Phone003Req;
import aot.phone.vo.response.Phone003Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Phone003Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Phone003Res> findPhoneCancel(Phone003Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" 	cnc.phone_cancel_id, ");
		sqlBuilder.append(" 	cnc.customer_code, ");
		sqlBuilder.append(" 	cnc.customer_name, ");
		sqlBuilder.append(" 	cnc.customer_branch, ");
		sqlBuilder.append(" 	cnc.contract_no, ");
		sqlBuilder.append(" 	cnc.phone_no, ");
		sqlBuilder.append(" 	cnc.date_cancel, ");
		sqlBuilder.append(" 	cnc.remark, ");
		sqlBuilder.append(" 	cnc.airport, ");
		sqlBuilder.append(" 	req.rental_area_code, ");
		sqlBuilder.append(" 	req.request_start_date, ");
		sqlBuilder.append(" 	req.request_end_date, ");
		sqlBuilder.append(" 	cnc.invoice_no_reqcash, ");
		sqlBuilder.append(" 	cnc.invoice_no_reqlg, ");
		sqlBuilder.append(" 	cnc.receipt_no_reqcash, ");
		sqlBuilder.append(" 	cnc.receipt_no_reqlg, ");
		sqlBuilder.append(" 	rate.charge_rates, ");
		sqlBuilder.append(" 	rate.vat, ");
		sqlBuilder.append(" 	rate.total_charge_rates, ");
		sqlBuilder.append(" 	cnc.transaction_no_lg, ");
		sqlBuilder.append(" 	cnc.sap_status_lg, ");
		sqlBuilder.append(" 	cnc.sap_error_desc_lg, ");
		sqlBuilder.append(" 	sap_lg.docno, ");
		sqlBuilder.append(" 	sap_lg.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap_lg.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap_lg.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND cnc.sap_status_lg = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_phone_req_cancel cnc ");
		sqlBuilder.append(" LEFT JOIN ric_phone_req req ");
		sqlBuilder.append(" ON cnc.phone_req_id = req.phone_req_id ");
		sqlBuilder.append(" LEFT JOIN (SELECT SUM(charge_rates) charge_rates, ");
		sqlBuilder.append("         SUM(vat) vat, ");
		sqlBuilder.append("         SUM(total_charge_rates) total_charge_rates, req_id ");
		sqlBuilder.append(" 	FROM ric_phone_rate_charge ");
		sqlBuilder.append(" 	GROUP BY req_id) rate ");
		sqlBuilder.append(" ON cnc.phone_req_id = rate.req_id ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap_lg ");
		sqlBuilder.append(" 	ON cnc.transaction_no_lg = sap_lg.refkey1 ");
		sqlBuilder.append(" WHERE cnc.is_delete = 'N' ");
		if (StringUtils.isNotEmpty(form.getCustomerName())) {
			sqlBuilder.append(" AND cnc.customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getContractNo())) {
			sqlBuilder.append(" AND cnc.contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getPhoneNo())) {
			sqlBuilder.append(" AND cnc.phone_no LIKE ? ");
			params.add("%" + form.getPhoneNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getDateCancel())) {
			
		}
		sqlBuilder.append(" ORDER BY cnc.phone_cancel_id DESC ");
		List<Phone003Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Phone003Res> listRowmapper = new RowMapper<Phone003Res>() {
		@Override
		public Phone003Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Phone003Res vo = new Phone003Res();
			vo.setPhoneCancelId(Integer.toString(rs.getInt("phone_cancel_id")));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setPhoneNo(rs.getString("phone_no"));
			vo.setRentalAreaCode(rs.getString("rental_area_code"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalchargeRates(rs.getBigDecimal("total_charge_rates"));
			vo.setDateCancel(ConvertDateUtils.formatDateToString(rs.getDate("date_cancel"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setInvoiceNoReqcash(rs.getString("invoice_no_reqcash"));
			vo.setReceiptNoReqcash(rs.getString("receipt_no_reqcash"));
			vo.setInvoiceNoReqlg(rs.getString("invoice_no_reqlg"));
			vo.setReceiptNoReqlg(rs.getString("receipt_no_reqlg"));
			vo.setTransactionNoLg(rs.getString("transaction_no_lg"));
			vo.setInvoiceNoLg(rs.getString("docno"));
			vo.setReceiptNoLg(rs.getString("dzdocNo"));
			vo.setSapStatusLg(rs.getString("sap_status_lg"));
			vo.setSapErrorDescLg(rs.getString("sap_error_desc_lg"));
			vo.setDocno(rs.getString("docno"));
			vo.setShowButton(rs.getString("showButton"));
			return vo;
		}
	};
}
