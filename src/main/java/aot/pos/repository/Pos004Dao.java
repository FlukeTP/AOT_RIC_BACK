package aot.pos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.pos.vo.response.Pos004Res;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class Pos004Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	
	public List<Pos004Res> findData() {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT "  
				+" c.pos_customer_id, "  
				+" c.customer_code, "  
				+" c.customer_name, " 
				+" c.customer_branch, " 
				+" c.contract_no, "  
				+" COUNT(u.user_name) AS num_user "  
				+" FROM ric_pos_customer c "  
				+" LEFT JOIN fw_users u "  
				+" ON c.pos_customer_id = u.pos_customer_id "  
				+" GROUP BY c.pos_customer_id, "  
				+" c.customer_code, "  
				+" c.customer_name, "  
				+" c.customer_branch, " 
				+" c.contract_no "  
				+" ORDER BY c.pos_customer_id ASC ");

		List<Pos004Res> data = commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), pos004Rowmapper);
		return data;
	}
	
	private RowMapper<Pos004Res> pos004Rowmapper = new RowMapper<Pos004Res>() {
		@Override
		public Pos004Res mapRow(ResultSet rs, int arg1) throws SQLException {
			Pos004Res vo = new Pos004Res();
			vo.setPosCustomerId(rs.getString("pos_customer_id"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setCustomerBranch(rs.getString("customer_branch"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setNumUser(rs.getString("num_user"));
			return vo;
		}
	};
	
}
