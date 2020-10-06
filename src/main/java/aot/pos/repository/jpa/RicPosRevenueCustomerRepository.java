package aot.pos.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.pos.model.RicPosRevenueCustomer;

@Repository
public interface RicPosRevenueCustomerRepository extends CrudRepository<RicPosRevenueCustomer, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	public List<RicPosRevenueCustomer> findAll();
}
