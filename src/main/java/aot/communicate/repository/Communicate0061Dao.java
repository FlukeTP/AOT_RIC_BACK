package aot.communicate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.communicate.vo.request.Communicate0061Req;
import aot.communicate.vo.response.Communicate0061Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Communicate0061Dao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Communicate0061Res> findAll(Communicate0061Req form) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT commu_transceiver_config_id, ");
		sqlBuilder.append(" 	effective_date, ");
		sqlBuilder.append(" 	service_type, ");
		sqlBuilder.append(" 	charge_rate_name, ");
		sqlBuilder.append(" 	charge_rate, ");
		sqlBuilder.append(" 	insurance_fee, ");
		sqlBuilder.append(" 	remark, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_date IS NOT NULL THEN updated_date ");
		sqlBuilder.append(" 	ELSE created_date ");
		sqlBuilder.append(" 	END AS updated_date, ");
		sqlBuilder.append(" 	CASE ");
		sqlBuilder.append(" 		WHEN updated_by IS NOT NULL THEN updated_by ");
		sqlBuilder.append(" 	ELSE created_by ");
		sqlBuilder.append(" 	END AS updated_by ");
		sqlBuilder.append(" FROM ric_communicate_handheid_transceiver_charge_rates_config ");
		sqlBuilder.append(" WHERE is_delete = 'N' ");
//		if (StringUtils.isNotEmpty(form.getCustomerName())) {
//			sqlBuilder.append(" AND cnc.customer_name LIKE ? ");
//			params.add("%" + form.getCustomerName().trim() + "%");
//		}
		sqlBuilder.append(" ORDER BY effective_date DESC ");
		List<Communicate0061Res> target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(),
				listRowmapper);
		return target;
	}

	private RowMapper<Communicate0061Res> listRowmapper = new RowMapper<Communicate0061Res>() {
		@Override
		public Communicate0061Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Communicate0061Res vo = new Communicate0061Res();
			vo.setCommuTransceiverConfigId(rs.getLong("commu_transceiver_config_id"));
			vo.setEffectiveDate(ConvertDateUtils.formatDateToString(rs.getDate("effective_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setServiceType(rs.getString("service_type"));
			vo.setChargeRateName(rs.getString("charge_rate_name"));
			vo.setInsuranceFee(rs.getBigDecimal("insurance_fee"));
			vo.setChargeRate(rs.getBigDecimal("charge_rate"));
			vo.setRemark(rs.getString("remark"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setUpdatedBy(rs.getString("updated_by"));
			return vo;
		}
	};
}
