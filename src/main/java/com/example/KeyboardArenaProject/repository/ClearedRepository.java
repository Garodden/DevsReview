package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClearedRepository extends JpaRepository<Cleared, UserBoardCompositeKey> {
}
