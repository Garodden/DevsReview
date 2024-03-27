package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<UserEntity, String> {
}
