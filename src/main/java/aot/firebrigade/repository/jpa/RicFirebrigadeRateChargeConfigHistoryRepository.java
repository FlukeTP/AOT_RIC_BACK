package aot.firebrigade.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.firebrigade.model.RicFirebrigadeRateChargeConfigHistory;

@Repository
public interface RicFirebrigadeRateChargeConfigHistoryRepository extends CrudRepository<RicFirebrigadeRateChargeConfigHistory, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.rateConfigId = :rateConfigId order by e.rateHistoryId DESC")
	public List<RicFirebrigadeRateChargeConfigHistory> findByRateConfigId(@Param("rateConfigId") Long rateConfigId);
}
