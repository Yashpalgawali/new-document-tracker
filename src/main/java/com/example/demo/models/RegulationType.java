package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_regulation_type")
@SequenceGenerator(name = "regulation_type_seq",allocationSize = 1 , initialValue = 1)
public class RegulationType {
	
	@Id
	@GeneratedValue(generator = "regulation_type_seq",strategy = GenerationType.AUTO)
	private Integer regulation_type_id;
	
	private String regulation_type;

}
