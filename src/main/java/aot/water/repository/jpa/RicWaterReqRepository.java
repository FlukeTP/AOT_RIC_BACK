package aot.water.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterReq;

@Repository
public interface RicWaterReqRepository extends CrudRepository <RicWaterReq, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.meterSerialNo = :serialNo")
	public RicWaterReq findBySerialNo(@Param("serialNo") String serialNo);
}
