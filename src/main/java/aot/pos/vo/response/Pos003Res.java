package aot.pos.vo.response;

import java.util.List;

public class Pos003Res {

	private PosRevenueCustomerRes header;
	private List<PosRevenueCustomerProductRes> product;
	private List<PosRevenueCustomerPaymentRes> payment;
	
	public PosRevenueCustomerRes getHeader() {
		return header;
	}
	public void setHeader(PosRevenueCustomerRes header) {
		this.header = header;
	}
	public List<PosRevenueCustomerProductRes> getProduct() {
		return product;
	}
	public void setProduct(List<PosRevenueCustomerProductRes> product) {
		this.product = product;
	}
	public List<PosRevenueCustomerPaymentRes> getPayment() {
		return payment;
	}
	public void setPayment(List<PosRevenueCustomerPaymentRes> payment) {
		this.payment = payment;
	}
	
}
