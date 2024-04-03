package com.example.KeyboardArenaProject.dto.freeBoard;

import com.example.KeyboardArenaProject.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreeBoardWriteRequest {

    private String id;
    private String title;
    private String content;
    private Integer board_rank;




    public Board toEntity(){
        return Board.builder()
                .id(id).title(title).content(content)
                .board_type(1).board_rank(board_rank).active(true).build();
    }



}
