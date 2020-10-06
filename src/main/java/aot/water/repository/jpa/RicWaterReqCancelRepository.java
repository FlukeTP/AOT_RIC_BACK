package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterReqCancel;

@Repository
public interface RicWaterReqCancelRepository extends CrudRepository<RicWaterReqCancel, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicWaterReqCancel> findAll();
}
