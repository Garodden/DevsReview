package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Board;
//import com.example.KeyboardArenaProject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<Board,String> {

    List<Board> findAllByBoardTypeOrderByLikesDesc(Integer boardType);

    @Query(value = "UPDATE board SET title=:title, content=:content, board_rank=:boardRank, updated_date=:updatedDate where board_id=:boardId",nativeQuery = true)
    @Modifying
    @Transactional
    void updateBoard(String title, String content, Integer boardRank, String boardId, LocalDateTime updatedDate);


}
