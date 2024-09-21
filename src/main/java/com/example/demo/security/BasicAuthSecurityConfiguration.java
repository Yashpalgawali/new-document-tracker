package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

@Configuration
public class BasicAuthSecurityConfiguration {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->{
			auth.anyRequest().authenticated();
		});
		//http.formLogin();
		
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		
		http.httpBasic();
		
		http.csrf(csrf -> csrf.disable());
		
		http.logout(logout->{ 
			logout.logoutUrl("/logout");
			
		});
		return http.build(); 
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configBasicAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username,password,enabled from tbl_user where username=?")
			.authoritiesByUsernameQuery("select username,role FROM tbl_user WHERE username=?");
	}
  
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		//System.err.println("Inside WebMvcConfigurer cors config() \n");
//		return new WebMvcConfigurer() {
//			public void addCorsMappings(CorsRegistry registry ) {
//				
//				registry.addMapping("/**")
//						.allowedMethods("OPTIONS","GET","POST","PUT","DELETE","PATCH")
//						.allowedOrigins("http://localhost:4200")
//						.allowedHeaders("OPTIONs");
//			}
//		};
//	}
}