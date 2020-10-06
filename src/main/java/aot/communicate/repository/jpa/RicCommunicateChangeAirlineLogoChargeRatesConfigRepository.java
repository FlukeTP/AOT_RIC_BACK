package aot.communicate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateChangeAirlineLogoChargeRatesConfig;

@Repository
public interface RicCommunicateChangeAirlineLogoChargeRatesConfigRepository extends CrudRepository<RicCommunicateChangeAirlineLogoChargeRatesConfig, Long> {

}
