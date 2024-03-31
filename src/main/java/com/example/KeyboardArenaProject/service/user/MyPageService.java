package com.example.KeyboardArenaProject.service.user;
import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.LikeRepository;
import com.example.KeyboardArenaProject.repository.MyPageRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MyPageService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final MyPageRepository myPageRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public MyPageService(UserRepository userRepository, LikeRepository likeRepository, MyPageRepository myPageRepository, UserService userService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
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
        List<Board> myBoards = myPageRepository.findAllByIdOrderByCreatedDateDesc(userId);
        if (myBoards.isEmpty()) {
            throw new MyBoardNotFoundException("작성한 게시글이 없습니다");
        }
        for (Board board : myBoards) {
            log.info("MyPageService - getMyBoards: 게시글 작성일자: {}", board.getCreatedDate());
        }
        return myBoards;
    }

    public List<Like> getMyLikes(String userId) {
        List<Like> myLikes = likeRepository.findByCompositeId_Id(userId);
        if(myLikes.isEmpty()) {
            throw new MyLikeNotFoundExcpetion("좋아요를 누른 게시글이 없습니다");
        }
        return likeRepository.findByCompositeId_Id(userId);
    }

    public List<Board> getMyLikedBoards(List<Like> likes) {
        List<String> boardIds = likes.stream()
                .map(like -> like.getCompositeId().getBoardId()).
                collect(Collectors.toList());
        List<Board> myLikedBoards = myPageRepository.findAllByBoardIdInOrderByCreatedDateDesc(boardIds);
        for (Board likedBoard : myLikedBoards) {
            log.info("MyPageService - getMyLikedBoards: 작성일자 내림차순으로 조회한 좋아요 누른 게시물의 작성일자 : {}", likedBoard.getCreatedDate());
        }
        return myPageRepository.findAllByBoardIdInOrderByCreatedDateDesc(boardIds);
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

    public class MyLikeNotFoundExcpetion extends RuntimeException {
        public MyLikeNotFoundExcpetion(String message) {
            super(message);
        }
    }
}