package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.electric.model.SystemCalConfig;

@Repository
public interface SystemCalConfigRepository extends CrudRepository<SystemCalConfig, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.validDate desc")
	public List<SystemCalConfig> findAllCalConfig();

}
