package baiwa.module.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import baiwa.module.model.SystemConstant;
import baiwa.module.vo.request.SysConstantReq;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class SystemConstantDao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public List<SystemConstant> list(SysConstantReq form) {
		List<SystemConstant> target = new ArrayList<SystemConstant>();
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM system_constant WHERE is_delete = 'N' ");

		if (StringUtils.isNotBlank(form.getConstantKey())) {
		sqlBuilder.append(" AND constant_key LIKE ? ");
		params.add("%" +  form.getConstantKey().replaceAll(" ", "%") + "%");
		}
		sqlBuilder.append(" ORDER BY constant_id DESC ");
		target = commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), listRowmapper);
		return target;
	}
	
	private RowMapper<SystemConstant> listRowmapper = new RowMapper<SystemConstant>() {
		@Override
		public SystemConstant mapRow(ResultSet rs, int arg1) throws SQLException {
			SystemConstant vo = new SystemConstant();
			vo.setConstantId(rs.getLong("constant_id"));
			vo.setConstantKey(rs.getString("constant_key"));
			vo.setConstantValue(rs.getString("constant_value"));
			return vo;
		}
	};

}
