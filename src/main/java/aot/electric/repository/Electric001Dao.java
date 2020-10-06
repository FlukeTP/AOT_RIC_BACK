package aot.electric.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.constant.ElectricConstants;
import aot.common.constant.WaterConstants;
import aot.electric.model.RicElectricInfo;
import aot.electric.model.RicElectricReq;
import aot.electric.model.SystemCalConfig;
import aot.electric.vo.request.Electric001Req;
import aot.electric.vo.response.Electric001Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Electric001Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<RicElectricInfo> findDropdownSerialNo() {
		StringBuilder sqlBuilder = new StringBuilder();
		// List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT serial_no_meter FROM ric_electric_info WHERE IS_DELETED = 'N' ");
		sqlBuilder.append(" GROUP BY serial_no_meter ORDER BY serial_no_meter ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), new RowMapper<RicElectricInfo>() {
			@Override
			public RicElectricInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				RicElectricInfo vo = new RicElectricInfo();
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				return vo;
			}
		});
	}

	public List<Electric001Res> findByCondition(Electric001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlOneSapControl("RIC_ELECTRIC_INFO");
		sqlBuilder.append(" WHERE 1 = 1 ");

		if (request.getId() != null) {
			sqlBuilder.append(" AND TABLE_A.ELECTRIC_INFO_ID = ? ");
			params.add(request.getId());
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND TABLE_A.ENTREPRENUER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		
		if (StringUtils.isNotBlank(request.getMeterName())) {
			sqlBuilder.append(" AND TABLE_A.METER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getMeterName()));
		}
		
		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND TABLE_A.PERIOD_MONTH = ? ");
			params.add(request.getPeriodMonth().trim());
		}

		if (StringUtils.isNotBlank(request.getSerialNoMeter())) {
			sqlBuilder.append(" AND TABLE_A.SERIAL_NO_METER LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getSerialNoMeter()));
		}
		
		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND TABLE_A.CONTRACT_NO LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}
		
		sqlBuilder.append(" AND TABLE_A.IS_DELETED = 'N' ");

		sqlBuilder.append(" ORDER BY TABLE_A.CONTRACT_NO ASC, TABLE_A.SERIAL_NO_METER ASC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<Electric001Res>() {
			@Override
			public Electric001Res mapRow(ResultSet rs, int arg1) throws SQLException {
				Electric001Res vo = new Electric001Res();
				vo.setElectricInfoId(rs.getLong("electric_info_id"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				vo.setBackwardMeterValue(rs.getBigDecimal("backward_meter_value"));
				vo.setCurrentMeterValue(rs.getString("current_meter_value"));
				vo.setCalPercent(rs.getBigDecimal("cal_percent"));
				vo.setBaseValue(rs.getBigDecimal("base_value"));
				vo.setFtValue(rs.getBigDecimal("ft_value"));
				vo.setEnergyValue(rs.getBigDecimal("energy_value"));
				vo.setServiceValue(rs.getBigDecimal("service_value"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
				vo.setPeriodMonth(rs.getString("period_month"));
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setBackwardAmount(rs.getBigDecimal("backward_amount"));
				vo.setCurrentAmount(rs.getBigDecimal("current_amount"));
				vo.setMeterDate(rs.getDate("meter_date"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setCustomerType(rs.getString("customer_type"));
				vo.setRoCode(rs.getString("ro_code"));
				vo.setRoName(rs.getString("ro_name"));
				vo.setReverseBtn(rs.getString("REVERSE_BTN"));
				vo.setReceipt(rs.getString("dzdocNo"));
				vo.setMeterDateStr(
						ConvertDateUtils.formatDateToString(vo.getMeterDate(), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				if (vo.getCalPercent() != null && vo.getCalPercent().compareTo(new BigDecimal(40)) > 0) {
					vo.setFlagCalPercent(FLAG.Y_FLAG);
				} else {
					vo.setFlagCalPercent(FLAG.N_FLAG);
				}
				return vo;
			}
		});
	}
	
//	public List<RicElectricInfo> syncDataByPeriodMonth(String periodMonth) {
//		StringBuilder sqlBuilder = new StringBuilder();
//		List<Object> params = new ArrayList<>();
//		sqlBuilder.append(" SELECT *, CUST.NAME_ORG1 cust_name  ");
//		sqlBuilder.append(" FROM RIC_ELECTRIC_INFO INFO ");
//		sqlBuilder.append(" LEFT JOIN SAP_CUSTOMER CUST ");
//		sqlBuilder.append(" 	ON INFO.ENTREPRENUER_CODE = CUST.PARTNER ");
//		sqlBuilder.append(" WHERE INFO.IS_DELETED = 'N' ");
//		sqlBuilder.append(" AND INFO.PERIOD_MONTH = ? ");
//		params.add(periodMonth);
//		
//		sqlBuilder.append(" ORDER BY INFO.CONTRACT_NO ASC, INFO.SERIAL_NO_METER ASC ");
//		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicElectricInfo>() {
//			@Override
//			public RicElectricInfo mapRow(ResultSet rs, int arg1) throws SQLException {
//				RicElectricInfo vo = new RicElectricInfo();
//				vo.setElectricInfoId(rs.getLong("electric_info_id"));
//				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
////				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
//				vo.setEntreprenuerName(rs.getString("cust_name"));
//				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
//				vo.setBackwardMeterValue(rs.getBigDecimal("backward_meter_value"));
//				vo.setCurrentMeterValue(rs.getString("current_meter_value"));
//				vo.setCalPercent(rs.getBigDecimal("cal_percent"));
//				vo.setBaseValue(rs.getBigDecimal("base_value"));
//				vo.setFtValue(rs.getBigDecimal("ft_value"));
//				vo.setServiceValue(rs.getBigDecimal("service_value"));
//				vo.setVat(rs.getBigDecimal("vat"));
//				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
//				vo.setInvoiceNo(rs.getString("invoice_no"));
//				vo.setPeriodMonth(rs.getString("period_month"));
//				vo.setVoltageType(rs.getString("voltage_type"));
//				vo.setBackwardAmount(rs.getBigDecimal("backward_amount"));
//				vo.setCurrentAmount(rs.getBigDecimal("current_amount"));
//				vo.setMeterDate(rs.getDate("meter_date"));
//				vo.setMeterName(rs.getString("meter_name"));
//				vo.setTransactionNo(rs.getString("transaction_no"));
//				vo.setSapError(rs.getString("sap_error"));
//				vo.setSapJsonReq(rs.getString("sap_json_req"));
//				vo.setSapJsonRes(rs.getString("sap_json_res"));
//				vo.setSapStatus(rs.getString("sap_status"));
//				vo.setContractNo(rs.getString("contract_no"));
//				vo.setCustomerType(rs.getString("customer_type"));
//				vo.setRoCode(rs.getString("ro_code"));
//				vo.setRoName(rs.getString("ro_name"));
//				return vo;
//			}
//		});
//	}
	
	public List<RicElectricInfo> findByPeriodMonth(Electric001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM RIC_ELECTRIC_INFO ");
		sqlBuilder.append(" WHERE IS_DELETED = 'N' ");
		
		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND PERIOD_MONTH = ? ");
			params.add(request.getPeriodMonth().trim());
		}
		
		sqlBuilder.append(" ORDER BY SERIAL_NO_METER ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicElectricInfo>() {
			@Override
			public RicElectricInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				RicElectricInfo vo = new RicElectricInfo();
				vo.setElectricInfoId(rs.getLong("electric_info_id"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setSerialNoMeter(rs.getString("serial_no_meter"));
				vo.setBackwardMeterValue(rs.getBigDecimal("backward_meter_value"));
				vo.setCurrentMeterValue(rs.getString("current_meter_value"));
				vo.setCalPercent(rs.getBigDecimal("cal_percent"));
				vo.setBaseValue(rs.getBigDecimal("base_value"));
				vo.setFtValue(rs.getBigDecimal("ft_value"));
				vo.setServiceValue(rs.getBigDecimal("service_value"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalAmount(rs.getBigDecimal("total_amount"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
				vo.setPeriodMonth(rs.getString("period_month"));
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setBackwardAmount(rs.getBigDecimal("backward_amount"));
				vo.setCurrentAmount(rs.getBigDecimal("current_amount"));
				vo.setMeterDate(rs.getDate("meter_date"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setContractNo(rs.getString("contract_no"));
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
//		sqlBuilder.append(" UPDATE RIC_ELECTRIC_REQ "
//				+ " SET FLAG_INFO = ? "
//				+ ", UPDATED_BY = ? "
//				+ ", UPDATED_DATE = ? "
//				+ " WHERE FLAG_INFO = ? ");
//		params.add(flagTarget);
//		params.add(UserLoginUtils.getCurrentUsername());
//		params.add(new Date());
//		params.add(flagInit);
//		
//		if(id != null) {
//			sqlBuilder.append(" AND REQ_ID = ? ");
//			params.add(id);
//		};
//		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
//	}
	
	public List<RicElectricReq> findByFlagInfoBeforeSyncData() {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT * FROM ric_electric_req ");
		sqlBuilder.append(" WHERE flag_info = ? ");
		sqlBuilder.append(" AND invoice_no_cash IS NOT NULL ");
		sqlBuilder.append(" AND invoice_no_lg IS NOT NULL ");
		sqlBuilder.append(" AND request_type != ? ");
		sqlBuilder.append(" AND is_delete = ? ");
		params.add(FLAG.N_FLAG);
		params.add(ElectricConstants.REQUEST_TYPE.PACKAGES);
		params.add(FLAG.N_FLAG);
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicElectricReq>() {
			@Override
			public RicElectricReq mapRow(ResultSet rs, int arg1) throws SQLException {
				RicElectricReq vo = new RicElectricReq();
				vo.setReqId(rs.getLong("req_id"));
				vo.setMeterSerialNo(rs.getString("meter_serial_no"));
				vo.setDefaultMeterNo(rs.getBigDecimal("default_meter_no"));
				vo.setRentalAreaCode(rs.getString("rental_area_code"));
				vo.setRentalAreaName(rs.getString("rental_area_name"));
				vo.setCustomerType(rs.getString("customer_type"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setCustomerCode(rs.getString("customer_code"));
				vo.setCustomerName(rs.getString("customer_name"));
				vo.setMeterName(rs.getString("meter_name"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setCreatedBy(rs.getString("created_by"));
				vo.setIsDelete(rs.getString("is_delete"));
//				vo.setRequestStatus(rs.getString("request_status"));
//				vo.setInstallPositionService(rs.getString("install_position_service"));
//				vo.setRequestStartDate(rs.getDate("request_start_date"));
//				vo.setRequestEndDate(rs.getDate("request_end_date"));
				return vo;
			}
		});
	}
	
	public Integer countByFlagInfoBeforeSyncData() {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT COUNT(1) FROM ric_electric_req ");
		sqlBuilder.append(" WHERE flag_info = ? ");
		sqlBuilder.append(" AND invoice_no_cash IS NOT NULL ");
		sqlBuilder.append(" AND invoice_no_lg IS NOT NULL ");
		sqlBuilder.append(" AND request_type != ? ");
		sqlBuilder.append(" AND is_delete = ? ");
		params.add(FLAG.N_FLAG);
		params.add(ElectricConstants.REQUEST_TYPE.PACKAGES);
		params.add(FLAG.N_FLAG);
		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), Integer.class);
	}
	
	public List<SystemCalConfig> findFtConfig() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM SYSTEM_CAL_CONFIG ");
		sqlBuilder.append(" WHERE VALID_DATE <= ? ");
		params.add(new Date());
		sqlBuilder.append(" ORDER BY VALID_DATE DESC ");
		sqlBuilder.append(" OFFSET 0 ROWS ");
		sqlBuilder.append(" FETCH NEXT 1 ROWS ONLY ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<SystemCalConfig>() {
			@Override
			public SystemCalConfig mapRow(ResultSet rs, int arg1) throws SQLException {
				SystemCalConfig vo = new SystemCalConfig();
				vo.setCalConfigId(rs.getLong("cal_config_id"));
				vo.setValue(rs.getString("value"));
				return vo;
			}
		});
	}
	
	public List<RicElectricReq> checkCancelDateBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		final String sql = "SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_ELECTRIC_REQ REQ " + 
				"		 LEFT JOIN SAP_CUSTOMER CUST " + 
				"		 	ON REQ.CUSTOMER_CODE = CUST.PARTNER AND SUBSTRING(REQ.CUSTOMER_BRANCH, 1, 5) = CUST.ADR_KIND " + 
				"		 LEFT JOIN RIC_ELECTRIC_REQ_CANCEL CANCEL " + 
				"		 	ON REQ.REQ_ID = CANCEL.REQ_ID " + 
				"		 LEFT JOIN RIC_ELECTRIC_REQ_CHANGE CHG " + 
				"		 	ON REQ.REQ_ID = CHG.REQ_ID " + 
				"		 LEFT JOIN SAP_RIC_CONTROL SAP_CASH" + 
				"		 	ON REQ.TRANSACTION_NO_CASH = SAP_CASH.REFKEY1 " + 
				"		 LEFT JOIN SAP_RIC_CONTROL SAP_LG " + 
				"		 	ON REQ.TRANSACTION_NO_LG = SAP_LG.REFKEY1 " +
				"		 WHERE 1=1 " +
				" 			AND NOT EXISTS(  " + 
				"				  SELECT INFO.ID_REQ  " + 
				"				  FROM RIC_ELECTRIC_INFO INFO  " + 
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
		
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicElectricReq>() {
			@Override
			public RicElectricReq mapRow(ResultSet rs, int arg1) throws SQLException {
				RicElectricReq vo = new RicElectricReq();
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
				vo.setVoltageType(rs.getString("voltage_type"));
				vo.setRequestType(rs.getString("request_type"));
				return vo;
			}
		});
	}
}
