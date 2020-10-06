package aot.posControl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.posControl.repository.RicPosSaleProductRepository;
import aot.posControl.vo.response.RicPosSaleProductResponse;

@Service
public class PosSaleProductService {
	
	@Autowired
	private RicPosSaleProductRepository ricPosSaleProductRepository;
	
	public RicPosSaleProductResponse getSaleProduct(String frequencyReportId){
		RicPosSaleProductResponse res = new RicPosSaleProductResponse();
		res.setSaleProduct(ricPosSaleProductRepository.findByFrequencyReportId(Long.valueOf(frequencyReportId) ));
		return res;
		 
	}
}