package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.utils.GenerateIdUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "like_board")
@Entity
public class Like {

    @Column(name = "board_id", nullable = false)
    @Id
    private String boardId;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "if_like", nullable = false)
    private boolean ifLike;

    @Builder Like(String boardId, String id, boolean ifLike){
        this.boardId=GenerateIdUtils.generateBoardId(LocalDateTime.now());
        this.id = GenerateIdUtils.generateUserId(LocalDateTime.now());
        this.ifLike = true;
    }

}
