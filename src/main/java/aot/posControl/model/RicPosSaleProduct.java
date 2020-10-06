package aot.posControl.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_pos_sale_product")
public class RicPosSaleProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -687057236929192294L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ricPosSaleProductId;
	private long frequencyReportId;
	private String saleNo;
	private String saleDate;
	private String posNo;
	private String docDate;
	private Integer saleType;;
	private String voidDate;
	private String voidReason;
	private BigDecimal dtlSeq;
	private String dtlProductCode;
	private String dtlProductName;
	private String dtlReSalesType;
	private Integer dtlVatType;
	private BigDecimal dtlVatRate;
	private BigDecimal dtlProductQty;
	private String dtlUnitCode;
	private BigDecimal dtlUnitPrice;
	private BigDecimal dtlDiscountAmt;
	private BigDecimal dtlVatAmt;
	private BigDecimal dtlAmtExcVat;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String isDelete;
	public long getRicPosSaleProductId() {
		return ricPosSaleProductId;
	}
	public void setRicPosSaleProductId(long ricPosSaleProductId) {
		this.ricPosSaleProductId = ricPosSaleProductId;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getDocDate() {
		return docDate;
	}
	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
	public String getVoidDate() {
		return voidDate;
	}
	public void setVoidDate(String voidDate) {
		this.voidDate = voidDate;
	}
	public String getVoidReason() {
		return voidReason;
	}
	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}
	public BigDecimal getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(BigDecimal dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public String getDtlProductCode() {
		return dtlProductCode;
	}
	public void setDtlProductCode(String dtlProductCode) {
		this.dtlProductCode = dtlProductCode;
	}
	public String getDtlProductName() {
		return dtlProductName;
	}
	public void setDtlProductName(String dtlProductName) {
		this.dtlProductName = dtlProductName;
	}
	public String getDtlReSalesType() {
		return dtlReSalesType;
	}
	public void setDtlReSalesType(String dtlReSalesType) {
		this.dtlReSalesType = dtlReSalesType;
	}
	public BigDecimal getDtlVatRate() {
		return dtlVatRate;
	}
	public void setDtlVatRate(BigDecimal dtlVatRate) {
		this.dtlVatRate = dtlVatRate;
	}
	public BigDecimal getDtlProductQty() {
		return dtlProductQty;
	}
	public void setDtlProductQty(BigDecimal dtlProductQty) {
		this.dtlProductQty = dtlProductQty;
	}
	public String getDtlUnitCode() {
		return dtlUnitCode;
	}
	public void setDtlUnitCode(String dtlUnitCode) {
		this.dtlUnitCode = dtlUnitCode;
	}
	public BigDecimal getDtlUnitPrice() {
		return dtlUnitPrice;
	}
	public void setDtlUnitPrice(BigDecimal dtlUnitPrice) {
		this.dtlUnitPrice = dtlUnitPrice;
	}
	public BigDecimal getDtlDiscountAmt() {
		return dtlDiscountAmt;
	}
	public void setDtlDiscountAmt(BigDecimal dtlDiscountAmt) {
		this.dtlDiscountAmt = dtlDiscountAmt;
	}
	public BigDecimal getDtlVatAmt() {
		return dtlVatAmt;
	}
	public void setDtlVatAmt(BigDecimal dtlVatAmt) {
		this.dtlVatAmt = dtlVatAmt;
	}
	public BigDecimal getDtlAmtExcVat() {
		return dtlAmtExcVat;
	}
	public void setDtlAmtExcVat(BigDecimal dtlAmtExcVat) {
		this.dtlAmtExcVat = dtlAmtExcVat;
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
	public Integer getDtlVatType() {
		return dtlVatType;
	}
	public void setDtlVatType(Integer dtlVatType) {
		this.dtlVatType = dtlVatType;
	}
	public long getFrequencyReportId() {
		return frequencyReportId;
	}
	public void setFrequencyReportId(long frequencyReportId) {
		this.frequencyReportId = frequencyReportId;
	}
	
	
}
