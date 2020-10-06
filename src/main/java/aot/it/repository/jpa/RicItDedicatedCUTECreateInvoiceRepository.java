package aot.it.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItDedicatedCUTECreateInvoice;

@Repository
public interface RicItDedicatedCUTECreateInvoiceRepository extends CrudRepository<RicItDedicatedCUTECreateInvoice, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N'")
	List<RicItDedicatedCUTECreateInvoice> findAll();
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.requestStartDate <= CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 101)")
	List<RicItDedicatedCUTECreateInvoice> findByCheckStartDate();
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.requestStartDate <= CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 101) and e.requestEndDate >= CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 101)")
	List<RicItDedicatedCUTECreateInvoice> findByCheckStartDateAndCheckEndDate();
}
