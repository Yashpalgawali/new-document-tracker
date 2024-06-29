package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

import com.example.demo.models.User;

@Service("userserv")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userrepo;
	
	@Override
	public User saveUser(User user) {

		return userrepo.save(user);
	}

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> uobject = userrepo.findById(id);
		if(uobject!=null)
		{
			return uobject.get();
		}
		else {
			return null;
		}
	}

}
