package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class BasicAuthSecurityConfiguration {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->{
			auth.anyRequest().authenticated();
		});
		//http.formLogin();
		
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.httpBasic();
		
		http.csrf(csrf -> csrf.disable());
		
		http.logout(logout->{ 
			logout.logoutUrl("/logout").logoutSuccessUrl("/login");
			
		});
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user1 = User.withUsername("in28minutes")
				//.password("{noop}dummy")
				.password("dummy")
				.passwordEncoder(str -> passwordEncoder().encode(str)  )
				.roles("USER")
				.build();
		
		UserDetails admin = User.withUsername("admin")
				//.password("{noop}dummy")
				.password("dummy")
				.passwordEncoder(str -> passwordEncoder().encode(str)  )
				.roles("ADMIN","USER")
				.build();
		 
		return new InMemoryUserDetailsManager(user1 ,admin);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry ) {
				
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:4200");
			}
		};
	}
}