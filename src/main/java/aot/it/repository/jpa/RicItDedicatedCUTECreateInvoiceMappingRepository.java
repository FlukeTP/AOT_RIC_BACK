package aot.it.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import aot.it.model.RicItDedicatedCUTECreateInvoiceMapping;

@Repository
public interface RicItDedicatedCUTECreateInvoiceMappingRepository extends CrudRepository<RicItDedicatedCUTECreateInvoiceMapping, Long> {

	@Query(value="select * from ric_it_dedicated_cute_create_invoice_mapping i where i.id_header = ?1 and  is_delete = 'N'",nativeQuery= true)
	public List<RicItDedicatedCUTECreateInvoiceMapping> findByIdHdr(Long id);
	
//	@Query(value="delete from ric_it_dedicated_cute_create_invoice_mapping where id_header = ?1",nativeQuery= true)
	public long deleteByIdHeader(Long id);
}
