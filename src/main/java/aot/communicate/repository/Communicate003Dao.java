package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.communicate.vo.request.Communicate003Req;
import aot.communicate.vo.response.Communicate003Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Communicate003Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Communicate003Res> findAllList(Communicate003Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
//		sqlBuilder.append(" SELECT logo.commu_change_logo_id, ");
//		sqlBuilder.append(" 	logo.customer_code, ");
//		sqlBuilder.append(" 	logo.customer_name, ");
//		sqlBuilder.append(" 	logo.customer_branch, ");
//		sqlBuilder.append(" 	logo.contract_no, ");
//		sqlBuilder.append(" 	logo.rental_area_name, ");
//		sqlBuilder.append(" 	logo.payment_type, ");
//		sqlBuilder.append(" 	logo.bank_name, ");
//		sqlBuilder.append(" 	logo.bank_branch, ");
//		sqlBuilder.append(" 	logo.bank_explanation, ");
//		sqlBuilder.append(" 	logo.bank_guarantee_no, ");
//		sqlBuilder.append(" 	logo.bank_exp_no, ");
//		sqlBuilder.append(" 	logo.start_date, ");
//		sqlBuilder.append(" 	logo.end_date, ");
//		sqlBuilder.append(" 	logo.service_type, ");
//		sqlBuilder.append(" 	logo.charge_rates, ");
//		sqlBuilder.append(" 	logo.total_amount, ");
//		sqlBuilder.append(" 	logo.remark, ");
//		sqlBuilder.append(" 	logo.airport, ");
//		sqlBuilder.append(" 	logo.transaction_no, ");
//		sqlBuilder.append(" 	logo.sap_status, ");
//		sqlBuilder.append(" 	logo.sap_error_desc, ");
//		sqlBuilder.append(" 	sap.docno, ");
//		sqlBuilder.append(" 	sap.dzdocNo ");
//		sqlBuilder.append(" FROM ric_communicate_change_airline_logo logo ");
//		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
//		sqlBuilder.append(" ON logo.transaction_no = sap.refkey1 ");
		
		sqlBuilder = SqlGeneratorUtils.genSqlOneSapControl("RIC_COMMUNICATE_CHANGE_AIRLINE_LOGO");
		sqlBuilder.append(" WHERE TABLE_A.is_delete = 'N' ");
		
		if (StringUtils.isNotEmpty(form.getCustomerCode())) {
			sqlBuilder.append(" AND TABLE_A.customer_code LIKE ? ");
			params.add("%" + form.getCustomerCode().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getCustomerName())) {
			sqlBuilder.append(" AND TABLE_A.customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getContractNo())) {
			sqlBuilder.append(" AND TABLE_A.contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
//		if (StringUtils.isNotEmpty(form.getCourseName())) {
//			sqlBuilder.append(" AND TABLE_A.course_name LIKE ? ");
//			params.add("%" + form.getCourseName().trim() + "%");
//		}
		if (StringUtils.isNotEmpty(form.getStartDate())) {
			String[] date = form.getStartDate().split("/");
			sqlBuilder.append(" AND TABLE_A.start_date = ? ");
			params.add(date[2] + "-" + date[1] + "-" + date[0]);
		}
		sqlBuilder.append(" ORDER BY TABLE_A.commu_change_logo_id DESC ");
		List<Communicate003Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Communicate003Res> listRowmapper = new RowMapper<Communicate003Res>() {
		@Override
		public Communicate003Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Communicate003Res vo = new Communicate003Res();
			vo.setCommuChangeLogoId(Long.valueOf(rs.getInt("commu_change_logo_id")));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNo(rs.getString("bank_exp_no"));
			vo.setStartDate(ConvertDateUtils.formatDateToString(rs.getDate("start_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setEndDate(ConvertDateUtils.formatDateToString(rs.getDate("end_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setServiceType(rs.getString("service_type"));
			vo.setChargeRates(rs.getBigDecimal("charge_rates"));
			vo.setTotalAmount(rs.getBigDecimal("total_amount"));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapErrorDesc(rs.getString("sap_error_desc"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setReverseBtn(rs.getString("REVERSE_BTN"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			return vo;
		}
	};
}
