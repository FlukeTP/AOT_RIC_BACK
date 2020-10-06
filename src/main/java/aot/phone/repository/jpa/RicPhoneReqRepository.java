package aot.phone.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneReq;

@Repository
public interface RicPhoneReqRepository extends CrudRepository<RicPhoneReq, Long> {

	public Integer countByFlagInfo(String nFlag);
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.phoneNo = :phoneNo")
	public RicPhoneReq findByPhoneNo(@Param("phoneNo") String phoneNo);

}
