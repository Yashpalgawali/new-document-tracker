package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	private String hist_file_path;
	
	private String hist_file_name; 
	
	@ManyToOne(cascade =  CascadeType.MERGE)
	@JoinColumn(name="vendor_id")
	private Vendor vendor;
	
	@OneToOne(cascade =  CascadeType.MERGE)
	@JoinColumn(name="regulation_id")
	private Regulation regulation;
	
}
