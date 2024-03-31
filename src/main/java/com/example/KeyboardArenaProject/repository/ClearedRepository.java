package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearedRepository extends JpaRepository<Cleared, UserBoardCompositeKey> {
    List<Cleared> findAllByCompositeId_BoardId(String boardId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Cleared e WHERE e.compositeId = :id")
    boolean ifClearedById(UserBoardCompositeKey id);

    Cleared findByCompositeId(UserBoardCompositeKey curUsersClearRecord);
}
