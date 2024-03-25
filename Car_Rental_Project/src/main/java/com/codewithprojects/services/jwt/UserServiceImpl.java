package com.codewithprojects.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithprojects.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			public UserDetails loadUserByUsername(String username) {
			return userRepository.findFirstByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException ("User not found"));
			}
			};
	}

}
