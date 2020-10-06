package aot.heavyeqp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.heavyeqp.model.RicHeavyManageEquipmentType;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Heavyeqp004Dao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
		
	public List<RicHeavyManageEquipmentType> getallHeavyEquipments() {
		List<RicHeavyManageEquipmentType> target = new ArrayList<RicHeavyManageEquipmentType>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_heavy_manage_equipment_type WHERE is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY heavy_manage_equipment_type_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}
	
	private RowMapper<RicHeavyManageEquipmentType> listRowmapper = new RowMapper<RicHeavyManageEquipmentType>() {
		@Override
		public RicHeavyManageEquipmentType mapRow(ResultSet rs, int arg1) throws SQLException {
			RicHeavyManageEquipmentType vo = new RicHeavyManageEquipmentType();
			vo.setHeavyManageEquipmentTypeId(rs.getLong("heavy_manage_equipment_type_id"));
			vo.setGlAccount(rs.getString("gl_account"));
			vo.setEquipmentType(rs.getString("equipment_type"));
			vo.setDetail(rs.getString("detail"));
			vo.setCreatedBy(rs.getString("created_by"));
			return vo;
		}
	};

}
