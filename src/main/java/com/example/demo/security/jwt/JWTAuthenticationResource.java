package com.example.demo.security.jwt;

import java.time.Instant;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTAuthenticationResource {

	private JwtEncoder jwtEncoder;
	private final AuthenticationManager authenticationManager;
	
	public JWTAuthenticationResource(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
		super();
		this.jwtEncoder = jwtEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	//This is for Angular
//	@PostMapping("/authenticate")
//	public 	JwtResponse authenticate(Authentication auth) {
//		return new JwtResponse(createToken(auth));
//	}
	
	@PostMapping("/authenticate")
	public 	JwtResponse authenticate(@RequestBody LoginRequest request) {
		// Authenticate user manually
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

		return new JwtResponse(createToken(auth));
	}

	private String createToken(Authentication auth) {
		
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		
//		user.getUser().getUsertype();
		
		logger.info("User fetched is {} ",user);
		//logger.info("User is {} , ID is {} and the name is {} and authorites are {} ", user, user.getUserId(),
			//																				user.getUsername(), user.getAuthorities()); 

		var claims = JwtClaimsSet.builder().issuer("self")
								.issuedAt(Instant.now())
								.expiresAt(Instant.now().plusSeconds(60*30))
								.subject(auth.getName()).claim("scope", createScope(auth))
								.claim("userId", user.getUserId()) // 👈 Custom claim: adds user_id to the payload of the token.
								.claim("username", user.getUsername())
								.claim("user_type", user.getUser().getUsertype().getUser_type_id())
								.build() ;
								
		String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		System.err.println("Token VALUE is "+tokenValue);
		return tokenValue;
	}

	private String createScope(Authentication auth) {
 
		String res = auth.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
		return res;
		
	}
}
record JwtResponse(String token) {}
record LoginRequest(String username, String password) {}