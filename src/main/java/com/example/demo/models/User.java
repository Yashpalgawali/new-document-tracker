package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "user_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(generator = "user_seq" , strategy = GenerationType.AUTO)
	private Long userid;
	
	private String username;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Transient
	private String cnf_password;
	
	private Integer enabled;
	
	private String role;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_type_id")
	private UserType usertype;

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", cnf_password=" + cnf_password + ", enabled=" + enabled + ", role=" + role + "]";
	}

	
}
