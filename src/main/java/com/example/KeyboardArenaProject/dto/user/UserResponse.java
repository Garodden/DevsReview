package com.example.KeyboardArenaProject.dto.user;

import com.example.KeyboardArenaProject.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	private String id;
	private String userId;
	private String password;
	private String nickname;
	private int userRank;
	private int point;
	private String email;
	private String findPw;
	private String findPwQuestion;
	private Boolean isActive;

	public UserResponse(User user) {
		id = user.getId();
		userId = user.getUserId();
		password = user.getPassword();
		nickname = user.getNickname();
		userRank = user.getUserRank();
		point = user.getPoint();
		email = user.getEmail();
		findPw = user.getFindPw();
		findPwQuestion = user.getFindPwQuestion();
		isActive = user.getIsActive();
	}
}
