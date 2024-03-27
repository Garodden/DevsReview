package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.utils.GenerateIdUtils;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "like_board")
@Entity
public class Like {

    @EmbeddedId
    private UserBoardCompositeKey compositeId;

    @Column(name = "if_like", nullable = false)
    private boolean ifLike;

    @Builder Like(String id, String boardId, boolean ifLike){
        this.compositeId = new UserBoardCompositeKey(GenerateIdUtils.generateBoardId(LocalDateTime.now()), GenerateIdUtils.generateUserId(LocalDateTime.now()));
        this.ifLike = true;
    }

    public String getId(){
        return compositeId.getId();
    }

    public String getBoardId(){
        return compositeId.getId();
    }
    public boolean getIfLike(){
        return ifLike;
    }

}
