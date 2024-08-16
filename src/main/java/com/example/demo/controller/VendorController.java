package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.models.UserType;
import com.example.demo.models.Vendor;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;

@RequestMapping("vendor")
@CrossOrigin("*")
@RestController
public class VendorController {
 
	private VendorService vendserv;
	
	private UserService userserv;
	
	@Autowired	
	public VendorController(VendorService vendserv, UserService userserv) {
		super();
		this.vendserv = vendserv;
		this.userserv = userserv;
	}

	@PostMapping("/")
	public ResponseEntity<Vendor> saveVendor(@RequestBody Vendor vendor)
	{
		User user = new User();
		
		user.setEmail(vendor.getVendor_email());
		user.setUsername(vendor.getUsername());
		user.setPassword(vendor.getPassword());
		user.setEnabled(1);
		user.setRole("vendor");
		
		UserType utype = new UserType();
		
		utype.setUser_type_id(2);
		utype.setUser_type("vendor");
		
		user.setUsertype(utype);
		
		userserv.saveUser(user);
		
		vendor.setUser(user);
		vendor.setEnabled(1);		
		Vendor vend = vendserv.saveVendor(vendor);
		if(vend!=null) { 
			return new  ResponseEntity<Vendor>(vend, HttpStatus.CREATED );
		}
		else {
			return new  ResponseEntity<Vendor>(HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		
		List<Vendor> vlist = vendserv.getAllVendors();
		if(vlist.size()>0)
			return new  ResponseEntity<List<Vendor>>(vlist , HttpStatus.OK);
		else
			return new  ResponseEntity<List<Vendor>>( HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Integer id) {
		
		Vendor vendor = vendserv.getVendorById(id);
		if(vendor!=null) {
			return new  ResponseEntity<Vendor>(vendor, HttpStatus.OK);
		}
		else {
			return new  ResponseEntity<Vendor>( HttpStatus.NOT_FOUND);
		}
	}
}
