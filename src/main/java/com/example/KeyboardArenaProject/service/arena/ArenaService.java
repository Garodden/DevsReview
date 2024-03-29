package com.example.KeyboardArenaProject.service.arena;

import com.example.KeyboardArenaProject.entity.Board;
import com.example.KeyboardArenaProject.repository.ArenaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ArenaService {
    private ArenaRepository boardRepository;

    public ArenaService(ArenaRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<Board> findAllRankArena(){
        return boardRepository.findAllByBoardType(2);
    }

    public Board findByBoardId(String boardId){
        return boardRepository.findByBoardId( boardId);
    }

    public List<Board> findTop3ArenaOrderByLikes(){
        Pageable topThree = PageRequest.of(0, 3);
        return boardRepository.findArenasOrderByLikeDesc(topThree);

    }

    public List<Board> findNormalArenaOrderByCreatedDate(){
        return boardRepository.findByBoardTypeOrderByCreatedDateDesc(2);
    }
    @Transactional
    public void saveArena(Board Arena){
        boardRepository.save(Arena);
    }



}
