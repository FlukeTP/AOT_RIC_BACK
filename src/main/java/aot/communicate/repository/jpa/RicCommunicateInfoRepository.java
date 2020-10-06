package aot.communicate.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.communicate.model.RicCommunicateInfo;

@Repository
public interface RicCommunicateInfoRepository extends CrudRepository<RicCommunicateInfo, Long> {

	public Integer countByPeriodMonth(String yyyyMM);

}
