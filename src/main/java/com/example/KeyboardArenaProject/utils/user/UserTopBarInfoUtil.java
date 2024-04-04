package com.example.KeyboardArenaProject.utils.user;

import org.springframework.stereotype.Component;

import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.UserService;

@Component
public class UserTopBarInfoUtil {
	private static UserService userService;

	public UserTopBarInfoUtil(UserService userService) {
		UserTopBarInfoUtil.userService = userService;
	}

	public static UserTopBarInfo getUserTopBarInfo() {
		User user = userService.getCurrentUserInfo();
		return new UserTopBarInfo(user);
	}
}
