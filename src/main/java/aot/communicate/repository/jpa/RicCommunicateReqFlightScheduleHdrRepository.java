package aot.communicate.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateReqFlightScheduleHdr;

@Repository
public interface RicCommunicateReqFlightScheduleHdrRepository
		extends CrudRepository<RicCommunicateReqFlightScheduleHdr, Long> {

	@Query("select e from #{#entityName} e where e.isDeleted = 'N' and e.transactionNo = :transactionNo")
	public RicCommunicateReqFlightScheduleHdr findByTransactionNo(@Param("transactionNo") String transactionNo);

}
