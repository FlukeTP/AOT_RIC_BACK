package aot.common.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.common.model.Users;
import aot.electric.model.RicElectricInfo;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class UsersRepository {
	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;

	public Users getUsers(String username) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM FW_USERS ");
		sqlBuilder.append(" WHERE USER_NAME = ? ");
		sqlBuilder.append(" AND IS_DELETE = 'N' ");
		params.add(username);
		return commonJdbcTemplate.executeQueryForObject(sqlBuilder.toString(), params.toArray(), UsersRowMapper);
	}
	
	private RowMapper<Users> UsersRowMapper = new RowMapper<Users>() {
		@Override
		public Users mapRow(ResultSet rs, int arg1) throws SQLException {
			Users vo = new Users();
			vo.setUserId(rs.getLong("user_id"));
			vo.setUserName(rs.getString("user_name"));
			vo.setAirportCode(rs.getString("airport_code"));
			vo.setAirportDes(rs.getString("airport_des"));
			vo.setEmail(rs.getString("email"));
			vo.setName(rs.getString("name"));
			vo.setSurname(rs.getString("surname"));
			vo.setProfitCenter(rs.getString("profit_center"));
			vo.setPosCustomerId(rs.getLong("pos_customer_id"));
			return vo;
		}
	};
	
}
