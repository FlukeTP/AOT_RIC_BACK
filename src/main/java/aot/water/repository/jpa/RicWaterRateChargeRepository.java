package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterRateCharge;

@Repository
public interface RicWaterRateChargeRepository  extends CrudRepository<RicWaterRateCharge, Long>{

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.reqId = :reqId")
	public List<RicWaterRateCharge> findByReqId(@Param("reqId") Long id);
	
//	public List<RicWaterRateCharge> findByReqId(Long id);
}
