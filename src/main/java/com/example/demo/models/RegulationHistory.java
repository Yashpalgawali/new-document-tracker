package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

 
@Entity
@SequenceGenerator(name = "regulation_hist_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_regulation_hist")
public class RegulationHistory {

	@Id
	@GeneratedValue(generator = "regulation_hist_seq",strategy = GenerationType.AUTO)
	private Integer hist_regulation_id;
	
	private String hist_regulation_name;
	
	private String hist_regulation_description;
	
	private String hist_regulation_frequency;
	
	private String hist_regulation_issued_date;
	
	private String hist_next_renewal_date;
	
	private String hist_file_path;
	 
	private String hist_file_name; 
	
	@ManyToOne(cascade =  CascadeType.MERGE)
	@JoinColumn(name="vendor_id")
	private Vendor vendor;
	
	@OneToOne(cascade =  CascadeType.MERGE)
	@JoinColumn(name="regulation_id")
	private Regulation regulation;

	public Integer getHist_regulation_id() {
		return hist_regulation_id;
	}

	public void setHist_regulation_id(Integer hist_regulation_id) {
		this.hist_regulation_id = hist_regulation_id;
	}

	public String getHist_regulation_name() {
		return hist_regulation_name;
	}

	public void setHist_regulation_name(String hist_regulation_name) {
		this.hist_regulation_name = hist_regulation_name;
	}

	public String getHist_regulation_description() {
		return hist_regulation_description;
	}

	public void setHist_regulation_description(String hist_regulation_description) {
		this.hist_regulation_description = hist_regulation_description;
	}

	public String getHist_regulation_frequency() {
		return hist_regulation_frequency;
	}

	public void setHist_regulation_frequency(String hist_regulation_frequency) {
		this.hist_regulation_frequency = hist_regulation_frequency;
	}

	public String getHist_regulation_issued_date() {
		return hist_regulation_issued_date;
	}

	public void setHist_regulation_issued_date(String hist_regulation_issued_date) {
		this.hist_regulation_issued_date = hist_regulation_issued_date;
	}

	public String getHist_next_renewal_date() {
		return hist_next_renewal_date;
	}

	public void setHist_next_renewal_date(String hist_next_renewal_date) {
		this.hist_next_renewal_date = hist_next_renewal_date;
	}

	public String getHist_file_path() {
		return hist_file_path;
	}

	public void setHist_file_path(String hist_file_path) {
		this.hist_file_path = hist_file_path;
	}

	public String getHist_file_name() {
		return hist_file_name;
	}

	public void setHist_file_name(String hist_file_name) {
		this.hist_file_name = hist_file_name;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Regulation getRegulation() {
		return regulation;
	}

	public void setRegulation(Regulation regulation) {
		this.regulation = regulation;
	}

	public RegulationHistory(Integer hist_regulation_id, String hist_regulation_name,
			String hist_regulation_description, String hist_regulation_frequency, String hist_regulation_issued_date,
			String hist_next_renewal_date, String hist_file_path, String hist_file_name, Vendor vendor,
			Regulation regulation) {
		super();
		this.hist_regulation_id = hist_regulation_id;
		this.hist_regulation_name = hist_regulation_name;
		this.hist_regulation_description = hist_regulation_description;
		this.hist_regulation_frequency = hist_regulation_frequency;
		this.hist_regulation_issued_date = hist_regulation_issued_date;
		this.hist_next_renewal_date = hist_next_renewal_date;
		this.hist_file_path = hist_file_path;
		this.hist_file_name = hist_file_name;
		this.vendor = vendor;
		this.regulation = regulation;
	}

	public RegulationHistory() {
		super();
	}	
}
