package aot.pos.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import aot.pos.model.RicPosRevenueCustomerProduct;

@Repository
public interface RicPosRevenueCustomerProductRepository extends CrudRepository<RicPosRevenueCustomerProduct, Long> {

	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.contractNo = :contractNo and e.saleDate = :saleDate")
	public List<RicPosRevenueCustomerProduct> findByContractNoAndSaleDate(@Param("contractNo") String contractNo, @Param("saleDate") Date saleDate);
	
	@Query("select e from #{#entityName} e where e.isDelete = 'Y' and e.contractNo = :contractNo and e.saleDate = :saleDate")
	public List<RicPosRevenueCustomerProduct> findByContractNoAndSaleDateY(@Param("contractNo") String contractNo, @Param("saleDate") Date saleDate);
	
	@Query("select e from #{#entityName} e where e.isDelete = 'N' and e.saleDate = :saleDate")
	public List<RicPosRevenueCustomerProduct> findBySaleDate(@Param("saleDate") Date saleDate);
}
