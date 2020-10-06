package baiwa.module.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import baiwa.module.model.FwUsers;
import baiwa.module.vo.request.UsersReq;
import baiwa.util.CommonJdbcTemplate;

@Repository
public class FwUserDao {
	
	
	@Autowired
	CommonJdbcTemplate  commonJdbcTemplate;
	
	public List<FwUsers> getAlluser(UsersReq req) {
		
		return null;
	}
}
