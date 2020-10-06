package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItNetworkChargeRatesConfig;

@Repository
public interface RicItNetworkChargeRatesConfigRepository extends CrudRepository<RicItNetworkChargeRatesConfig, Long> {

}
