package com.shivamstack.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shivamstack.authenticationservice.models.AuthenticationRequest;
import com.shivamstack.authenticationservice.models.AuthenticationResponse;
import com.shivamstack.authenticationservice.securityconfiguration.MyUserDetailsService;
import com.shivamstack.authenticationservice.utility.JwtUtil;

@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authrequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password " + e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUsername());
		final String jwtToken = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
	}

}
