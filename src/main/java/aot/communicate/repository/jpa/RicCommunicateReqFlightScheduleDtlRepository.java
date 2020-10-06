package aot.communicate.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateReqFlightScheduleDtl;

@Repository
public interface RicCommunicateReqFlightScheduleDtlRepository
		extends CrudRepository<RicCommunicateReqFlightScheduleDtl, Long> {

	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.idHdr = :idHdr")
	public List<RicCommunicateReqFlightScheduleDtl> findByIdHdr(@Param("idHdr") Long idHdr);

}
