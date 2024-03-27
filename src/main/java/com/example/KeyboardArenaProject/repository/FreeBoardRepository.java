package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Board;
//import com.example.KeyboardArenaProject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<Board,String> {
    List<Board> findAllByBoardTypeOrderByLikeDesc(Integer boardType);
}
