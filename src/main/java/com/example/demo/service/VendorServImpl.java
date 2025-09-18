package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.User;
import com.example.demo.models.UserType;
import com.example.demo.models.Vendor;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.VendorRepository;

@Service("vendserv")
public class VendorServImpl implements VendorService {
//	private final UserRepository userrepo;
	private final VendorRepository vendrepo;
	private final ActivityRepository actrepo;
	private final UserService userserv;
	private final BCryptPasswordEncoder passEncoder;
	private final IUserTypeService usertypeserv;
	
	public VendorServImpl(VendorRepository vendrepo, ActivityRepository actrepo, UserService userserv,
			BCryptPasswordEncoder passEncoder, IUserTypeService usertypeserv) {
		super();
		this.vendrepo = vendrepo;
		this.actrepo = actrepo;
		this.userserv = userserv;
		this.passEncoder = passEncoder;
		this.usertypeserv = usertypeserv;
	}

	@Override
	@Transactional
	public Vendor saveVendor(Vendor vendor) {

		User user = new User();

		user.setEmail(vendor.getVendor_email());
		user.setUsername(vendor.getUsername());
		user.setPassword(passEncoder.encode(vendor.getPassword()));
		user.setEnabled(1);
		user.setRole("vendor");

		UserType utype =  usertypeserv.getUserTypeById(2);
		
		user.setUsertype(utype);

		User savedUser = userserv.saveUser(user);

		if (savedUser != null) {
			vendor.setUser(user);
			vendor.setEnabled(1);

			Vendor vend = vendrepo.save(vendor);

			if (vend != null) {

				Activity act = new Activity();

				act.setActivity("Vendor " + vendor.getVendor_name() + " is saved successfully");
				act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
				act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));
				actrepo.save(act);
				return vend;
			}
			else {
				Activity act = new Activity();

				act.setActivity("Vendor " + vendor.getVendor_name() + " is not saved ");
				act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
				act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));
				actrepo.save(act);
				throw new GlobalException("User "+user.getUsername()+" is not saved");
			}
			
		} 
		else {
			Activity act = new Activity();

			act.setActivity("User " + vendor.getVendor_name() + " is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));
			actrepo.save(act);
			
			throw new GlobalException("User "+user.getUsername()+" is not saved");
		}

	}

	@Override
	public List<Vendor> getAllVendors() {

		List<Vendor> vendorList = vendrepo.findAll();
		if(vendorList.size()>0) {
			return vendorList;
		}
		throw new ResourceNotFoundException("No vendor found");
	}

	@Override
	public Vendor getVendorById(Integer id) throws ResourceNotFoundException {
		return vendrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" Vendor not found for ID " + id));
	}

	@Override
	public int updateVendor(Vendor vendor) {
		return vendrepo.updateVendor(vendor.getVendor_name(), vendor.getVendor_email(), vendor.getUser().getUserid(),
				vendor.getEnabled(), vendor.getVendor_id());
	}

	@Override
	public Vendor getVendorByUserId(Long userid) {
		 
		User user = userserv.getUserById(userid);
		return vendrepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("No vendor found for given User ID "+userid) ) ;		
	}

}
