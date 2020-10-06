package aot.it.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import aot.it.model.RicItNetworkCreateInvoiceMapping;

@Repository
public interface RicItNetworkCreateInvoiceMappingRepository extends CrudRepository<RicItNetworkCreateInvoiceMapping, Long> {
	
	@Query(value="select * from ric_it_network_create_invoice_mapping i where i.id_network_create_invoice = ?1 and  is_delete = 'N'",nativeQuery= true)
	public List<RicItNetworkCreateInvoiceMapping> findByIdHdr(Long idNetworkCreateInvoice);

}
