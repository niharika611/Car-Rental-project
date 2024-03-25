package com.codewithprojects.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.dto.UserDto;
import com.codewithprojects.entity.User;
import com.codewithprojects.enums.UserRole;
import com.codewithprojects.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount=userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount==null) {
			User newAdminAccount=new User();
			newAdminAccount.setName("Admin");
			newAdminAccount.setEmail("admin@test.com");
			newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdminAccount.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminAccount);
			System.out.println("Admin account created successfully");
		}
	}

	@Override
	public UserDto createCustomer(SignUpRequest signUpRequest) {
		User user=new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createdUser=userRepository.save(user);
		UserDto userDto=new UserDto();
		userDto.setId(createdUser.getId());
		return userDto;
	}

	@Override
	public boolean hasCustomerWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

}
