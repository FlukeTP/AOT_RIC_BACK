package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import baiwa.module.model.FwOrg;

public interface FwOrgRepository extends CrudRepository<FwOrg, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<FwOrg> findAll();
}
