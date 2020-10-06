package aot.it.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.it.vo.request.It005Req;
import aot.it.vo.response.It005Res;
import aot.util.sap.SqlGeneratorUtils;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class It005Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<It005Res> findAll(It005Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT report.*,  ");
//		it_training_room_usage_id,
//		sqlBuilder.append(" 	entreprenuer_name, ");
//		sqlBuilder.append(" 	entreprenuer_code, ");
//		sqlBuilder.append(" 	entreprenuer_branch, ");
//		sqlBuilder.append(" 	contract_no, ");
//		sqlBuilder.append(" 	rental_area_name, ");
//		sqlBuilder.append(" 	room_type, ");
//		sqlBuilder.append(" 	req_start_date, ");
//		sqlBuilder.append(" 	timeperiod, ");
//		sqlBuilder.append(" 	total_charge_rates, ");
//		sqlBuilder.append(" 	remark, ");
//		sqlBuilder.append(" 	airport, ");
//		sqlBuilder.append(" 	payment_type, ");
//		sqlBuilder.append(" 	bank_name, ");
//		sqlBuilder.append(" 	bank_branch, ");
//		sqlBuilder.append(" 	bank_explanation, ");
//		sqlBuilder.append(" 	bank_guarantee_no, ");
//		sqlBuilder.append(" 	bank_exp_no, ");
//		sqlBuilder.append(" 	receipt_no, ");
//		sqlBuilder.append(" 	invoice_no, ");
//		sqlBuilder.append(" 	invoice_address, ");
//		sqlBuilder.append(" 	color_time, ");
		sqlBuilder.append(" 	sap_status, ");
		sqlBuilder.append(" 	sap_error, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN report.updated_date IS NOT NULL THEN report.updated_date ");
		sqlBuilder.append(" 		ELSE report.created_date ");
		sqlBuilder.append(" 	END AS updated_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN report.updated_by IS NOT NULL THEN report.updated_by ");
		sqlBuilder.append(" 		ELSE report.created_by ");
		sqlBuilder.append(" 	END AS updated_by, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND report.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_it_cute_training_room_usage_report report ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON report.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE is_deleted = 'N' ");
		if (StringUtils.isNotBlank(form.getContractNo())) {
			sqlBuilder.append(" AND report.contract_no LIKE ? ");
			params.add("%" +  form.getContractNo().replaceAll(" ", "%") + "%");
		}

		if (StringUtils.isNotBlank(form.getEntreprenuerCode())) {
			sqlBuilder.append(" AND report.entreprenuer_code LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(form.getEntreprenuerCode()));
		}
		
		if (StringUtils.isNotBlank(form.getEntreprenuerName())) {
			sqlBuilder.append(" AND report.entreprenuer_name LIKE ? ");
			params.add(SqlGeneratorUtils.StringLIKE(form.getEntreprenuerName()));
		}
		
		if (StringUtils.isNotBlank(form.getRoomType())) {
			sqlBuilder.append(" AND report.room_type LIKE ? ");
			params.add("%" +  form.getRoomType().replaceAll(" ", "%") + "%");
		}
		sqlBuilder.append(" ORDER BY report.it_training_room_usage_id DESC ");
		List<It005Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}
	
	public List<It005Res> findTimeForCalendar(It005Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT timeperiod ");
		sqlBuilder.append(" FROM ric_it_cute_training_room_usage_report ");
		sqlBuilder.append(" WHERE ");
		sqlBuilder.append(" room_type = ? ");
		params.add(form.getRoomType());
		sqlBuilder.append(" AND ");
		sqlBuilder.append(" req_start_date = ?");
		params.add(ConvertDateUtils.parseStringToDate(form.getReqStartDate(),
				ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
		List<It005Res> targetTime = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				mapperTimeperiod);
		return targetTime;
	}
	
	private RowMapper<It005Res> mapperTimeperiod = new RowMapper<It005Res>() {
		@Override
		public It005Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It005Res vo = new It005Res();
			vo.setTimeperiod(rs.getString("timeperiod"));
			return vo;
		}
	};

	private RowMapper<It005Res> listRowmapper = new RowMapper<It005Res>() {
		@Override
		public It005Res mapRow(ResultSet rs, int arg1) throws SQLException {
			It005Res vo = new It005Res();
			vo.setItTrainingRoomUsageId(rs.getLong("it_training_room_usage_id"));
			vo.setEntreprenuerName(rs.getString("entreprenuer_name"));
			vo.setEntreprenuerCode(rs.getString("entreprenuer_code"));
			vo.setEntreprenuerBranch(rs.getString("entreprenuer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRentalAreaName(rs.getString("rental_area_name"));
			vo.setRoomType(rs.getString("room_type"));
			vo.setTimeperiod(rs.getString("timeperiod"));
			vo.setTotalChargeRates(rs.getBigDecimal("total_charge_rates"));
			vo.setReqStartDate(ConvertDateUtils.formatDateToString(rs.getDate("req_start_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setInvoiceAddress(rs.getString("invoice_address"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setBankName(rs.getString("bank_name"));
			vo.setBankBranch(rs.getString("bank_branch"));
			vo.setBankExplanation(rs.getString("bank_explanation"));
			vo.setBankGuaranteeNo(rs.getString("bank_guarantee_no"));
			vo.setBankExpNo(rs.getString("bank_exp_no"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setColorTime(rs.getString("color_time"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			return vo;
		}
	};
}
