package aot.water.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterMeter;

@Repository
public interface RicWaterMeterRepository extends CrudRepository<RicWaterMeter, Long>{
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.serialNo = :serialNo")
	public RicWaterMeter findBySerialNo(@Param("serialNo") String serialNo);

}
