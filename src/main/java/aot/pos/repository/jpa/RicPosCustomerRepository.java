package aot.pos.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.pos.model.RicPosCustomer;

@Repository
public interface RicPosCustomerRepository extends CrudRepository<RicPosCustomer, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.contractNo = :contractNo")
	public RicPosCustomer checkContractNo(@Param("contractNo") String contractNo);
}
