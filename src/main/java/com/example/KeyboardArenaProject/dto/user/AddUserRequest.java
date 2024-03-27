package com.example.KeyboardArenaProject.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
	private String userId;
	private String password;
	private String nickname;
	private String email;
	private String findPw;
	private String findPwQuestion;
}
