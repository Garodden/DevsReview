package com.example.KeyboardArenaProject.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    String nickname;
    String content;
    LocalDateTime createdTime;

    CommentResponse(String nickname, String content, LocalDateTime createdTime){
        this.nickname = nickname;
        this.content = content;
        this.createdTime = createdTime;
    }
}
