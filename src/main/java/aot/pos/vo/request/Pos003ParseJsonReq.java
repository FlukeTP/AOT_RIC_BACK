package aot.pos.vo.request;

import java.util.List;

public class Pos003ParseJsonReq {

	private PosRevenueCustomerReq header;
	private List<PosRevenueCustomerProductReq> product;
	private List<PosRevenueCustomerPaymentReq> payment;
	
	public PosRevenueCustomerReq getHeader() {
		return header;
	}
	public void setHeader(PosRevenueCustomerReq header) {
		this.header = header;
	}
	public List<PosRevenueCustomerProductReq> getProduct() {
		return product;
	}
	public void setProduct(List<PosRevenueCustomerProductReq> product) {
		this.product = product;
	}
	public List<PosRevenueCustomerPaymentReq> getPayment() {
		return payment;
	}
	public void setPayment(List<PosRevenueCustomerPaymentReq> payment) {
		this.payment = payment;
	}
	
}
