package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateReqHdr;
import aot.communicate.vo.request.Communicate007Req;
import aot.communicate.vo.response.Communicate007Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Repository
public class Communicate007Dao {
	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public void updateFlagInfo(String flagInit, String flagTarget, Long id) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" UPDATE RIC_COMMUNICATE_REQ_HDR "
				+ " SET FLAG_SYNC_INFO = ? "
				+ ", UPDATED_BY = ? "
				+ ", UPDATED_DATE = ? "
				+ " WHERE FLAG_SYNC_INFO = ? ");
		params.add(flagTarget);
		params.add(UserLoginUtils.getCurrentUsername());
		params.add(new Date());
		params.add(flagInit);
		
		if(id != null) {
			sqlBuilder.append(" AND ID = ? ");
			params.add(id);
		};
		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
	}
	
	public Integer countByFlagInfoBeforeSyncData() {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT COUNT(1) FROM RIC_COMMUNICATE_REQ_HDR ");
		sqlBuilder.append(" WHERE FLAG_SYNC_INFO = ? ");
//		sqlBuilder.append(" AND INVOICE_NO IS NOT NULL ");
		sqlBuilder.append(" AND INVOICE_NO_CANCEL IS NULL ");
		sqlBuilder.append(" AND is_deleted = ? ");
		params.add(FLAG.N_FLAG);
		params.add(FLAG.N_FLAG);
		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), Integer.class);
	}
	
	public List<RicCommunicateReqHdr> checkCancelDateBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_COMMUNICATE_REQ_HDR REQ ");
		sqlBuilder.append(" LEFT JOIN SAP_CUSTOMER CUST ");
		sqlBuilder.append(" 	ON REQ.ENTREPRENUER_CODE = CUST.PARTNER AND SUBSTRING(REQ.CUSTOMER_BRANCH, 1, 5) = CUST.ADR_KIND ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP ");
		sqlBuilder.append(" 	ON REQ.TRANSACTION_NO = SAP.REFKEY1 ");
		sqlBuilder.append(" WHERE 1=1 ");
		sqlBuilder.append(" AND SAP.DZDOCNO IS NOT NULL ");
		sqlBuilder.append(" AND NOT EXISTS( "
				+ " SELECT INFO.TRANSACTION_NO_REQ "
				+ " FROM RIC_COMMUNICATE_INFO INFO "
				+ " WHERE REQ.TRANSACTION_NO = INFO.TRANSACTION_NO_REQ "
				+ " AND INFO.PERIOD_MONTH = ? "
				+ ")" );
		params.add(periodMonth);
		sqlBuilder.append(" AND FORMAT (REQ.request_date, 'yyyyMM') <= ? ");
		params.add(periodMonth);
		sqlBuilder.append(" AND (FORMAT (REQ.cancel_date, 'yyyyMM') >= ? OR REQ.cancel_date IS NULL) ");
		params.add(periodMonth);
		sqlBuilder.append(" AND REQ.IS_DELETED = 'N' ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicCommunicateReqHdr>() {
			@Override
			public RicCommunicateReqHdr mapRow(ResultSet rs, int arg1) throws SQLException {
				RicCommunicateReqHdr vo = new RicCommunicateReqHdr();
				vo.setId(rs.getLong("id"));
				vo.setRoNumber(rs.getString("ro_number"));
				vo.setRoName(rs.getString("ro_name"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("cust_name"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setPhoneAmount(rs.getBigDecimal("phone_amount"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setRequestDate(rs.getDate("request_date"));
				vo.setEndDate(rs.getDate("end_date"));
				vo.setCancelDate(rs.getDate("cancel_date"));
				return vo;
			}
		});
	}

	public List<Communicate007Res> findByCondition(Communicate007Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlCommunicateInfo("RIC_COMMUNICATE_INFO", "RIC_COMMUNICATE_REQ_HDR");
		sqlBuilder.append(" WHERE 1=1 ");
		sqlBuilder.append(" AND INFO.IS_DELETED = 'N' ");

		if (StringUtils.isNotBlank(request.getPeriodMonth())) {
			sqlBuilder.append(" AND INFO.PERIOD_MONTH = ? ");
			params.add(request.getPeriodMonth());
		}
		
		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND INFO.CONTRACT_NO LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND INFO.ENTREPRENUER_CODE LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND INFO.ENTREPRENUER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		
		sqlBuilder.append(" ORDER BY INFO.ID ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<Communicate007Res>() {
			@Override
			public Communicate007Res mapRow(ResultSet rs, int arg1) throws SQLException {
				Communicate007Res vo = new Communicate007Res();
				vo.setId(rs.getLong("id"));
				vo.setRoNumber(rs.getString("ro_number"));
				vo.setRoName(rs.getString("ro_name"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setPhoneAmount(rs.getBigDecimal("phone_amount"));
				vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
				vo.setVat(rs.getBigDecimal("vat"));
				vo.setTotalAll(rs.getBigDecimal("total_all"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setSapError(rs.getString("sap_error"));
				vo.setSapJsonReq(rs.getString("sap_json_req"));
				vo.setSapJsonRes(rs.getString("sap_json_res"));
				vo.setSapStatus(rs.getString("sap_status"));
				vo.setTransactionReq(rs.getString("transaction_no_req"));
				vo.setReceiptNo(rs.getString("dzdocNo"));
				vo.setInvoiceNo(rs.getString("invoice_no"));
				vo.setReverseBtn(rs.getString("REVERSE_BTN"));
				vo.setFlagEndDate(rs.getString("FLAG_ENDDATE"));
				vo.setRequestDateStr(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("request_date")));
				vo.setEndDateStr(ConvertDateUtils.dateToDDMMYYYY(rs.getDate("end_date")));
				return vo;
			}
		});
	}
}
