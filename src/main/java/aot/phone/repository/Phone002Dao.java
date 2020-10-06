package aot.phone.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import aot.phone.vo.request.Phone002SaveReq;
import aot.phone.vo.response.Phone002Res;
import aot.phone.vo.response.Phone002ServiceTypeRes;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Phone002Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Phone002ServiceTypeRes> getServiceType(String serviceType) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sqlBuilder.append(" SELECT phone_type, service_type ");
		sqlBuilder.append(" FROM ric_phone_rate_charge_config ");
		sqlBuilder.append(" WHERE phone_type = ?  AND is_delete = 'N' ");
		params.add(serviceType);
		List<Phone002ServiceTypeRes> dataRes = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return dataRes;
	}

	private RowMapper<Phone002ServiceTypeRes> listRowmapper = new RowMapper<Phone002ServiceTypeRes>() {
		@Override
		public Phone002ServiceTypeRes mapRow(ResultSet rs, int arg1) throws SQLException {
			Phone002ServiceTypeRes vo = new Phone002ServiceTypeRes();
			vo.setPhoneType(rs.getString("phone_type"));
			vo.setServiceType(rs.getString("service_type"));
			return vo;
		}
	};

	public List<Phone002Res> findData(@RequestBody Phone002SaveReq req) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT h.phone_req_id, ");
		sql.append(" 	h.entrepreneur_code, ");
		sql.append(" 	h.entrepreneur_name, ");
		sql.append(" 	h.branch_customer, ");
		sql.append(" 	h.phone_no, ");
		sql.append(" 	d.charge_rates, ");
		sql.append(" 	d.vat, ");
		sql.append(" 	d.total_charge_rate, ");
		sql.append(" 	h.request_start_date, ");
		sql.append(" 	h.request_end_date, ");
		sql.append(" 	h.contract_no, ");
		sql.append(" 	h.invoice_no_cash, ");
		sql.append(" 	h.sap_status_cash, ");
		sql.append(" 	h.sap_error_desc_cash, ");
		sql.append(" 	h.sap_json_req_cash, ");
		sql.append(" 	h.sap_json_res_cash, ");
		sql.append(" 	h.sap_status_lg, ");
		sql.append(" 	h.sap_error_desc_lg, ");
		sql.append(" 	h.sap_json_req_lg, ");
		sql.append(" 	h.sap_json_res_lg, ");
		sql.append(" 	h.invoice_no_lg, ");
		sql.append(" 	h.transaction_no_cash, ");
		sql.append(" 	h.transaction_no_lg, ");
		sql.append(" 	h.request_status, ");
		sql.append(" 	sap_cash.docno AS docno_cash, ");
		sql.append(" 	sap_cash.dzdocNo AS dzdocNo_cash, ");
		sql.append(" 	sap_cash.reverse_inv AS reverse_inv_cash, ");
		sql.append(" 	sap_cash.reverse_rec AS reverse_rec_cash, ");
		sql.append(" 	sap_lg.docno AS docno_lg, ");
		sql.append(" 	sap_lg.dzdocNo AS dzdocNo_lg, ");
		sql.append(" 	sap_lg.reverse_inv AS reverse_inv_lg, ");
		sql.append(" 	sap_lg.reverse_rec AS reverse_rec_lg, ");
		sql.append(" 	CASE ");
		sql.append("  		WHEN (sap_cash.reverse_inv IS NULL ");
		sql.append(" 	 		AND sap_cash.reverse_rec IS NULL) ");
		sql.append(" 	 		AND h.sap_status_cash = 'SAP_SUCCESS' THEN '' ");
		sql.append(" 		ELSE 'X' ");
		sql.append(" 	END AS show_button_cash, ");
		sql.append(" 	CASE ");
		sql.append("  		WHEN (sap_lg.reverse_inv IS NULL ");
		sql.append(" 	 		AND sap_lg.reverse_rec IS NULL) ");
		sql.append(" 	 		AND h.sap_status_lg = 'SAP_SUCCESS' THEN '' ");
		sql.append(" 		ELSE 'X' ");
		sql.append(" 	END AS show_button_lg, ");
		sql.append(" 	CASE ");
		sql.append(" 		WHEN sap_cash.dzdocNo IS NOT NULL ");
		sql.append(" 		AND sap_lg.dzdocNo IS NOT NULL ");
		sql.append(" 		AND sap_cash.reverse_inv IS NULL ");
		sql.append(" 		AND sap_cash.reverse_rec IS NULL ");
		sql.append(" 		AND sap_lg.reverse_inv IS NULL ");
		sql.append(" 		AND sap_lg.reverse_rec IS NULL ");
		sql.append(" 		THEN '' ");
		sql.append(" 		ELSE 'X' ");
		sql.append(" 	END AS sap_return_s ");
		sql.append(" FROM ric_phone_req h ");
		sql.append(" LEFT JOIN (  ");
		sql.append(" 	SELECT SUM(CONVERT(NUMERIC, charge_rates)) AS charge_rates, ");
		sql.append(" 		SUM(CONVERT(NUMERIC, vat)) AS vat, ");
		sql.append(" 		SUM(CONVERT(NUMERIC, total_charge_rates)) AS total_charge_rate, ");
		sql.append(" 		req_id ");
		sql.append(" 	FROM ric_phone_rate_charge ");
		sql.append(" 	GROUP BY req_id) d ON ");
		sql.append(" 	h.phone_req_id = d.req_id ");
		sql.append(" Left JOIN sap_ric_control sap_cash ON ");
		sql.append(" 	h.transaction_no_cash = sap_cash.refkey1 ");
		sql.append(" Left JOIN sap_ric_control sap_lg ON ");
		sql.append(" 	h.transaction_no_lg = sap_lg.refkey1 ");
		sql.append(" WHERE h.is_delete = 'N' ");
		// check data for search
		if (StringUtils.isNotBlank(req.getEntrepreneurName())) {
			sql.append("AND h.entrepreneur_name LIKE ? ");
			params.add("%" + req.getEntrepreneurName().trim() + "%");
		}
		if (StringUtils.isNotBlank(req.getContractNo())) {
			sql.append("AND h.contract_no LIKE ? ");
			params.add("%" + req.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(req.getPhoneNo())) {
			sql.append("AND h.phone_no LIKE ? ");
			params.add("%" + req.getPhoneNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(req.getRequestStatus())) {
			sql.append("AND h.request_status = ? ");
			params.add(req.getRequestStatus().trim());
		}
		sql.append(" ORDER BY h.phone_req_id DESC ");

		List<Phone002Res> data = commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), list02Rowmapper);
		return data;
	}

	private RowMapper<Phone002Res> list02Rowmapper = new RowMapper<Phone002Res>() {
		@Override
		public Phone002Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Phone002Res vo = new Phone002Res();

			vo.setPhoneReqId(rs.getString("phone_req_id"));
			vo.setEntrepreneurCode(rs.getString("entrepreneur_code"));
			vo.setEntrepreneurName(rs.getString("entrepreneur_name"));
			vo.setBranchCustomer(rs.getString("branch_customer"));
			vo.setPhoneNo(rs.getString("phone_no"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rate"));
			vo.setRequestStartDate(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRequestEndDate(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRequestStatus(rs.getString("request_status"));
			vo.setSapStatusCash(rs.getString("sap_status_cash"));
			vo.setSapErrorDescCash(rs.getString("sap_error_desc_cash"));
			vo.setSapJsonReqCash(rs.getString("sap_json_req_cash"));
			vo.setSapJsonResCash(rs.getString("sap_json_res_cash"));
			vo.setTransactionNoCash(rs.getString("transaction_no_cash"));
			vo.setDocnoCash(rs.getString("docno_cash"));
			vo.setReverseInvCash(rs.getString("reverse_inv_cash"));
			vo.setReverseRecCash(rs.getString("reverse_rec_cash"));
			vo.setInvoiceNoCash(rs.getString("invoice_no_cash"));
			vo.setShowButtonCash(rs.getString("show_button_cash"));

			vo.setSapStatusLg(rs.getString("sap_status_lg"));
			vo.setSapErrorDescLg(rs.getString("sap_error_desc_lg"));
			vo.setSapJsonReqLg(rs.getString("sap_json_req_lg"));
			vo.setSapJsonResLg(rs.getString("sap_json_res_lg"));
			vo.setTransactionNoLg(rs.getString("transaction_no_lg"));
			vo.setDocnoLg(rs.getString("docno_lg"));
			vo.setReverseInvLg(rs.getString("reverse_inv_lg"));
			vo.setReverseRecLg(rs.getString("reverse_rec_lg"));
			vo.setInvoiceNoLg(rs.getString("invoice_no_lg"));
			vo.setShowButtonLg(rs.getString("show_button_lg"));
			vo.setSapReturnS(rs.getString("sap_return_s"));
			vo.setDzdocNoCash(rs.getString("dzdocNo_cash"));
			vo.setDzdocNoLg(rs.getString("dzdocNo_lg"));
			
			return vo;

		}
	};

}
