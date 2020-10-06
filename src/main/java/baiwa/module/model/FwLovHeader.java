package baiwa.module.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FwLovHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1295937470382226378L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "lov_header_id")
	private Long lovHeaderId;
	@Column(name = "lov_key")
	private String lovKey;
	@Column(name = "descripton")
	private String descripton;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_delete")
	private String isDelete;

	public Long getLovHeaderId() {
		return lovHeaderId;
	}

	public void setLovHeaderId(Long lovHeaderId) {
		this.lovHeaderId = lovHeaderId;
	}

	public String getLovKey() {
		return lovKey;
	}

	public void setLovKey(String lovKey) {
		this.lovKey = lovKey;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
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

}
