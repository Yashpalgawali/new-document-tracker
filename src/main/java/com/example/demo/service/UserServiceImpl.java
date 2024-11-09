package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;

@Service("userserv")
public class UserServiceImpl implements UserService {

	private UserRepository userrepo;
	
	private ActivityRepository actrepo;
	
	private BCryptPasswordEncoder passEncoder;
	
	public UserServiceImpl(UserRepository userrepo, ActivityRepository actrepo,BCryptPasswordEncoder passEncoder) {
		super();
		this.userrepo = userrepo;
		this.actrepo = actrepo;
		this.passEncoder=passEncoder;
	}

	@Override
	public User saveUser(User user) {

		User savedUser = userrepo.save(user);
		if(savedUser!=null) {
			Activity act = new Activity();
			 
			act.setActivity("User "+ user.getUsername() +" is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);	
		}
		else {
			Activity act = new Activity();
			 
			act.setActivity("User "+ user.getUsername() +" is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
		}
		return savedUser;
	}

	@Override
	public User getUserById(Integer id) {
		
//		return userrepo.findById(id).orElse(null);
		Optional<User> uobject = userrepo.findById(id);
		if(uobject!=null) {
			return uobject.get();
		}
		else {
			return null;
		}
	}

	@Override
	public User getUserByUserName(String username) {

		return userrepo.findByUsername(username);
	}

	@Override
	public int updateUserPassword(User user) {
		String encodedPassword = passEncoder.encode(user.getCnf_password());
		System.err.println("updateUserPassword()  service \n "+user.toString());
		return userrepo.updateUserPassword(encodedPassword, user.getUserid());
		 
	}
}
