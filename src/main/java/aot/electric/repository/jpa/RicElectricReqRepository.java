package aot.electric.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricReq;

@Repository
public interface RicElectricReqRepository extends CrudRepository<RicElectricReq, Long>{

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.meterSerialNo = :serialNo")
	public RicElectricReq findBySerialNo(@Param("serialNo") String serialNo);
	
	public RicElectricReq findByReqId(Long reqId);
}
