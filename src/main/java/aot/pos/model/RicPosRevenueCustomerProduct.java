package aot.pos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_pos_revenue_customer_product")
public class RicPosRevenueCustomerProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2541324681011743615L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cus_pro_id")
	private Long cusProId;

	@Column(name = "contract_no")
	private String contractNo;
	
	@Column(name = "sale_date")
	private Date saleDate;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "including_vat_sale")
	private BigDecimal includingVatSale;

	@Column(name = "excluding_vat_sale")
	private BigDecimal excludingVatSale;
	
	@Column(name = "receipt_num")
	private Long receiptNum;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_delete")
	private String isDelete;

	public Long getCusProId() {
		return cusProId;
	}

	public void setCusProId(Long cusProId) {
		this.cusProId = cusProId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getIncludingVatSale() {
		return includingVatSale;
	}

	public void setIncludingVatSale(BigDecimal includingVatSale) {
		this.includingVatSale = includingVatSale;
	}

	public BigDecimal getExcludingVatSale() {
		return excludingVatSale;
	}

	public void setExcludingVatSale(BigDecimal excludingVatSale) {
		this.excludingVatSale = excludingVatSale;
	}

	public Long getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(Long receiptNum) {
		this.receiptNum = receiptNum;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
}
