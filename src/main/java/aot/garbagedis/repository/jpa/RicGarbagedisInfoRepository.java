package aot.garbagedis.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.garbagedis.model.RicGarbagedisInfo;

@Repository
public interface RicGarbagedisInfoRepository extends CrudRepository<RicGarbagedisInfo, Long> {


	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.garReqId = :garReqId and e.months = :months and e.years = :years")
	RicGarbagedisInfo findByGarReqIdAndMonthsAndYears(@Param("garReqId") Long garReqId, @Param("months") String months, @Param("years") String years);
	
	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.months = :months and e.years = :years")
	List<RicGarbagedisInfo> findByMonthsAndYears(@Param("months") String months, @Param("years") String years);
}
