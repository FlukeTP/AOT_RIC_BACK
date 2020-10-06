package aot.communicate.repository.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import aot.communicate.model.RicCommunicateReqDtl;

public interface RicCommunicateReqDtlRepository extends CrudRepository<RicCommunicateReqDtl, Long> {

	public List<RicCommunicateReqDtl> findByIdHdr(Long id);

}
