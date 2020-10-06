package aot.garbagedis.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import aot.garbagedis.model.RicGarbagedisReqDtl;

public interface RicGarbagedisReqDtlRepository extends CrudRepository<RicGarbagedisReqDtl, Long> {

	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.garReqId = :garReqId")
	List<RicGarbagedisReqDtl> findAllByGarReqId(@Param("garReqId") Long garReqId);
}
