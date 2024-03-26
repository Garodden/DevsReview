package com.example.KeyboardArenaProject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.service.user.UserService;

@Controller
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public String signup(AddUserRequest request) {
		userService.save(request);
		return "redirect:/login";
	}
}
