package com.example.KeyboardArenaProject.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.dto.user.ResetPwRequest;
import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.UserService;
import com.example.KeyboardArenaProject.service.user.UserService.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public ResponseEntity<?> signup(@Validated @RequestBody AddUserRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// 바인딩 오류가 발생한 경우, 오류 메시지를 클라이언트로 반환
			log.info("errors = \n{}", bindingResult);
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}
		User user = userService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(user.toResponse());
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response,
			SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}

	@GetMapping("/user/find/id")
	public ResponseEntity<Boolean> checkDuplicateUserId(@RequestParam String userId) {
		log.info("checkDuplicateUserId: 아이디 중복여부 체크중: {}", userId);
		log.info("checkDuplicateUserId: 아이디 중복여부 반환 {}", userService.checkDuplicateUserId(userId));
		return ResponseEntity.status(HttpStatus.OK).body(userService.checkDuplicateUserId(userId));
	}

	@GetMapping("/user/find/email")
	public ResponseEntity<Boolean> checkDuplicateEmail(@RequestParam String email) {
		log.info("checkDuplicateEmail: 이메일 중복여부 체크중: {}", email);
		log.info("checkDuplicateEmail: 이메일 중복여부 반환 {}", userService.checkDuplicateEmail(email));
		return ResponseEntity.status(HttpStatus.OK).body(userService.checkDuplicateEmail(email));
	}

	@PostMapping("/user/find/id")
	public ResponseEntity<String> getUserIdByEmail(@RequestParam String email) {
		String userId;
		try {
			userId = userService.getUserId(email);
			log.info("getUserIdByEmail - 이메일 주소, 아이디: {}, {}", email, userId);
			log.info("getUserIdByEmail : 해당 이메일 주소로 아이디를 찾았습니다. ");
			return ResponseEntity.status(HttpStatus.OK).body(userId);
		} catch (UserNotFoundException e) {
			log.error("getUserIdByEmail - UserNotFoundException: 해당 이메일 주소로 아이디를 찾지 못했습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾지 못했습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send user ID");
		}
	}

	@PostMapping("/user/find/pw")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPwRequest resetPwRequest) {
		log.info("UserController - resetPassword: {}, {}, {}", resetPwRequest.getUserId(),
			resetPwRequest.getFindPwQuestion(),
			resetPwRequest.getFindPw());
		try {
			String newPassword = userService.resetPassword(resetPwRequest.getUserId(),
				resetPwRequest.getFindPwQuestion(), resetPwRequest.getFindPw());
			log.info("newPassword: {}", newPassword);
			return ResponseEntity.status(HttpStatus.OK).body(newPassword);
		} catch(UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원정보를 조회할 수 없습니다.");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 찾기 중 오류가 발생했습니다.");
		}

	}


	// 테스트 용도
	@GetMapping("/userinfo")
	@ResponseBody
	public ResponseEntity<UserResponse> getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();

		log.info("현재 로그인된 사용자의 id(pk): {}", user.getId());
		log.info("현재 로그인된 사용자의 아이디(userId): {}", user.getUserId());
		log.info("현재 로그인된 사용자의 비밀번호(password): {}", user.getPassword());
		log.info("현재 로그인된 사용자의 유저네임(nickname): {}", user.getNickname());
		return ResponseEntity.status(HttpStatus.OK)
			.body(user.toResponse());
	}
}
