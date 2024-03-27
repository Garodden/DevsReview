package com.example.KeyboardArenaProject.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.UserService;

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
}
