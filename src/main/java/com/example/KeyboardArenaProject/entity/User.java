package com.example.KeyboardArenaProject.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.KeyboardArenaProject.utils.GenerateIdUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

	@Column(name="rank", nullable = false)
	private int rank;

	@Column(name="point", nullable = false)
	private int point;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="findPw", nullable = false)
	private String findPw;

	@Column(name="findPwQuestion", nullable = false)
	private String findPwQuestion;

	@Column(name="isActive", nullable = false)
	private Boolean isActive;

	public User(LocalDateTime signUpDate) {
		this.id = GenerateIdUtils.generateUserId(signUpDate);
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
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
