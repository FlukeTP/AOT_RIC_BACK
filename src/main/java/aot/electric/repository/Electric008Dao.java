package aot.electric.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.electric.vo.response.Electric008Res;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.ConvertDateUtils;

@Repository
public class Electric008Dao {

	private static final Logger logger = LoggerFactory.getLogger(Electric008Dao.class);

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	public List<Electric008Res> findData() {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(
				" SELECT c.type_config_id,c.electric_type,c.rate_type,m.voltage_type,c.updated_by,c.updated_date,c.description ");
		sql.append(" FROM ric_electric_charge_type_config c ");
		sql.append(" LEFT JOIN ric_electric_charge_mapping m ");
		sql.append(" ON c.type_config_id = m.type_config_id ");
		sql.append(" WHERE c.is_delete ='N' ");
		sql.append(" ORDER BY c.type_config_id ASC ");

		List<Electric008Res> data = commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), listRowmapper);
		logger.info("datas.size()={}", data.size());
		return data;
	}

	private RowMapper<Electric008Res> listRowmapper = new RowMapper<Electric008Res>() {
		@Override
		public Electric008Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Electric008Res vo = new Electric008Res();
			vo.setTypeConfigId(rs.getLong("type_config_id"));
			vo.setElectricType(rs.getString("electric_type"));
			vo.setRateType(rs.getString("rate_type"));
			vo.setVoltageType(rs.getString("voltage_type"));
			vo.setUpdatedBy(rs.getString("updated_by"));
			vo.setUpdatedDate(ConvertDateUtils.formatDateToString(rs.getDate("updated_date"),
					ConvertDateUtils.DD_MM_YYYY, ConvertDateUtils.LOCAL_EN));
			vo.setDescription(rs.getString("description"));
			return vo;
		}
	};

}
