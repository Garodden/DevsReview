package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    List<BoardEntity> findAllByBoardType(int i);
}