package com.example.KeyboardArenaProject.repository;

import com.example.KeyboardArenaProject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {

}
