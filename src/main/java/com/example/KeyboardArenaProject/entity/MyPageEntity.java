package com.example.KeyboardArenaProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class MyPageEntity {
    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "rank", nullable = false)
    private int rank;

    @Column(name = "point", nullable = false)
    private int point;

    @Column(name = "email", nullable = false)
    private String email;


    @Builder
    public MyPageEntity(String id, String userId, int rank, int point, String email) {
        this.id = id;
        this.userId = userId;
        this.rank = rank;
        this.point = point;
        this.email = email;
    }
}
