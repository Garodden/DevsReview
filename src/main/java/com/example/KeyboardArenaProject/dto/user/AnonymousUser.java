package com.example.KeyboardArenaProject.dto.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnonymousUser {

    private String id;
    private String userId;
    private String password;
    private String nickname;
    private int userRank;
    private int point;

    public AnonymousUser(){
        this.id="";
        this.userId="";
        this.password="1";
        this.nickname="anonymousUser";
        this.userRank=0;
        this.point=0;
    }
}
