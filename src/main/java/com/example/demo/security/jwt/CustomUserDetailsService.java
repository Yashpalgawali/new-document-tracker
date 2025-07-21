package com.example.demo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository usersRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
//		 User user = usersRepository.findByUsername(username)
//		            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//		        return new CustomUserDetails(user);
		 User user = usersRepository.findByEmail(username)
		            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		        return new CustomUserDetails(user);
	}
	
	 
}
