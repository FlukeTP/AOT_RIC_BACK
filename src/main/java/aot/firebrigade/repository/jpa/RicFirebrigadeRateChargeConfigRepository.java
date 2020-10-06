package aot.firebrigade.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.firebrigade.model.RicFirebrigadeRateChargeConfig;

@Repository
public interface RicFirebrigadeRateChargeConfigRepository extends CrudRepository<RicFirebrigadeRateChargeConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.rateConfigId DESC")
	public List<RicFirebrigadeRateChargeConfig> findAllChargeConfig();
}
