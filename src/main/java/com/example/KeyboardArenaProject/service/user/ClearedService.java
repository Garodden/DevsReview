package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.entity.Cleared;
import com.example.KeyboardArenaProject.entity.compositeKey.UserBoardCompositeKey;
import com.example.KeyboardArenaProject.repository.ClearedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClearedService {
    ClearedRepository clearedRepository;

    public ClearedService(ClearedRepository clearedRepository) {
        this.clearedRepository = clearedRepository;
    }
    public int findParticipatesByBoardId(String boardId){
        return clearedRepository.findAllByCompositeId_BoardId(boardId).size();
    }
    //클리어한 유저 데이터가 존재하는지
    public boolean findIfUserClearDataExists(UserBoardCompositeKey id){
        return clearedRepository.ifClearedById(id);
    }
    //챌린지 시작 시간 기록
    public void saveClearStartTime(Cleared cleared){
        clearedRepository.save(cleared);
    }

    @Transactional
    public void update(UserBoardCompositeKey curUsersClearRecord) {
        Cleared clearedData = clearedRepository.findByCompositeId(curUsersClearRecord);
        clearedData.updateStartTime();
    }
}
