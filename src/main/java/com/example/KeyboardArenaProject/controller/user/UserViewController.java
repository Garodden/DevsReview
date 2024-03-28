package com.example.KeyboardArenaProject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.KeyboardArenaProject.service.user.UserService;

@Controller
public class UserViewController {
	private final UserService userService;

	public UserViewController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("user", userService.getCurrentUserInfo());
		return "home";
	}

}
