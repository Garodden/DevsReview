package com.example.KeyboardArenaProject.dto.user;

import com.example.KeyboardArenaProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageInformation {
    private String userId;
    private String nickname;
    private int userRank;
    private int point;
    private String email;

    public MyPageInformation(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.userRank = user.getUserRank();
        this.point = user.getPoint();
        this.email = user.getEmail();
    }
}
