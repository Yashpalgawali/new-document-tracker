package com.example.demo.security.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtAuthentication {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.httpBasic(basic->{});
		http.authorizeHttpRequests(auth -> {

			auth.antMatchers("/users/**", "/authenticate", "/error").permitAll();
			auth.anyRequest().permitAll();
//			auth.anyRequest().authenticated();
		});

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.csrf(csrf -> csrf.disable());
		http.cors(cors -> {
			cors.configurationSource(request -> {
				CorsConfiguration config = new CorsConfiguration();

				config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000")); // Your
																											// Angular
																											// app's URL
//	 			 config.setAllowedOrigins(Arrays.asList("*")); // Your Angular app's URL
				config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
				config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
				config.setAllowCredentials(true); // Allow cookies
				return config;
			});
		});
		http.logout(logout -> {
			logout.logoutUrl("/logouturl");
			logout.invalidateHttpSession(true);
			logout.clearAuthentication(true);
			logout.deleteCookies("SESSION");
			logout.logoutSuccessHandler((request, response, authentication) -> {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\": \" Logged Out Successfully \" }");
				response.getWriter().flush();
			});
		});
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	KeyPair keyPair() {
		KeyPairGenerator keypairgenerator = null;
		try {
			keypairgenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keypairgenerator.initialize(2048);
		return keypairgenerator.generateKeyPair();
	}

	@Bean
	RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString()).build();
	}

	@Bean
	JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		var jwkset = new JWKSet(rsaKey);
		return (jwkSelector, context) -> jwkSelector.select(jwkset);
	}

	@Bean
	JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
	}

	@Bean
	JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

}
