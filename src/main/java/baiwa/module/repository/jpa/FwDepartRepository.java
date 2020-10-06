package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import baiwa.module.model.FwDepart;

public interface FwDepartRepository extends CrudRepository<FwDepart, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<FwDepart> findAll();
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.orgCode = :orgCode")
	public List<FwDepart> findByOrgCode(@Param("orgCode") String orgCode);
	
	@Query("select e from #{#entityName} e where e.isDelete = 'Y' and e.orgCode = :orgCode")
	public List<FwDepart> findByOrgCodeY(@Param("orgCode") String orgCode);
}
