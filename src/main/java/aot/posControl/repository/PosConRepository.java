package aot.posControl.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.posControl.model.RicPosFrequencyReport;
import aot.posControl.vo.request.PosConUserDetailRequest;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class PosConRepository {
	
	@Autowired
	CommonJdbcTemplate commonJdbcTemplate;
	
	public List<HashMap<String, String>> getFrequency(PosConUserDetailRequest req)  throws Exception{
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT sap_2.frequency, convert(date, sap_2.frequencyEnd, 112) AS frequency_end , sap_2.frequencyUnit");
		sql.append(" , convert(date, sap_2.frequencyStart, 112) AS frequency_start, pos_cus.customer_code, sap_2.reportingRuleNo ");
		sql.append(" , pos_cus.customer_name , pos_cus.customer_branch, sap_1.salesType , st.salesTypeName, sap_2.contractNo ");
		sql.append(" FROM fw_users fu ");
		sql.append(" LEFT JOIN ric_pos_customer pos_cus ON fu.pos_customer_id = pos_cus.pos_customer_id");
		sql.append(" LEFT JOIN aot_ric_t_POS_sapOut_2 sap_2 ON sap_2.contractNo = pos_cus.contract_no");
		sql.append(" LEFT JOIN aot_ric_t_POS_sapOut_1 sap_1 ON sap_1.contractNo = sap_2.contractNo ");
		sql.append(" LEFT JOIN aot_ric_m_POS_sapOutSaleType st ON sap_1.salesType = st.salesType ");
		sql.append(" WHERE fu.user_name = ? ");
		params.add(req.getUsername());
		return commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), posControlRowMapper);

	}
	
	private RowMapper<HashMap<String, String>> posControlRowMapper = new RowMapper<HashMap<String, String>>() {
		@Override
		public HashMap<String, String> mapRow(ResultSet rs, int arg1) throws SQLException {
			HashMap<String, String> vo = new HashMap<String, String>();
			vo.put("frequency",rs.getString("frequency"));
			vo.put("frequency_start",rs.getString("frequency_start"));
			vo.put("frequency_end",rs.getString("frequency_end"));
			vo.put("frequency_unit",rs.getString("frequencyUnit"));
			
			vo.put("customer_code",rs.getString("customer_code"));
			vo.put("customer_name",rs.getString("customer_name"));
			vo.put("contract_no",rs.getString("contractNo"));
			vo.put("customer_branch",rs.getString("customer_branch"));
			vo.put("sales_type",rs.getString("salesType"));
			vo.put("sales_type_name",rs.getString("salesTypeName"));
			vo.put("reporting_rule_no",rs.getString("reportingRuleNo"));
			
			return vo;
		}
	};
	
	public List<RicPosFrequencyReport> getFreqReport(PosConUserDetailRequest req){
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT pos_re.* FROM fw_users fu ");
		sql.append(" INNER JOIN ric_pos_customer pos_cus ON fu.pos_customer_id = pos_cus.pos_customer_id ");
		sql.append(" INNER JOIN ric_pos_frequency_report pos_re ON pos_cus.contract_no = pos_re.contract_no ");
		sql.append(" WHERE fu.user_name = ? ");
		params.add(req.getUsername());
		return commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), frequencyReportRowMapper);
		
	}
	
	private RowMapper<RicPosFrequencyReport> frequencyReportRowMapper = new RowMapper<RicPosFrequencyReport>() {
		@Override
		public RicPosFrequencyReport mapRow(ResultSet rs, int arg1) throws SQLException {
			RicPosFrequencyReport vo = new RicPosFrequencyReport();
			vo.setPosFrequencyReportId(rs.getLong("pos_frequency_report_id"));
			vo.setStartDate(rs.getDate("start_date"));
			vo.setEndDate(rs.getDate("end_date"));
			vo.setCustomerCode(rs.getString("customer_code"));
			vo.setCustomerName(rs.getString("customer_name"));
			vo.setContractNo(rs.getString("contract_no"));
			vo.setSaleType(rs.getString("sale_type"));
			vo.setSaleTypeName(rs.getString("sale_type_name"));
			vo.setStatus(rs.getString("status"));
			vo.setReportingRuleNo(rs.getString("reporting_rule_no"));
			return vo;
		}
	};
	
}
