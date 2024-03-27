package com.example.KeyboardArenaProject.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.KeyboardArenaProject.utils.GenerateIdUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
	@Id
	@Column(name="id", updatable = false)
	private String id;

	@Column(name="userId", nullable = false)
	private String userId;

	@Column(name="password", nullable = false)
	private String password;

	@Column(name="nickname", nullable = false)
	private String nickname;

	@Column(name="userRank")
	private int userRank;

	@Column(name="point")
	private int point;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="findPw", nullable = false)
	private String findPw;

	@Column(name="findPwQuestion", nullable = false)
	private String findPwQuestion;

	@Column(name="isActive")
	private Boolean isActive;

	@Builder
	public User(String userId, String password, String nickname, String email, String findPw, String findPwQuestion, String auth) {
		this.id = GenerateIdUtils.generateUserId(LocalDateTime.now());
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.findPw = findPw;
		this.findPwQuestion = findPwQuestion;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("user"));
	}

	@Override
	public String getUsername() {
		return email;
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
}
