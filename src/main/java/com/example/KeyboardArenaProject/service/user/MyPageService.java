package com.example.KeyboardArenaProject.service.user;
import com.example.KeyboardArenaProject.dto.mypage.MyArenaResponse;
import com.example.KeyboardArenaProject.dto.mypage.MyCommentedBoardsResponse;
import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.repository.ClearedRepository;
import com.example.KeyboardArenaProject.repository.CommentRepository;
import com.example.KeyboardArenaProject.repository.LikeRepository;
import com.example.KeyboardArenaProject.repository.MyPageRepository;
import com.example.KeyboardArenaProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MyPageService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClearedRepository clearedRepository;
    private final CommentRepository commentRepository;
    private final MyPageRepository myPageRepository;
    private final UserService userService;
    private final ClearedService clearedService;
    private final PasswordEncoder encoder;

    public MyPageService(UserRepository userRepository, LikeRepository likeRepository,
		ClearedRepository clearedRepository, CommentRepository commentRepository, MyPageRepository myPageRepository, UserService userService,
		ClearedService clearedService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
		this.clearedRepository = clearedRepository;
		this.commentRepository = commentRepository;
		this.myPageRepository = myPageRepository;
        this.userService = userService;
		this.clearedService = clearedService;
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
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword) {
        // 현재 로그인한 사용자 정보
        User currentUser = userService.getCurrentUserInfo();
        log.info("비밀번호 변경을 시도하는 사용자ID - {}", currentUser.getUserId());

        // 현재 비밀번호 확인
        if (!encoder.matches(currentPassword, currentUser.getPassword())) {
            log.warn("현재 비밀번호가 일치하지 않습니다.");
            throw new CurrentPasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 확인
        if (!newPassword.equals(confirmNewPassword)) {
            log.warn("새로운 비밀번호와 새로운 비밀번호 확인 비밀번호가 일치하지 않습니다.");
            throw new NewPasswordMismatchException("새로운 비밀번호와 새로운 비밀번호 확인 비밀번호가 일치하지 않습니다.");
        }
        // 새로운 비밀번호 변경
        currentUser.setPassword(encoder.encode(newPassword));
        userRepository.save(currentUser);
        log.info("사용자 {}의 비밀번호가 성공적으로 변경되었습니다!", currentUser.getUserId());
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
            .map(like -> like.getCompositeId().getBoardId())
            .collect(Collectors.toList());
        List<Board> myLikedBoards = myPageRepository.findAllByBoardIdInOrderByCreatedDateDesc(boardIds);
        for (Board likedBoard : myLikedBoards) {
            log.info("MyPageService - getMyLikedBoards: 작성일자 내림차순으로 조회한 좋아요 누른 게시물의 작성일자 : {}", likedBoard.getCreatedDate());
        }
        return myLikedBoards;
    }

    public List<MyArenaResponse> getMyArenaDetails(String id) {
        // 아레나 정보를 담을 리스트
        List<MyArenaResponse> myArenaDetails = new ArrayList<>();

        // 유저가 클리어한 기록을 참전 시작일시 기준으로 내림차순 조회
        List<Cleared> myArenas = clearedRepository.findAllByCompositeId_idOrderByStartTimeDesc(id);
        // boardId만 모은 리스트
        List<String> boardIds = myArenas.stream()
            .map(myArena -> myArena.getCompositeId().getBoardId())
            .collect(Collectors.toList());
        // 유저가 클리어한 아레나를 Board 테이블에서 다시 조회
        List<Board> myArenasFromBoard = myPageRepository.findAllById(boardIds);

        for(Board myArena: myArenasFromBoard) {
            // 유저가 클리어한 아레나에 참전한 기록 전체를 조회
            List<Cleared> participantList = clearedService.findAllByBoardId(myArena.getBoardId());
            // 유저가 클리어한 아레나에 참전한 인원 수
            long participantSize = participantList.size();
            // 전체 참전인원 수 중에서 유저의 등수 계산
            long myRank = clearedService.findRanking(participantList, id);
            MyArenaResponse myArenaDetail = MyArenaResponse
                .builder()
                .board(myArena)
                .participates(clearedService.findParticipatesByBoardId(myArena.getBoardId()))
                .percentage(((double) myRank / participantSize) * 100) // 등수를 전체 참전 인원 수로 나누어 퍼센티지 계산
                .build();
            myArenaDetails.add(myArenaDetail);
        }
        return myArenaDetails;
    }

    public List<MyCommentedBoardsResponse> getMyCommentedBoards(String id) {
        List<Comment> myComments = commentRepository.findAllByIdOrderByCreatedDateDesc(id);
        if(myComments.isEmpty()) {
            throw new MyCommentNotFoundException("댓글 작성 이력을 조회하지 못했습니다.");
        }

        List<String> boardIds = myComments.stream()
            .map(Comment::getBoardId)
            .collect(Collectors.toList());

        List<Board> myCommentedBoards = myPageRepository.findAllByBoardIdInOrderByCreatedDateDesc(boardIds);

        List<MyCommentedBoardsResponse> myCommentedBoardsResponse = new ArrayList<>();

        for (Board board : myCommentedBoards) {
            User user = userService.findById(board.getId());
            String nickname = user.getNickname();
            if(nickname.isBlank()) {
                throw new AuthorNotFoundException("게시물의 작성자를 조회하지 못했습니다.");
            }
            List<Comment> myCommentsForBoard = commentRepository.findAllByBoardIdAndIdByCreatedDateDesc(board.getBoardId(), id);
            log.info("{}", myCommentsForBoard.size());
            MyCommentedBoardsResponse response = MyCommentedBoardsResponse
                .builder()
                .board(board)
                .author(nickname)
                .myComments(myCommentsForBoard)
                .build();
            myCommentedBoardsResponse.add(response);
        }
        return myCommentedBoardsResponse;
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
    public class MyCommentNotFoundException extends RuntimeException {
        public MyCommentNotFoundException(String message) {
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

    public class AuthorNotFoundException extends RuntimeException {
        public AuthorNotFoundException(String message) {
            super(message);
        }
    }
}