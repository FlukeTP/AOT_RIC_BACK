package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItStaffPagePublicPageConfig;

@Repository
public interface RicItStaffPagePublicPageConfigRepository extends CrudRepository<RicItStaffPagePublicPageConfig, Long> {

}
