package com.example.KeyboardArenaProject.dto.arena;

import com.example.KeyboardArenaProject.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ArenaResponse {
    String title;
    int boardRank;
    LocalDateTime createdDate;
    int participates;
    int comments;
    int likes;
    int boardType;
    public ArenaResponse(Board arenaEntity){
        title = arenaEntity.getTitle();
        boardRank = arenaEntity.getBoardRank();
        createdDate = arenaEntity.getCreatedDate();
        participates=0;
        comments = arenaEntity.getComment();
        likes = arenaEntity.getLike();
        boardType =arenaEntity.getBoardType();
    }
}
