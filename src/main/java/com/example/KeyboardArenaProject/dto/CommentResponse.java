package com.example.KeyboardArenaProject.dto;

import com.example.KeyboardArenaProject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    public CommentResponse(Comment comment ){
        this.nickname = comment.getNickName();
        this.content = comment.getContent();
        this. createdTime = comment.getCreatedDate();
    }
}
