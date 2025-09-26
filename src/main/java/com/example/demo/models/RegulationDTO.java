package com.example.demo.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegulationDTO {

	Integer regulation_id;
	
	String regulation_name;
	
	String regulation_description;
	
	String regulation_frequency;
	
    String regulation_issued_date;
	
	String next_renewal_date;
	
	String file_path;
	
	String file_name;

	Vendor vendor;
	
	RegulationType regulation_type;

}
