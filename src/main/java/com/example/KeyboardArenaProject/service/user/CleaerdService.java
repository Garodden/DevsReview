package com.example.KeyboardArenaProject.service.user;

import com.example.KeyboardArenaProject.repository.ClearedRepository;
import org.springframework.stereotype.Service;

@Service
public class CleaerdService {
    ClearedRepository clearedRepository;

    public CleaerdService(ClearedRepository clearedRepository) {
        this.clearedRepository = clearedRepository;
    }
    public int findParticipatesByBoardId(String boardId){
        return clearedRepository.findAllByCompositeId_BoardId(boardId).size();
    }

}
