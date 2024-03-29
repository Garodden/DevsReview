package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPageRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
}
