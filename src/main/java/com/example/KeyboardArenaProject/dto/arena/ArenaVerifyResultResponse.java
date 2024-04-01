package com.example.KeyboardArenaProject.dto.arena;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ArenaVerifyResultResponse {
    String title;
    Long clearTimeBySeconds;


    @Builder
    ArenaVerifyResultResponse(String title, Long clearTimeBySeconds) {
        this.title = title;
        this.clearTimeBySeconds = clearTimeBySeconds;
    }

    public String resultPopupText(Boolean ifCleared){
        if(ifCleared) {
            return "축하합니다 !\n" +
                    title + " 아레나를" +
                    clearTimeBySeconds.toString() + "초에 클리어 하셨군요!\n" +
                    "아레나 게시판에 등록을 성공하였습니다!";
        }
        else{
            return title + " 아레나를 120초 내에 클리어하지 못했습니다. 다시 시도해보세요!";
        }
    }
}
