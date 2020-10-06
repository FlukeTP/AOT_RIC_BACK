package aot.heavyeqp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ric_manage_heavy_equipment")
public class RicManageHeavyEquipment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994813194666733346L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manage_heavy_equipment_id")
	private Long manageHeavyEquipmentId;

	@Column(name = "equipment_code")
	private String equipmentCode;

	@Column(name = "equipment_type")
	private String equipmentType;

	@Column(name = "equipment_no")
	private String equipmentNo;

	@Column(name = "status")
	private String status;

	@Column(name = "responsible_person")
	private String responsiblePerson;

	@Column(name = "remark")
	private String remark;

	@Column(name = "number_license_plate")
	private String numberLicensePlate;

	@Column(name = "license_plate")
	private String licensePlate;

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

	public Long getManageHeavyEquipmentId() {
		return manageHeavyEquipmentId;
	}

	public void setManageHeavyEquipmentId(Long manageHeavyEquipmentId) {
		this.manageHeavyEquipmentId = manageHeavyEquipmentId;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentNo() {
		return equipmentNo;
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNumberLicensePlate() {
		return numberLicensePlate;
	}

	public void setNumberLicensePlate(String numberLicensePlate) {
		this.numberLicensePlate = numberLicensePlate;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
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
