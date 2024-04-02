package com.example.KeyboardArenaProject.dto.arena;


import com.example.KeyboardArenaProject.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArenaReceiveForm {
    private String title;
    private String content;
    private int boardRank;
}