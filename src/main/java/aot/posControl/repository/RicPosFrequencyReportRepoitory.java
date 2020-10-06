package aot.posControl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.posControl.model.RicPosFrequencyReport;

@Repository
public interface RicPosFrequencyReportRepoitory extends CrudRepository<RicPosFrequencyReport, Long>{
	
	public List<RicPosFrequencyReport> findByContractNo(String contractNo);
}
