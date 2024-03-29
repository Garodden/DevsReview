package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Like, UserBoardCompositeKey> {
    @Query(value = "UPDATE like_board SET if_like=:bool WHERE board_id=:boardId and id=:id", nativeQuery = true)
    @Modifying
    @Transactional
    void clickLike(Boolean bool,String boardId,String id);

    @Query(value = "SELECT COUNT(*) FROM like_board WHERE board_id=:boardId and if_like=true", nativeQuery = true )
    int countAllByBoardIdAndIfLikeIsTrue(String boardId);


}
