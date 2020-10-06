package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterServiceChargeRatesConfig;

@Repository
public interface RicWaterServiceConfigRepository extends CrudRepository<RicWaterServiceChargeRatesConfig, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by water_service_config_id DESC")
	public List<RicWaterServiceChargeRatesConfig> datalist();

}
