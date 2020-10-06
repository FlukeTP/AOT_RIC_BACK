package aot.posControl.vo.response;

import java.util.List;

import aot.posControl.model.RicPosSaleProduct;

public class RicPosSaleProductResponse {
	
	private String message;
	private List<RicPosSaleProduct> saleProduct;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<RicPosSaleProduct> getSaleProduct() {
		return saleProduct;
	}
	public void setSaleProduct(List<RicPosSaleProduct> saleProduct) {
		this.saleProduct = saleProduct;
	}
}
