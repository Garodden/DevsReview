package com.example.KeyboardArenaProject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
}
