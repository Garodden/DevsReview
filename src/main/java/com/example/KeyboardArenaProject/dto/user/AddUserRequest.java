package com.example.KeyboardArenaProject.dto.user;

import lombok.Data;
@Data
public class AddUserRequest {
	private String userId;
	private String password;
	private String nickname;
	private String email;
	private String findPw;
	private String findPwQuestion;
}
