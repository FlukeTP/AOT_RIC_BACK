package aot.electric.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricChargeTypeConfig;

@Repository
public interface RicElectricChargeTypeConfigRepository extends CrudRepository<RicElectricChargeTypeConfig, Long> {

}
