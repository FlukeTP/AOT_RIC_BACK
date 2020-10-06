package aot.communicate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import aot.communicate.model.RicCommunicateFlightInfoChargeRatesConfig;

@Repository
public interface RicCommunicateFlightInfoChargeRatesConfigRepository extends CrudRepository<RicCommunicateFlightInfoChargeRatesConfig, Long> {

}
