package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.SapCustomer;

@Repository
public interface SapCustomerRepository extends CrudRepository<SapCustomer, Long> {
	
	@Query(value = " SELECT * FROM sap_customer WHERE customer_type = ?1 AND status = 'Active' AND is_delete = 'N' ", nativeQuery = true)
	public List<SapCustomer> findByCustomerType(String type);
	
}
