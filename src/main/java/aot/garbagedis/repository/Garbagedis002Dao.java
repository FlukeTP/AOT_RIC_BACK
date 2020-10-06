package aot.garbagedis.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.garbagedis.vo.request.Garbagedis002HdrReq;
import aot.garbagedis.vo.response.Garbagedis002HdrRes;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Garbagedis002Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Garbagedis002HdrRes> getAll(Garbagedis002HdrReq form) {
		List<Garbagedis002HdrRes> target = new ArrayList<Garbagedis002HdrRes>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT hdr.gar_req_id, ");
		sqlBuilder.append(" 	hdr.customer_code, ");
		sqlBuilder.append(" 	hdr.customer_name, ");
		sqlBuilder.append(" 	hdr.customer_branch, ");
		sqlBuilder.append(" 	hdr.contract_no, ");
		sqlBuilder.append(" 	hdr.rental_object, ");
		sqlBuilder.append(" 	hdr.service_type, ");
		sqlBuilder.append(" 	hdr.trash_location, ");
		sqlBuilder.append(" 	hdr.start_date, ");
		sqlBuilder.append(" 	hdr.end_date, ");
		sqlBuilder.append(" 	hdr.total_charge_rates, ");
		sqlBuilder.append(" 	hdr.address, ");
		sqlBuilder.append(" 	hdr.remark, ");
		sqlBuilder.append(" 	hdr.airport, ");
		sqlBuilder.append(" 	hdr.transaction_no, ");
		sqlBuilder.append(" 	hdr.sap_status, ");
		sqlBuilder.append(" 	hdr.sap_error, ");
		sqlBuilder.append(" 	hdr.sap_json_req, ");
		sqlBuilder.append(" 	t_weight.general_weight, ");
		sqlBuilder.append(" 	t_weight.hazardous_weight, ");
		sqlBuilder.append(" 	t_weight.infectious_weight, ");
		sqlBuilder.append(" 	total.money_amount, ");
		sqlBuilder.append(" 	sap.docno, ");
		sqlBuilder.append(" 	sap.dzdocNo, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append("  		WHEN (sap.reverse_inv IS NULL ");
		sqlBuilder.append(" 	 		AND sap.reverse_rec IS NULL) ");
		sqlBuilder.append(" 	 		AND hdr.sap_status = 'SAP_SUCCESS' THEN '' ");
		sqlBuilder.append(" 		ELSE 'X' ");
		sqlBuilder.append(" 	END AS showButton ");
		sqlBuilder.append(" FROM ric_garbagedis_req_hdr hdr ");
		sqlBuilder.append(" LEFT JOIN  ");
		sqlBuilder.append(" ( ");
		sqlBuilder.append(" 	SELECT  ");
		sqlBuilder.append(" 		gar_req_id, ");
		sqlBuilder.append(" 		SUM(trash_weight.ขยะทั่วไป) AS general_weight, ");
		sqlBuilder.append(" 		SUM(trash_weight.ขยะอันตราย) AS hazardous_weight, ");
		sqlBuilder.append(" 		SUM(trash_weight.ขยะติดเชื้อ) AS infectious_weight ");
		sqlBuilder.append(" 	FROM ( ");
		sqlBuilder.append(" 		SELECT gar_req_id, ");
		sqlBuilder.append(" 		trash_type, ");
		sqlBuilder.append(" 		trash_weight ");
		sqlBuilder.append(" 		FROM ric_garbagedis_req_dtl ");
		sqlBuilder.append(" 		WHERE is_deleted = 'N' ");
		sqlBuilder.append(" 	) trash_weight ");
		sqlBuilder.append(" 	PIVOT ( ");
		sqlBuilder.append(" 		MAX(trash_weight) ");
		sqlBuilder.append(" 		FOR trash_type IN ([ขยะทั่วไป], [ขยะอันตราย], [ขยะติดเชื้อ]) ");
		sqlBuilder.append(" 	) AS trash_weight ");
		sqlBuilder.append(" 	GROUP BY gar_req_id ");
		sqlBuilder.append(" ) t_weight ON hdr.gar_req_id = t_weight.gar_req_id ");
		sqlBuilder.append(" LEFT JOIN ( ");
		sqlBuilder.append(" 	SELECT gar_req_id id, ");
		sqlBuilder.append(" 		SUM(money_amount) money_amount ");
		sqlBuilder.append(" 	FROM ric_garbagedis_req_dtl ");
		sqlBuilder.append(" 	GROUP BY gar_req_id ");
		sqlBuilder.append(" ) total ON hdr.gar_req_id = total.id ");
		sqlBuilder.append(" LEFT JOIN sap_ric_control sap ");
		sqlBuilder.append(" ON hdr.transaction_no = sap.refkey1 ");
		sqlBuilder.append(" WHERE hdr.is_deleted = 'N' ");
		if (StringUtils.isNotBlank(form.getCustomerName())) {
			sqlBuilder.append(" AND customer_name LIKE ? ");
			params.add("%" + form.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getContractNo())) {
			sqlBuilder.append(" AND contract_no LIKE ? ");
			params.add("%" + form.getContractNo().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getTrashLocation())) {
			sqlBuilder.append(" AND trash_location LIKE ? ");
			params.add("%" + form.getTrashLocation().trim() + "%");
		}
		if (StringUtils.isNotBlank(form.getServiceType())) {
			sqlBuilder.append(" AND service_type LIKE ? ");
			params.add("%" + form.getServiceType().trim() + "%");
		}
		sqlBuilder.append(" ORDER by hdr.gar_req_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}

	private RowMapper<Garbagedis002HdrRes> listRowmapper = new RowMapper<Garbagedis002HdrRes>() {
		@Override
		public Garbagedis002HdrRes mapRow(ResultSet rs, int arg1) throws SQLException {
			Garbagedis002HdrRes vo = new Garbagedis002HdrRes();
			vo.setGarReqId(rs.getLong("gar_req_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setRentalObject(rs.getString("rental_object"));
			vo.setServiceType(rs.getString("service_type"));
			vo.setTrashLocation(rs.getString("trash_location"));
			vo.setStartDate(ConvertDateUtils.formatDateToString(rs.getDate("start_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setEndDate(ConvertDateUtils.formatDateToString(rs.getDate("end_date"), ConvertDateUtils.DD_MM_YYYY,
					ConvertDateUtils.LOCAL_EN));
			vo.setAddress(rs.getString("address"));
			vo.setRemark(rs.getString("remark"));
			vo.setAirport(rs.getString("airport"));
			vo.setTransactionNo(rs.getString("transaction_no"));
			vo.setSapStatus(rs.getString("sap_status"));
			vo.setSapError(rs.getString("sap_error"));
			vo.setInvoiceNo(rs.getString("docno"));
			vo.setReceiptNo(rs.getString("dzdocNo"));
			vo.setShowButton(rs.getString("showButton"));
			vo.setGeneralWeight(rs.getString("general_weight"));
			vo.setHazardousWeight(rs.getString("hazardous_weight"));
			vo.setInfectiousWeight(rs.getString("infectious_weight"));
			vo.setTotalMoneyAmount(rs.getBigDecimal("money_amount"));
			vo.setSapJsonReq(rs.getString("sap_json_req"));
			return vo;
		}
	};
}
