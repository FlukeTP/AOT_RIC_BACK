package aot.phone.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.phone.model.RicPhoneInfo;

@Repository
public interface RicPhoneInfoRepository extends CrudRepository<RicPhoneInfo, Long> {

	public RicPhoneInfo findByEntreprenuerCode(String entreprenuerCode);

	public Integer countByPeriodMonth(String periodMonth);

	public void deleteByPeriodMonth(String periodMonth);

}
