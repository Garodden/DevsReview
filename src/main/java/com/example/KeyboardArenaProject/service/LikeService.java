package com.example.KeyboardArenaProject.service;

import com.example.KeyboardArenaProject.entity.Like;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.repository.FreeBoardRepository;
import com.example.KeyboardArenaProject.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }


    public Like findById(UserBoardCompositeKey compositeKey){
        return likeRepository.findById(compositeKey).orElse(null);
    }

    public int clickLikeOne(UserBoardCompositeKey compositeKey){
        likeRepository.clickLike(true, compositeKey.getBoardId(), compositeKey.getId());
        return likeRepository.countAllByBoardIdAndIfLikeIsTrue(compositeKey.getBoardId());
    }


    public int clickLikeTwo(UserBoardCompositeKey compositeKey){
        likeRepository.clickLike(false, compositeKey.getBoardId(), compositeKey.getId());
        return likeRepository.countAllByBoardIdAndIfLikeIsTrue(compositeKey.getBoardId());
    }


    public int save(UserBoardCompositeKey compositeKey){
        likeRepository.save(Like.builder()
                .boardId(compositeKey.getBoardId())
                .id(compositeKey.getId())
                .ifLike(true)
                .build());
        return likeRepository.countAllByBoardIdAndIfLikeIsTrue(compositeKey.getBoardId());
    }
}
