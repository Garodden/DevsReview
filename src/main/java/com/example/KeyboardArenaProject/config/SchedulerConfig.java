package com.example.KeyboardArenaProject.config;


import com.example.KeyboardArenaProject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {
    private final UserService userService;

    //10분마다 실행
    //주간 랭크전을 전부 플레이한 사람들에 한에서 랭크를 매김
    @Scheduled(fixedDelay = 600000)
    public void setUsersRank(){
        userService.updateRank();
    }

}
