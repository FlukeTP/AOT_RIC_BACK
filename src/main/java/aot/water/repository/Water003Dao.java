package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.util.sap.SqlGeneratorUtils;
import aot.water.model.RicWaterMeter;
import aot.water.vo.request.Water003FindMeterReq;
import aot.water.vo.request.Water003Req;
import aot.water.vo.response.Water003Res;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Water003Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public List<RicWaterMeter> getElectricMeterByStatus(Water003FindMeterReq req) {
		List<RicWaterMeter> target = new ArrayList<RicWaterMeter>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * "
				+ " FROM ric_water_meter A "
				+ " WHERE NOT EXISTS "
				+ " (SELECT * "
				+ " FROM ric_water_req B "
				+ " WHERE A.serial_no = B.meter_serial_no "
				+ " AND B.request_status = 'Y' "
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
	
	
	public List<Water003Res> findWaterList(Water003Req req) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
//		sqlBuilder.append(" SELECT req.* ");
//		sqlBuilder.append(" FROM ric_water_req req ");
//		sqlBuilder.append(" LEFT JOIN ric_water_meter meter ");
//		sqlBuilder.append(" 	ON req.meter_serial_no = meter.serial_no ");
//		sqlBuilder.append(" WHERE req.is_delete='N' ");
		sqlBuilder = SqlGeneratorUtils.genSqlSapRequest("RIC_WATER_REQ", "RIC_WATER_REQ_CANCEL", "RIC_WATER_REQ_CHANGE");
		sqlBuilder.append(" WHERE 1 = 1 ");
		sqlBuilder.append(" AND REQ.is_delete = ? ");
		params.add(FLAG.N_FLAG);
		
		if (StringUtils.isNotEmpty(req.getCustomerCode())) {
			sqlBuilder.append(" AND REQ.customer_code Like ? ");
			params.add("%" + req.getCustomerCode().trim() + "%");
		}
		
		if (StringUtils.isNotEmpty(req.getCustomerName())) {
			sqlBuilder.append(" AND REQ.customer_name Like ? ");
			params.add("%" + req.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(req.getContracNo())) {
			sqlBuilder.append(" AND REQ.contract_no Like ? ");
			params.add("%" + req.getContracNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(req.getRentalAreaName())) {
			sqlBuilder.append(" AND REQ.rental_area_name Like ? ");
			params.add("%" + req.getRentalAreaName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(req.getInstallPositionService())) {
			sqlBuilder.append(" AND REQ.install_position_service Like ? ");
			params.add("%" + req.getInstallPositionService().trim() + "%");
		}
		if (StringUtils.isNotEmpty(req.getRequestStatus())) {
			sqlBuilder.append(" AND REQ.request_status = ? ");
			params.add(req.getRequestStatus());
		}
		
		if (StringUtils.isNotEmpty(req.getCustomerType())) {
			sqlBuilder.append(" AND REQ.customer_type = ? ");
			params.add(req.getCustomerType());
		}
		
		if (StringUtils.isNotEmpty(req.getRequestType())) {
			sqlBuilder.append(" AND REQ.request_type = ? ");
			params.add(req.getRequestType());
		}

		sqlBuilder.append(" ORDER BY REQ.REQ_ID DESC ");
		List<Water003Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Water003Res> listRowmapper = new RowMapper<Water003Res>() {
		@Override
		public Water003Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Water003Res vo = new Water003Res();
			vo.setReqId(rs.getString("req_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setCustomerType(rs.getString("customer_type"));
			vo.setRequestStartDate(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRequestEndDate(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setStatus(rs.getString("request_status"));
			vo.setMeterSerialNo(rs.getString("meter_serial_no"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
			vo.setInstallPositionService(rs.getString("install_position_service"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setRequestType(rs.getString("request_type"));
			

			String msg ="";
			msg = rs.getString("request_type");
			if(StringUtils.isNotBlank(msg)&& "อื่น ๆ".equals(msg)) {
				vo.setSumChargeRate(rs.getString("total_charge_rate_other"));
			}else if(StringUtils.isNotBlank(msg)&& "ขอใช้เหมาจ่าย".equals(msg)) {
				vo.setSumChargeRate(rs.getString("sum_charge_rate"));
			}else if(StringUtils.isNotBlank(msg)&& "ขอใช้ถาวร".equals(msg)) {
				vo.setSumChargeRate(rs.getString("total_charge_rates"));
			}else if(StringUtils.isNotBlank(msg)&& "ขอใช้ชั่วคราว".equals(msg)) {
				vo.setSumChargeRate(rs.getString("total_charge_rates"));
			}

			vo.setPaymentType(rs.getString("payment_type"));
			vo.setInvoiceNoCash(rs.getString("invoice_no_cash"));
			vo.setTransactionNoCash(rs.getString("transaction_no_cash"));
			vo.setSapStatusCash(rs.getString("sap_status_cash"));
			vo.setSapErrorDescCash(rs.getString("sap_error_desc_cash"));
			vo.setSapJsonReqCash(rs.getString("sap_json_req_cash"));
			vo.setSapJsonResCash(rs.getString("sap_json_res_cash"));
			vo.setReceiptCash(rs.getString("DZDOCNO_CH"));
			
			vo.setInvoiceNoLg(rs.getString("invoice_no_lg"));
			vo.setTransactionNoLg(rs.getString("transaction_no_lg"));
			vo.setSapStatusLg(rs.getString("sap_status_lg"));
			vo.setSapErrorDescLg(rs.getString("sap_error_desc_lg"));
			vo.setSapJsonReqLg(rs.getString("sap_json_req_lg"));
			vo.setSapJsonResLg(rs.getString("sap_json_res_lg"));
			vo.setReceiptLg(rs.getString("DZDOCNO_LG"));
			
			vo.setInvoiceNoPackages(rs.getString("invoice_no_packages"));
			vo.setTransactionNoPackages(rs.getString("transaction_no_packages"));
			vo.setSapStatusPackages(rs.getString("sap_status_packages"));
			vo.setSapErrorDescPackages(rs.getString("sap_error_desc_packages"));
			vo.setSapJsonReqPackages(rs.getString("sap_json_req_packages"));
			vo.setSapJsonResPackages(rs.getString("sap_json_res_packages"));
			vo.setReceiptPackages(rs.getString("DZDOCNO_PACKAGE"));
			
			vo.setAllBtn(rs.getString("ALL_BTN"));
			vo.setReverseBtnCash(rs.getString("REVERSE_BTN_CASH"));
			vo.setReverseBtnLg(rs.getString("REVERSE_BTN_LG"));
			vo.setReverseBtnPk(rs.getString("REVERSE_BTN_PK"));
			return vo;
		}
	};
	
	public void updateIsDeletedOnChangeSuccess() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" UPDATE RIC_WATER_REQ SET IS_DELETE = ? "
				+ " FROM RIC_WATER_REQ REQ "
				+ " INNER JOIN RIC_WATER_REQ_CHANGE CHG "
//				+ " ON REQ.METER_SERIAL_NO = CHG.OLD_SERIAL_NO "
				+ " ON REQ.REQ_ID = CHG.REQ_ID "
				+ " INNER JOIN SAP_RIC_CONTROL SAP_CHG "
				+ " ON CHG.TRANSACTION_NO_LG = SAP_CHG.REFKEY1 "
				+ " WHERE SAP_CHG.DZDOCNO IS NOT NULL ");
		params.add(FLAG.Y_FLAG);
		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
	}
}
