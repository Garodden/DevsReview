package com.example.KeyboardArenaProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class BoardEntity {
    @Id
    private String board_id;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer board_type;
    // 1 - 자유게시판 / 2 - 주간 랭크전 아레나 / 3- 일반 아레나

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_date;

    @LastModifiedDate
    @Column
    private LocalDateTime updated_date;

    @Column(nullable = false)
    private Integer board_rank;

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
