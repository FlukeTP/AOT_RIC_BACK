package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItCUTETrainingRoomUsageReport;

@Repository
public interface RicItCUTETrainingRoomUsageReportRepository extends CrudRepository<RicItCUTETrainingRoomUsageReport, Long>  {

	
}
