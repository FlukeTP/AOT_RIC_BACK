package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricChargeMapping;

@Repository
public interface RicElectricChargeMappingRepository extends CrudRepository<RicElectricChargeMapping, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.typeConfigId = :typeConfigId")
	public List<RicElectricChargeMapping> findByTypeConfigId(@Param("typeConfigId") Long typeConfigId);
}
