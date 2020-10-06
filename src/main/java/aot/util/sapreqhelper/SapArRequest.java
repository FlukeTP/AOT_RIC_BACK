package aot.util.sapreqhelper;

import aot.util.sap.domain.request.ArRequest;

public interface SapArRequest {
	public ArRequest getARRequest(String busPlace, String comCode, String id ,String doctype);
}
