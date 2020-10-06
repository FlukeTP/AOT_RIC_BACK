package aot.heavyeqp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.heavyeqp.model.RicManageHeavyEquipment;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Heavyeqp002Dao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
		
	public List<RicManageHeavyEquipment> getallHeavyEquipments() {
		List<RicManageHeavyEquipment> target = new ArrayList<RicManageHeavyEquipment>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM ric_manage_heavy_equipment WHERE is_delete = 'N' ");
		sqlBuilder.append(" ORDER BY manage_heavy_equipment_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}
	
	private RowMapper<RicManageHeavyEquipment> listRowmapper = new RowMapper<RicManageHeavyEquipment>() {
		@Override
		public RicManageHeavyEquipment mapRow(ResultSet rs, int arg1) throws SQLException {
			RicManageHeavyEquipment vo = new RicManageHeavyEquipment();
			vo.setManageHeavyEquipmentId(rs.getLong("manage_heavy_equipment_id"));
			vo.setEquipmentCode(rs.getString("equipment_code"));
			vo.setEquipmentType(rs.getString("equipment_type"));
			vo.setEquipmentNo(rs.getString("equipment_no"));
			vo.setStatus(rs.getString("status"));
			vo.setResponsiblePerson(rs.getString("responsible_person"));
			vo.setRemark(rs.getString("remark"));
			vo.setNumberLicensePlate(rs.getString("number_license_plate"));
			vo.setLicensePlate(rs.getString("license_plate"));
			return vo;
		}
	};

}
