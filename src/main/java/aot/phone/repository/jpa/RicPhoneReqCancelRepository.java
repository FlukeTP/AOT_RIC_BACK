package aot.phone.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneReqCancel;

@Repository
public interface RicPhoneReqCancelRepository extends CrudRepository<RicPhoneReqCancel, Long>  {

	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicPhoneReqCancel> findAll();
}
