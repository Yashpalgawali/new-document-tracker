package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Vendor;

public interface VendorService {

	public Vendor saveVendor(Vendor vendor);
	
	public List<Vendor> getAllVendors();
	
	public Vendor getVendorById(Integer id);

	public Vendor updateVendor(Vendor vendor);
}
