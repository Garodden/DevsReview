package com.example.KeyboardArenaProject.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddUserRequest {
	private String userId;
	private String password;
	private String nickname;
	private String email;
	private String findPw;
	private String findPwQuestion;
}
