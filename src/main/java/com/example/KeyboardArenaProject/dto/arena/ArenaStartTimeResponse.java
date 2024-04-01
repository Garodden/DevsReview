package com.example.KeyboardArenaProject.dto.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArenaStartTimeResponse {
    LocalDateTime startTime;

}
