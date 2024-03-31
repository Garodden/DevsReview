package com.example.KeyboardArenaProject.controller.user;

import java.util.List;

import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.MyPageService;
import com.example.KeyboardArenaProject.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;

    public MyPageController(MyPageService myPageService, UserService userService) {
        this.myPageService = myPageService;
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String getUserInfo(Model model) {

        String currentUserId = userService.getCurrentUserId();
        // 현재 로그인한 사용자의 ID와 경로 변수로 받은 사용자 ID가 일치하는지 확인하고,
        // 일치하지 않으면 접근을 거부하거나 다른 처리를 해야 합니다.
        //       if (!currentUserId.equals(userId)) {
        // 다른 사용자의 마이페이지에 접근하려는 경우에 대한 처리
        // 예를 들어 접근 거부 페이지를 보여줄 수 있습니다.
        // redirectAttributes.addFlashAttribute("errorMessage", "다른 사용자의 마이페이지에 접근할 수 없습니다.");
        //            return "redirect:/mypage";
        // return "액세스 거부페이지로 출력하는게 더 나아보인다!";
        //        }

        MyPageInformation userInfo = myPageService.getUserInfo(currentUserId);
        model.addAttribute("userInfo", userInfo);
        return "mypage";
    }

    @GetMapping("/mypage/boards")
    public String getMyBoards(Model model) {
        User user = userService.getCurrentUserInfo();
        try {
            List<Board> myBoards = myPageService.getMyBoards(user.getId());
            // List<String> formattedDates = myBoards.stream()
            //     .map(board -> formatDate(board.getCreatedDate()))
            //     .collect(Collectors.toList());
            model.addAttribute("myBoards", myBoards);
            return "myboards";
        } catch(MyPageService.MyBoardNotFoundException e) {
            String errorMessage = "작성한 게시글이 없습니다";
            model.addAttribute("errorMessage", errorMessage);
            return "myboards";
        }

    }

    @GetMapping("/mypage/boards/liked")
    public String getLikedBoards(Model model) {
        User user = userService.getCurrentUserInfo();
        String userId = user.getUserId();
        try {
            List<Like> likes = myPageService.getMyLikes(userId);
            for(Like like : likes) {
                log.info("MyPageController - getLikedBoards: 좋아요 boardId는 {}, id는 {}", like.getCompositeId().getBoardId(), like.getCompositeId().getId());
            }
            List<Board> likedBoards = myPageService.getMyLikedBoards(likes);
            for(Board board: likedBoards) {
                log.info("MyPageController - getLikedBoards: 좋아요 누른 게시글 boardId는 {}, id는 {}", board.getBoardId(), board.getId());
            }
            model.addAttribute("likedBoards", likedBoards);
            return "likedboards";
        } catch(MyPageService.MyLikeNotFoundExcpetion e) {
            String errorMessage = "좋아요를 누른 게시글이 없습니다";
            model.addAttribute("errorMessage", errorMessage);
            return "likedboards";
        }

    }

}