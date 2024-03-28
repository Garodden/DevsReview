package com.example.KeyboardArenaProject.dto.freeBoard;

import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.service.freeBoard.FreeBoardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class FreeBoardWriteRequest {

    private String id;
    private String title;
    private String content;
    private Integer board_rank;




    public Board toEntity(){
        return Board.builder().board_id("board_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd"))+"_"
                        +LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
                .id(id).title(title).content(content)
                .board_type(1).board_rank(board_rank).active(true).build();
    }



}
