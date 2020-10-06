package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import baiwa.module.model.FwUsersRole;

@Repository
public interface FwUsersRoleRepository extends CrudRepository<FwUsersRole, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<FwUsersRole> findAll();
	
	@Query(value ="select * from fw_users_role where is_delete = 'N' AND username = ?1 " ,  nativeQuery = true)
	public List<FwUsersRole> findByUsername(String username);
}
