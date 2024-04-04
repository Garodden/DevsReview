package com.example.KeyboardArenaProject.repository;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KeyboardArenaProject.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findOptionalUserById(String id);
	Optional<User> findByUserId(String userId);
	Optional<User> findByEmail(String email);
	boolean existsByUserId(String userId);
	boolean existsByEmail(String email);
	Optional<User> findByUserIdAndFindPwQuestionAndFindPw(String userId, String findPwQuestion, String findPw);

}
