package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterInstallationChargeRatesConfig;

@Repository
public interface RicWaterInstallationConfigRepository  extends CrudRepository<RicWaterInstallationChargeRatesConfig, Long> {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by water_installation_id DESC")
	public List<RicWaterInstallationChargeRatesConfig> datalist();
	
	public RicWaterInstallationChargeRatesConfig findByWaterMeterSize(String MeterSize);

}
