package com.example.KeyboardArenaProject.service.arena;

import com.example.KeyboardArenaProject.entity.BoardEntity;
import com.example.KeyboardArenaProject.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArenaService {
    private BoardRepository boardRepository;

    public ArenaService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<BoardEntity> findAllRankArena(){
        return boardRepository.findAllByBoardType(2);
    }

}
