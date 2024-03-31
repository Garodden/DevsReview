package com.example.KeyboardArenaProject.entity;

import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;


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
        this.compositeId = UserBoardCompositeKey.builder().id(id).boardId(boardId).build();
        this.ifLike = ifLike;
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
