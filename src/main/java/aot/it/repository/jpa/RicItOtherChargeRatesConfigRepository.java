package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItOtherChargeRatesConfig;

@Repository
public interface RicItOtherChargeRatesConfigRepository extends CrudRepository<RicItOtherChargeRatesConfig, Long> {

}
