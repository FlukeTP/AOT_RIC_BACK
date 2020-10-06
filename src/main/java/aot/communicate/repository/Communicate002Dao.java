package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.constant.CommunicateConstants;
import aot.communicate.vo.request.Communicate002Req;
import aot.communicate.vo.response.Communicate002Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.constant.CommonConstants.FLAG;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;
import baiwa.util.NumberUtils;

@Repository
public class Communicate002Dao {

	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public List<Communicate002Res> findByCondition(Communicate002Req request) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder = SqlGeneratorUtils.genSqlOneSapControl("RIC_COMMUNICATE_REQ_HDR");
		sqlBuilder.append(" WHERE 1 = 1 ");
		sqlBuilder.append(" AND IS_DELETED = ? ");
		params.add(FLAG.N_FLAG);
		
		if (request.getId() != null) {
			sqlBuilder.append(" AND ID = ? ");
			params.add(request.getId());
		}

		if (StringUtils.isNotBlank(request.getContractNo())) {
			sqlBuilder.append(" AND CONTRACT_NO LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getContractNo()));
		}

		if (StringUtils.isNotBlank(request.getEntreprenuerCode())) {
			sqlBuilder.append(" AND ENTREPRENUER_CODE LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(request.getEntreprenuerName())) {
			sqlBuilder.append(" AND ENTREPRENUER_NAME LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(request.getEntreprenuerName()));
		}
		
		sqlBuilder.append(" AND FLAG_CANCEL != ? ");
		params.add(CommunicateConstants.FLAG_CANCEL.FALSE);
		sqlBuilder.append(" ORDER BY ID DESC ");
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), communicateReqRowMapper);
	}

	private RowMapper<Communicate002Res> communicateReqRowMapper = new RowMapper<Communicate002Res>() {
		@Override
		public Communicate002Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Communicate002Res vo = new Communicate002Res();
			vo.setId(rs.getLong("id"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setPhoneAmount(NumberUtils.toDecimalFormat(rs.getBigDecimal("phone_amount"), false));
//			vo.setChargeRates(NumberUtils.toDecimalFormat(rs.getBigDecimal("charge_rates"), true));
			vo.setInsuranceRates(NumberUtils.toDecimalFormat(rs.getBigDecimal("insurance_rates"), true));
			vo.setTotalChargeRates(NumberUtils.toDecimalFormat(rs.getBigDecimal("total_charge_rates"), true));
//			vo.setPhoneAmount(rs.getBigDecimal("phone_amount"));
//			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
//			vo.setInsuranceRates(rs.getBigDecimal("insurance_rates"));
//			vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
			vo.setRemark(rs.getString("remark"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setRequestDateStr(ConvertDateUtils.formatDateToString(rs.getDate("request_date"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setCancelDateStr(ConvertDateUtils.formatDateToString(rs.getDate("cancel_date"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setEndDateStr(ConvertDateUtils.formatDateToString(rs.getDate("end_date"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setSapStatusCancel(rs.getString("sap_status_cancel"));
			vo.setSapErrorCancel(rs.getString("sap_error_cancel"));
			vo.setSapJsonReqCancel(rs.getString("sap_json_req_cancel"));
			vo.setSapJsonResCancel(rs.getString("sap_json_res_cancel"));
			vo.setCancelDate(rs.getDate("cancel_date"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNoStr(ConvertDateUtils.formatDateToString(rs.getDate("bank_exp_no"), ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.
					LOCAL_EN));
			vo.setRoNumber(rs.getString("ro_number"));
			vo.setRoName(rs.getString("ro_name"));
			vo.setTotalAllDF(NumberUtils.toDecimalFormat(rs.getBigDecimal("total_all"), true));
			vo.setReceiptNoReq(rs.getString("dzdocNo"));
			vo.setInvoiceNoCancel(rs.getString("invoice_no_cancel"));
			vo.setReverseBtn(rs.getString("REVERSE_BTN"));
			vo.setTransactionNoCancel(rs.getString("transaction_no_cancel"));
			return vo;
		}
	};

}
