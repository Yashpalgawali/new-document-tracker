package com.example.demo.service;

import java.util.List;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Vendor;

public interface VendorService {

	public Vendor saveVendor(Vendor vendor);
	
	public List<Vendor> getAllVendors();
	
	public Vendor getVendorById(Integer id) throws ResourceNotFoundException;
	
	public Vendor getVendorByUserId(Long userid) throws ResourceNotFoundException;

	public int updateVendor(Vendor vendor);
	
	 
}
