package com.example.demo.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegulationDTO {

	private Integer regulation_id;
	
	private String regulation_name;
	
	private String regulation_description;
	
	private String regulation_frequency;
	
	private String regulation_issued_date;
	
	private String file_path;
	
	private String file_name;

	private  Vendor vendor;
	
	private RegulationType regulation_type;
//	private Integer vendor_id;
//	
//	private Integer regulation_type_id;
	

}
