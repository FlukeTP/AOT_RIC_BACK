package aot.communicate.repository.jpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import aot.communicate.model.RicCommunicateReqHdr;

@Repository
public interface RicCommunicateReqHdrRepository extends CrudRepository<RicCommunicateReqHdr, Long> {
	
	public RicCommunicateReqHdr findByTransactionNo(String transactionNo);
}
