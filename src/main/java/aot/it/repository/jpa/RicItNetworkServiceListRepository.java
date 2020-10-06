package aot.it.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItNetworkServiceList;

@Repository
public interface RicItNetworkServiceListRepository extends CrudRepository<RicItNetworkServiceList, Long> {

//	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.networkCreateInvoiceId = :networkCreateInvoiceId and e.months = :months and e.years = :years")
//	RicItNetworkServiceList findByNetworkCreateInvoiceIdAndMonthsAndYears(@Param("networkCreateInvoiceId") Long networkCreateInvoiceId, @Param("months") String months, @Param("years") String years);
//	
//	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.months = :months and e.years = :years")
//	List<RicItNetworkServiceList> findByMonthsAndYears(@Param("months") String months, @Param("years") String years);
}
