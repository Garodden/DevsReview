package com.example.KeyboardArenaProject.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	@Bean
	public WebSecurityCustomizer configure() {      // 스프링 시큐리티 기능 비활성화
		return web -> web.ignoring().requestMatchers("/static/**","/css/**", "/js/**", "/media/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(auth ->
				auth.requestMatchers(
					"/login", "/signup", "/user", "/findId", "/findPw", "/user/find/id","/user/find/email", "/user/find/pw")
					.permitAll()
					.anyRequest()
					.authenticated())
			.formLogin(auth -> auth.loginPage("/login")
				.defaultSuccessUrl("/"))
			.logout(auth -> auth.logoutSuccessUrl("/login")
				.invalidateHttpSession(true))
			.csrf(auth -> auth.disable());
		return httpSecurity.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
