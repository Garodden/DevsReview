package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.utils.GenerateIdUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name= "comment_board")
@DynamicInsert
@NoArgsConstructor
public class Comment {

    @Id
    @Column(name = "comment_id", nullable = false)
    private String commentId;

    @Column(name = "board_id", nullable = false)
    private String boardId;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private String updatedDate;

    @Builder Comment(String commentId,String boardId,String id,String content,LocalDateTime createdDate,LocalDateTime updatedDate){
        this.commentId = GenerateIdUtils.generateCommentId(LocalDateTime.now());
        this.boardId = id;
        this.content = content;
        this.createdDate =updatedDate;
        this.updatedDate = null;
    }

    public Comment(String boardId, String content, String id, String nickname){
        this.commentId = GenerateIdUtils.generateCommentId(LocalDateTime.now());
        this.boardId=boardId;
        this.content=content;
        this.id=id;
        this.nickName=nickname;
        this.createdDate=LocalDateTime.now();
    }

}
