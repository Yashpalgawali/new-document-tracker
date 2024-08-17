package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	private String file_path;
	
	private String file_name;
	
	@ManyToOne(cascade =  CascadeType.MERGE , fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name="vendor_id")
	@ToString.Exclude
	private Vendor vendor;
	
	@OneToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name="regulation_type_id")
	@ToString.Exclude
	private RegulationType regulationtype;
	
	 @OneToMany(mappedBy = "regulation", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	 @JsonIgnore
	 @ToString.Exclude
	 private List<RegulationHistory> history;
}
