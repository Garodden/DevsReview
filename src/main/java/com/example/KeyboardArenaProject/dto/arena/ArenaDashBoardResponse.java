package com.example.KeyboardArenaProject.dto.arena;

import com.example.KeyboardArenaProject.dto.user.UserTopBarInfo;

import java.util.List;

public class ArenaDashBoardResponse {
    UserTopBarInfo userTopBarInfo;
    List<ArenaResponse> arena;

    public ArenaDashBoardResponse(UserTopBarInfo userTopBarInfo, List<ArenaResponse> arena){
        this.userTopBarInfo = userTopBarInfo;
        this.arena = arena;
    }

}
