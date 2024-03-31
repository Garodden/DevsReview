package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.cglib.core.Local;


import java.time.Duration;
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
    Cleared(String id, String boardId, int tries, LocalDateTime startTime){
        this.compositeId = UserBoardCompositeKey.builder().id(id).boardId(boardId).build();
        this.clearTime = null;
        this.tries = tries;
        this.startTime = startTime;
    }

    public void updateStartTime(){
        startTime = LocalDateTime.now();
    }

    public LocalTime updateClearTime(){
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        long seconds = duration.getSeconds();
        this.clearTime = LocalTime.ofSecondOfDay(seconds % (24 * 3600));
        this.tries+=1;
        return clearTime;
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
