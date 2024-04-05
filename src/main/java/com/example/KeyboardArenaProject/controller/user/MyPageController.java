package com.example.KeyboardArenaProject.controller.user;

import java.util.List;

import com.example.KeyboardArenaProject.dto.mypage.MyArenaResponse;
import com.example.KeyboardArenaProject.dto.mypage.MyCommentedBoardsResponse;
import com.example.KeyboardArenaProject.dto.mypage.MyLikedBoardsResponse;
import com.example.KeyboardArenaProject.dto.user.ChangePwRequest;
import com.example.KeyboardArenaProject.dto.user.DeleteUserRequest;
import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.service.user.MyPageService;
import com.example.KeyboardArenaProject.service.user.UserService;
import com.example.KeyboardArenaProject.utils.user.UserTopBarInfoUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/mypage/changePassword")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        return "changePw";
    }

    @PostMapping("/mypage/changePassword")
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestBody ChangePwRequest changePwRequest) {
        try {
            myPageService.changePassword(changePwRequest.getCurrentPassword(), changePwRequest.getNewPassword(),
                changePwRequest.getConfirmNewPassword());
            log.info("비밀번호가 성공적으로 변경되었습니다.");
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (MyPageService.CurrentPasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        } catch (MyPageService.NewPasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새로운 비밀번호와 새로운 비밀번호 확인 비밀번호가 일치하지 않습니다.");
        }
    }

    @GetMapping("/mypage/signout")
    public String showSignoutForm(Model model) {
        String userId = userService.getCurrentUserId();
        model.addAttribute("userId", userId);
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        return "signout";
    }

    @PostMapping("/mypage/signout")
    public ResponseEntity<String> signout(@RequestBody DeleteUserRequest deleteUserRequest) {
        try {
            userService.signout(deleteUserRequest.getPassword(), deleteUserRequest.getConfirmPassword());
            log.info("회원탈퇴가 성공적으로 처리되었습니다.");
            return ResponseEntity.ok("회원탈퇴가 성공적으로 처리되었습니다.");
        } catch (UserService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        } catch (UserService.PasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        } catch (UserService.ConfirmPasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }

    @GetMapping("/mypage/boards")
    public String getMyBoards(Model model) {
        User user = userService.getCurrentUserInfo();
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        try {
            List<Board> myBoards = myPageService.getMyBoards(user.getId());
            model.addAttribute("myBoards", myBoards);
            return "myboards";
        } catch (MyPageService.MyBoardNotFoundException e) {
            String errorMessage = "작성한 게시글이 없습니다";
            model.addAttribute("errorMessage", errorMessage);
            return "myboards";
        }

    }

    @GetMapping("/mypage/boards/liked")
    public String getLikedBoards(Model model) {
        User user = userService.getCurrentUserInfo();
        String id = user.getId();
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());

        try {
            List<Like> likes = myPageService.getMyLikes(id);
            for (Like like : likes) {
                log.info("MyPageController - getLikedBoards: 좋아요 boardId는 {}, id는 {}",
                    like.getCompositeId().getBoardId(), like.getCompositeId().getId());
            }
            List<MyLikedBoardsResponse> likedBoards = myPageService.getMyLikedBoards(likes);
            model.addAttribute("likedBoards", likedBoards);
            return "likedBoards";
        } catch (MyPageService.MyLikeNotFoundExcpetion e) {
            String errorMessage = "좋아요를 누른 게시글이 없습니다";
            model.addAttribute("errorMessage", errorMessage);
            return "likedBoards";
        }
    }

    @GetMapping("/mypage/boards/commented")
    public String getMyCommentedBoards(Model model) {
        User user = userService.getCurrentUserInfo();
        String id = user.getId();
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        try {
            List<MyCommentedBoardsResponse> myCommentedBoards = myPageService.getMyCommentedBoards(id);
            model.addAttribute("myCommentedBoards", myCommentedBoards);
        } catch(MyPageService.MyCommentNotFoundException | MyPageService.AuthorNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
		return "commentBoards";
    }
    @GetMapping("/mypage/arenas")
    public String getMyArenas(Model model) {
        User user = userService.getCurrentUserInfo();
        model.addAttribute("userTopBarInfo", UserTopBarInfoUtil.getUserTopBarInfo());
        String id = user.getId();
        try {
            List<MyArenaResponse> myArenaDetails = myPageService.getMyArenaDetails(id);
            model.addAttribute("myArenas", myArenaDetails);
            return "myArenas";
        } catch (MyPageService.MyClearedBoardNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.info("{}", model.getAttribute("errorMessage"));
            return "myArenas";
        }

	}
}