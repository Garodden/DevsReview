package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.utils.GenerateIdUtils;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;


import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "like_board")
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
