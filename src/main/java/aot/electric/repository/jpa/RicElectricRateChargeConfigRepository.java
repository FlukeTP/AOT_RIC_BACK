package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricRateChargeConfig;

@Repository
public interface RicElectricRateChargeConfigRepository extends CrudRepository<RicElectricRateChargeConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.rateConfigId DESC")
	public List<RicElectricRateChargeConfig> findAllChargeConfig();

	@Query(value = " SELECT * FROM ric_electric_rate_charge_config where range_ampere = ?1 AND phase = ?2 AND is_delete = 'N' ", nativeQuery = true)
	public List<RicElectricRateChargeConfig> findByRangeAmpereByPhase(String ampare, String phase);
}
