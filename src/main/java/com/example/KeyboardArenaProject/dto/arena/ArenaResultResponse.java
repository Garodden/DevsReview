package com.example.KeyboardArenaProject.dto.arena;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class ArenaResultResponse {
    String title;
    LocalTime time;
    Long participants;
    Long nthPlace;
    Double percentage;

    @Builder ArenaResultResponse(String title, Long participants, LocalTime time, Long nthPlace) {
        this.title = title;
        this.time = time;
        this.participants = participants;
        this.nthPlace = nthPlace;
        this.percentage = (double) nthPlace / participants;
    }

    public String resultPopupText(Boolean ifCleared){
        if(ifCleared) {
            return "축하합니다 !\n" +
                    title + " 아레나를" +
                    time.toString() + "에 클리어 하셨군요!\n" +
                    participants.toString() + "명 중 "+nthPlace.toString()+"+ 등으로\n" +
                    "상위 " + String.format("%.2f", percentage*100)+"%입니다!";
        }
        else{
            return title + " 아레나 클리어에 실패했습니다. 다시 시도해보세요!";
        }
    }
}
