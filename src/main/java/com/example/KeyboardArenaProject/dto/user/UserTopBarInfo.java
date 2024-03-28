package com.example.KeyboardArenaProject.dto.user;


import com.example.KeyboardArenaProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTopBarInfo {
    String nickname;
    int rank;
    int point;
    public UserTopBarInfo(User user){
        this.nickname = user.getNickname();
        this.rank = user.getUserRank();
        this.point = user.getPoint();
    }
}
