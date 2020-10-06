package aot.electric.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricMeter;

@Repository
public interface RicElectricMeterRepository extends CrudRepository<RicElectricMeter, Long>{
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.serialNo = :serialNo")
	public RicElectricMeter findBySerialNo(@Param("serialNo") String serialNo);

}
