package com.codewithprojects.controller;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithprojects.dto.AuthenticationRequest;
import com.codewithprojects.dto.AuthenticationResponse;
import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entity.User;
import com.codewithprojects.repository.UserRepository;
import com.codewithprojects.services.auth.AuthService;
import com.codewithprojects.services.jwt.UserService;
import com.codewithprojects.utils.JWTUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JWTUtil jwtUtil;
	private final UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<?> signUpCustomer(@RequestBody SignUpRequest signUpRequest) {
		if (authService.hasCustomerWithEmail(signUpRequest.getEmail()))
			return new ResponseEntity<>("Customer already exists with this emailId", HttpStatus.NOT_ACCEPTABLE);
		UserDto createdCustomerDto = authService.createCustomer(signUpRequest);
		if (createdCustomerDto == null)
			return new ResponseEntity<>("Customer not created, Try again later", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws BadCredentialsException, DisabledException, UsernameNotFoundException {
		//System.out.println(authenticationRequest);
		try {
			//System.out.println(authenticationRequest.getEmail()+" "+authenticationRequest.getPassword());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (Exception e) {
			//System.out.println("catch");
			e.printStackTrace();
			throw new BadCredentialsException("Incorrect username or password.");
		}
		final User userDetails = (User) userService.userDetailsService()
				.loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if (optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserid(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		}
		return authenticationResponse;
	}
}
