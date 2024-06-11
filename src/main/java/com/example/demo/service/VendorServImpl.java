package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Vendor;
import com.example.demo.repository.VendorRepository;

@Service("vendserv")
public class VendorServImpl implements VendorService {

	@Autowired
	VendorRepository vendrepo;
	
	@Override
	public Vendor saveVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		return vendrepo.save(vendor);
	}

	@Override
	public List<Vendor> getAllVendors() {
		// TODO Auto-generated method stub
		return vendrepo.findAll();
	}

	@Override
	public Vendor getVendorById(Integer id) {
		Optional<Vendor> vendor = vendrepo.findById(id);
		if(vendor!=null) {
			
			return vendor.get();
		}
		else {
			return null;
		}
	}

	@Override
	public Vendor updateVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		return null;
	}

}
