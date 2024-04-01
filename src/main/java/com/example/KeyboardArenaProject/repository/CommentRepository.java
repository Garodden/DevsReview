package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByBoardIdOrderByCreatedDateDesc(String BoardId);

    List<Comment> findAllByIdOrderByCreatedDateDesc(String id);
}
