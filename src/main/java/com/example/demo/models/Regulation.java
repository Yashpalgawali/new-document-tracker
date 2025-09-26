package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SequenceGenerator(name = "regulation_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_regulation")
public class Regulation {

	@Id
	@GeneratedValue(generator = "regulation_seq",strategy = GenerationType.AUTO)
	private Integer regulation_id;

	private String regulation_name;

	private String regulation_description;

	private String regulation_frequency;

	private String regulation_issued_date;

	private String next_renewal_date;

	private String file_path;

	private String file_name;

	@ManyToOne(cascade =  CascadeType.MERGE , fetch = FetchType.EAGER)
//	@JsonIgnore
	@JoinColumn(name="vendor_id")
	//@ToString.Exclude
	private Vendor vendor;

	@OneToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	//@JsonIgnore
	@JoinColumn(name="regulation_type_id")
	//@ToString.Exclude
	private RegulationType regulationtype;

	@OneToMany(mappedBy = "regulation", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JsonIgnore
    private List<RegulationHistory> history;

	public Integer getRegulation_id() {
		return regulation_id;
	}

	public void setRegulation_id(Integer regulation_id) {
		this.regulation_id = regulation_id;
	}

	public String getRegulation_name() {
		return regulation_name;
	}

	public void setRegulation_name(String regulation_name) {
		this.regulation_name = regulation_name;
	}

	public String getRegulation_description() {
		return regulation_description;
	}

	public void setRegulation_description(String regulation_description) {
		this.regulation_description = regulation_description;
	}

	public String getRegulation_frequency() {
		return regulation_frequency;
	}

	public void setRegulation_frequency(String regulation_frequency) {
		this.regulation_frequency = regulation_frequency;
	}

	public String getRegulation_issued_date() {
		return regulation_issued_date;
	}

	public void setRegulation_issued_date(String regulation_issued_date) {
		this.regulation_issued_date = regulation_issued_date;
	}

	public String getNext_renewal_date() {
		return next_renewal_date;
	}

	public void setNext_renewal_date(String next_renewal_date) {
		this.next_renewal_date = next_renewal_date;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public RegulationType getRegulationtype() {
		return regulationtype;
	}

	public void setRegulationtype(RegulationType regulationtype) {
		this.regulationtype = regulationtype;
	}

	public List<RegulationHistory> getHistory() {
		return history;
	}

	public void setHistory(List<RegulationHistory> history) {
		this.history = history;
	}

	public Regulation(Integer regulation_id, String regulation_name, String regulation_description,
			String regulation_frequency, String regulation_issued_date, String next_renewal_date, String file_path,
			String file_name, Vendor vendor, RegulationType regulationtype, List<RegulationHistory> history) {
		super();
		this.regulation_id = regulation_id;
		this.regulation_name = regulation_name;
		this.regulation_description = regulation_description;
		this.regulation_frequency = regulation_frequency;
		this.regulation_issued_date = regulation_issued_date;
		this.next_renewal_date = next_renewal_date;
		this.file_path = file_path;
		this.file_name = file_name;
		this.vendor = vendor;
		this.regulationtype = regulationtype;
		this.history = history;
	}

	public Regulation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Regulation [regulation_id=" + regulation_id + ", regulation_name=" + regulation_name
				+ ", regulation_description=" + regulation_description + ", regulation_frequency="
				+ regulation_frequency + ", regulation_issued_date=" + regulation_issued_date + ", next_renewal_date="
				+ next_renewal_date + ", file_path=" + file_path + ", file_name=" + file_name + ", vendor=" + vendor
				+ ", regulationtype=" + regulationtype + ", history=" + history + ", getRegulation_id()="
				+ getRegulation_id() + ", getRegulation_name()=" + getRegulation_name()
				+ ", getRegulation_description()=" + getRegulation_description() + ", getRegulation_frequency()="
				+ getRegulation_frequency() + ", getRegulation_issued_date()=" + getRegulation_issued_date()
				+ ", getNext_renewal_date()=" + getNext_renewal_date() + ", getFile_path()=" + getFile_path()
				+ ", getFile_name()=" + getFile_name() + ", getVendor()=" + getVendor() + ", getRegulationtype()="
				+ getRegulationtype() + "]";
	}
	 
	 
}
