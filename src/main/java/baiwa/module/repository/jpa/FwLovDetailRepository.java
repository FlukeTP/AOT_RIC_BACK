package baiwa.module.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import baiwa.module.model.FwLovDetail;

public interface FwLovDetailRepository extends CrudRepository<FwLovDetail, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.lovKey = :lovKey")
	public List<FwLovDetail> findByLovKey(@Param("lovKey") String lovKey);

	public FwLovDetail findByLovKeyAndLovCode(String lovKey, String lovCode);

}
