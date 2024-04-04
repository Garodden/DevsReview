package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByBoardIdOrderByCreatedDateDesc(String BoardId);

    List<Comment> findAllByIdOrderByCreatedDateDesc(String id);

    // 내가 작성한 게시글의 boardId, 나의 id로 작성일자 기준 내림차순으로 댓글 정렬
    @Query("SELECT c FROM Comment c where c.boardId = :boardId AND c.id = :id ORDER BY c.createdDate DESC")
    List<Comment> findAllByBoardIdAndIdByCreatedDateDesc(@Param("boardId") String boardId, @Param("id") String id);
}
