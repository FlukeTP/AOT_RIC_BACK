package aot.electric.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.electric.model.RicElectricReqFile;

@Repository
public interface RicElectricReqFileRepository extends CrudRepository<RicElectricReqFile, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.reqId = :reqId order by e.reqFileId desc")
	public List<RicElectricReqFile> findByReqId(@Param("reqId") Long reqId);
	
	public RicElectricReqFile findByReqFileId( Long reqFileId);
}
