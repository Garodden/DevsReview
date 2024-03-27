package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<User, String> {
}
