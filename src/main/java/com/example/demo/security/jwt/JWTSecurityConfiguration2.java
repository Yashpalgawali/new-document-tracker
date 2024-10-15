package com.example.demo.security.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

//@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
public class JWTSecurityConfiguration2 {
	
	@Autowired
    private DataSource dataSource;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.authorizeHttpRequests(auth ->{
			auth.anyRequest().authenticated();
		});
		//http.formLogin();
		
		http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		 
	 	http.csrf().disable();
//		http.logout(logout->{ 
//			logout.logoutUrl("/logout").logoutSuccessUrl("/login");
//		});
	 	http
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/") // Redirect after successful logout
		.deleteCookies("SESSION")
		.invalidateHttpSession(true);
		
		//http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);	
		
		return http.build();
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication()
					.dataSource(dataSource)
					.passwordEncoder(new BCryptPasswordEncoder())
					.usersByUsernameQuery("select username,password,enabled from tbl_user WHERE username=?")
					.authoritiesByUsernameQuery("select username,role FROM tbl_user WHERE username=?")
					;
	}
	
//	@Bean
//	public KeyPair keyPair() {
//	       KeyPairGenerator keypairgenerator = null;
//		try {
//			keypairgenerator = KeyPairGenerator.getInstance("RSA");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	       keypairgenerator.initialize(2048);
//	       return keypairgenerator.generateKeyPair();
//	}
//
//	@Bean 
//	public RSAKey rsaKey(KeyPair keyPair) {
//		return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate())
//			.keyID(UUID.randomUUID().toString())
//			.build();
//
//	}
//
//	@Bean
//	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
//		var jwkset = new JWKSet(rsaKey);
//
////		new JWKSource() {
////			@Override
////			public List get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
////				return jwkSelector.select(jwkset);
////			}
////		};
//		return (jwkSelector , context) -> jwkSelector.select(jwkset);
//	}
//
//	@Bean
//	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
//		return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
//	} 
//
//	
//	@Bean
//	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
//		return new NimbusJwtEncoder(jwkSource);
//	}
	
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