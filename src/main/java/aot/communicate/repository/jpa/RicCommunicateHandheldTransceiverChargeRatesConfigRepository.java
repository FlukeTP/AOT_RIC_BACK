package aot.communicate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import aot.communicate.model.RicCommunicateHandheldTransceiverChargeRatesConfig;

@Repository
public interface RicCommunicateHandheldTransceiverChargeRatesConfigRepository extends CrudRepository<RicCommunicateHandheldTransceiverChargeRatesConfig, Long> {

}
