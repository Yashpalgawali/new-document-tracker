package com.example.demo.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class BasicAuthSecurityConfiguration {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->{
			auth.antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
			
			auth.antMatchers(HttpMethod.POST, "/vendor/**").permitAll();
			auth.antMatchers(HttpMethod.GET , "/notification/active","/regulationtype/","/expired/regulation/vendor/**","/vendor/user/**").hasAnyRole("VENDOR","ADMIN");
			auth.antMatchers("/vendor/**","/notification/**", "/activity/**", "/regulationhist/**", "/regulationtype/**").hasRole("ADMIN");
           
			auth.anyRequest().authenticated();
		});
		//http.formLogin(); // This will enable the Form based Login
		http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Your Angular app's URL
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            config.setAllowCredentials(true); // Allow cookies
            return config;
        });
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

		http.httpBasic();
		
		http.csrf(csrf -> csrf.disable());
		
		http.logout(logout->{ 
			logout.logoutUrl("/logout");
			logout.invalidateHttpSession(true);
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
			.usersByUsernameQuery("SELECT username,password,enabled FROM tbl_user WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username,role FROM tbl_user WHERE username=?");
			//.usersByUsernameQuery("select username,password,enabled from tbl_user where username=?")
			//.authoritiesByUsernameQuery("select username,role FROM tbl_user WHERE username=?");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	} 
}