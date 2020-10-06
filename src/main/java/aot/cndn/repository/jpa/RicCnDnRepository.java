package aot.cndn.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.cndn.model.RicCnDn;

@Repository
public interface RicCnDnRepository extends CrudRepository<RicCnDn, Long> {

}
