package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import baiwa.module.model.FwUsers;

@Repository
public interface FwUsersRepository extends CrudRepository<FwUsers, Long> {

	@Query(value = "select * from fw_users e where e.is_delete = 'N' ", nativeQuery = true)
	public List<FwUsers> findAll();

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.userName = :userName")
	public FwUsers checkUserName(@Param("userName") String userName);

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.posCustomerId = :posCustomerId")
	public List<FwUsers> posUserAll(@Param("posCustomerId") Long posCustomerId);
}