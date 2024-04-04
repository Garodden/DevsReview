package com.example.KeyboardArenaProject.service.user;


import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.KeyboardArenaProject.dto.arena.ArenaBestUserResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.repository.CommentRepository;

import jakarta.transaction.Transactional;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.UserRepository;
import com.example.KeyboardArenaProject.utils.user.GeneratePwUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final BCryptPasswordEncoder encoder;
	private final MailSender mailSender;


	@PersistenceContext
	EntityManager entityManager;
	public UserService(UserRepository userRepository, CommentRepository commentRepository, BCryptPasswordEncoder encoder, MailSender mailSender) {

		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
		this.encoder = encoder;
		this.mailSender = mailSender;
	}

	public User save(AddUserRequest dto) {

		if (userRepository.existsByUserId(dto.getUserId())) {
			throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
		}
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
		}
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


	// 회원 탈퇴
	public void signout(String password, String confirmPassword) {
		User currentUser = getCurrentUserInfo();
		Optional<User> userOptional = userRepository.findById(currentUser.getId());
		List<Comment> myComments = commentRepository.findAllByIdOrderByCreatedDateDesc(currentUser.getId());
		log.info("조회된 코멘트 목록: {}", myComments);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			log.info("회원 탈퇴를 시도하는 사용자ID - {}", user.getUserId());
			if (!encoder.matches(password, user.getPassword())) {
				log.warn("비밀번호가 일치하지 않습니다.");
				throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
			}

			if (!password.equals(confirmPassword)) {
				log.warn("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				throw new ConfirmPasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			}

			log.info("사용자 {}의 계정을 비활성화합니다.", user.getUserId());
			user.setIsActive(false);
			user.setUserId(user.getUserId() + "(탈퇴)");
			user.setNickname(user.getNickname() + "(탈퇴)");
			// Comment 테이블의 nickname 변경
			for (Comment comment : myComments) {
				log.info("댓글 작성자: {}",comment.getNickName());
				comment.setNickName(comment.getNickName() + "(탈퇴)");
			}
			commentRepository.saveAll(myComments);
			userRepository.save(user);
			log.info("사용자의 계정이 성공적으로 비활성화되었습니다. - {}", user.getUserId());
		} else {
			log.warn("사용자를 찾을 수 없습니다: {}", currentUser.getUserId());
			throw new UserNotFoundException("사용자를 찾을 수 없습니다: " + currentUser.getUserId());
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
	public void givePoints(String Id, int points) {
		userRepository.updatePoints(Id, points);
	}

	//특정주기마다 랭크 최신화시키는 쿼리
	@Transactional
	public void updateRank() {
		String sql = "UPDATE user AS u" +
				"    JOIN (" +
				"        SELECT" +
				"            uc.id," +
				"            SUM((uc.total_users - uc.board_rank) / (uc.total_users-1) )+1 AS rank_score" +
				"        FROM (" +
				"                 SELECT" +
				"                     id," +
				"                     board_id," +
				"                     RANK() OVER (PARTITION BY board_id ORDER BY clear_time ASC) AS board_rank," +
				"                     COUNT(*) OVER (PARTITION BY board_id) AS total_users" +
				"                 FROM user_cleared_board" +
				"                 WHERE board_id IN (SELECT board_id FROM board WHERE board_type = 2)" +
				"             ) AS uc" +
				"        GROUP BY uc.id" +
				"    ) AS scores ON u.id = scores.id" +
				"	SET u.user_rank = ROUND(scores.rank_score)" +
				"	WHERE u.user_rank != 10;";
		entityManager.createNativeQuery(sql).executeUpdate();
	}


	public List<ArenaBestUserResponse> findTop5UsersOfBoard(String boardId) {
		String sql = "SELECT u.nickname, u.user_rank as userRank, ct.clear_time as clearTime, ct.clear_rank as clearRank" +
				" FROM user AS u" +
				" JOIN(SELECT id, clear_time, RANK() OVER (ORDER BY clear_time ASC) AS clear_rank" +
				" FROM user_cleared_board" +
				"            WHERE board_id = '" + boardId + "' AND clear_time IS NOT NULL" +
				"            ORDER BY clear_time ASC" +
				") AS ct" +
				" ON u.id = ct.id" +
				" ORDER BY clearRank" +
				" LIMIT 5";


		List<ArenaBestUserResponse> responseList = new ArrayList<>();
		List<Object[]> resultList = entityManager.createNativeQuery(sql).getResultList();

		for (Object[] result : resultList) {
			String nickname = (String) result[0];
			int userRank = (int) result[1];
			LocalTime clearTime = ((Time) result[2]).toLocalTime(); // DB에서 Timestamp 타입으로 받는 경우
			int clearRank = ((Long) result[3]).intValue();

			ArenaBestUserResponse response = ArenaBestUserResponse.builder()
					.nickname(nickname)
					.userRank(userRank)
					.clearTime(clearTime)
					.clearRank(clearRank)
					.build();

			responseList.add(response);
		}

		return responseList;
	}

	public static class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	public class ConfirmPasswordMismatchException extends RuntimeException {
		public ConfirmPasswordMismatchException(String message) {
			super(message);
		}
	}

	public class PasswordMismatchException extends RuntimeException {
		public PasswordMismatchException(String message) {
			super(message);
		}
	}

	// public class InCorrectAnswerException extends RuntimeException {
	// 	public InCorrectAnswerException(String message) {
	// 		super(message);
	// 	}
	// }
}


