package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
