package aot.water.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterWasteDetail;

@Repository
public interface RicWaterWasteDetailRepository extends CrudRepository<RicWaterWasteDetail, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.wasteHeaderId = :wasteHeaderId")
	public List<RicWaterWasteDetail> findByWasteHeaderId(@Param("wasteHeaderId") Long wasteHeaderId);

}
