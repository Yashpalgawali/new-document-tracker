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
@Table(name="tbl_user_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "user_type_seq" , allocationSize = 1, initialValue = 1)
public class UserType {

	@Id
	@GeneratedValue(generator = "user_type_seq" , strategy = GenerationType.AUTO)
	private Integer user_type_id;
	
	private String user_type;
}
