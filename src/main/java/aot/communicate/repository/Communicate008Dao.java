package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;
import aot.communicate.vo.request.Communicate008Req;
import aot.communicate.vo.response.Communicate008Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Communicate008Dao {
	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public List<RicCommunicateReqFlightScheduleHdr> checkCancelDateBeforeSyncData(String periodMonth) {
		List<Object> params = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" SELECT REQ.*, CUST.NAME_ORG1 cust_name FROM RIC_COMMUNICATE_REQ_FLIGHT_SCHEDULE_HDR REQ ");
		sqlBuilder.append(" LEFT JOIN SAP_CUSTOMER CUST ");
		sqlBuilder.append(" 	ON REQ.ENTREPRENUER_CODE = CUST.PARTNER AND SUBSTRING(REQ.CUSTOMER_BRANCH, 1, 5) = CUST.ADR_KIND ");
		sqlBuilder.append(" LEFT JOIN SAP_RIC_CONTROL SAP ");
		sqlBuilder.append(" 	ON REQ.TRANSACTION_NO = SAP.REFKEY1 ");
		sqlBuilder.append(" WHERE 1=1 ");
		sqlBuilder.append(" AND SAP.DZDOCNO IS NOT NULL ");
		sqlBuilder.append(" AND NOT EXISTS( "
				+ " SELECT INFO.TRANSACTION_NO_REQ "
				+ " FROM RIC_COMMUNICATE_FLIGHT_SCHEDULE_INFO INFO "
				+ " WHERE REQ.TRANSACTION_NO = INFO.TRANSACTION_NO_REQ "
				+ " AND INFO.PERIOD_MONTH = ? "
				+ ")" );
		params.add(periodMonth);
		sqlBuilder.append(" AND FORMAT (REQ.REQUEST_DATE, 'yyyyMM') <= ? ");
		params.add(periodMonth);
		sqlBuilder.append(" AND (FORMAT (REQ.CANCEL_DATE, 'yyyyMM') >= ? OR REQ.CANCEL_DATE IS NULL) ");
		params.add(periodMonth);
		sqlBuilder.append(" AND REQ.IS_DELETED = 'N' ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<RicCommunicateReqFlightScheduleHdr>() {
			@Override
			public RicCommunicateReqFlightScheduleHdr mapRow(ResultSet rs, int arg1) throws SQLException {
				RicCommunicateReqFlightScheduleHdr vo = new RicCommunicateReqFlightScheduleHdr();
				vo.setId(rs.getLong("id"));
				vo.setRentalAreaCode(rs.getString("rental_area_code"));
				vo.setRentalAreaName(rs.getString("rental_area_name"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("cust_name"));
				vo.setContractNo(rs.getString("contract_no"));
				vo.setCustomerBranch(rs.getString("customer_branch"));
				vo.setTransactionNo(rs.getString("transaction_no"));
				vo.setRequestDate(rs.getDate("request_date"));
				vo.setEndDate(rs.getDate("end_date"));
				vo.setCancelDate(rs.getDate("cancel_date"));
				return vo;
			}
		});
	}
	
	public List<Communicate008Res> findByCondition(Communicate008Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlCommunicateInfo("RIC_COMMUNICATE_FLIGHT_SCHEDULE_INFO", "RIC_COMMUNICATE_REQ_FLIGHT_SCHEDULE_HDR");
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
		
		sqlBuilder.append(" ORDER BY INFO.ID DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), new RowMapper<Communicate008Res>() {
			@Override
			public Communicate008Res mapRow(ResultSet rs, int arg1) throws SQLException {
				Communicate008Res vo = new Communicate008Res();
				vo.setId(rs.getLong("id"));
				vo.setRoNumber(rs.getString("rental_area_code"));
				vo.setRoName(rs.getString("rental_area_name"));
				vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
				vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
				vo.setContractNo(rs.getString("contract_no"));
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
				vo.setRequestDateStr(ConvertDateUtils.formatDateToString(rs.getDate("request_date"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				vo.setEndDateStr(ConvertDateUtils.formatDateToString(rs.getDate("end_date"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
				return vo;
			}
		});
	}

}
