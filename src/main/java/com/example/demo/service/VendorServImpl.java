package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.User;
import com.example.demo.models.Vendor;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;

@Service("vendserv")
public class VendorServImpl implements VendorService {
 	
	private VendorRepository vendrepo;
	private ActivityRepository actrepo;
	private UserRepository userrepo;
	
	public VendorServImpl(VendorRepository vendrepo, ActivityRepository actrepo,UserRepository userrepo) {
		super();
		this.vendrepo = vendrepo;
		this.actrepo = actrepo;
		this.userrepo=userrepo;
	}
	
	@Override
	public Vendor saveVendor(Vendor vendor) {
		
		Vendor vend = vendrepo.save(vendor);
		if(vend!=null) {
			
			Activity act = new Activity();
			
			act.setActivity("Vendor "+vendor.getVendor_name()+" is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
			
		}
		return vend;
	}

	@Override
	public List<Vendor> getAllVendors() {
		
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
		 
		return null;
	}

	@Override
	public Vendor getVendorByUserId(Integer userid) {
		User user = userrepo.findById(userid).get();
		return vendrepo.findByUser(user);
	}

}
