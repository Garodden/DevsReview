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

	//포인트 증가
	@Query(value = "UPDATE user SET point = point+ :points  where id=:id ",nativeQuery = true)
	@Modifying
	@Transactional
	void updatePoints(String id, int points);

	Optional<User> findByUserIdAndFindPwQuestionAndFindPw(String userId, String findPwQuestion, String findPw);

}
