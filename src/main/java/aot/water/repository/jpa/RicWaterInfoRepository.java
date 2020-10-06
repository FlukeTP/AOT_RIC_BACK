
package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterInfo;

@Repository
public interface RicWaterInfoRepository extends CrudRepository<RicWaterInfo, Long> {

	public RicWaterInfo findBySerialNoMeterAndPeriodMonth(String serialNoMeter, String periodMonth);

	public List<RicWaterInfo> findBySerialNoMeterAndIsDeleted(String meterSerialNo, String nFlag);
	
}
