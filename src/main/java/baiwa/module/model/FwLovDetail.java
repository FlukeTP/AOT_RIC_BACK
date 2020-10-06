package baiwa.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FwLovDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703905257243372517L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lov_detail_id")
	private Long lovDetailId;
	@Column(name = "lov_key")
	private String lovKey;
	@Column(name = "lov_code")
	private String lovCode;
	@Column(name = "desc_th_1")
	private String descTh1;
	@Column(name = "desc_th_2")
	private String descTh2;
	@Column(name = "desc_en_1")
	private String descEn1;
	@Column(name = "desc_en_2")
	private String descEn2;
	@Column(name = "order_no")
	private Integer orderNo;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_delete")
	private String isDelete = "N";

	public Long getLovDetailId() {
		return lovDetailId;
	}

	public void setLovDetailId(Long lovDetailId) {
		this.lovDetailId = lovDetailId;
	}

	public String getLovKey() {
		return lovKey;
	}

	public void setLovKey(String lovKey) {
		this.lovKey = lovKey;
	}

	public String getLovCode() {
		return lovCode;
	}

	public void setLovCode(String lovCode) {
		this.lovCode = lovCode;
	}

	public String getDescTh1() {
		return descTh1;
	}

	public void setDescTh1(String descTh1) {
		this.descTh1 = descTh1;
	}

	public String getDescTh2() {
		return descTh2;
	}

	public void setDescTh2(String descTh2) {
		this.descTh2 = descTh2;
	}

	public String getDescEn1() {
		return descEn1;
	}

	public void setDescEn1(String descEn1) {
		this.descEn1 = descEn1;
	}

	public String getDescEn2() {
		return descEn2;
	}

	public void setDescEn2(String descEn2) {
		this.descEn2 = descEn2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
