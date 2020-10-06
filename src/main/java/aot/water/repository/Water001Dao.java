package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.constant.WaterConstants;
import aot.util.sap.SqlGeneratorUtils;
import aot.water.model.RicWaterInfo;
import aot.water.model.RicWaterReq;
import aot.water.vo.request.Water001Req;
import aot.water.vo.response.Water001Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Water001Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<RicWaterInfo> findDropdownSerialNo() {
		StringBuilder sqlBuilder = new StringBuilder();
		// List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT serial_no_meter FROM ric_water_info WHERE IS_DELETED = 'N' ");
		sqlBuilder.append(" GROUP BY serial_no_meter ORDER BY serial_no_meter ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), new RowMapper<RicWaterInfo>() {
			@Override
			public RicWaterInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				RicWaterInfo vo = new RicWaterInfo();
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				return vo;
			}
		});
	}

	public List<Water001Res> findByCondition(Water001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlOneSapControl("RIC_WATER_INFO");
		sqlBuilder.append(" WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND TABLE_A.entreprenuer_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		
		if (StringUtils.isNotBlank(request.getMeterName())) {
			sqlBuilder.append(" AND TABLE_A.meter_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getMeterName()));
		}

		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND TABLE_A.period_month = ? ");
			params.add(request.getPeriodMonth().trim());
		}

		if (StringUtils.isNotBlank(request.getSerialNoMeter())) {
			sqlBuilder.append(" AND TABLE_A.serial_no_meter = ? ");
			params.add(request.getSerialNoMeter().trim());
		}
		
		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND TABLE_A.CONTRACT_NO = ? ");
			params.add(request.getContractNo().trim());
		}
		sqlBuilder.append(" AND TABLE_A.IS_DELETED = 'N' ");

		sqlBuilder.append(" ORDER BY TABLE_A.CONTRACT_NO ASC, TABLE_A.SERIAL_NO_METER ASC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<Water001Res>() {
			@Override
			public Water001Res mapRow(ResultSet rs, int arg1) throws SQLException {
				Water001Res vo = new Water001Res();
				vo.setWaterInfoId(rs.getLong("water_info_id"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				vo.setBackwardMeterValue(rs.getBigDecimal("backward_meter_value"));
				vo.setCurrentMeterValue(rs.getString("current_meter_value"));
				vo.setBaseValue(rs.getBigDecimal("base_value"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setServiceValue(rs.getBigDecimal("service_value"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
				vo.setPeriodMonth(rs.getString("period_month"));
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setCurrentAmount(rs.getBigDecimal("current_amount"));
				vo.setBackwardAmount(rs.getBigDecimal("backward_amount"));
				vo.setTreatmentFee(rs.getBigDecimal("treatment_fee"));
				vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setMeterDate(rs.getDate("meter_date"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setWaterType(rs.getString("water_type"));
				vo.setCustomerType(rs.getString("customer_type"));
				vo.setRoCode(rs.getString("ro_code"));
				vo.setRoName(rs.getString("ro_name"));
				vo.setReverseBtn(rs.getString("REVERSE_BTN"));
				vo.setReceipt(rs.getString("dzdocNo"));
				vo.setRecycleAmount(rs.getBigDecimal("recycle_amount"));
				vo.setWaterType1(rs.getString("water_type1"));
				vo.setWaterType2(rs.getString("water_type2"));
				vo.setWaterType3(rs.getString("water_type3"));
				vo.setFlagCheck(false);
				vo.setMeterDateStr(ConvertDateUtils.formatDateToString(vo.getMeterDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				return vo;
			}
		});
	}
	
	public List<RicWaterInfo> findByPeriodMonth(Water001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_water_info ");
		sqlBuilder.append(" WHERE IS_DELETED = 'N' ");
		
		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND period_month = ? ");
			params.add(request.getPeriodMonth().trim());
		}
		
		sqlBuilder.append(" ORDER BY serial_no_meter ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicWaterInfo>() {
			@Override
			public RicWaterInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				RicWaterInfo vo = new RicWaterInfo();
				vo.setWaterInfoId(rs.getLong("water_info_id"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
//				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				vo.setBackwardMeterValue(rs.getBigDecimal("backward_meter_value"));
				vo.setCurrentMeterValue(rs.getString("current_meter_value"));
				vo.setBaseValue(rs.getBigDecimal("base_value"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setServiceValue(rs.getBigDecimal("service_value"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
				vo.setPeriodMonth(rs.getString("period_month"));
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setCurrentAmount(rs.getBigDecimal("current_amount"));
				vo.setBackwardAmount(rs.getBigDecimal("backward_amount"));
				vo.setTreatmentFee(rs.getBigDecimal("treatment_fee"));
				vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setMeterDate(rs.getDate("meter_date"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setWaterType(rs.getString("water_type"));
				vo.setCustomerType(rs.getString("customer_type"));
				vo.setRoCode(rs.getString("ro_code"));
				vo.setRoName(rs.getString("ro_name"));
				return vo;
			}
		});
	}
	
//	public void updateFlagInfo(String flagInit, String flagTarget, Long id) {
//		StringBuilder sqlBuilder = new StringBuilder();
//		List<Object> params = new ArrayList<>();
//		sqlBuilder.append(" UPDATE RIC_WATER_REQ "
//				+ " SET FLAG_INFO = ? "
//				+ ", UPDATED_BY = ? "
//				+ ", UPDATED_DATE = ? "
//				+ " FROM RIC_WATER_REQ REQ"
//				+ " LEFT JOIN SAP_RIC_CONTROL SAP_CASH "
//				+ " 	ON REQ.TRANSACTION_NO_CASH = SAP_CASH.REFKEY1 "
//				+ " LEFT JOIN SAP_RIC_CONTROL SAP_LG "
//				+ " 	ON REQ.TRANSACTION_NO_LG = SAP_LG.REFKEY1 "
//				+ " WHERE REQ.FLAG_INFO = ? "
//				+ " AND (SAP_CASH.DZDOCNO IS NOT NULL OR SAP_LG.DZDOCNO IS NOT NULL) ");
//		params.add(flagTarget);
//		params.add(UserLoginUtils.getCurrentUsername());
//		params.add(new Date());
//		params.add(flagInit);
//		
//		if(id != null) {
//			sqlBuilder.append(" AND REQ_ID = ? ");
//			params.add(id);
//		}
//		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
//	}
	
//	public List<RicWaterReq> findByFlagInfoBeforeSyncData() {
//		List<Object> params = new ArrayList<>();
//		StringBuilder sqlBuilder = new StringBuilder();
//		sqlBuilder.append(" SELECT * FROM ric_water_req ");
//		sqlBuilder.append(" WHERE flag_info = ? ");
//		sqlBuilder.append(" AND (invoice_no_cash IS NOT NULL AND invoice_no_lg IS NOT NULL) ");
//		sqlBuilder.append(" AND request_type != ? ");
//		sqlBuilder.append(" AND is_delete = ? ");
//		params.add(FLAG.N_FLAG);
//		params.add(WaterConstants.REQUEST_TYPE.PACKAGES);
//		params.add(FLAG.N_FLAG);
//		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicWaterReq>() {
//			@Override
//			public RicWaterReq mapRow(ResultSet rs, int arg1) throws SQLException {
//				RicWaterReq vo = new RicWaterReq();
//				vo.setReqId(rs.getLong("req_id"));
//				vo.setMeterSerialNo(rs.getString("meter_serial_no"));
//				vo.setDefaultMeterNo(rs.getBigDecimal("default_meter_no"));
//				vo.setRentalAreaCode(rs.getString("rental_area_code"));
//				vo.setRentalAreaName(rs.getString("rental_area_name"));
//				vo.setCustomerType(rs.getString("customer_type"));
//				vo.setCustomerCode(rs.getString("customer_code"));
//				vo.setCustomerName(rs.getString("customer_name"));
//				vo.setCustomerBranch(rs.getString("customer_branch"));
//				vo.setContractNo(rs.getString("contract_no"));
//				vo.setMeterName(rs.getString("meter_name"));
//				vo.setCreatedBy(rs.getString("created_by"));
//				vo.setIsDelete(rs.getString("is_delete"));
//				vo.setMeterType(rs.getString("meter_type"));
//				vo.setWaterType1(rs.getString("water_type1"));
//				vo.setWaterType2(rs.getString("water_type2"));
//				vo.setWaterType3(rs.getString("water_type3"));
//				return vo;
//			}
//		});
//	}
	
//	public Integer countByFlagInfoBeforeSyncData() {
//		List<Object> params = new ArrayList<>();
//		StringBuilder sqlBuilder = new StringBuilder();
//		sqlBuilder.append(" SELECT COUNT(1) FROM ric_water_req ");
//		sqlBuilder.append(" WHERE flag_info = ? ");
//		sqlBuilder.append(" AND (invoice_no_cash IS NOT NULL AND invoice_no_lg IS NOT NULL) ");
//		sqlBuilder.append(" AND request_type != ? ");
//		sqlBuilder.append(" AND is_delete = ? ");
//		params.add(FLAG.N_FLAG);
//		params.add(WaterConstants.REQUEST_TYPE.PACKAGES);
//		params.add(FLAG.N_FLAG);
//		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), Integer.class);
//	}
	
	public List<RicWaterReq> checkCancelDateBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		final String sql = "SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_WATER_REQ REQ " + 
				"		 LEFT JOIN SAP_CUSTOMER CUST " + 
				"		 	ON REQ.CUSTOMER_CODE = CUST.PARTNER AND SUBSTRING(REQ.CUSTOMER_BRANCH, 1, 5) = CUST.ADR_KIND " + 
				"		 LEFT JOIN RIC_WATER_REQ_CANCEL CANCEL " + 
				"		 	ON REQ.REQ_ID = CANCEL.REQ_ID " + 
				"		 LEFT JOIN RIC_WATER_REQ_CHANGE CHG " + 
				"		 	ON REQ.REQ_ID = CHG.REQ_ID " + 
				"		 LEFT JOIN SAP_RIC_CONTROL SAP_CASH" + 
				"		 	ON REQ.TRANSACTION_NO_CASH = SAP_CASH.REFKEY1 " + 
				"		 LEFT JOIN SAP_RIC_CONTROL SAP_LG " + 
				"		 	ON REQ.TRANSACTION_NO_LG = SAP_LG.REFKEY1 " +
				"		 WHERE 1=1 " +
				" 			AND NOT EXISTS(  " + 
				"				  SELECT INFO.ID_REQ  " + 
				"				  FROM RIC_WATER_INFO INFO  " + 
				"				  WHERE REQ.REQ_ID = INFO.ID_REQ  " + 
				"				  AND INFO.PERIOD_MONTH = ?  " + 
				"				 ) " +
				"			AND " + 
				"				( " + 
				"					( " + 
				"						REQ.CUSTOMER_TYPE = ? " + 
				"						AND FORMAT (REQ.REQUEST_START_DATE, 'yyyyMM') <= ? " + 
				"						AND (FORMAT (REQ.REQUEST_END_DATE, 'yyyyMM') >= ? OR (CANCEL.DATE_CANCEL IS NULL AND CHG.DATE_CHANGE IS NULL)) " + 
				"						AND SAP_CASH.DZDOCNO IS NOT NULL " + 
				"						AND SAP_LG.DZDOCNO IS NOT NULL " + 
				"					) " + 
				"					OR " + 
				"					( " + 
				"						REQ.CUSTOMER_TYPE = ? " + 
				"						AND FORMAT (REQ.REQUEST_START_DATE, 'yyyyMM') <= ? " + 
				"						AND (FORMAT (REQ.REQUEST_END_DATE, 'yyyyMM') >= ?) " + 
				"					) " + 
				"				) " ;
		params.add(periodMonth);
		params.add(WaterConstants.CUSTOMER_TYPE);
		params.add(periodMonth);
		params.add(periodMonth);
		params.add(WaterConstants.EMPLOYEE_TYPE);
		params.add(periodMonth);
		params.add(periodMonth);
		
		sqlBuilder.append(sql);
		sqlBuilder.append(" AND REQ.REQUEST_TYPE != ? ");
		params.add(WaterConstants.REQUEST_TYPE.PACKAGES);
		sqlBuilder.append(" AND REQ.IS_DELETE = 'N' ");
		
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicWaterReq>() {
			@Override
			public RicWaterReq mapRow(ResultSet rs, int arg1) throws SQLException {
				RicWaterReq vo = new RicWaterReq();
				vo.setReqId(rs.getLong("req_id"));
				vo.setMeterSerialNo(rs.getString("meter_serial_no"));
				vo.setDefaultMeterNo(rs.getBigDecimal("default_meter_no"));
				vo.setRentalAreaCode(rs.getString("rental_area_code"));
				vo.setRentalAreaName(rs.getString("rental_area_name"));
				vo.setCustomerType(rs.getString("customer_type"));
				vo.setCustomerCode(rs.getString("customer_code"));
//				vo.setCustomerName(rs.getString("cust_name"));
				vo.setCustomerName(rs.getString("customer_name"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setCreatedBy(rs.getString("created_by"));
				vo.setIsDelete(rs.getString("is_delete"));
				vo.setMeterType(rs.getString("meter_type"));
				vo.setWaterType1(rs.getString("water_type1"));
				vo.setWaterType2(rs.getString("water_type2"));
				vo.setWaterType3(rs.getString("water_type3"));
				return vo;
			}
		});
	}
	
}
