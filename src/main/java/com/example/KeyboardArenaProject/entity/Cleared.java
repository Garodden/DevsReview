package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;


import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "user_cleared_board")
public class Cleared {

    @EmbeddedId
    private UserBoardCompositeKey compositeId;


    @Column(name = "clear_time")
    private LocalTime clearTime;

    @Column(name = "tries", nullable = false)
    private int tries;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Builder
    Cleared(String id, String boardId, LocalTime clearTime, int tries, LocalDateTime startTime){
        this.compositeId = new UserBoardCompositeKey(id, boardId);
        this.clearTime = clearTime;
        this.tries = tries;
        this.startTime = startTime;
    }

    public String getId(){
        return compositeId.getId();
    }

    public String getBoardId(){
        return compositeId.getId();
    }

    public void setId(String id){
        compositeId.setId(id);
    }

    public void setBoardId(String boardId){
        compositeId.setBoardId(boardId);
    }

}
