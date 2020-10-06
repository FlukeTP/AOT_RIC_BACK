package aot.electric.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RicElectricRateCharge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8300518607048289765L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_charge_id")
	private Long rateChargeId;

	@Column(name = "employee_id")
	private String employeeId;
	@Column(name = "employee_code")
	private String employeeCode;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name = "electric_phase")
	private String electricPhase;
	@Column(name = "electric_ampere")
	private String electricAmpere;
	@Column(name = "charge_type")
	private String chargeType;
	@Column(name = "charge_rate")
	private String chargeRate;
	@Column(name = "charge_vat")
	private String chargeVat;
	@Column(name = "total_charge_rate")
	private String totalChargeRate;
	@Column(name = "remark")
	private String remark;
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
	@Column(name = "req_id")
	private Long reqId;

	public Long getRateChargeId() {
		return rateChargeId;
	}

	public void setRateChargeId(Long rateChargeId) {
		this.rateChargeId = rateChargeId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getElectricPhase() {
		return electricPhase;
	}

	public void setElectricPhase(String electricPhase) {
		this.electricPhase = electricPhase;
	}

	public String getElectricAmpere() {
		return electricAmpere;
	}

	public void setElectricAmpere(String electricAmpere) {
		this.electricAmpere = electricAmpere;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getChargeVat() {
		return chargeVat;
	}

	public void setChargeVat(String chargeVat) {
		this.chargeVat = chargeVat;
	}

	public String getTotalChargeRate() {
		return totalChargeRate;
	}

	public void setTotalChargeRate(String totalChargeRate) {
		this.totalChargeRate = totalChargeRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getReqId() {
		return reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

}