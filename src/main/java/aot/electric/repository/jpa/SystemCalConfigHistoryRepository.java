package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.electric.model.SystemCalConfigHistory;

@Repository
public interface SystemCalConfigHistoryRepository extends CrudRepository<SystemCalConfigHistory, Long>  {
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' order by e.historyId asc")
	public List<SystemCalConfigHistory> findAll();
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.historyCodeType = :historyCodeType order by e.historyDate desc")
	public List<SystemCalConfigHistory> findByHistoryCodeType(@Param("historyCodeType") String historyCodeType);
}
