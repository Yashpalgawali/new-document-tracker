package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "vendor_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_vendor")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "vendor_seq" )
	private Integer vendor_id;
	
	private String vendor_name;
	
	
	private String vendor_email;
	 
	@Transient
	private String username;
	
	@Transient
	private String password;
	  
	@JsonIgnore
	@ToString.Exclude
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user; 
	
	private Integer enabled;
}
