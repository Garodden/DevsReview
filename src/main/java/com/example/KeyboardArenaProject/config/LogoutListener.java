package com.example.KeyboardArenaProject.config;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogoutListener implements ApplicationListener<LogoutSuccessEvent> {
	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		log.info("LogoutListener : 로그아웃 이벤트 발생");

		// 현재 인증된 사용자 정보 조회
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			String username = authentication.getName();
			log.info("LogoutListener :로그아웃 전 로그인된 사용자: {}", username);
		} else {
			log.info("LogoutListener :로그인된 사용자가 없습니다.");
		}
	}

}
