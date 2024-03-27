package com.example.KeyboardArenaProject.dto.freeBoard;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FreeBoardResponse {

    private String id;
    private String title;
    private String content;
    private Integer board_type;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private Integer board_rank;

}
