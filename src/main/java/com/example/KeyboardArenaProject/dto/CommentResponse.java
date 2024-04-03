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
    String CommentId;
    String writerId;
    int writerRank;
    String content;
    LocalDateTime createdDate;

    public CommentResponse(String nickname, String content, LocalDateTime createdDate){
        this.nickname = nickname;
        this.content = content;
        this.createdDate = createdDate;
    }

    //
    public CommentResponse(Comment comment ){
        this.nickname = comment.getNickName();
        this.CommentId = comment.getCommentId();
        this.writerId = comment.getId();
        writerRank=0;
        this.content = comment.getContent();
        this. createdDate = comment.getCreatedDate();
    }
}
