package aot.communicate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateChangeAirlineLogo;

@Repository
public interface RicCommunicateChangeAirlineLogoRepository extends CrudRepository<RicCommunicateChangeAirlineLogo, Long> {

}
