package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Vendor;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;

@RequestMapping("vendor")
@RestController
public class VendorController {

	private final VendorService vendserv;
	private final UserService userserv;

	public VendorController(VendorService vendserv, UserService userserv) {
		this.vendserv = vendserv;
		this.userserv = userserv;
	}

	@PostMapping("/")
	public ResponseEntity<Vendor> saveVendor(@RequestBody Vendor vendor) {

		Vendor vend = vendserv.saveVendor(vendor);
		return ResponseEntity.status(HttpStatus.CREATED).body(vend);		
	}

	@GetMapping("/")
	public ResponseEntity<List<Vendor>> getAllVendors() {

		List<Vendor> vlist = vendserv.getAllVendors();		 
		return ResponseEntity.status(HttpStatus.OK).body(vlist);		 
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vendor> getVendorById(@PathVariable Integer id) throws ResourceNotFoundException {

		Vendor vendor = vendserv.getVendorById(id);
		return new ResponseEntity<>(vendor, HttpStatus.OK);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Vendor> getVendorByUserId(@PathVariable Long id) throws ResourceNotFoundException {

		Vendor vendor = vendserv.getVendorByUserId(id);
		return new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
		 
	}
}