package aot.posControl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import aot.posControl.model.RicPosSaleProduct;

public interface RicPosSaleProductRepository extends CrudRepository<RicPosSaleProduct, Long>{
	
	public List<RicPosSaleProduct> findByFrequencyReportId(long frequencyReportId);
	public RicPosSaleProduct findByFrequencyReportIdAndSaleNo(long frequencyReportId, String saleNo);
}
