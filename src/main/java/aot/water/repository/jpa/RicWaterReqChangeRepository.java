package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterReqChange;

@Repository
public interface RicWaterReqChangeRepository extends CrudRepository<RicWaterReqChange, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicWaterReqChange> findAll();
}
