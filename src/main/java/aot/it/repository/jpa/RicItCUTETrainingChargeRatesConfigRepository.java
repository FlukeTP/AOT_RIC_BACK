package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItCUTETrainingChargeRatesConfig;

@Repository
public interface RicItCUTETrainingChargeRatesConfigRepository extends CrudRepository<RicItCUTETrainingChargeRatesConfig, Long> {

}
