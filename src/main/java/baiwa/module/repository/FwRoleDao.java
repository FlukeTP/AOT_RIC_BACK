package baiwa.module.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import baiwa.util.CommonJdbcTemplate;

@Repository
public class FwRoleDao {

	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
}
