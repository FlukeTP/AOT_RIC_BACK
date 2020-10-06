package aot.garbagedis.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.garbagedis.vo.request.Garbagedis001Req;
import aot.garbagedis.vo.response.Garbagedis001Res;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Garbagedis001Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Garbagedis001Res> getAll(Garbagedis001Req form) {
		List<Garbagedis001Res> target = new ArrayList<Garbagedis001Res>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT gar_info_id, ");
		sqlBuilder.append(" 	gar_req_id, ");
		sqlBuilder.append(" 	months, ");
		sqlBuilder.append(" 	years, ");
		sqlBuilder.append(" 	customer_code, ");
		sqlBuilder.append(" 	customer_name, ");
		sqlBuilder.append(" 	customer_branch, ");
		sqlBuilder.append(" 	info.contract_no, ");
		sqlBuilder.append(" 	rental_object, ");
		sqlBuilder.append(" 	trash_location, ");
		sqlBuilder.append(" 	start_date, ");
		sqlBuilder.append(" 	end_date, ");
		sqlBuilder.append(" 	general_weight, ");
		sqlBuilder.append(" 	hazardous_weight, ");
		sqlBuilder.append(" 	infectious_weight, ");
		sqlBuilder.append(" 	general_money, ");
		sqlBuilder.append(" 	hazardous_money, ");
		sqlBuilder.append(" 	infectious_money, ");
		sqlBuilder.append(" 	total_money, ");
		sqlBuilder.append(" 	address, ");
		sqlBuilder.append(" 	remark, ");
		sqlBuilder.append(" 	airport, ");
		sqlBuilder.append(" 	transaction_no, ");
		sqlBuilder.append(" 	sap_status, ");
		sqlBuilder.append(" 	sap_error, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND info.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 	ELSE 'X' ");
		sqlBuilder.append(" END AS showButton ");
		sqlBuilder.append(" FROM ric_garbagedis_info info ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON info.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE info.is_deleted = 'N' ");
		//find month and year
		String[] date = form.getMonthly().split("/");
		sqlBuilder.append(" AND info.months = ? ");
		params.add(Long.valueOf(date[0]).toString());
		sqlBuilder.append(" AND info.years = ? ");
		params.add(date[1]);
		if (StringUtils.isNotBlank(form.getCustomerName())) {
			sqlBuilder.append(" AND customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getContractNo())) {
			sqlBuilder.append(" AND info.contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getTrashLocation())) {
			sqlBuilder.append(" AND trash_location LIKE ? ");
			params.add("%" + form.getTrashLocation().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getRentalObject())) {
			sqlBuilder.append(" AND rental_object LIKE ? ");
			params.add("%" + form.getRentalObject().trim() + "%");
		}
		sqlBuilder.append(" ORDER by info.gar_info_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}

	private RowMapper<Garbagedis001Res> listRowmapper = new RowMapper<Garbagedis001Res>() {
		@Override
		public Garbagedis001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Garbagedis001Res vo = new Garbagedis001Res();
			vo.setGarInfoId(rs.getLong("gar_info_id"));
			vo.setGarReqId(rs.getLong("gar_req_id"));
			vo.setMonths(rs.getString("months"));
			vo.setYears(rs.getString("years"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setTrashLocation(rs.getString("trash_location"));
			vo.setRentalObject(rs.getString("rental_object"));
			vo.setGeneralWeight(rs.getString("general_weight"));
			vo.setHazardousWeight(rs.getString("hazardous_weight"));
			vo.setInfectiousWeight(rs.getString("infectious_weight"));
			vo.setGeneralMoney(rs.getString("general_money"));
			vo.setHazardousMoney(rs.getString("hazardous_money"));
			vo.setInfectiousMoney(rs.getString("infectious_money"));
			vo.setTotalMoney(rs.getString("total_money"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setShowButton(rs.getString("showButton"));
			return vo;
		}
	};
}
