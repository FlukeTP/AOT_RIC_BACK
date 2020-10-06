package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricReqChange;

@Repository
public interface RicElectricReqChangeRepository  extends CrudRepository<RicElectricReqChange, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicElectricReqChange> findAll();
}
