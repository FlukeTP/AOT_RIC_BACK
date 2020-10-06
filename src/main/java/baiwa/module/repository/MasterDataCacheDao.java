package baiwa.module.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import baiwa.module.model.MasterDataCacheItem;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class MasterDataCacheDao {
	
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	
	
	public List<String> getHeaderKey() {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT lov_key FROM fw_lov_header WHERE is_delete = 'N' ");
		sql.append(" ORDER BY lov_header_id DESC ");
		
		return commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), listRowmapper);
	}
	
	private RowMapper<String> listRowmapper = new RowMapper<String>() {
		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("lov_key");
		}
	};
	
	
	public List<MasterDataCacheItem> getDetailItem(String lovKey) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT lov_key, lov_code, desc_th_1, desc_th_2, desc_en_1, desc_en_2 ");
		sql.append(" FROM fw_lov_detail WHERE is_delete = 'N' ");
		sql.append(" AND lov_key = ? ");
		sql.append(" ORDER BY order_no ASC ");
		params.add(lovKey);
		
		return commonJdbcTemplate.executeQuery(sql.toString(), params.toArray(), rowmapperDetail);
	}
	
	private RowMapper<MasterDataCacheItem> rowmapperDetail = new RowMapper<MasterDataCacheItem>() {
		@Override
		public MasterDataCacheItem mapRow(ResultSet rs, int arg1) throws SQLException {
			
			MasterDataCacheItem vo = new MasterDataCacheItem();
			vo.setLovKey(rs.getString("lov_key"));
			vo.setLovCode(rs.getString("lov_code"));
			vo.setDescTh1(rs.getString("desc_th_1"));
			vo.setDescTh2(rs.getString("desc_th_2"));
			vo.setDescEn1(rs.getString("desc_en_1"));
			vo.setDescEn2(rs.getString("desc_en_2"));
			return vo;
		}
	};
}
