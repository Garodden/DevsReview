package com.example.KeyboardArenaProject.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public User save(AddUserRequest dto) {
		return userRepository.save(
			User.builder()
				.userId(dto.getUserId())
				.password(encoder.encode(dto.getPassword()))
				.nickname(dto.getNickname())
				.email(dto.getEmail())
				.findPw(dto.getFindPw())
				.findPwQuestion(dto.getFindPwQuestion())
				.build()
		);
	}

	public User getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		return (User) userDetails;
	}

	// 임시 유저 정보 접근용
	public User getTemporalUserGet(){
		return User.builder()
				.userId("user_1111_1111")
				.auth(" ")
				.findPwQuestion("내가 다녔던 초등학교 는?")
				.userId("1111")
				.password("1111")
				.email("ormy@ormy.com")
				.nickname("오르미")
				.findPw("상동초").build();
	}

}


