package com.example.KeyboardArenaProject.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.utils.user.GenerateIdUtils;

@Table(name = "user")
@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User implements UserDetails {
	@Getter
	@Id
	@Column(name="id", updatable = false, unique = true)
	private String id;

	@Column(name="user_id", nullable = false)
	private String userId;

	@Column(name="password", nullable = false)
	private String password;

	@Column(name="nickname", nullable = false)
	private String nickname;

	@Column(name="user_rank")
	private int userRank;

	@Column(name="point")
	private int point;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="find_pw", nullable = false)
	private String findPw;

	@Column(name="find_pw_question", nullable = false)
	private String findPwQuestion;

	@Column(name="is_active")
	private Boolean isActive;

	@Builder
	public User(String userId, String password, String nickname, String email, String findPw, String findPwQuestion, String auth) {
		this.id = GenerateIdUtils.generateUserId(LocalDateTime.now());
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.userRank = 1;
		this.point = 0;
		this.email = email;
		this.findPw = findPw;
		this.findPwQuestion = findPwQuestion;
		this.isActive = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}

	@Override
	public String getUsername() {
		return nickname;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserResponse toResponse() {
		return UserResponse.builder()
			.id(id)
			.userId(userId)
			.password(password)
			.nickname(nickname)
			.userRank(userRank)
			.point(point)
			.email(email)
			.findPw(findPw)
			.findPwQuestion(findPwQuestion)
			.isActive(isActive)
			.build();
	}
}
