package aot.sap.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.sap.model.SapRicControl;

@Repository
public interface SapRicControlRepository extends CrudRepository<SapRicControl, Long>  {
	
	public SapRicControl findByRefkey1(String refkey1);

}
