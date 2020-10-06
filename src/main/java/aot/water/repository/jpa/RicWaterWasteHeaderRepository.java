package aot.water.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.water.model.RicWaterWasteHeader;
@Repository
public interface RicWaterWasteHeaderRepository extends CrudRepository<RicWaterWasteHeader, Long> {

}
