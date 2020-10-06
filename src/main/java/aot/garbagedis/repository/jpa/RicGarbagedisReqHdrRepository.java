package aot.garbagedis.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import aot.garbagedis.model.RicGarbagedisReqHdr;

public interface RicGarbagedisReqHdrRepository extends CrudRepository<RicGarbagedisReqHdr, Long> {

	@Query("select e from #{#entityName} e where e.isDeleted = 'N'")
	List<RicGarbagedisReqHdr> findAll();
	
	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.serviceType = :serviceType and e.startDate <= CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 101) and e.endDate >= CONVERT(VARCHAR(10), CURRENT_TIMESTAMP, 101) ")
	List<RicGarbagedisReqHdr> findByServiceTypeAndCheckStartDateAndCheckEndDate(@Param("serviceType") String serviceType);
}
