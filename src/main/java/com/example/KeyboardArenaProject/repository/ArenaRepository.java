package com.example.KeyboardArenaProject.repository;
import com.example.KeyboardArenaProject.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArenaRepository extends JpaRepository<Board, String> {

    List<Board> findAllByBoardType(int i);

    // top 3 liked normal arena
    @Query("Select b FROM Board b where b.boardType = 3 AND b.ifActive = true order by b.likes desc")
    List<Board> findArenasOrderByLikeDesc(Pageable pageable);
    @Query("SELECT count(b) FROM Board b WHERE b.boardType = 3")
    int findNumberOfArenas();

    Board findByBoardId(String boardId);

    // rest of the normal arena
    @Query("SELECT b FROM Board b where b.boardType = :type AND b.ifActive = true AND b.boardId NOT IN (:topThreeBoardIds) ORDER BY b.createdDate DESC")
    List<Board> findActiveArenasOrderByCreatedDateDesc(@Param("type") int type, @Param("topThreeBoardIds") List<String> topThreeBoardIds);

    void deleteByBoardId(String boardId);
}


