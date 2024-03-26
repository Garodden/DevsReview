package com.example.KeyboardArenaProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class BoardEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    @Id
    private String boardId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "board_type", nullable = false)
    private Integer boardType;
    // 1 - 자유게시판 / 2 - 주간 랭크전 아레나 / 3- 일반 아레나

    @CreatedDate
    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "board_rank", nullable = false)
    private Integer boardRank;

    @Column(nullable = false)
    private Integer like;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Integer view;
    //조회수

    @Column(nullable = false)
    private Integer comment;
    //댓글 수
}
