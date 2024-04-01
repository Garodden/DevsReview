package com.example.KeyboardArenaProject.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
	@NotBlank(message = "userId is mandatory")
	private String userId; // 아이디 (6~12자 이내, 영문/숫자 사용가능)

	@NotBlank(message = "password is mandatory")
	private String password; //비밀번호(8~16자 이내, 영문/숫자/기호 사용가능)

	@NotBlank(message = "nickname is mandatory")
	private String nickname; //유저네임 (3~12자 이내, 문자/숫자 사용가능)

	@NotBlank(message = "email is mandatory")
	private String email;

	@NotBlank(message = "findPw is mandatory")
	private String findPw;

	@NotBlank(message = "findPwQuestion is mandatory")
	private String findPwQuestion;
}
