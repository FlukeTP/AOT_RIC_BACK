package aot.water.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterMeter;
import aot.water.repository.jpa.RicWaterMeterRepository;
import aot.water.vo.request.Water002Req;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Water002Dao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	@Autowired
	RicWaterMeterRepository  ricWaterMeterRepository;
	
//	public List<RicElectricMeter> getallElectricMeter(Electric002Req req) {
//		List<RicElectricMeter> target = new ArrayList<RicElectricMeter>();
//		Iterable<RicElectricMeter> meterList = ricElectricMeterRepository.findAll();
//		meterList.forEach(target::add);	
//		return target;
//	}
	
	public List<RicWaterMeter> getallWaterMeter(Water002Req form) {
		List<RicWaterMeter> target = new ArrayList<RicWaterMeter>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_water_meter WHERE is_delete = 'N' ");

		if (StringUtils.isNotBlank(form.getSerialNo())) {
		sqlBuilder.append(" AND serial_no LIKE ? ");
		params.add("%" +  form.getSerialNo().replaceAll(" ", "%") + "%");
		}
		if(StringUtils.isNotBlank(form.getStatus())) {
		sqlBuilder.append(" AND meter_status = ? ");
		params.add(form.getStatus());
		}	
		sqlBuilder.append(" ORDER BY service_number DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}
	
	private RowMapper<RicWaterMeter> listRowmapper = new RowMapper<RicWaterMeter>() {
		@Override
		public RicWaterMeter mapRow(ResultSet rs, int arg1) throws SQLException {
			RicWaterMeter vo = new RicWaterMeter();
			vo.setMeterId(rs.getLong("meter_id"));
			vo.setAirport(rs.getString("airport"));
			vo.setSerialNo(rs.getString("serial_no"));
			vo.setMeterType(rs.getString("meter_type"));
			vo.setMeterName(rs.getString("meter_name"));
			vo.setMultipleValue(rs.getString("multiple_value"));
			vo.setMeterLocation(rs.getString("meter_location"));
			vo.setFunctionalLocation(rs.getString("functional_location"));
			vo.setMeterStatus(rs.getString("meter_status"));
			vo.setServiceNumber(rs.getString("service_number"));
			vo.setRemark(rs.getString("remark"));
			return vo;
		}
	};
	
	
	public void nativeSCript() {
		
	}
	
}
