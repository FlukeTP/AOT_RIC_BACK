package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import aot.water.model.RicWaterInsuranceChargeRatesConfig;

public interface RicWaterInsuranceChargeRatesConfigRepository extends CrudRepository<RicWaterInsuranceChargeRatesConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.waterInsuranceConfigId desc")
	public List<RicWaterInsuranceChargeRatesConfig> datalist();
	
	public RicWaterInsuranceChargeRatesConfig findByWaterMeterSize(String MeterSize);
}
