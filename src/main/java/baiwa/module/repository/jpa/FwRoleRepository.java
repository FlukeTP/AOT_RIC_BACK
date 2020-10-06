package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import baiwa.module.model.FwRole;

public interface FwRoleRepository extends CrudRepository<FwRole, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<FwRole> findAll();
}
