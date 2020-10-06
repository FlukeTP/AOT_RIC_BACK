package aot.communicate.model;

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
@Table(name = "ric_communicate_req_flight_schedule_dtl")
public class RicCommunicateReqFlightScheduleDtl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063629670988185825L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "id_hdr")
	private Long idHdr;
	
	@Column(name = "connect_signal")
	private String connectSignal;


	@Column(name = "location")
	private String location;
	
	@Column(name = "service")
	private String service;
	
	@Column(name = "amount_lg")
	private BigDecimal amountLg;

	@Column(name = "amount_month")
	private BigDecimal amountMonth;

	@Column(name = "remark")
	private String remark;
	
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "is_deleted")
	private String isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdHdr() {
		return idHdr;
	}

	public void setIdHdr(Long idHdr) {
		this.idHdr = idHdr;
	}

	public String getConnectSignal() {
		return connectSignal;
	}

	public void setConnectSignal(String connectSignal) {
		this.connectSignal = connectSignal;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public BigDecimal getAmountLg() {
		return amountLg;
	}

	public void setAmountLg(BigDecimal amountLg) {
		this.amountLg = amountLg;
	}

	public BigDecimal getAmountMonth() {
		return amountMonth;
	}

	public void setAmountMonth(BigDecimal amountMonth) {
		this.amountMonth = amountMonth;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
}
