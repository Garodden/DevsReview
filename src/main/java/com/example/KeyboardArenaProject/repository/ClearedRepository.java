package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClearedRepository extends JpaRepository<Cleared, UserBoardCompositeKey> {
    List<Cleared> findAllByCompositeId_BoardId(String boardId);

    //클리어 데이터가 있는지
    @Query("SELECT CASE WHEN e.clearTime is NULL then false ELSE true END FROM Cleared  e where e.compositeId =:id")
    Boolean ifClearedById(UserBoardCompositeKey id);

    //유저 데이터가 있는지

    Long countByCompositeId_BoardId(String boardId);


    List<Cleared> findAllByCompositeId_BoardIdOrderByClearTimeAsc(String boardId);

    Cleared findByCompositeId(UserBoardCompositeKey curUsersClearRecord);

    List<Cleared> findAllByCompositeId_idOrderByStartTimeDesc(String id);
}