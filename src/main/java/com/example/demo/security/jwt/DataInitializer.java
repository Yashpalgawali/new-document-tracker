package com.example.demo.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	 @Bean
//	    CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//	        return args -> {
//	            if (userRepository.findByUsername("admin").isEmpty()) {
//	            	logger.info("USER FOUND");
//	                Users admin = new Users();
//	                admin.setUsername("admin");
//	                admin.setEmail("crankyash@gmail.com");
//	                admin.setEnabled(1);
//	                admin.setPassword(passwordEncoder.encode("admin")); // Don't store plain text passwords!
//	                admin.setRole("ROLE_ADMIN");
//	                userRepository.save(admin);
//	                System.out.println("Admin user created: admin / admin123");
//	            } else {
//	                System.out.println("Admin user already exists.");
//	            }
//	        };
//	    }
}
