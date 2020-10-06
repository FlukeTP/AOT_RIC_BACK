package baiwa.module.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import baiwa.module.model.FwLovHeader;

@Repository
public interface FwLovHeaderRepository extends CrudRepository<FwLovHeader, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.lovKey = :lovKey")
	public FwLovHeader findBylovKey(@Param("lovKey") String lovKey);

}
