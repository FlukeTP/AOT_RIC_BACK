package aot.cndn.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.cndn.vo.request.CnDn001Req;
import aot.cndn.vo.response.CnDn001Res;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class CnDn001Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<CnDn001Res> findAll(CnDn001Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT *, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND cndn.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_cn_dn cndn ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON cndn.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE cndn.is_delete = 'N' ");
		if (StringUtils.isNotBlank(form.getDocType())) {
			sqlBuilder.append(" AND cndn.doc_type = ? ");
			params.add(form.getDocType());
		}
		if (StringUtils.isNotBlank(form.getCustomerName())) {
			sqlBuilder.append(" AND cndn.customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getSapType())) {
			sqlBuilder.append(" AND cndn.sap_type = ? ");
			params.add(form.getSapType());
		}
		if (StringUtils.isNotBlank(form.getCnDn())) {
			sqlBuilder.append(" AND cndn.cn_dn = ? ");
			params.add(form.getCnDn());
		}
		sqlBuilder.append(" ORDER BY cndn.cn_dn_id DESC ");
		
		List<CnDn001Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<CnDn001Res> listRowmapper = new RowMapper<CnDn001Res>() {
		@Override
		public CnDn001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			CnDn001Res vo = new CnDn001Res();
			vo.setCnDnId(rs.getLong("cn_dn_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setOldInvoiceNo(rs.getString("old_invoice_no"));
			vo.setOldReceiptNo(rs.getString("old_receipt_no"));
			vo.setDocType(rs.getString("doc_type"));
			vo.setSapType(rs.getString("sap_type"));
			vo.setCnDn(rs.getString("cn_dn"));
			vo.setAmount(rs.getBigDecimal("amount"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setGlAccount(rs.getString("gl_account"));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setCreatedBy(rs.getString("created_by"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setRequestType(rs.getString("request_type"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setOldTotalAmount(rs.getBigDecimal("old_total_amount"));
			vo.setOldTransactionNo(rs.getString("old_transaction_no"));
			return vo;
		}
	};
}
