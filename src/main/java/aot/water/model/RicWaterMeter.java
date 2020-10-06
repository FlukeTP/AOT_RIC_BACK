package aot.water.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RicWaterMeter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3126874126320428025L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meter_id")
	private Long meterId;
	@Column(name = "serial_no")
	private String serialNo;
	@Column(name = "meter_type")
	private String meterType;
	@Column(name = "meter_name")
	private String meterName;
	@Column(name = "multiple_value")
	private String multipleValue;
	@Column(name = "meter_location")
	private String meterLocation;
	@Column(name = "functional_location")
	private String functionalLocation;
	@Column(name = "meter_status")
	private String meterStatus;
	@Column(name = "service_number")
	private String serviceNumber;
	@Column(name = "remark")
	private String remark;
	@Column(name = "airport")
	private String airport;
	@Column(name = "created_date")
	private Date createDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "is_delete")
	private String isDelete;

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterName() {
		return meterName;
	}

	public void setMeterName(String meterName) {
		this.meterName = meterName;
	}

	public String getMultipleValue() {
		return multipleValue;
	}

	public void setMultipleValue(String multipleValue) {
		this.multipleValue = multipleValue;
	}

	public String getMeterLocation() {
		return meterLocation;
	}

	public void setMeterLocation(String meterLocation) {
		this.meterLocation = meterLocation;
	}

	public String getFunctionalLocation() {
		return functionalLocation;
	}

	public void setFunctionalLocation(String functionalLocation) {
		this.functionalLocation = functionalLocation;
	}

	public String getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
