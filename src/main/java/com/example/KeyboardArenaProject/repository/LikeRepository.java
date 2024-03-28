package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, UserBoardCompositeKey> {

}
