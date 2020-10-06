package aot.sap.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import aot.sap.model.SapRicControl;
import aot.util.sap.constant.SAPConstants;
import aot.util.sap.domain.response.SAPARResponseSuccess;
import baiwa.util.CommonJdbcTemplate;
import baiwa.util.UserLoginUtils;

@Repository
public class SapRicControlDao {
	private static final Logger logger = LoggerFactory.getLogger(SapRicControlDao.class);

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;

	@Autowired
	private CommonJdbcTemplate jdbcTemplate;

	public List<SapRicControl> findByDocNo(String docNo) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM sap_ric_control where docno = ? ");
		params.add(docNo);
		return commonJdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), sapControlRowMapper);
	}

	private RowMapper<SapRicControl> sapControlRowMapper = new RowMapper<SapRicControl>() {
		@Override
		public SapRicControl mapRow(ResultSet rs, int arg1) throws SQLException {
			SapRicControl vo = new SapRicControl();
			vo.setId(rs.getLong("sqp_ric_control_id"));
			vo.setComp(rs.getString("comp"));
			vo.setDocno(rs.getString("docno"));
			vo.setRefkey1(rs.getString("refkey1"));
			vo.setDzdocNo(rs.getString("dzdocNo"));
			vo.setDzref(rs.getString("dzref"));
			vo.setDzyear(rs.getString("dzyear"));
			vo.setReverseInv(rs.getString("reverse_inv"));
			vo.setReverseRec(rs.getString("reverse_rec"));
			vo.setYear(rs.getString("year"));
			return vo;
		}
	};

	public void updateDataJdbc(SAPARResponseSuccess sapARResponse, Boolean LG) {
		logger.info("###########  updateSapControlJdbc ###########");
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		if (StringUtils.isNotBlank(sapARResponse.getDOCNO())) {
			if (this.findNo(sapARResponse.getTRANSNO()) > 0) {
				sqlBuilder.append(" UPDATE sap_ric_control ");
				sqlBuilder.append(" SET docno = ? ");
				params.add(sapARResponse.getDOCNO());
				
				sqlBuilder.append(" , updated_date = ? ");
				params.add(UserLoginUtils.getDateNow());
				
				sqlBuilder.append(" , reverse_inv = null ");
				sqlBuilder.append("	WHERE refkey1 = ? ");
				params.add(sapARResponse.getTRANSNO());
				logger.info("executeupdate sql : {}", sqlBuilder.toString());
			} else {
				/* check LG (true => insert N/A to dzdocNo) */
				if(LG) {
					sqlBuilder.append(" INSERT INTO sap_ric_control ( refkey1, comp, docno, year, dzdocNo, create_date ) ");
					sqlBuilder.append(" VALUES (?,?,?,?,?,?)");
					params.add(sapARResponse.getTRANSNO());
					params.add(sapARResponse.getCOMP());
					params.add(sapARResponse.getDOCNO());
					params.add(sapARResponse.getYEAR());
					params.add(SAPConstants.N_A);
					params.add(UserLoginUtils.getDateNow());
				} else {
					sqlBuilder.append(" INSERT INTO sap_ric_control ( refkey1, comp, docno, year, create_date ) ");
					sqlBuilder.append(" VALUES (?,?,?,?,?)");
					params.add(sapARResponse.getTRANSNO());
					params.add(sapARResponse.getCOMP());
					params.add(sapARResponse.getDOCNO());
					params.add(sapARResponse.getYEAR());
					params.add(UserLoginUtils.getDateNow());
				}
				logger.info("executeInsert sql : {}", sqlBuilder.toString());
			}
			jdbcTemplate.executeInsert(sqlBuilder.toString(), params.toArray());
			logger.info("execute sql : Completed");
		}
	}

	private int findNo(String transacNo) {

		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT COUNT(1) as count FROM sap_ric_control WHERE  refkey1 = ?  ");

		params.add(transacNo);
		List<Integer> items = jdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), rowMapper);

		return items.get(0);
	}

	private RowMapper<Integer> rowMapper = new RowMapper<Integer>() {
		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getInt("count");
		}
	};

	public List<SapRicControl> findByRefkey1(String refkey1) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sqlBuilder.append(" SELECT * FROM sap_ric_control WHERE refkey1 = ?  ");
		params.add(refkey1);
		return jdbcTemplate.executeQuery(sqlBuilder.toString(), params.toArray(), rowMapperFindByRefkey1);
	}

	private RowMapper<SapRicControl> rowMapperFindByRefkey1 = new RowMapper<SapRicControl>() {
		@Override
		public SapRicControl mapRow(ResultSet rs, int arg1) throws SQLException {
			SapRicControl vo = new SapRicControl();
			vo.setId(rs.getLong("sqp_ric_control_id"));
			vo.setRefkey1(rs.getString("refkey1"));
			vo.setComp(rs.getString("comp"));
			vo.setDocno(rs.getString("docno"));
			vo.setYear(rs.getString("year"));
			vo.setReverseInv(rs.getString("reverse_inv"));
			vo.setDzyear(rs.getString("dzyear"));
			vo.setDzdocNo(rs.getString("dzdocNo"));
			vo.setDzref(rs.getString("dzref"));
			vo.setReverseRec(rs.getString("reverse_rec"));
			vo.setCreateDate(rs.getDate("create_date"));
			vo.setUpdatedDate(rs.getDate("updated_date"));
			return vo;
		}
	};

	public void updateSapConection(String sqlUpdateSapConnection, List<Object> params) {
		commonJdbcTemplate.executeUpdate(sqlUpdateSapConnection, params.toArray());
	}

}
