package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterMaintenanceOtherConfig;

@Repository
public interface RicWaterMaintenanceOtherConfigRepository extends CrudRepository<RicWaterMaintenanceOtherConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.waterOtherId desc")
	public List<RicWaterMaintenanceOtherConfig> datalist();
}
