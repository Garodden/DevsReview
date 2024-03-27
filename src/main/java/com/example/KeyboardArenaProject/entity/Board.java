package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.dto.freeBoard.FreeBoardResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "board")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Board {

    @Column(name = "board_Id")
    @Id
    private String boardId;

    @Column(name="id",nullable = false)
    private String id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "board_type",nullable = false)
    private Integer boardType;
    // 1 - 자유게시판 / 2 - 주간 랭크전 아레나 / 3- 일반 아레나

    @CreatedDate
    @Column(name="created_date",nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name="updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "board_rank",nullable = false)
    private Integer boardRank;

    @Column(name="\"like\"") //default 0
    private Integer like;

    @Column(name="\"active\"")  //default false
    private Boolean active;

    @Column(name="\"view\"")   //default 0
    private Integer view;
    //조회수

    @Column(name="\"comment\"")   //default 0
    private Integer comment;
    //댓글 수

    @Builder
    public Board(String board_id, String id, String title, String content, Integer board_type,
                 Integer board_rank, Boolean active){
        this.board_id=board_id;
        this.id=id;
        this.title=title;
        this.content=content;
        this. boardType=board_type;
        this.boardRank=board_rank;
        this.active=active;
    }

    public FreeBoardResponse toResponse(){
        return new FreeBoardResponse(id,title,content,boardType,createdDate,updatedDate,boardRank);
    }


}
