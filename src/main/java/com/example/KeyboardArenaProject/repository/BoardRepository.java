package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    List<BoardEntity> findAllByBoardType(int i);

    // top 3 liked normal arena
    @Query("Select b FROM BoardEntity b where b.boardType = 3 order by b.like desc")
    List<BoardEntity> findTop3ArenaByOrderByLikeDesc();

    // rest of the normal arena
    @Query("Select a from BoardEntity a where a not in (Select b FROM BoardEntity b where b.boardType = 3 order by b.like desc limit 3) order by a.createdDate desc")
    List<BoardEntity> findRestArenaOrderByCreatedDateDesc();
}