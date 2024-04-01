package com.example.KeyboardArenaProject.entity.compositeKey;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserBoardCompositeKey implements Serializable {
    private String id;
    private String boardId;

    @Builder UserBoardCompositeKey(String id, String boardId){
        this.id =id;
        this.boardId = boardId;
    }
}
