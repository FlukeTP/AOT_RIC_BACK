package aot.phone.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import baiwa.util.CommonJdbcTemplate;

@Repository
public class Phone004Dao {
	@Autowired
	private CommonJdbcTemplate commonJdbcTemplate;
	

}
