package com.example.KeyboardArenaProject.service.user;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.UserRepository;
import com.example.KeyboardArenaProject.utils.GeneratePwUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	private final MailSender mailSender;

	@PersistenceContext
	EntityManager entityManager;
	public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, MailSender mailSender) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.mailSender = mailSender;
	}

	public User save(AddUserRequest dto) {
		return userRepository.save(
			User.builder()
				.userId(dto.getUserId())
				.password(encoder.encode(dto.getPassword()))
				.nickname(dto.getNickname())
				.email(dto.getEmail())
				.findPw(dto.getFindPw())
				.findPwQuestion(dto.getFindPwQuestion())
				.build()
		);
	}

	public void updateRank(){
		String sql ="UPDATE user u \n" +
				"JOIN (\n" +
				"    SELECT \n" +
				"        uc.user_id, \n" +
				"        SUM(uc.rank / uc.total_users)+1 AS rank_score\n" +
				"    FROM (\n" +
				"        SELECT \n" +
				"            user_id, \n" +
				"            board_id, \n" +
				"            RANK() OVER (PARTITION BY board_id ORDER BY clear_time ASC) AS rank, \n" +
				"            COUNT(*) OVER (PARTITION BY board_id) AS total_users\n" +
				"        FROM user_cleared_board\n" +
				"        WHERE board_id IN (SELECT board_id FROM board WHERE board_type = 2)\n" +
				"    ) AS uc\n" +
				"    GROUP BY uc.user_id\n" +
				") AS scores ON u.id = scores.user_id\n" +
				"SET u.user_rank = ROUND(scores.rank_score);";

		entityManager.createNativeQuery(sql).executeUpdate();
	}

	// 현재 로그인한 유저 정보 조회
	public User getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User)authentication.getPrincipal();
	}

	public String getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		return user.getUserId();
	}


	// 이메일로 유저 아이디 확인
	public String getUserId(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		log.info("service - getUserId 입력된 이메일: {}", email);
		log.info("service - getUserId user: {}", userOptional);
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			log.info("service - getUserId userId: {}", user.getUserId());
			sendUserIdByEmail(user.getEmail(), user.getUserId());
			return user.getUserId();
		} else {
			throw new UserNotFoundException("User not found with email: " + email);
		}
	}

	// 이메일로 아이디 전송
	public void sendUserIdByEmail(String email, String userId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("[Keyboard Arena] 계정 아이디 확인"); // 메일 제목
		message.setTo(email);
		message.setText("회원님의 계정 아이디는 " + userId + "입니다.");
		mailSender.send(message);
	}

	public boolean checkDuplicateUserId(String userId) {
		log.info("checkDuplicateUserId: 아이디 중복여부 체크중: {}", userId);
		log.info("checkDuplicateUserId: 아이디 중복여부: {}", userRepository.existsByUserId(userId));
		return userRepository.existsByUserId(userId);
	}

	public boolean checkDuplicateEmail(String email) {
		log.info("checkDuplicateEmail: 이메일 중복여부 체크중: {}", email);
		log.info("checkDuplicateEmail: 이메일 중복여부: {}", userRepository.existsByEmail(email));
		return userRepository.existsByEmail(email);
	}

	public String resetPassword(String userId, String question, String answer) {
		Optional<User> userOptional = userRepository.findByUserIdAndFindPwQuestionAndFindPw(userId, question, answer);
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			log.info("service - resetPassword 찾은 userId: {}", user.getUserId());
			String newPassword = GeneratePwUtils.generateNewPassword();

			String encryptedPassword = encoder.encode(newPassword);
			log.info("userService - encryptedPassword: {}", encryptedPassword);
			user.setPassword(encryptedPassword);
			userRepository.save(user);
			log.info("userService - user's new password: {}", user.getPassword());
			return newPassword;
		} else {
			log.info("service - resetPassword : UserNotFoundException");
			throw new UserNotFoundException("User not found with userId:" + userId + ", question: " + question + ", answer: " + answer);
		}
	}

	public String getNickNameById(String id){
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get().getNickname();
		}
		else{
			return "user not found";
		}

	}


	public Optional<User> findOptionalUserById(String id) {
		return userRepository.findOptionalUserById(id);
	}
	public User findById(String id){
		return userRepository.findById(id).orElse(null);
	}


	@Transactional
	public void givePoints(String userId, int points) {
		User user = userRepository.findById(userId).orElse(null);

		if(user!=null){
			user.updatePoint(points);
		}
	}

	public static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	// public class InCorrectAnswerException extends RuntimeException {
	// 	public InCorrectAnswerException(String message) {
	// 		super(message);
	// 	}
	// }
}


