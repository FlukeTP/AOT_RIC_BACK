package aot.phone.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneInfo;
import aot.phone.vo.request.Phone001Req;
import aot.phone.vo.response.Phone001Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Phone001Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Phone001Res> findByCondition(Phone001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlOneSapControl("RIC_PHONE_INFO");
		sqlBuilder.append(" WHERE TABLE_A.IS_DELETED = 'N' ");

		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND TABLE_A.PERIOD_MONTH = ? ");
			params.add(request.getPeriodMonth().trim());
		}
		
		if (StringUtils.isNotBlank(request.getConcatCode())) {
			sqlBuilder.append(" AND TABLE_A.ENTREPRENUER_CODE = ? ");
			params.add(request.getConcatCode().trim());
		}

//		sqlBuilder.append(" ORDER BY serial_no_meter ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<Phone001Res>() {
			@Override
			public Phone001Res mapRow(ResultSet rs, int arg1) throws SQLException {
				Phone001Res vo = new Phone001Res();
				vo.setId(rs.getLong("id"));
				vo.setAddressCode(rs.getString("address_code"));
				vo.setPhoneNo(rs.getString("phone_no"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setMaintenanceCharge(rs.getBigDecimal("maintenance_charge"));
				vo.setServiceEquipmentCharge(rs.getBigDecimal("service_equipment_charge"));
				vo.setInternalLineCharge(rs.getBigDecimal("internal_line_charge"));
				vo.setOutterLineCharge(rs.getBigDecimal("outter_line_charge"));
				vo.setPhoneType(rs.getString("phone_type"));
				vo.setTotalCharge(rs.getBigDecimal("total_charge"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalChargeAll(rs.getBigDecimal("total_charge_all"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setPeriodMonth(rs.getString("period_month"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
//				vo.setCreatedBy(rs.getString("created_by"));
//				vo.setCreatedDate(rs.getDate("created_date"));
//				vo.setIsDeleted(rs.getString("is_deleted"));
				vo.setLngAmt(rs.getBigDecimal("lng_amt"));
				vo.setLngSvc(rs.getBigDecimal("lng_svc"));
				vo.setLocAmt(rs.getBigDecimal("loc_amt"));
				vo.setLocSvc(rs.getBigDecimal("loc_svc"));
				vo.setOvsAmt(rs.getBigDecimal("ovs_amt"));
				vo.setOvsSvc(rs.getBigDecimal("ovs_svc"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
//				vo.setRoCode(rs.getString("ro_code"));
//				vo.setRoName(rs.getString("ro_name"));
				vo.setReverseBtn(rs.getString("REVERSE_BTN"));
				vo.setReceipt(rs.getString("dzdocNo"));
				return vo;
			}
		});
	}
	
	public String findEntreprenuerName(String entreprenuerCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT name_org1 FROM sap_customer WHERE partner LIKE ? GROUP BY name_org1; ");
		params.add("%".concat(entreprenuerCode));
		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), String.class);
	}
	
	public void updateFlagInfo() {
		final String SQL = " UPDATE RIC_PHONE_REQ SET FLAG_INFO = ? ";
		commonJdbcTemplate.executeUpdate(SQL, FLAG.N_FLAG);
	}
	
	public List<RicPhoneInfo> syncDataByCondition(Phone001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM RIC_PHONE_INFO ");
		sqlBuilder.append(" WHERE IS_DELETED = 'N' ");

		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND PERIOD_MONTH = ? ");
			params.add(request.getPeriodMonth().trim());
		}
		
		if (StringUtils.isNotBlank(request.getConcatCode())) {
			sqlBuilder.append(" AND ENTREPRENUER_CODE = ? ");
			params.add(request.getConcatCode().trim());
		}

//		sqlBuilder.append(" ORDER BY serial_no_meter ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), phoneInfoRowMapper);
	}

	private RowMapper<RicPhoneInfo> phoneInfoRowMapper = new RowMapper<RicPhoneInfo>() {
		@Override
		public RicPhoneInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			RicPhoneInfo vo = new RicPhoneInfo();
			vo.setId(rs.getLong("id"));
			vo.setAddressCode(rs.getString("address_code"));
			vo.setPhoneNo(rs.getString("phone_no"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setMaintenanceCharge(rs.getBigDecimal("maintenance_charge"));
			vo.setServiceEquipmentCharge(rs.getBigDecimal("service_equipment_charge"));
			vo.setInternalLineCharge(rs.getBigDecimal("internal_line_charge"));
			vo.setOutterLineCharge(rs.getBigDecimal("outter_line_charge"));
			vo.setPhoneType(rs.getString("phone_type"));
			vo.setTotalCharge(rs.getBigDecimal("total_charge"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalChargeAll(rs.getBigDecimal("total_charge_all"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setAirport(rs.getString("airport"));
			vo.setPeriodMonth(rs.getString("period_month"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			vo.setSapJsonRes(rs.getString("sap_json_res"));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setCreatedDate(rs.getDate("created_date"));
			vo.setIsDeleted(rs.getString("is_deleted"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
			vo.setLngAmt(rs.getBigDecimal("lng_amt"));
			vo.setLngSvc(rs.getBigDecimal("lng_svc"));
			vo.setLocAmt(rs.getBigDecimal("loc_amt"));
			vo.setLocSvc(rs.getBigDecimal("loc_svc"));
			vo.setOvsAmt(rs.getBigDecimal("ovs_amt"));
			vo.setOvsSvc(rs.getBigDecimal("ovs_svc"));
			return vo;
		}
	};
	
	public void deleteByPeriodMonth(String PeriodMonth) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" DELETE FROM RIC_PHONE_INFO ");
		sqlBuilder.append(" WHERE PERIOD_MONTH = ? ");
		params.add(PeriodMonth);
		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
	}
}
