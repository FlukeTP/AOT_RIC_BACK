package aot.pos.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Pos003Req {

	private String json;
	private PosRevenueCustomerReq header;
	private List<PosRevenueCustomerProductReq> product;
	private List<PosRevenueCustomerPaymentReq> payment;
	private MultipartFile file;
	
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
