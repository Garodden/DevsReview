package com.example.KeyboardArenaProject.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public ResponseEntity<UserResponse> signup(@RequestBody AddUserRequest request) {
		User user = userService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(user.toResponse());
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response,
			SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}

	// @GetMapping("/test1")
	// @ResponseBody
	// public ResponseEntity<UserResponse> getUserInfo() {
	// 	// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	// String currentPrincipalName = authentication.getName();
	// 	// System.out.println(currentPrincipalName);
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	UserDetails userDetail = (UserDetails)authentication.getPrincipal();
	// 	User user = (User)userDetail;
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 		.body(user.toResponse());
	// }

	@GetMapping("/userinfo")
	@ResponseBody
	public ResponseEntity<UserResponse> getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();

		log.info("현재 로그인된 사용자의 id(pk): {}", user.getId());
		log.info("현재 로그인된 사용자의 아이디(userId): {}", user.getUserId());
		log.info("현재 로그인된 사용자의 비밀번호(password): {}", user.getPassword());
		log.info("현재 로그인된 사용자의 유저네임(nickname): {}", user.getNickname());
		return ResponseEntity.status(HttpStatus.OK)
			.body(user.toResponse());
	}
}
