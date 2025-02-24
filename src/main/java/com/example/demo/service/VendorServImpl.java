package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.VendorNotFoundException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.User;
import com.example.demo.models.UserType;
import com.example.demo.models.Vendor;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;

@Service("vendserv")
public class VendorServImpl implements VendorService {

	private VendorRepository vendrepo;
	private ActivityRepository actrepo;
	private UserRepository userrepo;
	private BCryptPasswordEncoder passEncoder;

	public VendorServImpl(VendorRepository vendrepo, ActivityRepository actrepo, UserRepository userrepo,
			BCryptPasswordEncoder passEncoder) {
		this.vendrepo = vendrepo;
		this.actrepo = actrepo;
		this.userrepo = userrepo;
		this.passEncoder = passEncoder;

	}

	@Override
	public Vendor saveVendor(Vendor vendor) {

		User user = new User();

		user.setEmail(vendor.getVendor_email());
		user.setUsername(vendor.getUsername());
		user.setPassword(passEncoder.encode(vendor.getPassword()));
		user.setEnabled(1);
		user.setRole("vendor");

		UserType utype = new UserType();

		utype.setUser_type_id(2);
		utype.setUser_type("vendor");

		user.setUsertype(utype);

		userrepo.save(user);

		vendor.setUser(user);

		vendor.setEnabled(1);
		
		System.err.println("Inside saveVendor() service \n "+ vendor.toString());
		Vendor vend = vendrepo.save(vendor);
		if (vend != null) {

			Activity act = new Activity();

			act.setActivity("Vendor " + vendor.getVendor_name() + " is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));

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
		return vendrepo.findById(id).orElseThrow(() -> new VendorNotFoundException(" Vendor not found for ID " + id));
//		if(vendor!=null) {
//			return vendor.get();
//		}	
//		else {
//			return null;
//		}
	}

	@Override
	public int updateVendor(Vendor vendor) {
		return vendrepo.updateVendor(vendor.getVendor_name(), vendor.getVendor_email(), vendor.getUser().getUserid(),
				vendor.getEnabled(), vendor.getVendor_id());
	}

	@Override
	public Vendor getVendorByUserId(Integer userid) {
		userrepo.findById(userid)
				.orElseThrow(() -> new VendorNotFoundException("No user/Vendor found for given ID " + userid));
		User user = userrepo.findById(userid).get();
		return vendrepo.findByUser(user);
	}

}
