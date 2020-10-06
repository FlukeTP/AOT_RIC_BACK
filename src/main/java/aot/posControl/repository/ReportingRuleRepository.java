package aot.posControl.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.posControl.vo.request.PosConUserDetailRequest;
import aot.posControl.vo.response.ReportingRuleResponse;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class ReportingRuleRepository {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	public List<ReportingRuleResponse> reportingRuleBYcontract(PosConUserDetailRequest req) throws Exception{
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT reportingRuleNo as label , reportingRuleNo as value ");
		sql.append(" FROM aot_ric_t_POS_sapOut_2 WHERE contractNo = ? ");
		sql.append(" GROUP BY reportingRuleNo ");
		params.add(req.getContractNo());
		List<ReportingRuleResponse> ls = commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), reportingRowMapper);
		return ls;
	}
	
	
	
	private RowMapper<ReportingRuleResponse> reportingRowMapper = new RowMapper<ReportingRuleResponse>() {
		@Override
		public ReportingRuleResponse mapRow(ResultSet rs, int arg1) throws SQLException {
			ReportingRuleResponse vo = new ReportingRuleResponse();	
			vo.setLabel(rs.getString("label"));
			vo.setValue(rs.getString("value"));
			return vo;
		}
	};
}