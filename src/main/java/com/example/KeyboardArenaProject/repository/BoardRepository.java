package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {

    List<Board> findAllByBoardType(int i);

    // top 3 liked normal arena
    @Query("Select b FROM Board b where b.boardType = 3 order by b.like desc")
    List<Board> findArenasOrderByLikeDesc(Pageable pageable);
    @Query("SELECT count(b) FROM Board b WHERE b.boardType = 3")
    int findNumberOfArenas();

    // rest of the normal arena
    List<Board> findByBoardTypeOrderByCreatedDateDesc(int type);
}