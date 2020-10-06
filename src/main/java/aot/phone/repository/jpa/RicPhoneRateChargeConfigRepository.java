package aot.phone.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneRateChargeConfig;

@Repository
public interface RicPhoneRateChargeConfigRepository extends CrudRepository<RicPhoneRateChargeConfig, Long> {

	@Query(value = "select * from ric_phone_rate_charge_config  where is_delete = 'N' order by rate_config_id asc", nativeQuery = true)
	public List<RicPhoneRateChargeConfig> findAllCharge();

	@Query(value = "select e.* from ric_phone_rate_charge_config e  where e.phone_type = ?1 AND e.service_type = ?2 AND  e.is_delete = 'N' order by e.rate_config_id asc", nativeQuery = true)
	public RicPhoneRateChargeConfig findByPhoneTypeByServiceType(String phoneType, String serviceType);

}
