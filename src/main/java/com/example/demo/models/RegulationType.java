package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="tbl_regulation_type")
@SequenceGenerator(name = "regulation_type_seq",allocationSize = 1 , initialValue = 1)
public class RegulationType {
	
	@Id
	@GeneratedValue(generator = "regulation_type_seq",strategy = GenerationType.AUTO)
	private Integer regulation_type_id;
	
	private String regulation_type;

	public Integer getRegulation_type_id() {
		return regulation_type_id;
	}

	public void setRegulation_type_id(Integer regulation_type_id) {
		this.regulation_type_id = regulation_type_id;
	}

	public String getRegulation_type() {
		return regulation_type;
	}

	public void setRegulation_type(String regulation_type) {
		this.regulation_type = regulation_type;
	}

	public RegulationType() {
		super();
	}

	public RegulationType(Integer regulation_type_id, String regulation_type) {
		super();
		this.regulation_type_id = regulation_type_id;
		this.regulation_type = regulation_type;
	}
	
}
