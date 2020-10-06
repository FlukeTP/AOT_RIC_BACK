package aot.sap.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sap_ric_control")
public class SapRicControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3998387220782303392L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sqp_ric_control_id")
	private Long id;

	@Column(name = "refkey1")
	private String refkey1;

	@Column(name = "comp")
	private String comp;

	@Column(name = "docno")
	private String docno;

	@Column(name = "year")
	private String year;

	@Column(name = "reverse_inv")
	private String reverseInv;

	@Column(name = "dzyear")
	private String dzyear;

	@Column(name = "dzdocNo")
	private String dzdocNo;

	@Column(name = "dzref")
	private String dzref;

	@Column(name = "reverse_rec")
	private String reverseRec;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefkey1() {
		return refkey1;
	}

	public void setRefkey1(String refkey1) {
		this.refkey1 = refkey1;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getReverseInv() {
		return reverseInv;
	}

	public void setReverseInv(String reverseInv) {
		this.reverseInv = reverseInv;
	}

	public String getDzyear() {
		return dzyear;
	}

	public void setDzyear(String dzyear) {
		this.dzyear = dzyear;
	}

	public String getDzdocNo() {
		return dzdocNo;
	}

	public void setDzdocNo(String dzdocNo) {
		this.dzdocNo = dzdocNo;
	}

	public String getDzref() {
		return dzref;
	}

	public void setDzref(String dzref) {
		this.dzref = dzref;
	}

	public String getReverseRec() {
		return reverseRec;
	}

	public void setReverseRec(String reverseRec) {
		this.reverseRec = reverseRec;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
