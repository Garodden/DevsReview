package com.example.KeyboardArenaProject.entity.compositeKey;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserBoardCompositeKey implements Serializable {
    private String id;
    private String boardId;
}
