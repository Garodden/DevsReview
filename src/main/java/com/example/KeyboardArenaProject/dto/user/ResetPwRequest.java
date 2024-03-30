package com.example.KeyboardArenaProject.dto.user;

import lombok.Data;

@Data
public class ResetPwRequest {
	private String userId;
	private String findPwQuestion;
	private String findPw;
}
