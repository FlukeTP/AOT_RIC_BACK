package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It004Req;
import aot.it.vo.response.It004Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It004Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It004Res> findAll(It004Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT other.*, ");
//		sqlBuilder.append(" 	entreprenuer_name, ");
//		sqlBuilder.append(" 	entreprenuer_code, ");
//		sqlBuilder.append(" 	entreprenuer_branch, ");
//		sqlBuilder.append(" 	contract_no, ");
//		sqlBuilder.append(" 	other_type, ");
//		sqlBuilder.append(" 	total_amount, ");
//		sqlBuilder.append(" 	charge_rates_type, ");
//		sqlBuilder.append(" 	charge_rates, ");
//		sqlBuilder.append(" 	total_charge_rates, ");
//		sqlBuilder.append(" 	remark, ");
//		sqlBuilder.append(" 	airport, ");
//		sqlBuilder.append(" 	request_start_date, ");
//		sqlBuilder.append(" 	request_end_date, ");
//		sqlBuilder.append(" 	receipt_no, ");
//		sqlBuilder.append(" 	invoice_no, ");
//		sqlBuilder.append(" 	invoice_no_cancel, ");
//		sqlBuilder.append(" 	payment_type, ");
//		sqlBuilder.append(" 	bank_name, ");
//		sqlBuilder.append(" 	bank_branch, ");
//		sqlBuilder.append(" 	bank_explanation, ");
//		sqlBuilder.append(" 	bank_guarantee_no, ");
//		sqlBuilder.append(" 	bank_exp_no, ");
//		sqlBuilder.append(" 	sap_status, ");
//		sqlBuilder.append(" 	sap_error, ");
//		sqlBuilder.append(" 	sap_status_cancel, ");
//		sqlBuilder.append(" 	sap_error_cancel, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	sap2.docno, ");
		sqlBuilder.append(" 	sap2.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN other.updated_date IS NOT NULL THEN other.updated_date ");
		sqlBuilder.append(" 		ELSE other.created_date ");
		sqlBuilder.append(" 	END AS updated_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN other.updated_by IS NOT NULL THEN other.updated_by ");
		sqlBuilder.append(" 		ELSE other.created_by ");
		sqlBuilder.append(" 	END AS updated_by, ");
		sqlBuilder.append("  	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND other.sap_status = 'SAP_SUCCESS' THEN '' ");
//		sqlBuilder.append(" 	 		AND other.sap_status_cancel = 'SAP_SUCCESS' THEN '' "); ///หมายเลข cancel ไม่ใช่ตัวนี้
		sqlBuilder.append("  		ELSE 'X' ");
		sqlBuilder.append("  	END AS showButton ");
		sqlBuilder.append(" FROM ric_it_other_create_invoice other ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON other.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap2 ");
		sqlBuilder.append(" ON other.transaction_no_cancel = sap2.refkey1 ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
		if (StringUtils.isNotBlank(form.getContractNo())) {
			sqlBuilder.append(" AND other.contract_no LIKE ? ");
			params.add("%" +  form.getContractNo().replaceAll(" ", "%") + "%");
		}

		if (StringUtils.isNotBlank(form.getEntreprenuerCode())) {
			sqlBuilder.append(" AND other.entreprenuer_code LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(form.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(form.getEntreprenuerName())) {
			sqlBuilder.append(" AND other.entreprenuer_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(form.getEntreprenuerName()));
		}
		
		if (StringUtils.isNotBlank(form.getOtherType())) {
			sqlBuilder.append(" AND other.other_type LIKE ? ");
			params.add(form.getOtherType().concat("%"));
		}
		sqlBuilder.append(" ORDER BY other.it_create_invoice_id DESC ");
		List<It004Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<It004Res> listRowmapper = new RowMapper<It004Res>() {
		@Override
		public It004Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It004Res vo = new It004Res();
			vo.setItOtherCreateInvoiceId(rs.getLong("it_create_invoice_id"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerBranch(rs.getString("entreprenuer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setOtherType(rs.getString("other_type"));
			vo.setChargeRatesType(rs.getString("charge_rates_type"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
			vo.setRequestStartDate(ConvertDateUtils.formatDateToString(rs.getDate("request_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRequestEndDate(ConvertDateUtils.formatDateToString(rs.getDate("request_end_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNo(ConvertDateUtils.formatDateToString(rs.getDate("bank_exp_no"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setInvoiceNo(rs.getString("invoice_no"));
			vo.setInvoiceNoCancel(rs.getString("invoice_no_cancel")); ///หมายเลข cancel ไม่ใช่ตัวนี้
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			vo.setSapStatusCancel(rs.getString("sap_status_cancel"));
			vo.setSapErrorCancel(rs.getString("sap_error_cancel"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			return vo;
		}
	};
}
