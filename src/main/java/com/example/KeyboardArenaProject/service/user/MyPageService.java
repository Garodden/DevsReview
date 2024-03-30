package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.MyPageRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MyPageService {
    private final UserRepository userRepository;
    private final MyPageRepository myPageRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public MyPageService(UserRepository userRepository, MyPageRepository myPageRepository, UserService userService, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.myPageRepository = myPageRepository;
        this.userService = userService;
        this.encoder = encoder;
    }

    public MyPageInformation getUserInfo(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new MyPageInformation(user);
        } else {
            throw new MyPageUserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 변경
    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        // 현재 로그인한 사용자 정보
        User currentUser = userService.getCurrentUserInfo();
        log.info("비밀번호 변경을 시도하는 사용자 정보 - {}", currentUser);

        // 현재 비밀번호 확인
        if (!encoder.matches(currentPassword, currentUser.getPassword())) {
            log.warn("현재 비밀번호가 일치하지 않습니다.");
            throw new CurrentPasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 확인
        if (!newPassword.equals(confirmPassword)) {
            log.warn("새로운 비밀번호와 새로운 비밀번호 확인 비밀번호가 일치하지 않습니다.");
            throw new NewPasswordMismatchException("새로운 비밀번호와 새로운 비밀번호 확인 비밀번호가 일치하지 않습니다.");
        }
        // 새로운 비밀번호 변경
        currentUser.setPassword(encoder.encode(newPassword));

    }

    public List<Board> getMyBoards(String userId) {
        List<Board> myBoards = myPageRepository.findAllById(userId);
        if (myBoards.isEmpty()) {
            throw new MyBoardNotFoundException("작성한 게시글이 없습니다");
        }
        for (Board board : myBoards) {
            log.info("MyPageService - getMyBoards: 게시글 작성일자: {}", board.getCreatedDate());
        }
        return myBoards;
    }


    public class MyPageUserNotFoundException extends RuntimeException {
        public MyPageUserNotFoundException(String message) {
            super(message);
        }
    }

    public class CurrentPasswordMismatchException extends RuntimeException {
        public CurrentPasswordMismatchException(String message) {
            super(message);
        }
    }

    public class NewPasswordMismatchException extends RuntimeException {
        public NewPasswordMismatchException(String message) {
            super(message);
        }
    }

    public class MyBoardNotFoundException extends RuntimeException {
        public MyBoardNotFoundException(String message) {
            super(message);
        }
    }
}