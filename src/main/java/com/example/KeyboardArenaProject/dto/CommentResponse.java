package com.example.KeyboardArenaProject.dto;

import com.example.KeyboardArenaProject.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {
    String nickname;
    String content;
    LocalDateTime createdTime;

    public CommentResponse(String nickname, String content, LocalDateTime createdTime){
        this.nickname = nickname;
        this.content = content;
        this.createdTime = createdTime;
    }

    //
    public CommentResponse(String nickname, Comment comment ){
        this.nickname = nickname;
        this.content = comment.getContent();
        this. createdTime = comment.getCreatedDate();
    }
}
