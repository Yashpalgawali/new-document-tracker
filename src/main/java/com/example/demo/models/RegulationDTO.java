package com.example.demo.models;


public class RegulationDTO {

	private Integer regulation_id;
	
	private String regulation_name;
	
	private String regulation_description;
	
	private String regulation_frequency;
	
	private String regulation_issued_date;
	
	private String next_renewal_date;
	
	private String file_path;
	
	private String file_name;

	private  Vendor vendor;
	
	private RegulationType regulation_type;
//	private Integer vendor_id;
//	
//	private Integer regulation_type_id;

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

	public RegulationType getRegulation_type() {
		return regulation_type;
	}

	public void setRegulation_type(RegulationType regulation_type) {
		this.regulation_type = regulation_type;
	}

	public RegulationDTO(Integer regulation_id, String regulation_name, String regulation_description,
			String regulation_frequency, String regulation_issued_date, String next_renewal_date, String file_path,
			String file_name, Vendor vendor, RegulationType regulation_type) {
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
		this.regulation_type = regulation_type;
	}

	public RegulationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
