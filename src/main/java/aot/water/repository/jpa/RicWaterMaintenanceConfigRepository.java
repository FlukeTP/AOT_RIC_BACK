package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterMaintenanceChargeRatesConfig;

@Repository
public interface RicWaterMaintenanceConfigRepository extends CrudRepository<RicWaterMaintenanceChargeRatesConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by water_maintenance_config_id DESC")
	public List<RicWaterMaintenanceChargeRatesConfig> datalist();
}
