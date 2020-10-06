package aot.electric.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RicElectricReqFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3816890033199845290L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "req_file_id")
	private Long reqFileId;
	
	@Column(name = "req_id")
	private Long reqId;
	@Column(name = "req_file_name")
	private String reqFileName;
	@Column(name = "req_doc_name")
	private String reqDocName;
	@Column(name = "req_file_extension")
	private String reqFileExtension;
	@Column(name = "period")
	private String period;
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
	
	
	
	public Long getReqFileId() {
		return reqFileId;
	}
	public void setReqFileId(Long reqFileId) {
		this.reqFileId = reqFileId;
	}
	public Long getReqId() {
		return reqId;
	}
	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}
	public String getReqFileName() {
		return reqFileName;
	}
	public void setReqFileName(String reqFileName) {
		this.reqFileName = reqFileName;
	}
	public String getReqFileExtension() {
		return reqFileExtension;
	}
	public void setReqFileExtension(String reqFileExtension) {
		this.reqFileExtension = reqFileExtension;
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
	public String getReqDocName() {
		return reqDocName;
	}
	public void setReqDocName(String reqDocName) {
		this.reqDocName = reqDocName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
}
