package com.example.demo.security;

import java.net.http.HttpResponse;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class BasicAuthenticationConfiguration {

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		http.httpBasic();
//		http.csrf().disable();
//		//http.cors();
//		http.cors().configurationSource(request -> {
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Your Angular app's URL
//            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//            config.setAllowCredentials(true); // Allow cookies
//            return config;
//        });
//		
//		http.authorizeRequests(auth->{
//			auth.antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
//			auth.anyRequest().authenticated();
//			
//		});
		http.httpBasic();
		//http.cors();
		http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Your Angular app's URL
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            config.setAllowCredentials(true); // Allow cookies
            return config;
        });
		http.csrf().disable();
		http.authorizeRequests(auth->{
			auth.antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
			auth.anyRequest().authenticated();
			
		});
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

//		http.headers().frameOptions().sameOrigin();
		
 
		http.logout(logout->{
		logout.logoutUrl("/logouturl");
		logout.invalidateHttpSession(true);
		logout.clearAuthentication(true);
		logout.deleteCookies("SESSION");
		logout.logoutSuccessHandler((request, response , authentication)->{
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\" message\": \" Logged Out Successfully \" }");
				response.getWriter().flush();
			});
		});
		return http.build();
	}
	@Autowired
	private DataSource dataSource;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
    	authBuilder.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(new BCryptPasswordEncoder())
            .usersByUsernameQuery("select username, password, enabled from tbl_user where username=?")
            .authoritiesByUsernameQuery("select username, role from tbl_user where username=?")
            ;
    }
     
	
	@Bean
	 BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
}
