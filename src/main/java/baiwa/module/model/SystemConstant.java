package baiwa.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SystemConstant implements Serializable {

	private static final long serialVersionUID = 6703905257243372517L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "constant_id")
	private Long constantId;
	@Column(name = "constant_key")
	private String constantKey;
	@Column(name = "constant_value")
	private String constantValue;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_delete")
	private String isDelete = "N";

	public Long getConstantId() {
		return constantId;
	}

	public String getConstantKey() {
		return constantKey;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setConstantId(Long constantId) {
		this.constantId = constantId;
	}

	public void setConstantKey(String constantKey) {
		this.constantKey = constantKey;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}

	public void setCreatedDate(Date createDate) {
		this.createdDate = createDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}
