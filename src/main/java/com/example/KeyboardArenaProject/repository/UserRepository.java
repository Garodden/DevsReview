package com.example.KeyboardArenaProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KeyboardArenaProject.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserId(String userId);
	Optional<User> findByEmail(String email);
}
