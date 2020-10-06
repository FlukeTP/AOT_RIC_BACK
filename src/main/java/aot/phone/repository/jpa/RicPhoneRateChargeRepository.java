package aot.phone.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneRateCharge;

@Repository
public interface RicPhoneRateChargeRepository extends CrudRepository<RicPhoneRateCharge, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.reqId = :phoneReqId order by e.rateChargeId asc")
	public List<RicPhoneRateCharge> findByReqId(@Param("phoneReqId") String phoneReqId);
}
