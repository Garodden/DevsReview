package com.example.KeyboardArenaProject.controller.freeBoard;

import com.example.KeyboardArenaProject.config.InterceptorConfiguration;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardRecieveForm;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardWriteRequest;
import com.example.KeyboardArenaProject.dto.user.AddUserRequest;
import com.example.KeyboardArenaProject.dto.user.UserResponse;
import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.User;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.LikeService;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;

import com.example.KeyboardArenaProject.service.user.UserDetailService;
import com.example.KeyboardArenaProject.service.user.UserService;
import io.micrometer.common.util.StringUtils;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FreeBoardController {
    private final FreeBoardService freeBoardService;
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;

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
    public String updateFreeBoard(@PathVariable String board_id,@ModelAttribute FreeBoardRecieveForm recieveForm){

        freeBoardService.updateBoard(recieveForm.getTitle(),recieveForm.getContent(), recieveForm.getBoardRank(), board_id);
        FreeBoardResponse response = freeBoardService.findByBoardId(board_id).toResponse();
        log.info("/board/{board_id} response {}",response);
        return "redirect:/board/"+board_id;
    }

    @DeleteMapping("/board/{board_id}")
    public String deleteFreeBoard(@PathVariable String board_id){
        freeBoardService.deleteBoard(board_id);
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String viewAllFreeBoard(Model model){

        List<Board> freeboardList = freeBoardService.findAllSortedFreeBoard();
        model.addAttribute("freeboard",freeboardList);
        model.addAttribute("loginedUserRank",userService.getCurrentUserInfo().getUserRank());

        return "index";
    }

    @GetMapping("/board/{board_id}")
    public String viewOneFreeBoard(@PathVariable String board_id,Model model,HttpServletRequest request){
        //ip
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }

        if(!freeBoardService.isContainsIpAndId(clientIp,board_id,userService.getCurrentUserId())){
            freeBoardService.saveIpAndId(clientIp,board_id,userService.getCurrentUserId());
            freeBoardService.plusView(board_id);
        }
        model.addAttribute("writer",freeBoardService.findWriter(board_id));
        model.addAttribute("post",freeBoardService.findByBoardId(board_id));
        model.addAttribute("comments",commentService.findCommentsByBoardId(board_id));
        model.addAttribute("loginedId",userService.getCurrentUserInfo().getId());
        List<Integer> commentWritersRank = new ArrayList<>();
        for (int i = 0; i < commentService.findCommentsByBoardId(board_id).size(); i++) {
            commentWritersRank.add(userService.findById(commentService.findCommentsByBoardId(board_id).get(i).getId()).getUserRank());
        }
        model.addAttribute("commentWritersRanks",commentWritersRank);

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
        UserBoardCompositeKey userBoardCompositeKey = UserBoardCompositeKey.builder().id(id).boardId(boardId).build();
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

    //댓글
    @PostMapping("/comments/{board_id}")
    public String saveComment(@PathVariable String board_id,@RequestBody Map<String,String> content){
        Comment comment = new Comment(board_id,content.get("content"), userService.getCurrentUserInfo().getId(),userService.getCurrentUserInfo().getNickname());
        commentService.saveComment(comment);
        return "redirect:/board/"+board_id;
    }

    @DeleteMapping("/comments/{comment_id}")
    public String deleteComment(@PathVariable String comment_id){
        String boardId = commentService.findCommentById(comment_id).getBoardId();
        commentService.deleteComment(comment_id);
        return "redirect:/board/"+boardId;
    }

}