package aot.firebrigade.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.firebrigade.vo.request.Firebrigade001Req;
import aot.firebrigade.vo.response.Firebrigade001Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Firebrigade001Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Firebrigade001Res> findFirebrigadeManage(Firebrigade001Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT *, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND man.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_firebrigade_manage man");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON man.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE man.is_delete = 'N' ");
		if (StringUtils.isNotEmpty(form.getCustomerName())) {
			sqlBuilder.append(" AND man.customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getContractNo())) {
			sqlBuilder.append(" AND man.contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getCourseName())) {
			sqlBuilder.append(" AND man.course_name LIKE ? ");
			params.add("%" + form.getCourseName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getStartDate())) {
			Date dateConv = ConvertDateUtils.parseStringToDate(form.getStartDate(), ConvertDateUtils.DD_MM_YYYY);
			String dateFormat = ConvertDateUtils.formatDateToString(dateConv, ConvertDateUtils.YYYY_MM_DD);
			sqlBuilder.append(" AND man.start_date >= ?");
			params.add(dateFormat);
		}
		if (StringUtils.isNotEmpty(form.getEndDate())) {
			Date dateConv = ConvertDateUtils.parseStringToDate(form.getEndDate(), ConvertDateUtils.DD_MM_YYYY);
			String dateFormat = ConvertDateUtils.formatDateToString(dateConv, ConvertDateUtils.YYYY_MM_DD);
			sqlBuilder.append(" AND man.start_date <= ?");
			params.add(dateFormat);
		}
		sqlBuilder.append(" ORDER BY man.fire_manage_id DESC ");
		List<Firebrigade001Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Firebrigade001Res> listRowmapper = new RowMapper<Firebrigade001Res>() {
		@Override
		public Firebrigade001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Firebrigade001Res vo = new Firebrigade001Res();
			vo.setFireManageId(Long.valueOf(rs.getInt("fire_manage_id")));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setAddress(rs.getString("address"));
			vo.setCourseName(rs.getString("course_name"));
			vo.setStartDate(ConvertDateUtils.formatDateToString(rs.getDate("start_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setPersonAmount(Long.valueOf(rs.getInt("person_amount")));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapErrorDesc(rs.getString("sap_error_desc"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setUnit(rs.getString("unit"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			return vo;
		}
	};
}
