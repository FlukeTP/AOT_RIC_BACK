package aot.it.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItDedicatedCUTEList;

@Repository
public interface RicItDedicatedCUTEListRepository extends CrudRepository<RicItDedicatedCUTEList, Long> {

//	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.dedicatedInvoiceId = :dedicatedInvoiceId and e.months = :months and e.years = :years")
//	RicItDedicatedCUTEList findByDedicatedInvoiceIdAndMonthsAndYears(@Param("dedicatedInvoiceId") Long dedicatedInvoiceId, @Param("months") String months, @Param("years") String years);
//	
//	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.months = :months and e.years = :years")
//	List<RicItDedicatedCUTEList> findByMonthsAndYears(@Param("months") String months, @Param("years") String years);
}
