package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItStaffPagePublicPageReq;

@Repository
public interface RicItStaffPagePublicPageReqRepository extends CrudRepository<RicItStaffPagePublicPageReq, Long> {

}
