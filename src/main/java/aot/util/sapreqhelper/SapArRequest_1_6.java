package aot.util.sapreqhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aot.util.sap.domain.request.ArRequest;

@Service
public class SapArRequest_1_6 implements SapArRequest {
	@Autowired
	private CommonARRequest commonARRequest;

	@Override
	public ArRequest getARRequest(String busPlace, String comCode, String id, String doctype) {
		return null;
	}
}
