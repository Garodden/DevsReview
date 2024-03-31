package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPageRepository extends JpaRepository<Board, String> {
    List<Board> findAllByIdOrderByCreatedDateDesc(String id);
    List<Board> findAllByBoardIdInOrderByCreatedDateDesc(List<String> boardIds);
}
