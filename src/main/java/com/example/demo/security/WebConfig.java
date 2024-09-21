package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	 	@Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Allow CORS for all endpoints
	                .allowedOrigins("http://localhost:4200") // Allow only your Angular app
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed methods
	                .allowedHeaders("*"); // Allow all headers
	    }
}
