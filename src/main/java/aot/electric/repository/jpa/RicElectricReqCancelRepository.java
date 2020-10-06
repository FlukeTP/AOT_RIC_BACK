package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricReqCancel;

@Repository
public interface RicElectricReqCancelRepository extends CrudRepository<RicElectricReqCancel, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicElectricReqCancel> findAll();
}
