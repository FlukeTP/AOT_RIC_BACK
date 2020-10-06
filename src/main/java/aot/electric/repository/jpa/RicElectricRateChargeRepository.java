package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import aot.electric.model.RicElectricRateCharge;

public interface RicElectricRateChargeRepository extends CrudRepository<RicElectricRateCharge, Long>{

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.reqId = :reqId")
	public List<RicElectricRateCharge> findByReqId(@Param("reqId") Long reqId);
}
