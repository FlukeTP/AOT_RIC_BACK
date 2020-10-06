package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.water.vo.request.Water007Req;
import aot.water.vo.response.Water007Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Water007Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Water007Res> findWaterCancel(Water007Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" 	cnc.water_cancel_id, ");
		sqlBuilder.append(" 	cnc.customer_code, ");
		sqlBuilder.append(" 	cnc.customer_name, ");
		sqlBuilder.append(" 	cnc.customer_branch, ");
		sqlBuilder.append(" 	cnc.contract_no, ");
		sqlBuilder.append(" 	cnc.serial_no, ");
		sqlBuilder.append(" 	meter.meter_name, ");
		sqlBuilder.append(" 	meter.meter_type, ");
		sqlBuilder.append(" 	wreq.rental_area_code, ");
		sqlBuilder.append(" 	wreq.request_type, ");
		sqlBuilder.append(" 	cnc.charge_rates, ");
		sqlBuilder.append(" 	cnc.vat, ");
		sqlBuilder.append(" 	cnc.totalcharge_rates, ");
		sqlBuilder.append(" 	cnc.date_cancel, ");
		sqlBuilder.append(" 	cnc.remark, ");
		sqlBuilder.append(" 	cnc.airport, ");
		sqlBuilder.append(" 	cnc.invoice_no_reqcash, ");
		sqlBuilder.append(" 	cnc.receipt_no_reqcash, ");
		sqlBuilder.append(" 	cnc.invoice_no_reqlg, ");
		sqlBuilder.append(" 	cnc.receipt_no_reqlg, ");
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
		sqlBuilder.append(" FROM ric_water_req_cancel cnc ");
		sqlBuilder.append(" LEFT JOIN ric_water_meter meter ");
		sqlBuilder.append(" 	ON cnc.serial_no = meter.serial_no ");
		sqlBuilder.append(" LEFT JOIN ric_water_req wreq  ");
		sqlBuilder.append(" 	ON cnc.req_id = wreq.req_id ");
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
		if (StringUtils.isNotEmpty(form.getSerialNo())) {
			sqlBuilder.append(" AND cnc.serial_no LIKE ? ");
			params.add("%" + form.getSerialNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getDateCancel())) {
			sqlBuilder.append(" AND cnc.date_cancel = ? ");
			params.add(form.getDateCancel());
		}
		sqlBuilder.append(" ORDER BY cnc.water_cancel_id DESC ");
		List<Water007Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Water007Res> listRowmapper = new RowMapper<Water007Res>() {
		@Override
		public Water007Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Water007Res vo = new Water007Res();
			vo.setWaterCancelId(Integer.toString(rs.getInt("water_cancel_id")));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRequestType(rs.getString("request_type"));
			vo.setSerialNo(rs.getString("serial_no"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setMeterType(rs.getString("meter_type"));
			vo.setRentalAreaCode(rs.getString("rental_area_code"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalchargeRates(rs.getBigDecimal("totalcharge_rates"));
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
