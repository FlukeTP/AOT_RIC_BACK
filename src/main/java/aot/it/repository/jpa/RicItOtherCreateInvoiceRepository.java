package aot.it.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import aot.it.model.RicItOtherCreateInvoice;

@Repository
public interface RicItOtherCreateInvoiceRepository extends CrudRepository<RicItOtherCreateInvoice, Long> {

}
