package aot.firebrigade.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.firebrigade.model.RicFirebrigadeManage;

@Repository
public interface RicFirebrigadeManageRepository extends CrudRepository<RicFirebrigadeManage, Long> {

}
