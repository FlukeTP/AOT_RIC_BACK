package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricInfo;

@Repository
public interface RicElectricInfoRepository extends CrudRepository<RicElectricInfo, Long> {

	public RicElectricInfo findBySerialNoMeterAndPeriodMonth(String serialNoMeter, String periodMonth);

	public List<RicElectricInfo> findBySerialNoMeterAndIsDeleted(String meterSerialNo, String nFlag);

	public RicElectricInfo findByPeriodMonthAndSerialNoMeter(String periodMonth, String serialNoMeter);
	
}
