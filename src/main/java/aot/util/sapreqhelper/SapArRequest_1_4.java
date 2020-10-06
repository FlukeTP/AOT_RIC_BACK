package aot.util.sapreqhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.electric.repository.jpa.RicElectricReqRepository;
import aot.util.sap.domain.request.ArRequest;

@Service
public class SapArRequest_1_4 implements SapArRequest {
	@Autowired
	private CommonARRequest commonARRequest;

	@Autowired
	private RicElectricReqRepository ricElectricReqRepository;

	@Override
	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) {
		return null;
	}

}
