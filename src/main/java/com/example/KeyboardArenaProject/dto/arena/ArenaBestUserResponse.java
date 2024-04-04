package com.example.KeyboardArenaProject.dto.arena;
import lombok.*;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Setter
public class ArenaBestUserResponse {
    String nickname;
    int userRank;
    LocalTime clearTime;
    int clearRank;
    @Builder ArenaBestUserResponse(String nickname, int userRank, LocalTime clearTime, int clearRank){
        this.nickname = nickname;
        this.userRank = userRank;
        this.clearTime = clearTime;
        this.clearRank = clearRank;
    }
}
