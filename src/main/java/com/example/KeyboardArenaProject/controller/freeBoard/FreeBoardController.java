package com.example.KeyboardArenaProject.controller.freeBoard;

import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardRecieveForm;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardWriteRequest;
import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.service.LikeService;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;

import com.example.KeyboardArenaProject.service.user.UserDetailService;
import com.example.KeyboardArenaProject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final UserService userService;
    private final LikeService likeService;

    @GetMapping("/")
    public String indexPage(Model model){
        User user = userService.getCurrentUserInfo();
        log.info("FreeBoardController-indexPage-현재 로그인한 유저 userId: {}", user.getUserId());
        return "index";
    }

    @PostMapping("/board")
    public String writeFreeBoard(@ModelAttribute FreeBoardRecieveForm recieveForm){
        FreeBoardWriteRequest request;
        Board board;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        log.warn("유저정보 id {}",user.getId());

        if(user == null){
            request = new FreeBoardWriteRequest("unknown",recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }else{
            request = new FreeBoardWriteRequest(user.getId(),recieveForm.getTitle(),recieveForm.getContent(),recieveForm.getBoardRank());
        }
        board = request.toEntity();
        freeBoardService.writeFreeBoard(board);
        log.info("board is created id = {}",board.getBoardId());
        return "redirect:/board";

    }
    @GetMapping("/newFreeboard")
    public String newFreeBoard(Model model){
        model.addAttribute("user",userService.getCurrentUserInfo());
//        if(boardId!=null){
//            model.addAttribute("board",freeBoardService.findByBoardId(boardId));
//
//        }else{
//            model.addAttribute("board",new Board());
//        }
        return "newFreeboard";
    }

    @PutMapping("/board/{board_id}")
    public ResponseEntity<FreeBoardResponse> updateFreeBoard(@PathVariable String board_id,@ModelAttribute FreeBoardRecieveForm recieveForm){

        freeBoardService.updateBoard(recieveForm.getTitle(),recieveForm.getContent(), recieveForm.getBoardRank(), board_id);
        FreeBoardResponse response = freeBoardService.findByBoardId(board_id).toResponse();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/board/{board_id}")
    public String deleteFreeBoard(@PathVariable String board_id){
        freeBoardService.deleteBoard(board_id);
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String viewAllFreeBoard(Model model){

        List<Board> freeboard = freeBoardService.findAllSortedFreeBoard();
        model.addAttribute("freeboard",freeboard);

        return "index";
    }

    @GetMapping("/board/{board_id}")
    public String viewOneFreeBoard(@PathVariable String board_id,Model model){
        model.addAttribute("writer",freeBoardService.findWriter(board_id));
        model.addAttribute("post",freeBoardService.findByBoardId(board_id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("loginedUserId",userService.getCurrentUserId());
        }else{
            model.addAttribute("loginedUserId","");
        }
        return "freeboardDetail";
    }

    @GetMapping("/board/{board_id}/update")
    public String updateOneFreeBoard(@PathVariable String board_id,Model model){
        model.addAttribute("user",userService.getCurrentUserInfo());
        model.addAttribute("post", freeBoardService.findByBoardId(board_id));
        return "updateFreeboard";
    }

    //좋아요
    @ResponseBody
    @PostMapping("/api/like")
    public void like(@RequestParam String boardId,@RequestParam String id){
        UserBoardCompositeKey userBoardCompositeKey = new UserBoardCompositeKey(id, boardId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            int likes;
            if(likeService.findById(userBoardCompositeKey)!=null){
                if(!likeService.findById(userBoardCompositeKey).isIfLike()){
                    likes = likeService.clickLikeOne(userBoardCompositeKey);

                }else{
                    likes = likeService.clickLikeTwo(userBoardCompositeKey);
                }

            }else{
                likes = likeService.save(userBoardCompositeKey);
            }
            freeBoardService.updateBoardLikes(likes, userBoardCompositeKey.getBoardId());
        }
    }

}