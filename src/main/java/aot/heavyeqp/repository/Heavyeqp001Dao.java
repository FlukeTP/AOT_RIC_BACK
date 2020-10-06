package aot.heavyeqp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.heavyeqp.vo.request.Heavyeqp001Req;
import aot.heavyeqp.vo.response.Heavyeqp001Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Heavyeqp001Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Heavyeqp001Res> getDataByDate(Heavyeqp001Req form) {
		List<Heavyeqp001Res> target = new ArrayList<Heavyeqp001Res>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT *, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND her.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_heavy_equipment_revenue her ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON her.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE is_deleted = 'N' ");
		if (StringUtils.isNotBlank(form.getCustomerName())) {
			sqlBuilder.append(" AND customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getEquipmentType())) {
			sqlBuilder.append(" AND equipment_type LIKE ? ");
			params.add("%" + form.getEquipmentType().trim() + "%");
		}
		if (StringUtils.isNotEmpty(form.getStartDate())) {
			Date dateConv = ConvertDateUtils.parseStringToDate(form.getStartDate(), ConvertDateUtils.DD_MM_YYYY);
			String dateFormat = ConvertDateUtils.formatDateToString(dateConv, ConvertDateUtils.YYYY_MM_DD);
			sqlBuilder.append(" AND start_date >= ?");
			params.add(dateFormat);
		}
		if (StringUtils.isNotEmpty(form.getEndDate())) {
			Date dateConv = ConvertDateUtils.parseStringToDate(form.getEndDate(), ConvertDateUtils.DD_MM_YYYY);
			String dateFormat = ConvertDateUtils.formatDateToString(dateConv, ConvertDateUtils.YYYY_MM_DD);
			sqlBuilder.append(" AND start_date <= ?");
			params.add(dateFormat);
		}
		sqlBuilder.append(" ORDER by heavy_equipment_revenue_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}

	private RowMapper<Heavyeqp001Res> listRowmapper = new RowMapper<Heavyeqp001Res>() {
		@Override
		public Heavyeqp001Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Heavyeqp001Res vo = new Heavyeqp001Res();
			vo.setAllFees(rs.getBigDecimal("all_fees"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setDriverRates(rs.getBigDecimal("driver_rates"));
			vo.setEndDate(rs.getDate("end_date"));
			vo.setEquipmentType(rs.getString("equipment_type"));
			vo.setHeavyEquipmentRevenueId(rs.getLong("heavy_equipment_revenue_id"));
			vo.setLicensePlate(rs.getString("license_plate"));
			vo.setNumberLicensePlate(rs.getString("number_license_plate"));
			vo.setPaymentType(rs.getString("payment_type"));
			vo.setPeriodTime(rs.getString("period_time"));
			vo.setResponsiblePerson(rs.getString("responsible_person"));
			vo.setStartDate(rs.getDate("start_date"));
			vo.setTotalMoney(rs.getBigDecimal("total_money"));
			vo.setVat(rs.getBigDecimal("vat"));
			vo.setRemark(rs.getString("remark"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			return vo;
		}
	};

}
