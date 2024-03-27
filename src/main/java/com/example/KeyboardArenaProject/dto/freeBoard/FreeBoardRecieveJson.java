package com.example.KeyboardArenaProject.dto.freeBoard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreeBoardRecieveJson {
    private String title;
    private String content;
    private Integer boardRank;
}
