package baiwa.module.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import baiwa.module.model.SystemConstant;

@Repository
public interface SystemConstantRepository extends CrudRepository<SystemConstant, Long> {

	public SystemConstant findByConstantKey(String key);

}
