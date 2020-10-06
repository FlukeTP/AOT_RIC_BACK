package aot.electric.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricMeter;
import aot.electric.repository.jpa.RicElectricMeterRepository;
import aot.electric.vo.request.Electric002Req;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Electric002Dao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	@Autowired
	RicElectricMeterRepository  ricElectricMeterRepository;
	
	
	public List<RicElectricMeter> getallElectricMeter(Electric002Req form) {
		List<RicElectricMeter> target = new ArrayList<RicElectricMeter>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append("  SELECT * FROM ric_electric_meter WHERE 1=1 ");
		sqlBuilder.append(" AND is_delete = 'N' ");
		if (StringUtils.isNotBlank(form.getSerialNo())) {
		sqlBuilder.append("  AND ");
		sqlBuilder.append("  serial_no LIKE ? ");
		sqlBuilder.append("  OR meter_name LIKE ? ");
		sqlBuilder.append("  OR meter_type LIKE ? ");
//		sqlBuilder.append("  OR meter_location LIKE ? ");
		params.add("%" +  form.getSerialNo().replaceAll(" ", "%") + "%");
		params.add("%" +  form.getSerialNo().replaceAll(" ", "%") + "%");
		params.add("%" +  form.getSerialNo().replaceAll(" ", "%") + "%");
//		params.add("%" +  form.getSerialNo().replaceAll(" ", "%") + "%");
		}
		if(StringUtils.isNotBlank(form.getStatus())) {
		sqlBuilder.append(" AND meter_status LIKE ? ");
		params.add("%" +  form.getStatus().replaceAll(" ", "%") + "%");
		}
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}
	
	private RowMapper<RicElectricMeter> listRowmapper = new RowMapper<RicElectricMeter>() {
		@Override
		public RicElectricMeter mapRow(ResultSet rs, int arg1) throws SQLException {
			RicElectricMeter vo = new RicElectricMeter();
			vo.setMeterId(rs.getLong("meter_id"));
			vo.setSerialNo(rs.getString("serial_no"));
			vo.setMeterType(rs.getString("meter_type"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setMultipleValue(rs.getString("multiple_value"));
			vo.setMeterLocation(rs.getString("meter_location"));
			vo.setFunctionalLocation(rs.getString("functional_location"));
			vo.setMeterStatus(rs.getString("meter_status"));
			vo.setServiceNumber(rs.getString("service_number"));
			vo.setAirport(rs.getString("airport"));
			vo.setRemark(rs.getString("remark"));
			return vo;
		}
	};
	
	
	public void nativeSCript() {
		
	}
	
}
