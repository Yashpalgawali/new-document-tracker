package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceNotModifiedException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.User;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.UserRepository;

@Service("userserv")
public class UserServiceImpl implements UserService {

	private final UserRepository userrepo;
	
	private final ActivityRepository actrepo;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
//	public UserServiceImpl(UserRepository userrepo, ActivityRepository actrepo,BCryptPasswordEncoder passEncoder) {
//		super();
//		this.userrepo = userrepo;
//		this.actrepo = actrepo;
//		this.passEncoder = passEncoder;
//	}
	/**
	 * @param userrepo
	 * @param actrepo
	 */
	public UserServiceImpl(UserRepository userrepo, ActivityRepository actrepo) {
		super();
		this.userrepo = userrepo;
		this.actrepo = actrepo;
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
			
			return savedUser;
		}
		else {
			Activity act = new Activity();
			 
			act.setActivity("User "+ user.getUsername() +" is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );			
			actrepo.save(act);
			
			throw new GlobalException("User "+user.getUsername()+" is not saved");
		}
		
	}

	@Override
	public User getUserById(Long id) {
		
		return userrepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found")); 
	}

	@Override
	public User getUserByUserName(String username) {
		return userrepo.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("No user found for given name "+username));
	}

	@Override
	public int updateUserPassword(User user) {
		String encodedPassword = passEncoder.encode(user.getCnf_password());
		int updateUserPassword = userrepo.updateUserPassword(encodedPassword, user.getUserid());
		if(updateUserPassword>0)
		{
			return updateUserPassword;		 
		}
		else {
			throw new ResourceNotModifiedException("The passowrd is not updated");
		}
	}

	@Override
	public User getUserByEmail(String email) {
		return userrepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("No User Found for given Email "+email)) ;
		
	}
}
