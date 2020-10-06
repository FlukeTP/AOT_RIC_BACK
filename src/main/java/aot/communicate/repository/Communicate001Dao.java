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

import aot.common.constant.CommunicateConstants;
import aot.communicate.vo.request.Communicate001Req;
import aot.communicate.vo.response.Communicate001Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;
import baiwa.util.UserLoginUtils;

@Repository
public class Communicate001Dao {

	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public List<Communicate001Res> findByCondition(Communicate001Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlSapReqCommunicate("RIC_COMMUNICATE_REQ_HDR");
		sqlBuilder.append(" WHERE TABLE_A.IS_DELETED = 'N' ");

		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND TABLE_A.CONTRACT_NO LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND TABLE_A.ENTREPRENUER_CODE LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND TABLE_A.ENTREPRENUER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}

		sqlBuilder.append(" AND TABLE_A.FLAG_CANCEL != ? ");
		params.add(CommunicateConstants.FLAG_CANCEL.TRUE);
		sqlBuilder.append(" ORDER BY TABLE_A.ID DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), communicateReqRowMapper);
	}
	
	private RowMapper<Communicate001Res> communicateReqRowMapper = new RowMapper<Communicate001Res>() {
		@Override
		public Communicate001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Communicate001Res vo = new Communicate001Res();
			vo.setId(rs.getLong("id"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setPhoneAmount(rs.getBigDecimal("phone_amount"));
			vo.setContractNo(rs.getString("contract_no"));
//			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setInsuranceRates(rs.getBigDecimal("insurance_rates"));
			vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalAll(rs.getBigDecimal("total_all"));
			vo.setRemark(rs.getString("remark"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setRequestDate(rs.getDate("request_date"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			vo.setSapJsonRes(rs.getString("sap_json_res"));
//			vo.setCreatedBy(rs.getString("created_by"));
//			vo.setCreatedDate(rs.getDate("created_date"));
//			vo.setIsDeleted(rs.getString("is_deleted"));
//			vo.setCancelDate(rs.getDate("cancel_date"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNoStr(dateToDDMMYYY(rs.getDate("bank_exp_no")));
			vo.setRequestDate(rs.getDate("request_Date"));
			vo.setRequestDateStr(dateToDDMMYYY(vo.getRequestDate()));
			vo.setEndDate(rs.getDate("end_date"));
			vo.setEndDateStr(dateToDDMMYYY(vo.getEndDate()));
			vo.setRoNumber(rs.getString("ro_number"));
			vo.setRoName(rs.getString("ro_name"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
			vo.setReverseBtn(rs.getString("REVERSE_BTN"));
			vo.setFlagCancel(rs.getString("flag_cancel"));
			vo.setCancelBtn(rs.getString("CANCEL_BTN"));
			vo.setFlagEndDate(rs.getString("FLAG_ENDDATE"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			
			/* _________ check wording payment type _________ */
			switch (rs.getString("payment_type")) {
			case CommunicateConstants.PAYMENT_TYPE.CASH.DESC_EN:
				vo.setPaymentTypeTh(CommunicateConstants.PAYMENT_TYPE.CASH.DESC_TH);
				break;
			case CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_EN:
				vo.setPaymentTypeTh(CommunicateConstants.PAYMENT_TYPE.BANK_GUARANTEE.DESC_TH);
				break;
			}
			return vo;
		}
	};
	
	private String dateToDDMMYYY(Date date) {
		return ConvertDateUtils.formatDateToString(date, ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN);
	}
	
	public void updateFlagCancel() {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" UPDATE RIC_COMMUNICATE_REQ_HDR "
				+ " SET "
				+ " FLAG_CANCEL = ? "
				+ ", UPDATED_BY = ? "
				+ ", UPDATED_DATE = ? "
				+ " WHERE 1 = 1 "
				+ " AND INVOICE_NO_CANCEL IS NOT NULL "
				+ " AND FLAG_CANCEL = ? ");
		params.add(CommunicateConstants.FLAG_CANCEL.TRUE);
		params.add(UserLoginUtils.getCurrentUsername());
		params.add(new Date());
		params.add(CommunicateConstants.FLAG_CANCEL.WAIT);
		commonJdbcTemplate.executeUpdate(sqlBuilder.toString(), params.toArray());
	}

}
