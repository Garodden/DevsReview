package com.example.KeyboardArenaProject.service.user;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	// 현재 로그인한 유저 정보 조회
	public User getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return user;
	}

	public String getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return user.getUserId();
	}


	public String getUserId(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		log.info("service - getUserId 입력된 이메일: {}", email);
		log.info("service - getUserId user: {}", userOptional);
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			log.info("service - getUserId userId: {}", user.getUserId());
			return user.getUserId();
		} else {
			throw new UserNotFoundException("User not found with email: " + email);
		}
	}

	//임시 유저 닉네임 제공 함수
	public String getNickNameById(String id){
		return (id+"nickname");
	}

	// 임시 유저 정보 접근용
	public static User getTemporalUserGet(){
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

	public class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}
}


