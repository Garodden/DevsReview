package com.example.KeyboardArenaProject.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.KeyboardArenaProject.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	public UserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		if (userId == null || userId.isEmpty()) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
	}

}
