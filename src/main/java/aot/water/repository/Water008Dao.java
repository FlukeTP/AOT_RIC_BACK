package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterMeter;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water008Req;
import aot.water.vo.response.Water008Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Water008Dao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Water008Res> findWaterChange(Water008Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT ");
		sqlBuilder.append(" 	chg.water_change_id, ");
		sqlBuilder.append(" 	chg.req_id, ");
		sqlBuilder.append(" 	chg.customer_code, ");
		sqlBuilder.append(" 	chg.customer_name, ");
		sqlBuilder.append(" 	chg.customer_branch, ");
		sqlBuilder.append(" 	chg.contract_no, ");
		sqlBuilder.append(" 	chg.old_serial_no, ");
		sqlBuilder.append(" 	chg.old_charge_rates, ");
		sqlBuilder.append(" 	chg.old_vat, ");
		sqlBuilder.append(" 	chg.old_totalcharge_rates, ");
		sqlBuilder.append(" 	chg.new_serial_no, ");
		sqlBuilder.append(" 	meter.meter_name, ");
		sqlBuilder.append(" 	meter.meter_type, ");
		sqlBuilder.append(" 	wreq.rental_area_code, ");
		sqlBuilder.append(" 	wreq.request_type, ");
		sqlBuilder.append(" 	chg.new_charge_rates, ");
		sqlBuilder.append(" 	chg.new_vat, ");
		sqlBuilder.append(" 	chg.new_totalcharge_rates, ");
		sqlBuilder.append(" 	chg.date_change, ");
		sqlBuilder.append(" 	chg.remark, ");
		sqlBuilder.append(" 	chg.airport, ");
		sqlBuilder.append(" 	chg.invoice_no_reqcash, ");
		sqlBuilder.append(" 	chg.receipt_no_reqcash, ");
		sqlBuilder.append(" 	chg.invoice_no_reqlg, ");
		sqlBuilder.append(" 	chg.receipt_no_reqlg, ");
		sqlBuilder.append(" 	chg.transaction_no_cash, ");
		sqlBuilder.append(" 	chg.sap_status_cash, ");
		sqlBuilder.append(" 	chg.sap_error_desc_cash, ");
		sqlBuilder.append(" 	sap_cash.docno invoice_no_cash, ");
		sqlBuilder.append(" 	sap_cash.dzdocNo receipt_no_cash, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap_cash.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap_cash.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND chg.sap_status_cash = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButtonCash, ");
		sqlBuilder.append(" 	chg.transaction_no_lg, ");
		sqlBuilder.append(" 	chg.sap_status_lg, ");
		sqlBuilder.append(" 	chg.sap_error_desc_lg, ");
		sqlBuilder.append(" 	sap_lg.docno invoice_no_lg, ");
		sqlBuilder.append(" 	sap_lg.dzdocNo receipt_no_lg, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap_lg.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap_lg.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND chg.sap_status_lg = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButtonLg ");
		sqlBuilder.append(" FROM ric_water_req_change chg ");
		sqlBuilder.append(" LEFT JOIN ric_water_meter meter ");
		sqlBuilder.append(" 	ON chg.new_serial_no = meter.serial_no ");
		sqlBuilder.append(" LEFT JOIN ric_water_req wreq ");
		sqlBuilder.append(" 	ON chg.req_id = wreq.req_id ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap_cash ");
		sqlBuilder.append(" 	ON chg.transaction_no_cash = sap_cash.refkey1 ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap_lg ");
		sqlBuilder.append(" 	ON chg.transaction_no_lg = sap_lg.refkey1 ");
		sqlBuilder.append(" WHERE chg.is_delete = 'N' ");
		if (StringUtils.isNotEmpty(form.getCustomerName())) {
			sqlBuilder.append(" AND chg.customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getContractNo())) {
			sqlBuilder.append(" AND chg.contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getNewSerialNo())) {
			sqlBuilder.append(" AND chg.new_serial_no LIKE ? ");
			params.add("%" + form.getNewSerialNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getDateChange())) {
			sqlBuilder.append(" AND chg.date_change = ? ");
			params.add(form.getDateChange());
		}
		sqlBuilder.append(" ORDER BY chg.water_change_id DESC ");
		List<Water008Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Water008Res> listRowmapper = new RowMapper<Water008Res>() {
		@Override
		public Water008Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Water008Res vo = new Water008Res();
			vo.setWaterChangeId(Integer.toString(rs.getInt("water_change_id")));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRequestType(rs.getString("request_type"));
			vo.setOldSerialNo(rs.getString("old_serial_no"));
			vo.setOldChargeRates(rs.getBigDecimal("old_charge_rates"));
			vo.setOldVat(rs.getBigDecimal("old_vat"));
			vo.setOldTotalchargeRates(rs.getBigDecimal("old_totalcharge_rates"));
			vo.setNewSerialNo(rs.getString("new_serial_no"));
			vo.setNewMeterName(rs.getString("meter_name"));
			vo.setNewMeterType(rs.getString("meter_type"));
			vo.setNewRentalAreaCode(rs.getString("rental_area_code"));
			vo.setDateChange(ConvertDateUtils.formatDateToString(rs.getDate("date_change"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setInvoiceNoReqcash(rs.getString("invoice_no_reqcash"));
			vo.setReceiptNoReqcash(rs.getString("receipt_no_reqcash"));
			vo.setInvoiceNoReqlg(rs.getString("invoice_no_reqlg"));
			vo.setReceiptNoReqlg(rs.getString("receipt_no_reqlg"));
			vo.setTransactionNoCash(rs.getString("transaction_no_cash"));
			vo.setInvoiceNoCash(rs.getString("invoice_no_cash"));
			vo.setReceiptNoCash(rs.getString("receipt_no_cash"));
			vo.setSapStatusCash(rs.getString("sap_status_cash"));
			vo.setSapErrorDescCash(rs.getString("sap_error_desc_cash"));
			vo.setTransactionNoLg(rs.getString("transaction_no_lg"));
			vo.setInvoiceNoLg(rs.getString("invoice_no_lg"));
			vo.setReceiptNoLg(rs.getString("receipt_no_lg"));
			vo.setSapStatusLg(rs.getString("sap_status_lg"));
			vo.setSapErrorDescLg(rs.getString("sap_error_desc_lg"));
			vo.setShowButtonCash(rs.getString("showButtonCash"));
			vo.setShowButtonLg(rs.getString("showButtonLg"));
			vo.setReqId(rs.getLong("req_id"));
			return vo;
		}
	};

	public List<RicWaterMeter> getWaterMeterByStatus(Water003FindMeterReq req) {
		List<RicWaterMeter> target = new ArrayList<RicWaterMeter>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * "
				+ " FROM ric_water_meter A "
				+ " WHERE NOT EXISTS "
				+ " (SELECT * "
				+ " FROM ric_water_req_change B "
				+ " WHERE A.serial_no = B.old_serial_no "
				+ " AND B.is_delete = 'N' "
				+ ") "
				+ "AND A.meter_status = 'open' ");

		if (StringUtils.isNotBlank(req.getCriteria())) {
			sqlBuilder.append(" AND A.serial_no Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");
			
			sqlBuilder.append(" OR A.meter_type Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");
			
			sqlBuilder.append(" OR A.meter_name Like ? ");
			params.add("%" + req.getCriteria().trim() + "%");			
		}
		

		sqlBuilder.append(" AND A.is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY service_number DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapperMeter);
		return target;
	}

	private RowMapper<RicWaterMeter> listRowmapperMeter = new RowMapper<RicWaterMeter>() {
		@Override
		public RicWaterMeter mapRow(ResultSet rs, int arg1) throws SQLException {
			RicWaterMeter vo = new RicWaterMeter();
			vo.setMeterId(rs.getLong("meter_id"));
			vo.setSerialNo(rs.getString("serial_no"));
			vo.setMeterType(rs.getString("meter_type"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setMultipleValue(rs.getString("multiple_value"));
			vo.setMeterLocation(rs.getString("meter_location"));
			vo.setFunctionalLocation(rs.getString("functional_location"));
			vo.setMeterStatus(rs.getString("meter_status"));
			vo.setServiceNumber(rs.getString("service_number"));
			vo.setAirport(rs.getString("airport"));
			vo.setRemark(rs.getString("remark"));
			return vo;
		}
	};
}
