package com.example.KeyboardArenaProject.controller;

import com.example.KeyboardArenaProject.entity.Comment;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.service.CommentService;
import com.example.KeyboardArenaProject.service.LikeService;
import com.example.KeyboardArenaProject.service.board.CommonBoardService;
import com.example.KeyboardArenaProject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
@RequiredArgsConstructor
public class LikeCommentController {

    private final LikeService likeService;
    private final CommentService commentService;
    private final CommonBoardService commonBoardService;
    private final UserService userService;
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
            commonBoardService.updateBoardLikes(likes, userBoardCompositeKey.getBoardId());
        }
    }

    //댓글
    @PostMapping("/comments/{board_id}")
    public String saveComment(@PathVariable String board_id,@RequestBody Map<String,String> content){
        Comment comment = new Comment(board_id,content.get("content"), userService.getCurrentUserInfo().getId(),userService.getCurrentUserInfo().getNickname());
        commentService.saveComment(comment);
        commonBoardService.plusCommentsCount(board_id);
        return "redirect:/board/"+board_id;
    }

    @DeleteMapping("/comments/{comment_id}")
    public void deleteComment(@PathVariable String comment_id){
        String boardId = commentService.findCommentById(comment_id).getBoardId();
        commentService.deleteComment(comment_id);
        commonBoardService.minusCommentsCount(boardId);
    }
}
