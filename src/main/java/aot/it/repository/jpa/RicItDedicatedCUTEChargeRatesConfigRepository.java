package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItDedicatedCUTEChargeRatesConfig;

@Repository
public interface RicItDedicatedCUTEChargeRatesConfigRepository extends CrudRepository<RicItDedicatedCUTEChargeRatesConfig, Long> {

}
